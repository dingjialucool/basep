package com.chinobot.aiuas.bot_prospect.flight.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import com.chinobot.common.utils.RouteFileUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chinobot.aiuas.bot_collect.info.entity.FlightTask;
import com.chinobot.aiuas.bot_collect.info.service.IFlightTaskService;
import com.chinobot.aiuas.bot_prospect.flight.constant.FlightConstant;
import com.chinobot.aiuas.bot_prospect.flight.entity.Flight;
import com.chinobot.aiuas.bot_prospect.flight.entity.FlightWork;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.FlightReferenceVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.UavWorkVo;
import com.chinobot.aiuas.bot_prospect.flight.mapper.FlightMapper;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightService;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightWorkService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.file.util.FileClient;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.GPSUtil;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.monitor.entity.UavOnline;
import com.chinobot.plep.home.monitor.service.IUavOnlineService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <p>
 * 航班 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2020-02-21
 */
@Service
public class FlightServiceImpl extends BaseService<FlightMapper, Flight> implements IFlightService {
    @Autowired
    private FileClient fileClient;
    @Autowired
    private IUploadFileService iUploadFileService;
    @Autowired
    private IFileBusService iFileBusService;
    @Autowired
    private IFlightWorkService flightWorkService;
    @Autowired
    private IUavOnlineService uavOnlineService;
    @Autowired
    private FlightMapper flightMapper;
    @Autowired
    private IFlightTaskService flightTaskService;

    @Override
    public Result edit(FlightReferenceVo flightReferenceVo) throws IOException {
        Flight flight = flightReferenceVo.getFlight();
        if(CommonUtils.isObjEmpty(flight.getIsDeleted())
                || !flight.getIsDeleted().equals(GlobalConstant.IS_DELETED_YES)){
            UploadFile uploadFile = iUploadFileService.getById(flight.getRouteFileUuid());
            byte[] bytes = fileClient.downloadFile(uploadFile.getPath());

            if(bytes.length > 0){
                File file = new File("/home/temp/routeFile.txt");
                FileUtils.writeByteArrayToFile(file, bytes);

                try {
                    String originName = uploadFile.getOriginName();
                    switch (originName.substring(originName.indexOf(".") + 1)){
                        case FlightConstant.ROUTE_FILE_TYPE_MMC:
                            flight.setRouteLnglats(RouteFileUtils.getLnglatsByMMC(file))
                                    .setRouteFileType("2");
                            break;
                        case FlightConstant.ROUTE_FILE_TYPE_SMD:
                            flight.setRouteLnglats(RouteFileUtils.getLnglatsBySMD(file))
                                    .setRouteFileType("2");
                            break;
                        case FlightConstant.ROUTE_FILE_TYPE_DJI:
                            flight.setRouteLnglats(RouteFileUtils.getLnglatsByDJI(file))
                                    .setRouteFileType("1");
                            break;
                        default:
                            break;
                    }
                } catch (Exception e) {
                    return ResultFactory.error("没有符合规范的航线数据！");
                }
                if(CommonUtils.isObjEmpty(flight.getRouteFileUuid())){
                    return ResultFactory.error("没有符合规范的航线数据！");
                }
                saveOrUpdate(flight);
                if(CommonUtils.objNotEmpty(flightReferenceVo.getReferenceFileId())){
                    FileBus fileBus = new FileBus();
                    fileBus.setFileId(flightReferenceVo.getReferenceFileId())
                            .setBusId(flight.getUuid())
                            .setModule(FlightConstant.FLIGHT_REFERENCE)
                            .setSort(0);
                    iFileBusService.save(fileBus);
                }
            }
        }else{
            iFileBusService.update(new LambdaUpdateWrapper<FileBus>()
                    .eq(FileBus::getBusId, flight.getUuid())
                    .eq(FileBus::getModule, FlightConstant.FLIGHT_REFERENCE)
                    .set(FileBus::getDataStatus, GlobalConstant.DATA_STATUS_INVALID));
            //将作业置为无效
            flightWorkService.update(new LambdaUpdateWrapper<FlightWork>()
            		.eq(FlightWork::getFlightUuid, flight.getUuid())
            		.set(FlightWork::getIsDeleted, true));
            //将航班采查任务置为无效
            flightTaskService.update(new LambdaUpdateWrapper<FlightTask>()
            		.eq(FlightTask::getFlightUuid, flight.getUuid())
            		.set(FlightTask::getIsDeleted, true));
            saveOrUpdate(flight);
        }

        return ResultFactory.success();
    }

	@Override
	public UavWorkVo getUavWorkInfo(String uavId) {
		UavWorkVo vo = new UavWorkVo();
		vo.setUavCode(uavId);
		vo.setIsWork("0");
		QueryWrapper<UavOnline> queryWrapper = new QueryWrapper<UavOnline>();
		queryWrapper.eq("uav_code", uavId);
		UavOnline uavOnline = uavOnlineService.getOne(queryWrapper);
		if(uavOnline != null && "1".equals(uavOnline.getRunStatus()) && uavOnline.getWorkStartTime() != null 
				&& StringUtils.isNotBlank(uavOnline.getWorkId()) && StringUtils.isNotBlank(uavOnline.getWorkType())) {
			vo.setIsWork("1");
			vo.setWorkDetail(flightMapper.getUavWorkDetail(uavOnline));
		}
		return vo;
	}

}
