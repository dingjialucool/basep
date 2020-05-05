package com.chinobot.framework.web.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.chinobot.aiuas.bot_resource.uav.entity.Uav;
import com.chinobot.aiuas.bot_resource.uav.service.IReUavService;
import com.chinobot.framework.web.service.IRegionsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chinobot.cityle.base.entity.AddressBase;
import com.chinobot.cityle.base.service.IAddressBaseService;
import com.chinobot.cityle.base.service.IUavService;
import com.chinobot.cityle.ddjk.entity.RobotFlight;
import com.chinobot.cityle.ddjk.entity.RobotGatherData;
import com.chinobot.cityle.ddjk.entity.RobotTrail;
import com.chinobot.cityle.ddjk.entity.TaskXc;
import com.chinobot.cityle.ddjk.service.IRobotFlightService;
import com.chinobot.cityle.ddjk.service.IRobotGatherDataService;
import com.chinobot.cityle.ddjk.service.IRobotTrailService;
import com.chinobot.cityle.ddjk.service.ITaskXcService;
import com.chinobot.cityle.warning.entity.EarlyWarningRecord;
import com.chinobot.cityle.warning.service.IEarlyWarningRecordService;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.utils.AmapUtils;
import com.chinobot.framework.web.constant.KafkaConstant;
import com.chinobot.framework.web.service.IKafkaDataService;

/**
 * Created by huangw on 2019/4/15.
 */
@Service
public class KafkaDataServiceImpl implements IKafkaDataService {
	
	private static final Logger log = LoggerFactory.getLogger(KafkaDataServiceImpl.class);
	
    @Autowired
    private IRobotTrailService robotTrailService;
    @Autowired
    private IRobotFlightService robotFlightService;
    @Autowired
    private IRobotGatherDataService robotGatherDataService;
    @Autowired
    private ITaskXcService taskXcService;
    @Autowired
    private IUploadFileService uploadFileService;
    @Autowired
    private IAddressBaseService addressBaseService;
    @Autowired
    private IEarlyWarningRecordService earlyWarningRecordService;
    @Autowired
    private IRegionsService regionsService;
    @Autowired
    private IUavService uavService;
    @Autowired
    private IReUavService iReUavService;

    @Override
    public String updateDataBase(String key, JSONObject jsonMsg){
        if(KafkaConstant.PIPE_TRAIL.equals(key)){
            return DoTrail(jsonMsg);
        }
        if(KafkaConstant.PIPE_FLIGHT.equals(key)){
            return DoFlight(jsonMsg);
        }
        if(KafkaConstant.PIPE_WARNING.equals(key)){
            return DoWarning(jsonMsg);
        }
        if(KafkaConstant.PIPE_GATHER_DATA.equals(key)){
            return DoGatherData(jsonMsg);
        }
        if(KafkaConstant.PIPE_ROBOT_STATUS.equals(key)){
            return DoUavStatus(jsonMsg);
        }
        if(KafkaConstant.PIPE_TASK_STATUS.equals(key)){
            return DoTaskStatus(jsonMsg);
        }
        if(KafkaConstant.PIPE_TASK_FILE.equals(key)) {
        	return DoTaskFile(jsonMsg);
        }
        
        return null;
    }

	/**
     * 更新轨迹数据
     * @param jsonMsg
     * @return
     */
    private String DoTrail(JSONObject jsonMsg){
//        RobotTrail trail = (RobotTrail) JSONObject.toBean(jsonMsg, RobotTrail.class);
        RobotTrail trail = JSON.parseObject(jsonMsg.toJSONString(), new TypeReference<RobotTrail>() {});
        robotTrailService.save(trail);
        return trail.getUuid();
    }

    /**
     * 更新飞行数据
     * @param jsonMsg
     * @return
     */
    private String DoFlight(JSONObject jsonMsg){
        RobotFlight flight = JSON.parseObject(jsonMsg.toJSONString(), new TypeReference<RobotFlight>() {});
        robotFlightService.save(flight);
        return flight.getUuid();
   }

    /**
     * 更新预警数据
     * @param jsonMsg
     * @return
     */
    private String DoWarning(JSONObject jsonMsg){

    	return DoGatherData(jsonMsg);
    }
    /**
     * 更新采集数据
     * @param jsonMsg
     * @return
     */
    private String DoGatherData(JSONObject jsonMsg){
        RobotGatherData data = JSON.parseObject(jsonMsg.toJSONString(), new TypeReference<RobotGatherData>() {});
        robotGatherDataService.save(data);
        // 保存预警
        saveEarlyWarningRecord(data);

        return data.getUuid();
    }

    /**
     * 更新设备状态
     * @param jsonMsg
     * @return
     */
    private String DoUavStatus(JSONObject jsonMsg){
        String uuid = jsonMsg.getString("uuid");
        Uav uav = iReUavService.getById(uuid);
        if( uav != null) {
        	 uav.setRunStatus(jsonMsg.getString("runStatus"));
             iReUavService.updateById(uav);
        	 return uav.getUuid();
        }
       return null;
    }

    /**
     * 更新任务状态
     * @param jsonMsg
     * @return
     */
    private String DoTaskStatus(JSONObject jsonMsg){
        String uuid = jsonMsg.getString("uuid");
        TaskXc taskXc = taskXcService.getById(uuid);
        if( taskXc != null) {
        	taskXc.setTaskStatus(jsonMsg.getString("taskStatus"));
        	if(jsonMsg.getString("taskStatus").equals("4")) {
        		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        		taskXc.setCompleteTime(LocalDateTime.parse(jsonMsg.getString("operateTime"), df));
        	}
        	taskXc.setTaskExplain(jsonMsg.getString("taskExplain"));
        	taskXcService.updateById(taskXc);
        	return taskXc.getUuid();
        }
        return null;
    }

    private String DoTaskFile(JSONObject jsonMsg){
         UploadFile taskFile = JSON.parseObject(jsonMsg.toJSONString(), new TypeReference<UploadFile>() {});
         uploadFileService.save(taskFile);
         return taskFile.getUuid();
    }

    /**
     * 保存预警信息
     * @param data
     * @author shizt
     * @date 2019年5月16日
     * @company chinobot
     */
    private void saveEarlyWarningRecord(RobotGatherData data) {
    	// 保存地址库
    	String location = data.getLongitude() + "," + data.getLatitude();
    	String addressJson = AmapUtils.getAddressByGeocode(location);
    	JSONObject addressObject = JSON.parseObject(addressJson);
    	JSONObject addressComponent = addressObject.getJSONObject("regeocode")
   			 									   .getJSONObject("addressComponent");

    	AddressBase addressBase = new AddressBase();
    	addressBase.setAtype("cle_early_warning_record");
    	addressBase.setCenter(location);
    	addressBase.setAddress(addressObject.getJSONObject("regeocode").getString("formatted_address"));
    	addressBase.setProvince(regionsService.getRegionsCode(addressComponent.getString("province")));
    	addressBase.setCity(regionsService.getRegionsCode(addressComponent.getString("city")));
    	addressBase.setDistrict(regionsService.getRegionsCode(addressComponent.getString("district")));
    	addressBase.setStreet(regionsService.getRegionsCode(addressComponent.getString("township")));

    	addressBaseService.saveOrUpdate(addressBase);

    	// 保存预警
    	EarlyWarningRecord record = new EarlyWarningRecord();
    	record.setTaskId(data.getTaskUuid());
    	record.setAbId(addressBase.getUuid());
    	record.setWarningTime(data.getOperateTime());
    	record.setContent(data.getWarnContent());
    	record.setWtype("01"); //TODO

    	earlyWarningRecordService.saveOrUpdate(record);
    }

}
