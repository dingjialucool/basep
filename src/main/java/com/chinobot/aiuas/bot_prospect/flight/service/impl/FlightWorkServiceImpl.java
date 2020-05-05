package com.chinobot.aiuas.bot_prospect.flight.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.airport.mapper.BotProspectAirportMapper;
import com.chinobot.aiuas.bot_collect.info.entity.CollectData;
import com.chinobot.aiuas.bot_collect.info.service.ICollectDataService;
import com.chinobot.aiuas.bot_collect.strategy.entity.Strategy;
import com.chinobot.aiuas.bot_collect.strategy.mapper.StrategyMapper;
import com.chinobot.aiuas.bot_collect.strategy.service.IStrategyService;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.UrgentWorkVo;
import com.chinobot.aiuas.bot_prospect.flight.constant.FlightConstant;
import com.chinobot.aiuas.bot_prospect.flight.constant.StrategyWorkConstant;
import com.chinobot.aiuas.bot_prospect.flight.entity.FlightWork;
import com.chinobot.aiuas.bot_prospect.flight.entity.dto.UavFlightWorkParamDto;
import com.chinobot.aiuas.bot_prospect.flight.entity.dto.WorkStateDto;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.FlightWorkPageVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.FlightWorkVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.UavFlightWorkVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.WorkFilesVo;
import com.chinobot.aiuas.bot_prospect.flight.mapper.FlightMapper;
import com.chinobot.aiuas.bot_prospect.flight.mapper.FlightWorkMapper;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightWorkService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.file.util.FastDFSClient;
import com.chinobot.common.file.util.FileClient;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.FileUtil;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.common.utils.ZipUtil;
import com.chinobot.framework.web.service.impl.BaseService;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 航班作业 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-02-24
 */
@Slf4j
@Service
public class FlightWorkServiceImpl extends BaseService<FlightWorkMapper, FlightWork> implements IFlightWorkService {

	@Autowired
	private FlightWorkMapper flightWorkMapper;
	@Autowired
	private IStrategyService iStrategyService;
	@Autowired
	private StrategyMapper strategyMapper;
	@Autowired
	private BotProspectAirportMapper botProspectAirportMapper;
	@Autowired
	private FlightMapper flightMapper;
	@Autowired
	private IFileBusService iFileBusService;
	@Value("${config.downloadUrl}")
	private String downloadUrl;
	@Value("${config.zipPath}")
	private String zipTemp;
	@Value("${config.pointTempPath}")
	private String pointTempPath;
	@Autowired
	private IUploadFileService uploadFileService;
	@Autowired
	private FastDFSClient fastDFSClient;
	@Autowired
	private ICollectDataService collectDataService;
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private FileClient fileClient;
	@Autowired
	private IUploadFileService iUploadFileService;

	@Override
	public IPage<FlightWorkPageVo> getPage(Page page, Map<String, Object> param) {
		return flightWorkMapper.getList(page, param);
	}

	@Override
	public Map<String, Object> getFlightWorkData(Page page, Map<String, Object> param, String... refreshModule) {
		Map<String, Object> result = new HashMap<>();
//		String flightDate = (String) param.get("flightDate");

		if(CommonUtils.isObjEmpty(param.get("workStatus"))){
			if(param.get("pageType").equals("flightArrangement")){
				param.put("workStatus", new String[]{StrategyWorkConstant.WORK_STATUS_EXECUTED,
						StrategyWorkConstant.WORK_STATUS_COMPLETED, StrategyWorkConstant.WORK_STATUS_DOING});
			}else{
				param.put("workStatus", new String[]{StrategyWorkConstant.WORK_STATUS_DONE,
						StrategyWorkConstant.WORK_STATUS_CANCEL});
			}
		}else{
			String workStatus = (String)param.get("workStatus");
			param.put("workStatus", new String[]{workStatus});
		}

		Set<String> refreshModuleSet = null;
		if(CommonUtils.objNotEmpty(refreshModule)){
			refreshModuleSet = new HashSet(Arrays.asList(refreshModule));
		}

		// 策略下拉框
		if(refreshModuleSet == null || refreshModuleSet.contains("strategySelect")) {
			result.put("strategySelect", iStrategyService
					.lambdaQuery()
					.select(Strategy::getUuid, Strategy::getStrategyName)
					.eq(Strategy::getIsDeleted, "0")
					.list());
		}
		// 无人机/飞手状态
		if(refreshModuleSet == null || refreshModuleSet.contains("uavPersonStatus")) {
			Map<String, Object> uavPersonStatus = new HashMap();
			uavPersonStatus.put("uav", flightWorkMapper.getUavMinuteList(param));
			uavPersonStatus.put("person", flightWorkMapper.getPersonMinuteList(param));
			result.put("uavPersonStatus", uavPersonStatus);
		}
		// 统计
		if(refreshModuleSet == null || refreshModuleSet.contains("statistics")) {
			result.put("statistics", flightWorkMapper.getStatistics(param));
		}
		// 作业分页
		if(refreshModuleSet == null || refreshModuleSet.contains("workPage")) {
			result.put("workPage", getPage(page, param));
		}

		return result;
	}

	@Override
	public Map<String, Object> getFlightTaskData(Map<String, Object> param, String... refreshModule) {
		Map<String, Object> result = new HashMap();
		Set<String> refreshModuleSet = null;
		if(CommonUtils.objNotEmpty(refreshModule)){
			refreshModuleSet = new HashSet(Arrays.asList(refreshModule));
		}

		// 统计
		if(refreshModuleSet == null || refreshModuleSet.contains("statistics")) {
			result.put("statistics", flightWorkMapper.getFlightTaskStatistics(param));
		}
		// 策略航班列表
		if(refreshModuleSet == null || refreshModuleSet.contains("flightList")) {
			result.put("flightList", flightWorkMapper.getStrategyFlightByWork(param));
		}
		// 机场无人机人员列表
		if(refreshModuleSet == null || refreshModuleSet.contains("airportUavPersonList")) {
			result.put("resources", botProspectAirportMapper.getAirportUavPersonVo(param));
		}
		// 航班作业时间
		if(refreshModuleSet == null || refreshModuleSet.contains("workTimeList")) {
			result.put("eventData", flightWorkMapper.getWorkTime(param));
		}

		return result;
	}

	@Override
	public List<FlightWorkPageVo> getList(Map<String, Object> param) {
		return flightWorkMapper.getList(param);
	}

	@Override
	public String edit(FlightWorkVo flightWorkVo) {
		FlightWork flightWork = new FlightWork();
		if(CommonUtils.objNotEmpty(flightWorkVo.getIsDeleted()) &&
				flightWorkVo.getIsDeleted().equals(GlobalConstant.IS_DELETED_YES)){
			flightWork.setUuid(flightWorkVo.getId())
					.setIsDeleted(flightWorkVo.getIsDeleted());
		}else if(CommonUtils.objNotEmpty(flightWorkVo.getWorkStatus()) &&
				flightWorkVo.getWorkStatus().equals(StrategyWorkConstant.WORK_STATUS_CANCEL)){
			flightWork.setUuid(flightWorkVo.getId())
					.setWorkStatus(flightWorkVo.getWorkStatus());
		}else{
			long workMinute = Duration.between(flightWorkVo.getStart(), flightWorkVo.getEnd()).getSeconds() / 60;
			if(CommonUtils.objNotEmpty(flightWorkVo.getWorkStatus()) &&
					flightWorkVo.getWorkStatus().equals(StrategyWorkConstant.WORK_STATUS_STANDBY)){
				flightWork.setWorkStatus(StrategyWorkConstant.WORK_STATUS_EXECUTED);
				// 作业时间。需要减1分钟，否则0点会跨天
				workMinute -= 1;
			}

			flightWork.setUuid(flightWorkVo.getId())
					.setUavUuid(flightWorkVo.getUavUuid())
					.setPersonUuid(flightWorkVo.getPersonUuid())
					.setFlightDate(flightWorkVo.getStart().toLocalDate())
					.setFlightTime(flightWorkVo.getStart().toLocalTime())
					.setWorkMinute((float)workMinute);
		}

		updateById(flightWork);
		return flightWork.getUuid();
	}

	@Override
	public Map<String, Object> getWorkInfo(Map<String, Object> param, String... refreshModule) {
		String flightWorkUuid = (String) param.get("flightWorkUuid");
		Map<String, Object> result = new HashMap<>();
		Set<String> refreshModuleSet = null;
		if(CommonUtils.objNotEmpty(refreshModule)){
			refreshModuleSet = new HashSet(Arrays.asList(refreshModule));
		}

		// 策略信息
		if(refreshModuleSet == null || refreshModuleSet.contains("strategyInfo")) {
			Map<String, Object> strategyInfo = strategyMapper.getInfoByFlightWorkId(flightWorkUuid);
			if(CommonUtils.objNotEmpty(strategyInfo)){
				strategyInfo.put("taskStrategy", packageTaskInfo(flightWorkMapper.getTaskInfoListByStrategy(flightWorkUuid)));
			}
			result.put("strategyInfo", strategyInfo);
		}
		// 航班信息
		if(refreshModuleSet == null || refreshModuleSet.contains("flightInfo")) {
			Map flightInfo = flightMapper.getFlightInfoByFlightWorkId(flightWorkUuid);
			flightInfo.put("collectTask", packageTaskInfo(flightWorkMapper.getTaskInfoListByFlightWorkId(flightWorkUuid)));
			result.put("flightInfo", flightInfo);
		}
		// 调度信息
		if(refreshModuleSet == null || refreshModuleSet.contains("taskInfo")) {
			result.put("taskInfo", flightWorkMapper.getPersonUavDateByFlightWorkId(flightWorkUuid));
		}
		// 飞行数据
		if(refreshModuleSet == null || refreshModuleSet.contains("flyData")) {
			Map flyData = new HashMap();
			Map flyDataParam = new HashMap();
			flyDataParam.put("busId", flightWorkUuid);
			flyDataParam.put("module", com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant.LOG_WORK);
			flyData.put("log", iFileBusService.getFileIdByBusId(flyDataParam));
			flyDataParam.put("module", com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant.STREAN_VEDIO);
			flyData.put("video", iFileBusService.getFileIdByBusId(flyDataParam));
			flyDataParam.put("module", com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant.OBJECT_MEDIA);
			flyData.put("objMedia", iFileBusService.getFileIdByBusId(flyDataParam));

			result.put("flyData", flyData);
		}
		// 作业结果
		if(refreshModuleSet == null || refreshModuleSet.contains("workResult")) {
			Map workResult = new HashMap();
			workResult.put("info", flightWorkMapper.getWorkResultByFlightWorkId(flightWorkUuid));
			workResult.put("warningList", flightWorkMapper.getWarningListByFlightWorkId(flightWorkUuid));
			result.put("workResult", workResult);
		}
		
		//数量监测
		if(refreshModuleSet == null || refreshModuleSet.contains("numberResult")) {
			result.put("numberResult", flightWorkMapper.getCollectData(flightWorkUuid));
		}
		

		return result;
	}

	/**
	 * 组装任务、对象
	 * @Author: shizt
	 * @Date: 2020/3/9 15:46
	 */
	private List packageTaskInfo(List<Map> taskInfoList){
		if(taskInfoList.size() == 0){
			return new ArrayList();
		}
		List result = new ArrayList();
		List sceneList = new ArrayList();
		Map sceneMap = new HashMap();
		Map dataMap = new HashMap();
		List taskList = new ArrayList();
		List infoList = new ArrayList();
		String sceneName = null;
		String sceneUuid = null;
		for (Map m: taskInfoList) {
			if(sceneUuid == null){
				sceneUuid = (String) m.get("sceneUuid");
				sceneName = (String) m.get("sceneName");
			}
			if(!m.get("sceneUuid").equals(sceneUuid)){
				dataMap.put("title", "任务");
				dataMap.put("list", taskList);
				sceneList.add(dataMap);
				dataMap = new HashMap();

				dataMap.put("title", "对象");
				dataMap.put("list", infoList);
				sceneList.add(dataMap);
				dataMap = new HashMap();

				sceneMap.put("title", sceneName);
				sceneMap.put("list", sceneList);

				result.add(sceneMap);

				sceneList = new ArrayList();
				sceneMap = new HashMap();
				taskList = new ArrayList();
				infoList = new ArrayList();
				sceneUuid = (String) m.get("sceneUuid");
				sceneName = (String) m.get("sceneName");
			}

			if("task".equals(m.get("type"))){
				taskList.add(m);
			}else if("info".equals(m.get("type"))){
				infoList.add(m);
			}
		}
		dataMap = new HashMap();
		dataMap.put("title", "任务");
		dataMap.put("list", taskList);
		sceneList.add(dataMap);

		dataMap = new HashMap();
		dataMap.put("title", "对象");
		dataMap.put("list", infoList);
		sceneList.add(dataMap);

		sceneMap.put("title", sceneName);
		sceneMap.put("list", sceneList);

		result.add(sceneMap);

		return result;
	}

	@Override
	public List<UavFlightWorkVo> getUavWorks(UavFlightWorkParamDto dto) {
		List<UavFlightWorkVo> list = flightWorkMapper.getUavWorks(dto);
		for(UavFlightWorkVo vo : list) {
			if(ThreadLocalUtil.getResources() != null) {
				vo.setOperateBy(ThreadLocalUtil.getResources().getUuid());
			}
			vo.setWorkType("1");
			vo.setFlightDownloadUrl(downloadUrl + vo.getRouteFileUuid());//航线文件下载路径
			vo.setObjects(flightWorkMapper.getObjAndTaskByFight(vo.getFlightId()));
			//基准图
			if(com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant.PHOTO_MODE.equals(vo.getMode())) {
				Map<String, Object> param = new HashMap<String, Object>(2);
				param.put("busId", vo.getFlightId());
				param.put("module", FlightConstant.FLIGHT_REFERENCE);
				List<FileBus> busList = iFileBusService.getFileBusList(param);
				if(busList.size()>0) {
					vo.setBaseDownloadUrl(downloadUrl + busList.get(0).getFileId());
				}
			}
		}
		return list;
	}

	@Override
	public void doneWork(WorkFilesVo workFilesVo) throws Exception {
		String flightWorkId = workFilesVo.getFlightWorkId();
		doSaveFiles(workFilesVo);

		updateById(new FlightWork()
				.setUuid(flightWorkId)
				.setWorkStatus(StrategyWorkConstant.WORK_STATUS_DONE));
	}

	@Override
	public void doSaveFiles(WorkFilesVo workFilesVo) throws Exception {
		String flightWorkId = workFilesVo.getFlightWorkId();
		List<String> logFileIds = workFilesVo.getLogFileIds();
		List<String> videoFileIds = workFilesVo.getVideoFileIds();
		List<String> objMediaIds = workFilesVo.getObjMediaIds();

		List<FileBus> fileBuses = new ArrayList<>();
		for (String id: logFileIds) {
			FileBus fileBus = new FileBus();
			fileBus.setFileId(id);
			fileBuses.add(fileBus);
		}
		iFileBusService.saveFileBusList(fileBuses,
				flightWorkId, com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant.LOG_WORK);

		fileBuses = new ArrayList<>();
		for (String id: videoFileIds) {
			FileBus fileBus = new FileBus();
			fileBus.setFileId(id);
			fileBuses.add(fileBus);
		}
		iFileBusService.saveFileBusList(fileBuses,
				flightWorkId, com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant.STREAN_VEDIO);

		fileBuses = new ArrayList<>();
		for (String id: objMediaIds) {
			FileBus fileBus = new FileBus();
			fileBus.setFileId(id);
			fileBuses.add(fileBus);
		}
		iFileBusService.saveFileBusList(fileBuses,
				flightWorkId, com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant.OBJECT_MEDIA);

		saveObjectWorkMedia(flightWorkId, objMediaIds);
	}

	/**
	 * 保存对象作业媒体 文件
	 * @param flightWorkId
	 * @param objMediaIds
	 * @throws Exception 
	 */
	@Transactional
	public void saveObjectWorkMedia(String flightWorkId, List<String> objMediaIds) throws Exception {
		//先把历史的删除
//		collectDataService.remove(CommonUtils.getEqQueryWrapper("work_uuid",flightWorkId));
		UpdateWrapper<CollectData> Wrapper = CommonUtils.getEqUpdateWrapper("work_uuid","is_deleted",flightWorkId, 0);
		Wrapper.set("is_deleted", 1);
		collectDataService.update(Wrapper);
		for(String fileId : objMediaIds) {
			UploadFile uploadFile = uploadFileService.getById(fileId);
			InputStream inputStream = FileUtil.byte2Input(fastDFSClient.downloadFile(uploadFile.getPath()));
			ZipUtil.deleteDir(pointTempPath);
			ZipUtil.deleteDir(zipTemp);//删除临时文件夹下的所有文件
			FileUtil.createDir(pointTempPath);
			FileUtil.createDir(zipTemp);
			boolean bo = ZipUtil.decompressZip(inputStream, pointTempPath, zipTemp+"/" +UUID.randomUUID() + ".zip");
			if(bo) {
				File tmpFile = new File(pointTempPath);
				for (File mediaDir : tmpFile.listFiles()) {
					String fileType = mediaDir.getName();
					if(!"__MACOSX".equals(fileType)) {
						//排序
						File[] listFiles = mediaDir.listFiles();
						Arrays.sort(listFiles,  new Comparator<File>() {
							@Override
							public int compare(File o1, File o2) {
								return o1.getName().compareTo(o2.getName());
							}
						});
						for(int i=0; i<listFiles.length; i++) {
							File mediaFile = listFiles[i];
							if(!".DS_Store".equals(mediaFile.getName())) {
								String collectUuidAndIndex = mediaFile.getName().substring(0,mediaFile.getName().lastIndexOf("."));
								String[] split = collectUuidAndIndex.split("_");
								String collectUuid = split[0];
								UploadFile uploadImgFile = uploadFileService.save(mediaFile);
								CollectData data = new CollectData();
								data.setWorkUuid(flightWorkId);
								data.setCollectUuid(collectUuid);
								data.setFileType(fileType);
								data.setFileUuid(uploadImgFile.getUuid());
								data.setSort(i);
								collectDataService.save(data);
							}
						}
					}
					
				}
			}
		}
	}

	@Transactional
	@Override
	public String changeWorkState(WorkStateDto dto) {
		if(StrategyWorkConstant.WORK_STATUS_DOING.equals(dto.getState())) {
			//下达操作
			FlightWork work = this.getById(dto.getWorkId());
			List<FlightWork> list = this.list(CommonUtils.getEqQueryWrapper("is_deleted", "work_status", "uav_uuid", 0, StrategyWorkConstant.WORK_STATUS_DOING, work.getUavUuid() ));
			if(list.size() > 0) {
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				return "领取失败， 该无人机在"+dtf.format(list.get(0).getFlightDate())+"还有执行中的任务！！！";
			}
			//发kafka
			UavFlightWorkParamDto param = new UavFlightWorkParamDto();
			param.setWorkId(dto.getWorkId());
			List<UavFlightWorkVo> uavWorks = getUavWorks(param);
			if(uavWorks.size() > 0 && uavWorks.get(0).getObjects().size()>0) {
				work.setWorkStatus(dto.getState());
				this.updateById(work);
				String jsonString = JSON.toJSONString(uavWorks.get(0));
//				System.out.println(jsonString);
				log.info("推送无人机常规任务：{}", jsonString);
				kafkaTemplate.send("aiuasWork", work.getUavUuid(), jsonString);
			}else {
				return "领取失败， 该任务信息不全！！！请重点检查该航班与对象、采查任务是否建立好关系，采查任务与算法是否建立好了关系，航班是否上传了文件。";
			}
		}
		
		if(StrategyWorkConstant.WORK_STATUS_EXECUTED.equals(dto.getState()) || StrategyWorkConstant.WORK_STATUS_COMPLETED.equals(dto.getState())) {
			//撤销、飞行结束操作
			FlightWork work = new FlightWork();
			work.setUuid(dto.getWorkId());
			work.setWorkStatus(dto.getState());
			this.updateById(work);
		}
		
		return "true";
	}

	@Override
	public void transmitUrgent(String urgentId) {
		UrgentWorkVo urgentWorkVo = flightWorkMapper.getUrgentWorkVo(urgentId);
		if(ThreadLocalUtil.getResources() != null) {
			urgentWorkVo.setOperateBy(ThreadLocalUtil.getResources().getUuid());
		}
		urgentWorkVo.setFlightDownloadUrl(downloadUrl + urgentWorkVo.getRouteFileId());//航线文件下载路径
		String jsonString = JSON.toJSONString(urgentWorkVo);
		log.info("推送无人机紧急调度任务：{}", jsonString);
		kafkaTemplate.send("aiuasWork", urgentWorkVo.getUavId(), jsonString);
	}

	@Override
	public Map getRouteByFlightWork(String flightWorkId) {
		Map routeByFlightWork = flightWorkMapper.getRouteByFlightWork(flightWorkId);

		UploadFile uploadFile = iUploadFileService.getById((String) routeByFlightWork.get("routeFileUuid"));
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
			switch (mode) {
				case 1:
				case 2:
					routeByFlightWork.put("lineType", "area");
					JSONObject flyConfig = jsonObject.getJSONObject("flyConfig");
					JSONObject outputParamter = jsonObject.getJSONObject("outputParamter");
					routeByFlightWork.put("pitch", flyConfig.getString("pitch"));
					routeByFlightWork.put("playSpace", outputParamter.getString("playSpace"));
					break;
				case 3:
					routeByFlightWork.put("lineType", "track");
					StringBuffer flyHeights = new StringBuffer();
					StringBuffer flySpeeds = new StringBuffer();
					StringBuffer yaws = new StringBuffer();
					StringBuffer pitchs = new StringBuffer();

					JSONArray path = jsonObject.getJSONArray("path");
					path.stream().forEach(p -> {
						JSONObject jsonObj = (JSONObject) p;
						flyHeights.append(jsonObj.get("flyHeight")).append(";");
						flySpeeds.append(jsonObj.get("flySpeed")).append(";");
						yaws.append(jsonObj.get("yaw")).append(";");
						pitchs.append(jsonObj.get("pitch")).append(";");
					});

					routeByFlightWork.put("flyHeights", flyHeights);
					routeByFlightWork.put("flySpeeds", flySpeeds);
					routeByFlightWork.put("yaws", yaws);
					routeByFlightWork.put("pitchs", pitchs);
					break;
			}
		}

		return routeByFlightWork;
	}

	@Override
	public List<Map> getWorkCountByMonth(String pageType, String month) {
		String[] workStatus = new String[0];
		if(CommonUtils.objNotEmpty(pageType)){
			if(pageType.equals("flightArrangement")){
				workStatus = new String[]{StrategyWorkConstant.WORK_STATUS_EXECUTED,
						StrategyWorkConstant.WORK_STATUS_COMPLETED, StrategyWorkConstant.WORK_STATUS_DOING};
			}else{
				workStatus = new String[]{StrategyWorkConstant.WORK_STATUS_DONE,
						StrategyWorkConstant.WORK_STATUS_CANCEL};
			}
		}

		return flightWorkMapper.getWorkCountByMonth(workStatus, month);
	}

	/**
	 * 组装策略航班列表
	 * @Author: shizt
	 * @Date: 2020/3/6 15:15
	 */
//	private Map packageFlightList(List<FlightWorkPageVo> list){
//		Map result = new HashMap();
//		Map flightData;
//		for (FlightWorkPageVo f: list) {
//			flightData = new HashMap<>();
//			flightData.put("flightUuid", f.getFlightUuid());
//			flightData.put("flightName", f.getFlightName());
//			result.put(f.getStrategyName(), flightData);
//		}
//
//		return result;
//	}

}
