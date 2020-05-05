package com.chinobot.plep.home.event.service.impl;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.chinobot.aiuas.bot_collect.result.entity.Result;
import com.chinobot.aiuas.bot_collect.result.service.IResultService;
import com.chinobot.aiuas.bot_prospect.flight.mapper.FlightWorkMapper;
import com.chinobot.aiuas.bot_resource.uav.entity.Uav;
import com.chinobot.aiuas.bot_resource.uav.service.IReUavService;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.cityle.base.service.IUavService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.file.util.FastDFSClient;
import com.chinobot.common.utils.Base64Utils;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.FileUtil;
import com.chinobot.common.utils.GPSUtil;
import com.chinobot.common.utils.HttpUtils;
import com.chinobot.common.utils.PointUtil;
import com.chinobot.common.websocket.server.WebSocketServer;
import com.chinobot.drools.generator.RuleGenerator;
import com.chinobot.framework.web.constant.KafkaConstant;
import com.chinobot.framework.web.controller.KafkaConsumer;
import com.chinobot.framework.web.service.impl.KafkaDataServiceImpl;
import com.chinobot.plep.app.dispatch.entity.AppDispatchFile;
import com.chinobot.plep.app.dispatch.service.IAppDispatchFileService;
import com.chinobot.plep.home.building.mapper.VillageMapper;
import com.chinobot.plep.home.dataset.entity.WarningInfo;
//import com.chinobot.plep.home.dataset.service.IWarningInfoService;
import com.chinobot.plep.home.event.entity.EarlyWarning;
import com.chinobot.plep.home.event.entity.EventMain;
import com.chinobot.plep.home.event.entity.PatrolRecord;
import com.chinobot.plep.home.event.service.IDoKafkaMsgService;
import com.chinobot.plep.home.event.service.IEarlyWarningService;
import com.chinobot.plep.home.event.service.IEventMainService;
import com.chinobot.plep.home.event.service.IPatrolRecordService;
import com.chinobot.plep.home.monitor.entity.PersonOnline;
import com.chinobot.plep.home.monitor.entity.PersonTrajectory;
import com.chinobot.plep.home.monitor.entity.UavFlight;
import com.chinobot.plep.home.monitor.entity.UavOnline;
import com.chinobot.plep.home.monitor.service.IPersonOnlineService;
import com.chinobot.plep.home.monitor.service.IPersonTrajectoryService;
import com.chinobot.plep.home.monitor.service.IUavFlightService;
import com.chinobot.plep.home.monitor.service.IUavOnlineService;
import com.chinobot.plep.home.plan.entity.Range;
import com.chinobot.plep.home.plan.service.IFlyPathService;
import com.chinobot.plep.home.point.entity.FixedPoint;
import com.chinobot.plep.home.point.service.IFixedFlyPointService;
import com.chinobot.plep.home.point.service.IFixedPointService;
import com.chinobot.plep.home.route.entity.RouteBuilding;
import com.chinobot.plep.home.route.service.IRouteBuildingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DoKafkaMsgServiceImpl implements IDoKafkaMsgService {
	
	private static final Logger log = LoggerFactory.getLogger(DoKafkaMsgServiceImpl.class);
	
	@Autowired
	private IUploadFileService uploadFileService;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IEventMainService eventMainService;
	@Autowired
	private IEarlyWarningService earlyWarningService;
	@Autowired
	private VillageMapper VillageMapper;
	@Autowired
    private IUavFlightService uavFlightService;
	@Autowired
    private IUavOnlineService uavOnlineService;
	@Autowired
	private FastDFSClient fastDFSClient;
	@Autowired
	private IRouteBuildingService routeBuildingService;
	@Autowired
	private IAppDispatchFileService appFileService;
	@Autowired
	private IPatrolRecordService patrolRecordService;
	@Autowired
	private IFlyPathService flyPathService;
	@Autowired
	private IFixedPointService fixedPointServiceImpl;
	@Autowired
	private IPersonService personService;
//	@Autowired
//	private IWarningInfoService warningInfoService;
	@Autowired
    private IPersonTrajectoryService personTrajectoryService;
    @Autowired
    private IPersonOnlineService personOnlineService;
	
	@Autowired
	private IFixedFlyPointService fixedFlyPointService;
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private IReUavService iReUavService;
	
	@Autowired
	private FlightWorkMapper flightWorkMapper;
	
	@Autowired
	private IResultService resultService;
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void doEarlyWarnning(String jsonMsg) throws Exception {
		
        
		JSONObject jsonObject = JSON.parseObject(jsonMsg);
		//风险预警表
		EarlyWarning warning = new EarlyWarning();
		warning.setLatitude(jsonObject.getDouble("latitude"));
		warning.setLongitude(jsonObject.getDouble("longitude"));
		//将经纬度转为地址
		warning.setAddress(HttpUtils.lnglatToAddress(jsonObject.getString("latitude"), jsonObject.getString("longitude")));
		earlyWarningService.save(warning);
		//文件表
		JSONArray imgs = jsonObject.getJSONArray("img");
		for(int i=0;i<imgs.size();i++) {
			if(CommonUtils.isNotEmpty(imgs.getString(i))) {
				UploadFile uFile = new UploadFile();
				uFile.setPath(imgs.getString(i));
				String fileName = imgs.getString(i).substring(imgs.getString(i).lastIndexOf("/")+1);
				uFile.setOriginName(fileName);
				uploadFileService.save(uFile);
				//文件关联表
				FileBus fileBus = new FileBus();
				fileBus.setBusId(warning.getUuid());
				fileBus.setFileId(uFile.getUuid());
				fileBus.setSort(i+1);
				fileBus.setModule("warning_img");
				fileBusService.save(fileBus);
			}
		}
		JSONArray vedio = jsonObject.getJSONArray("vedio");
		for(int i=0;i<vedio.size();i++) {
			if(CommonUtils.isNotEmpty(vedio.getString(i))) {
				UploadFile uFile = new UploadFile();
				uFile.setPath(vedio.getString(i));
				String fileName = vedio.getString(i).substring(vedio.getString(i).lastIndexOf("/")+1);
				uFile.setOriginName(fileName);
				uploadFileService.save(uFile);
				//文件关联表
				FileBus fileBus = new FileBus();
				fileBus.setBusId(warning.getUuid());
				fileBus.setFileId(uFile.getUuid());
				fileBus.setSort(i+1);
				fileBus.setModule("warning_vedio");
				fileBusService.save(fileBus);
			}
		}
		//事件主表
		EventMain event = new EventMain();
		event.setCode(UUID.randomUUID().toString().replaceAll("-", ""));
		event.setWarningId(warning.getUuid());
		event.setSource("1");
		event.setStatus("1");
		event.setLinkNow("1");
		java.awt.geom.Point2D.Double point = PointUtil.toPoint(warning.getLongitude(), warning.getLatitude());
		//绑定小区
		List<Map<String, String>> allPolyLine = VillageMapper.getAllPolyLine();
		for(Map<String, String> map : allPolyLine) {
			String vid = map.get("uuid");
			String lineString = map.get("polyline");
			List<java.awt.geom.Point2D.Double> pointList = PointUtil.toPointList(lineString);
			if(PointUtil.IsPtInPoly(point, pointList)) {
				event.setVillageId(vid);
				break;
			}
		}
		//绑定部门
		
		String[] paths = {"map/龙华区.txt", "map/福田区.txt", "map/宝安区.txt","map/大鹏新区.txt", "map/光明区.txt", "map/龙岗区.txt", "map/罗湖区.txt", "map/南山区.txt", "map/坪山区.txt", "map/盐田区.txt"};
		//龙华：3f62d70d2042fd7c6aab1293ee006c23   福田：23b4327e0b82c3d2acbbd39f77450bf8
		for(String path : paths) {
			//List<Double> pointList = PointUtil.fileToPointList(path);
			List<List<java.awt.geom.Point2D.Double>> listList = PointUtil.fileToPointListList(path);
			for(List<java.awt.geom.Point2D.Double> list : listList) {
				if(PointUtil.IsPtInPoly(point, list)) {
					if(path.contains("龙华")) {
						event.setDeptId("3f62d70d2042fd7c6aab1293ee006c23");	
					}
					if(path.contains("福田")) {
						event.setDeptId("23b4327e0b82c3d2acbbd39f77450bf8");	
					}
					if(path.contains("宝安")) {
						event.setDeptId("e9df603441af43bf9370443cf2205a52");	
					}
					if(path.contains("大鹏新区")) {
						event.setDeptId("d7996d4e36e847cfb44b0f33047d63b3");	
					}
					if(path.contains("光明")) {
						event.setDeptId("f20fa8455fd3479fb0b188a0edeea176");	
					}
					if(path.contains("龙岗")) {
						event.setDeptId("160c411d50a4356f541bd886ac2f5251");	
					}
					if(path.contains("罗湖")) {
						event.setDeptId("19b3c82adf0cf4b73f39240d1f836c3d");	
					}
					if(path.contains("南山")) {
						event.setDeptId("a0ef451fa9ad129cc4b3d169a42a4c32");	
					}
					if(path.contains("坪山")) {
						event.setDeptId("7b10ce9abb034105abc8a1335d3c3e1d");	
					}
					if(path.contains("盐田")) {
						event.setDeptId("e9b82f7a161a647d238113d3ac63ac13");	
					}
					 eventMainService.save(event); 
				} 
			}
			
		}
		
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void updateDataBase(String jsonMsg) {
		UavFlight flight = JSON.parseObject(jsonMsg.toString(), new TypeReference<UavFlight>() {});
		if(CommonUtils.isObjEmpty(flight) || isTure(flight)) {//若推送的无人机轨迹为空，或轨迹的ecode，经纬度，高度速度为空，则不保存
			return;
		}
		//转换坐标系
		double[] gcj02 = GPSUtil.gps84_To_Gcj02(flight.getLatitude(), flight.getLongitude());
		flight.setLatitude(gcj02[0]);
		flight.setLongitude(gcj02[1]);
        uavFlightService.save(flight);
        //更新无人机最后飞行状态
        uavOnlineService.updateUav(flight);
		
	}

	private boolean isTure(UavFlight flight) {
		if(CommonUtils.isEmpty(flight.getUavCode())) {
			return true;
		}
		if(CommonUtils.isObjEmpty(flight.getLatitude()) || flight.getLatitude().isNaN()) {
			return true;
		}
		if(CommonUtils.isObjEmpty(flight.getLongitude()) || flight.getLongitude().isNaN()) {
			return true;
		}
		if(CommonUtils.isObjEmpty(flight.getFlyingHeight()) || flight.getFlyingHeight().isNaN()) {
			return true;
		}
		if(CommonUtils.isObjEmpty(flight.getFlightSpeed()) || flight.getFlightSpeed().isNaN()) {
			return true;
		}
		
		//判断经纬度是否为0
		if("0".equals(flight.getLongitude().toString()) || "0".equals(flight.getLatitude().toString())) {
			return true;
		}
		
		//经纬度为0.0
		if(0 == flight.getLongitude()|| 0 == flight.getLatitude()) {
			return true;
		}
		
		return false;
		
	}
	
	
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void DoUavStatus(String value) {
		
		JSONObject jsonObject = JSON.parseObject(value);
		String ecode = jsonObject.getString("ecode");
    	String runStatus = jsonObject.getString("runStatus");
    	Date operateTime = jsonObject.getDate("operateTime");
    	if(CommonUtils.isEmpty(ecode)) {
    		return ;
    	}
        //先判断uav_online表中是否存在这个无人机
  		QueryWrapper<UavOnline> queryWrapper = new QueryWrapper<UavOnline>();
  		queryWrapper.eq("uav_code", ecode);
  		UavOnline uavOnline = uavOnlineService.getOne(queryWrapper);
  		if(uavOnline != null) {
  			UpdateWrapper<UavOnline> updateWrapper = new UpdateWrapper<UavOnline>();
			updateWrapper.eq("uav_code",ecode);
			if(runStatus.equals("0")) {
				updateWrapper.set("flight_speed", 0);
				updateWrapper.set("flying_height", 0);
			}
			updateWrapper.set("operate_time", operateTime);
			updateWrapper.set("run_status", runStatus);
			uavOnlineService.update(updateWrapper);
  		}

  		QueryWrapper<Uav> uavQueryWrapper = new QueryWrapper<>();
		uavQueryWrapper.eq("uuid", ecode);
		Uav uav = new Uav();
		uav.setRunStatus(runStatus);
		iReUavService.update(uav, uavQueryWrapper);
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void warningUpload(String jsonMsg) throws Exception {
		JSONArray jsonArray = JSON.parseArray(jsonMsg);
		for(int i=0;i<jsonArray.size();i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			InputStream inputStream = FileUtil.byte2Input(fastDFSClient.downloadFile(jsonObject.getString("path")));
			List<Double> lnglat = earlyWarningService.printImageTags(inputStream);
			if(lnglat.size()>1) {
				//预警信息
				EarlyWarning ew = new EarlyWarning();
				ew.setLongitude(lnglat.get(0));
				ew.setLatitude(lnglat.get(1));
				ew.setAddress(HttpUtils.lnglatToAddress(lnglat.get(0).toString(), lnglat.get(1).toString()));
				earlyWarningService.save(ew);
				//文件上传信息保存
				UploadFile upFile = new UploadFile();
				upFile.setSize(jsonObject.getBigDecimal("size"));
				upFile.setPath(jsonObject.getString("path"));
				upFile.setCurrentName(jsonObject.getString("currentName"));
				upFile.setOriginName(jsonObject.getString("originName"));
				uploadFileService.save(upFile);
				//关联预警图片
				FileBus fileBus = new FileBus();
				fileBus.setFileId(upFile.getUuid());
				fileBus.setSort(1);
				fileBus.setModule("warning_img");
				fileBus.setBusId(ew.getUuid());
				fileBusService.save(fileBus);
				//事件
				//TODO 事件过滤
				EventMain event = new EventMain();
				event.setCode(UUID.randomUUID().toString().replaceAll("-", ""));
				event.setWarningId(ew.getUuid());
				event.setSource("1");
				event.setStatus("1");
				event.setLinkNow("1");
				//绑定小区
				event.setVillageId(earlyWarningService.getBindVillageId(lnglat.get(0), lnglat.get(1)));
				//绑定部门
				event.setDeptId(earlyWarningService.getBindDeptId(lnglat.get(0), lnglat.get(1)));
				//event.setDeptId("a0ef451fa9ad129cc4b3d169a42a4c32");
				eventMainService.save(event);
			}else {
				System.out.println("没有经纬度");
			}
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void recordUpload(String value) throws Exception {
		JSONArray jsonArray = JSON.parseArray(value);
		for(int i=0;i<jsonArray.size();i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			String currentName = jsonObject.getString("currentName");
			String originName = jsonObject.getString("originName");
			String path = jsonObject.getString("path");
			BigDecimal size = jsonObject.getBigDecimal("size");
			UploadFile upFile = new UploadFile();
			upFile.setSize(size);
			upFile.setPath(path);
			upFile.setCurrentName(currentName);
			upFile.setOriginName(originName);
			uploadFileService.save(upFile);
			QueryWrapper<AppDispatchFile> appFileWrapper  = new QueryWrapper<AppDispatchFile>();
			appFileWrapper.eq("file_name", originName);
			appFileWrapper.eq("data_status", "1");
			AppDispatchFile appFile = appFileService.getOne(appFileWrapper);
			appFile.setFileId(upFile.getUuid());
			appFileService.updateById(appFile);
			RouteBuilding routeBuilding = routeBuildingService.getById(appFile.getRouteBuildingId());
			QueryWrapper<EventMain> eventWrapper = new QueryWrapper<EventMain>();
			eventWrapper.eq("building_id", routeBuilding.getBuildingId());
			eventWrapper.eq("data_status", "1");
			//EventMain eventMain = eventMainService.getOne(eventWrapper);//TODO 事件有多个怎么办
			List<EventMain> list = eventMainService.list(eventWrapper);
			for(EventMain eventMain: list) {
				PatrolRecord record = new PatrolRecord();
				record.setEventId(eventMain.getUuid());
				record.setTimeStart(appFile.getDateCreated());
				patrolRecordService.save(record);
				//关联预警图片
				FileBus fileBus = new FileBus();
				fileBus.setFileId(upFile.getUuid());
				fileBus.setSort(1);
				fileBus.setModule("patrol_img");
				fileBus.setBusId(record.getUuid());
				fileBusService.save(fileBus);
			}
			
		}
	}
	
	/**
	 * 存量变换预警处理
	 */
	@Override
	public void pushWarningStock(String value) {
		// TODO Auto-generated method stub
		JSONObject jsonObject = JSON.parseObject(value);
		
		
		
		String id = jsonObject.getString("JSON_ID");	//预警id
		String[] idArr = id.split("_");
		String ver = idArr[0];	//算法版本
		String domainType = idArr[1];	//领域类型
		String sceneType = idArr[2];	//场景类型
		
		log.info("成功解析-json对象", jsonObject);
		
		String img = jsonObject.getString("Image_Stream_N");	//预警图片
		float stock = jsonObject.getFloatValue("Stock_c");	//可能性
		String routeId = jsonObject.getString("Route_ID");	//存量变换的航线id是定点id
		String[] split = routeId.split("#");
		String flyPointId = split[0];
		String taskId = split[1];
		String pointId = fixedFlyPointService.getById(flyPointId).getFixedId();
		String objTypeN = jsonObject.getString("N_Obj");//预警图片语义类别
		JSONArray gps = jsonObject.getJSONArray("N_GPS");	//gps预警图片坐标
		JSONObject nLocObj = jsonObject.getJSONObject("N_LOC_Obj");//预警图片标记框
		try {
			UploadFile file = uploadWarningImg(img);
			
			WarningInfo warningInfo = new WarningInfo();
			warningInfo.setTaskId(taskId);
			warningInfo.setEarlyType(sceneType);
			warningInfo.setVersion(ver);
			warningInfo.setAccuracy(stock * 100);
			warningInfo.setSceneType(domainType);
			warningInfo.setLongitudeN(gps.getDouble(1));//预警图片原始经度
			warningInfo.setLatitudeN(gps.getDouble(0));//预警图片原始维度
			warningInfo.setHeightN(gps.getFloat(2));//预警图片gps高度
//			warningInfo.setLongitudeN(Double.parseDouble(gps.getString(1)));//预警图片原始经度
//			warningInfo.setLatitudeN(Double.parseDouble(gps.getString(0)));//预警图片原始维度
//			warningInfo.setHeightN(Float.parseFloat(gps.getString(2)));//预警图片gps高度
			double[] gd = GPSUtil.gps84_To_Gcj02(gps.getDouble(0), gps.getDouble(1));
			warningInfo.setLongitude(gd[1]);//预警图片转换后经度
			warningInfo.setLatitude(gd[0]);//预警图片转换后维度
			warningInfo.setObjTypeN(objTypeN);//预警图片语义类别
			
			warningInfo.setLabelXN(nLocObj.getFloat("B_x"));//预警图片标记框横坐标
			warningInfo.setLabelYN(nLocObj.getFloat("B_y"));//预警图片标记框纵坐标
			warningInfo.setLabelWN(nLocObj.getFloat("B_w"));//预警图片标记框宽度
			warningInfo.setLabelHN(nLocObj.getFloat("B_h"));//预警图片标记框高度
			warningInfo.setJsonId(id);//json文件id
			warningInfo.setPointId(flyPointId);//预警定点
			//转换经纬度为详细地址信息
			String address = HttpUtils.lnglatToAddress(String.valueOf(gd[1]), String.valueOf(gd[0]));
			if(address.equals("[]")) {
				address = "";	
				System.out.println("not found address: " + gps.toJSONString());
			}
			warningInfo.setAddress(address);
			
			//查询定点分派人
			FixedPoint fixedPoint = fixedPointServiceImpl.getById(pointId);
			warningInfo.setDisPerson(fixedPoint.getAssignId());
			
			//查询所属部门
			Person person = personService.getById(fixedPoint.getAssignId());
			warningInfo.setDeptId(person.getDeptId());
			
			String imgB = jsonObject.getString("Image_Stream_B");//基准图片
			UploadFile fileB = uploadWarningImg(imgB);//上传图片
			
			String objTypeB = jsonObject.getString("B_Obj");//基准图语义类别
			JSONArray gpsB = jsonObject.getJSONArray("B_GPS");	//gps基准图坐标
			JSONObject bLocObj = jsonObject.getJSONObject("B_LOC_Obj");//基准图标记框
			warningInfo.setLongitudeB(gpsB.getDouble(1));//基准图原始经度
			warningInfo.setLatitudeB(gpsB.getDouble(0));//基准图原始维度
			warningInfo.setHeightB(gpsB.getFloat(2));//基准图gps高度
			double[] bd = GPSUtil.gps84_To_Gcj02(gpsB.getDouble(0), gpsB.getDouble(1));
			warningInfo.setLongitudeB(bd[1]);//基准图转换后经度
			warningInfo.setLatitudeB(bd[0]);//基准图转换后维度
			warningInfo.setObjTypeB(objTypeB);//基准图语义类别
			warningInfo.setLabelXB(bLocObj.getFloat("B_x"));//基准图标记框横坐标
			warningInfo.setLabelYB(bLocObj.getFloat("B_y"));//基准图标记框纵坐标
			warningInfo.setLabelWB(bLocObj.getFloat("B_w"));//基准图标记框宽度
			warningInfo.setLabelHB(bLocObj.getFloat("B_h"));//基准图标记框高度
			
			//生成预警信息
//			boolean bo = warningInfoService.save(warningInfo);
			
//			log.info("预警信息"+warningInfo+"是否保存成功", bo);
			
			//保存预警信息的预警图
			FileBus fb = new FileBus();
			fb.setBusId(warningInfo.getUuid());
			fb.setFileId(file.getUuid());
			fb.setModule("warning_img");
			fileBusService.save(fb);
			
			//保存预警信息的基准图
			FileBus fbB = new FileBus();
			fbB.setBusId(warningInfo.getUuid());
			fbB.setFileId(fileB.getUuid());
			fbB.setModule("basic_img");
			fileBusService.save(fbB);
			
			//预警过滤
			eventMainService.warningFilter(warningInfo.getUuid());
			
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	
	/**
	 * 楼栋加建预警处理
	 */
	@Override
	public void pushWarningAddition(String value) {
		
		JSONObject jsonObject = JSON.parseObject(value);
		log.info("成功解析-json对象", jsonObject);
		
		String id = jsonObject.getString("JSON_ID");	//预警id
		String[] idArr = id.split("_");
		String ver = idArr[0];	//算法版本
		String domainType = idArr[1];	//领域类型
		String sceneType = idArr[2];	//场景类型
		
		String img = jsonObject.getString("Image_Stream_N");	//预警图片
		float stock = jsonObject.getFloatValue("C_Obj");	//可能性
		String routeId = jsonObject.getString("Route_ID");	//楼顶加建是区域扫描的航线id
		String[] split = routeId.split("#");
		String pathId = split[0];
		String taskId = split[1];
		String objTypeN = jsonObject.getString("N_Obj");//预警图片语义类别
		JSONArray gps = jsonObject.getJSONArray("GPS");	//gps预警图片坐标
		JSONObject nLocObj = jsonObject.getJSONObject("LOC_Obj");//预警图片标记框
		try {
			UploadFile file = uploadWarningImg(img);
			
			WarningInfo warningInfo = new WarningInfo();
			warningInfo.setTaskId(taskId);
			warningInfo.setEarlyType(sceneType);
			warningInfo.setVersion(ver);
			warningInfo.setAccuracy(stock * 100);
			warningInfo.setSceneType(domainType);
			warningInfo.setLongitudeN(gps.getDouble(1));//预警图片原始经度
			warningInfo.setLatitudeN(gps.getDouble(0));//预警图片原始维度
			warningInfo.setHeightN(gps.getFloat(2));//预警图片gps高度
			double[] gd = GPSUtil.gps84_To_Gcj02(gps.getDouble(0), gps.getDouble(1));
			warningInfo.setLongitude(gd[1]);//预警图片转换后经度
			warningInfo.setLatitude(gd[0]);//预警图片转换后维度
			warningInfo.setObjTypeN(objTypeN);//预警图片语义类别
//			warningInfo.setLabelXN(Float.parseFloat(nLocObj.get("N_x_c").toString()));//预警图片标记框横坐标
//			warningInfo.setLabelYN(Float.parseFloat(nLocObj.get("N_y_c").toString()));//预警图片标记框纵坐标
//			warningInfo.setLabelWN(Float.parseFloat(nLocObj.get("N_w").toString()));//预警图片标记框宽度
//			warningInfo.setLabelHN(Float.parseFloat(nLocObj.get("N_h").toString()));//预警图片标记框高度
			warningInfo.setLabelXN(nLocObj.getFloat("N_x_c"));//预警图片标记框横坐标
			warningInfo.setLabelYN(nLocObj.getFloat("N_y_c"));//预警图片标记框纵坐标
			warningInfo.setLabelWN(nLocObj.getFloat("N_w"));//预警图片标记框宽度
			warningInfo.setLabelHN(nLocObj.getFloat("N_h"));//预警图片标记框高度
			warningInfo.setJsonId(id);//json文件id
			warningInfo.setPathId(pathId);//预警路线id
			//转换经纬度为详细地址信息
			String address = HttpUtils.lnglatToAddress(String.valueOf(gd[1]), String.valueOf(gd[0]));
			if(address.equals("[]")) {
				address = "";	
//				System.out.println("not found address: " + gps.toJSONString());
			}
			warningInfo.setAddress(address);
			
			//查询区域分派人
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("flyPathId", pathId);
			Range range = flyPathService.getRangeByFlyPath(param);
			warningInfo.setDisPerson(range.getAssignId());

			//查询所属部门
			Person person = personService.getById(range.getAssignId());
			warningInfo.setDeptId(person.getDeptId());
			
			//生成预警信息
//			boolean bo = warningInfoService.save(warningInfo);
//			log.info("预警信息"+warningInfo+"是否保存成功", bo);
			
			//保存预警信息的预警图
			FileBus fb = new FileBus();
			fb.setBusId(warningInfo.getUuid());
			fb.setFileId(file.getUuid());
			fb.setModule("warning_img");
			fileBusService.save(fb);
			
			//预警过滤
			eventMainService.warningFilter(warningInfo.getUuid());
			
		} catch (Exception e) {
			log.error("", e);
		}
	}
	
	/**
	 * 上传base64的预警图片
	 * @param base64Img
	 * @return
	 */
	private UploadFile uploadWarningImg(String base64Img) {
		try {
			File tmpFile = File.createTempFile(CommonUtils.getUUID(), ".jpg");	//创建临时文件
			Base64Utils.Base64ToImage(base64Img, tmpFile.getAbsolutePath());	//生成临时图片内容
			List<UploadFile> files = uploadFileService.save(new File[] {tmpFile});//上传图片文件
			return files.get(0);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public void doPersonTrajectory(String jsonMsg) {
    	
    	log.info("人员行动轨迹", jsonMsg);
    	PersonTrajectory parseObject = JSON.parseObject(jsonMsg.toString(), new TypeReference<PersonTrajectory>() {});
    	//校验人员轨迹数据
    	if(check(parseObject)) {
    		return ;
    	}
    	
    	//新增人员轨迹
    	personTrajectoryService.saveOrUpdate(parseObject);
    	//更新人员在线状态
    	updatePerosnOnlineStatus(parseObject);
		
	}
	
	/**
	 * 数据校验
	 * @param parseObject
	 * @return
	 */
	@SuppressWarnings("unused")
	private boolean check(PersonTrajectory parseObject) {
		
		//校验人员轨迹数据是否为空
		if(CommonUtils.isObjEmpty(parseObject)) {
    		return true;
    	}
		
		//判断是否为空
		if(CommonUtils.isEmpty(parseObject.getPersonId()) || CommonUtils.isEmpty(parseObject.getLongitude().toString())
    			|| CommonUtils.isEmpty(parseObject.getLatitude().toString())) {
    		return true;
    	}
		
		//判断经纬度是否为0
		if("0".equals(parseObject.getLongitude().toString()) || "0".equals(parseObject.getLatitude().toString())) {
			return true;
		}
		
		//经纬度为0.0
		if(0 == parseObject.getLongitude()|| 0 == parseObject.getLatitude()) {
			return true;
		}
		
		//判断经纬度是否为非数字
		if(parseObject.getLatitude().isNaN() || parseObject.getLongitude().isNaN()) {
			return true;
		}
		return false;
		
	}
	
	
	
	private void updatePerosnOnlineStatus(PersonTrajectory parseObject) {
		//首先判断人员是否存在
    	QueryWrapper<PersonOnline> queryWrapper = new QueryWrapper<PersonOnline>();
    	queryWrapper.eq("person_id", parseObject.getPersonId());
    	List<PersonOnline> list = personOnlineService.list(queryWrapper);
    	PersonOnline personOnline = null;
    	if(list.size()>0) {
    		//人员存在,更新数据
    		personOnline = list.get(0);
    	}else {
    		//人员不存在，新增
    		personOnline = new PersonOnline();
    	}
    	personOnline.setPersonId(parseObject.getPersonId());
    	personOnline.setLatitude(parseObject.getLatitude());
		personOnline.setLongitude(parseObject.getLongitude());
		personOnline.setOnlineStatus("1");
		personOnline.setDataOrigin(parseObject.getDataOrigin());
		personOnline.setOperateTime(parseObject.getOperateTime());
		//推送人上线信息
		kafkaTemplate.sendDefault("p_person_online", JSON.toJSONString(personOnline));
		
		personOnlineService.saveOrUpdate(personOnline);
		
	}
	
	@Override
	public void taskConfirm(String value) throws Exception {
		JSONObject val = JSON.parseObject(value);
		String workType = val.getString("workType");
		Map fightNameAndEname = null;
		if("1".equals(workType)) {//常规任务
			fightNameAndEname = flightWorkMapper.getFightNameAndEname(val.getString("workId"), val.getString("uavId"));
		}
		if("2".equals(workType)) {//紧急调度任务
			fightNameAndEname = flightWorkMapper.getUrgentNameAndEname(val.getString("workId"), val.getString("uavId"));
		}
		if(fightNameAndEname != null) {
			fightNameAndEname.put("workType", workType);
			fightNameAndEname.put("operateBy", val.getString("operateBy"));
			JSONObject jsb = new JSONObject ();
			jsb.put ("key", "task_confirm_socket");
			jsb.put ("msg", fightNameAndEname);
			WebSocketServer.sendInfo("task_confirm_socket", jsb.toJSONString());
		}
	}
	
	@Transactional
	@Override
	public void numerPost(String value) {
		JSONObject val = JSON.parseObject(value);
//		DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		//先删除
		String workId = val.getString("workId");
		String objectId = val.getString("objectId");
		resultService.remove(CommonUtils.getEqQueryWrapper("work_id", "collect_id", workId, objectId));
		String startTime = val.getString("startTime");
		String endTime = val.getString("endTime");
//		int totalCarNum = val.getIntValue("totalCarNum");
//		LocalDateTime startTimeP = LocalDateTime.parse(startTime, pattern);
//		LocalDateTime endTimeP = LocalDateTime.parse(endTime, pattern);
//		Duration duration = Duration.between(startTimeP,endTimeP);
//		long days = duration.toDays(); //相差的天数
//		long hours = duration.toHours();//相差的小时数
//		long minutes = duration.toMinutes();//相差的分钟数
//		long millis = duration.toMillis();//相差毫秒数
//		float spend = (float) (millis/1000.0/60.0);
//		String spendString = String.format("%.2f", spend);
		insertResult(workId, objectId, "startTime", startTime, KafkaConstant.TIME_RS_TYPE, "开始时间", null);
		insertResult(workId, objectId, "endTime", endTime, KafkaConstant.TIME_RS_TYPE, "结束时间", null);
//		insertResult(workId, objectId, "speedMinute", spendString, KafkaConstant.FLOAT_RS_TYPE, "持续耗时");
//		insertResult(workId, objectId, "totalCarNum", totalCarNum + "", KafkaConstant.INT_RS_TYPE, "车次总数");
		JSONArray jsonArray = val.getJSONArray("items");
		for(int i=0; i<jsonArray.size(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			insertResult(workId, objectId, jsonObject.getString("rs_key") , jsonObject.getString("rs_value") , jsonObject.getString("rs_type") , jsonObject.getString("remark"),jsonObject.getString("baseDataCode") );
		}
	}
	
	private void insertResult(String workId, String collectId, String key, String val, String dateType, String remark, String baseDataCode) {
		Result rs = new Result();
		rs.setWorkId(workId);
		rs.setCollectId(collectId);
		rs.setRsKey(key);
		rs.setRsValue(val);
		rs.setRsType(dateType);
		rs.setRemark(remark);
		rs.setBaseDataCode(baseDataCode);
		resultService.save(rs);
	}
}
