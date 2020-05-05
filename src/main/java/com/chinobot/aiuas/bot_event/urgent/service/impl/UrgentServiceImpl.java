package com.chinobot.aiuas.bot_event.urgent.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_event.urgent.constant.UrgentConstant;
import com.chinobot.aiuas.bot_event.urgent.entity.Urgent;
import com.chinobot.aiuas.bot_event.urgent.entity.dto.HistoryUrgentDTO;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.HistoryUrgentVo;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.PersonListVo;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.UrgentWorkStatusVo;
import com.chinobot.aiuas.bot_event.urgent.mapper.UrgentMapper;
import com.chinobot.aiuas.bot_event.urgent.service.IUrgentService;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.WorkFilesVo;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightWorkService;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.file.util.FileClient;
import com.chinobot.common.message.entity.Message;
import com.chinobot.common.message.entity.dto.MessageDto;
import com.chinobot.common.message.utils.MessageUtils;
import com.chinobot.common.utils.*;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.meeting.service.IMeetingService;
import freemarker.template.TemplateException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 紧急调度表 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2020-03-19
 */
@Service
public class UrgentServiceImpl extends BaseService<UrgentMapper, Urgent> implements IUrgentService {
    @Autowired
    private UrgentMapper urgentMapper;
    @Autowired
    private IUploadFileService iUploadFileService;
    @Autowired
    private IFlightWorkService iFlightWorkService;
    @Autowired
    private IPersonService iPersonService;
    @Autowired
    private IMeetingService iMeetingService;
    @Autowired
    private IFileBusService iFileBusService;
    @Autowired
    private FileClient fileClient;
    @Value("${config.tempPath}")
    private String tempPath;
    @Value("${config.templateId}")
    private String templateFlyTask;
    private final String pageUrl = "/pages/webrtc-room/index/index";

    @Override
    public String add(Urgent urgent) throws Exception {
        String[] startLnglat = urgent.getPersonLnglat().split(",");
        double[] startLatlng84 = GPSUtil.gcj02_To_Gps84(Double.parseDouble(startLnglat[1]), Double.parseDouble(startLnglat[0]));
        String[] endLnglat = urgent.getTargetLnglat().split(",");
        double[] endLatlng84 = GPSUtil.gcj02_To_Gps84(Double.parseDouble(endLnglat[1]), Double.parseDouble(endLnglat[0]));
        List<double[]> latlngs = new ArrayList();
        latlngs.add(startLatlng84);
        latlngs.add(endLatlng84);
        Float flyHeight = urgent.getFlyHeight();
        Float flySpeed = urgent.getFlySpeed();
        JSONObject mmcRoute = RouteFileUtils.createMMCRoute(latlngs, flyHeight, flySpeed);

        // 创建文件
        File mmcFile = FileUtil.createJsonFile(mmcRoute.toString(), tempPath, "mmc_" + System.currentTimeMillis() + ".mission");
        // 上传文件
        UploadFile uploadFile = iUploadFileService.save(mmcFile);
        // 删除原文件
        mmcFile.delete();

        urgent.setRouteFileId(uploadFile.getUuid())
                .setWorkStatus(UrgentConstant.URGENT_WORK_STATUS_ORDER);
        save(urgent);

        sendMessage(urgent);
        iFlightWorkService.transmitUrgent(urgent.getUuid());

        return urgent.getUuid();
    }

    @Override
    public IPage<Map> getPage(Page page) {
        return urgentMapper.getPage(page);
    }

    @Override
    public Map getInfo(String urgentId) {
        Map info = urgentMapper.getInfo(urgentId);

        Map flyData = new HashMap();
        Map flyDataParam = new HashMap();
        flyDataParam.put("busId", urgentId);
        flyDataParam.put("module", com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant.LOG_WORK);
        flyData.put("log", iFileBusService.getFileIdByBusId(flyDataParam));
        flyDataParam.put("module", com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant.STREAN_VEDIO);
        flyData.put("video", iFileBusService.getFileIdByBusId(flyDataParam));
        flyDataParam.put("module", com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant.OBJECT_MEDIA);
        flyData.put("objMedia", iFileBusService.getFileIdByBusId(flyDataParam));
        info.put("flyData", flyData);

        return info;
    }

    @Override
    public void editWorkStatus(UrgentWorkStatusVo urgentWorkStatusVo) throws Exception {
        String type = urgentWorkStatusVo.getType();
        String urgentId = urgentWorkStatusVo.getUrgentId();
        WorkFilesVo workFilesVo = urgentWorkStatusVo.getWorkFilesVo();
        switch (type){
            case "endFlight":
                updateById(new Urgent().setUuid(urgentId)
                        .setWorkStatus(UrgentConstant.URGENT_WORK_STATUS_TODO));
                break;
            case "reRelease":
                iFlightWorkService.transmitUrgent(urgentId);
                break;
            case "complete":
                workFilesVo.setFlightWorkId(urgentId);
                iFlightWorkService.doSaveFiles(workFilesVo);
                updateById(new Urgent().setUuid(urgentId)
                        .setWorkStatus(UrgentConstant.URGENT_WORK_STATUS_COMPLETED));
                break;
        }
    }

    @Override
    public List<Map> getAppUrgentList(Map<String, String> param) {
        List<Map> appUrgentList = urgentMapper.getAppUrgentList(param);

        appUrgentList.stream().forEach(map -> {
            if(CommonUtils.objNotEmpty(map.get("routeFileUuid"))) {
                UploadFile uploadFile = iUploadFileService.getById((String) map.get("routeFileUuid"));
                byte[] bytes = fileClient.downloadFile(uploadFile.getPath());
                if (bytes.length > 0) {
                    File file = new File("/home/temp/routeFile.txt");
                    String str = null;
                    try {
                        FileUtils.writeByteArrayToFile(file, bytes);
                        str = FileUtils.readFileToString(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JSONObject jsonObject = JSONObject.fromObject(str);

                    int mode = jsonObject.getInt("mode");
                    switch (mode){
                        case 1:
                        case 2:
                            JSONObject flyConfig = jsonObject.getJSONObject("flyConfig");
                            map.put("fly_height", flyConfig.getString("flyHeight"));
                            map.put("fly_speed", flyConfig.getString("flySpeed"));
                            break;
                        case 3:
                            JSONArray path = jsonObject.getJSONArray("path");
                            if(path.size() > 0){
                                map.put("fly_height", ((JSONObject)path.get(0)).getString("flyHeight"));
                                map.put("fly_speed", ((JSONObject)path.get(0)).getString("flySpeed"));
                            }
                            break;
                    }
                }
            }
        });
        return appUrgentList;
    }

    @Override
    public Map getRouteByUrgent(String urgentId) {
        return urgentMapper.getRouteByUrgent(urgentId);
    }

    private void sendMessage(Urgent urgent) throws IOException, TemplateException {
        //发送微信通知
        Person person = iPersonService.getById(urgent.getPersonId());
        //发消息
        MessageDto msgDto = new MessageDto();
        msgDto.setSendPid(ThreadLocalUtil.getResources().getUuid());
        msgDto.setCode("sky-monitor");
        msgDto.setReceivePid(urgent.getPersonId());
        Map<String, Object> customParam = new HashMap<String, Object>();//模板参数
        customParam.put("center", urgent.getTargetLnglat());
        customParam.put("address", urgent.getTargetAddress());
        Message message = MessageUtils.send(msgDto, customParam);
        String msgAddress = "(" + urgent.getTargetLnglat() + ")" + urgent.getTargetAddress();
        String page = pageUrl + "?type=2&msgId=" + message.getUuid() + "&msgAddress=" + msgAddress;
        com.alibaba.fastjson.JSONObject data = new com.alibaba.fastjson.JSONObject();
        setTemplateValueObject(data, "thing5", "空中监控");//名称
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        setTemplateValueObject(data, "date2", LocalDateTime.now().format(dtf));//发送时间
        setTemplateValueObject(data, "name1", CommonUtils.doLongString(ThreadLocalUtil.getResources().getPname(), 10));//发送人
        setTemplateValueObject(data, "thing6", CommonUtils.doLongString(msgAddress, 20));//地址
        setTemplateValueObject(data, "thing3", CommonUtils.doLongString(message.getContent(), 20));//内容
        if(StringUtils.isNotBlank(person.getOpenId())) {
            iMeetingService.seendWxMsg(templateFlyTask, person.getOpenId(), page, data);
        }
    }

    private void setTemplateValueObject(com.alibaba.fastjson.JSONObject obj, String key, Object Val) {
        com.alibaba.fastjson.JSONObject objSon = new com.alibaba.fastjson.JSONObject();
        objSon.put("value", Val);
        obj.put(key, objSon);
    }

	@Override
	public IPage<HistoryUrgentVo> getHistoryUrgentList(HistoryUrgentDTO dto) {
		
		Page page  = new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		
		return urgentMapper.getHistoryUrgentList(page,dto);
	}

	@Override
	public List<PersonListVo> getPersons(String type) {
		if("1".equals(type)) {
			return urgentMapper.getPersons();
		}
		
		return urgentMapper.getDoPersons();
	}

}
