package com.chinobot.plep.home.event.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.warning.entity.Warning;
import com.chinobot.aiuas.bot_collect.warning.service.IWarningService;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.mapper.PersonMapper;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.mapper.FileBusMapper;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.common.websocket.server.WebSocketServer;
import com.chinobot.drools.constant.DroolsConstant;
import com.chinobot.drools.executor.RuleExecutor;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.building.entity.Village;
import com.chinobot.plep.home.building.mapper.VillageMapper;
import com.chinobot.plep.home.dataset.entity.DataSet;
import com.chinobot.plep.home.dataset.entity.Rule;
import com.chinobot.plep.home.dataset.entity.WarningInfo;
import com.chinobot.plep.home.dataset.mapper.WhiteListMapper;
import com.chinobot.plep.home.dataset.service.IDataSetService;
import com.chinobot.plep.home.dataset.service.IJdbcUtilService;
import com.chinobot.plep.home.dataset.service.IRuleService;
//import com.chinobot.plep.home.dataset.service.IWarningInfoService;
import com.chinobot.plep.home.event.entity.EventMain;
import com.chinobot.plep.home.event.mapper.EventMainMapper;
import com.chinobot.plep.home.event.service.IEventMainService;
import com.chinobot.plep.home.scene.entity.Scene;
import com.chinobot.plep.home.scene.service.ISceneService;

import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 * 事件主表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-06-11
 */
@Slf4j
@Service
public class EventMainServiceImpl extends BaseService<EventMainMapper, EventMain> implements IEventMainService {
	
	@Autowired
	private EventMainMapper eventMainMapper;

	@Autowired
	private PersonMapper personMapper;
	
	@Autowired
	private FileBusMapper fileBusMapper;
	
	@Autowired
	private VillageMapper villageMapper;
	
//	@Autowired
//	private IWarningInfoService warnInfoService;
	
	@Autowired
	private ISceneService sceneService;
	
	@Autowired
	private IRuleService ruleService;
	
	@Autowired
	private IDataSetService dataSetService;
	
	@Autowired
	private IJdbcUtilService jdbcUtilService;
	
	@Autowired
	private WhiteListMapper whiteListMapper;
	
	@Autowired
	private IFileBusService fileBusService;
	
	@Autowired
	private IWarningService warningService;
	
	@Override
	public IPage<Map> getEventList(Page page, Map<String, Object> param) {
		if(!ThreadLocalUtil.isShenz()) {
			param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
		}else if(String.valueOf(param.get("deptId")).equals(GlobalConstant.SUPER_DEPT_ID)){
			param.remove("deptId");
		}
		IPage<Map> list = eventMainMapper.getEventList(page, param);
		for(Map map : list.getRecords()) {
			String uuid = (String) map.get("uuid");
			String source = (String) map.get("source");
			String warningId = (String) map.get("warning_id");
			//对了，那个推送过来的图片和视频是用风险预警表的主键去关联的，不是事件主表，手动录入的才是用事件主表去关联
//			if(source.equals("1")) {
//				map.put("imgList", eventMainMapper.getImgListByBusId(warningId, "warning_img"));//预警图片
//			}else if(source.equals("2")){
				map.put("imgList", eventMainMapper.getImgListByBusId(uuid, "event_img"));//预警图片
//			}
		}
		return list; 
	}

	@Override
	public Map getEventDetail(String eventId) {
		Map map = new HashMap();
//		EventMain eventMain = (EventMain) eventMainMapper.selectById(eventId);
		//预警信息
		Map warningMap = eventMainMapper.getEventWarningInfoById(eventId);
		String source = (String) warningMap.get("source");
		String warningId = (String) warningMap.get("warning_id");
		//对了，那个推送过来的图片和视频是用风险预警表的主键去关联的，不是事件主表，手动录入的才是用事件主表去关联
		if(source.equals("1")) {
			warningMap.put("imgList", eventMainMapper.getImgListByBusId(warningId, "warning_img"));//预警图片
			warningMap.put("vedioList", eventMainMapper.getVedioListByBusId(warningId, "warning_vedio"));//预警视频
		}else if(source.equals("2")){
			warningMap.put("imgList", eventMainMapper.getImgListByBusId(eventId, "event_img"));//预警图片
			warningMap.put("vedioList", eventMainMapper.getVedioListByBusId(eventId, "event_vedio"));//预警视频
		}
		
		List<Map> patrolList = eventMainMapper.getPatrolListById(eventId);
		for(Map patrol : patrolList) {
			String patrolId = (String)patrol.get("uuid");
			patrol.put("imgList", eventMainMapper.getImgListByBusId(patrolId, "patrol_img"));//巡查图片
			patrol.put("vedioList", eventMainMapper.getVedioListByBusId(patrolId, "patrol_vedio"));//巡查视频
		}
		warningMap.put("patrolList", patrolList);
		
		List<Map> reformList = eventMainMapper.getReformListById(eventId);
		for(Map reform : reformList) {
			String reformId = (String)reform.get("uuid");
			reform.put("imgList", eventMainMapper.getImgListByBusId(reformId, "reform_img"));//整改图片
			reform.put("vedioList", eventMainMapper.getVedioListByBusId(reformId, "reform_vedio"));//整改视频
		}
		warningMap.put("reformList", reformList);
		
//		warningMap.put("buildingList", buildingService.getNearbyBuilding(param));//周边建筑
		warningMap.put("personList", getPersonList());//巡查人员
		return warningMap;
	}

	@Override
	public void handle(Map<String, Object> param) {
		// TODO Auto-generated method stub
		try {
			String uuid = String.valueOf(param.get("uuid"));
			EventMain event = (EventMain) eventMainMapper.selectById(uuid);
			String hs = String.valueOf(param.get("hs"));
			event.setDescription(String.valueOf(param.get("description")));
			event.setStatus(String.valueOf(param.get("hs")));
			if(hs.equals("1")) {
				event.setVillageId(String.valueOf(param.get("villageId")));
				event.setEventCategory(String.valueOf(param.get("eventCategory")));
				event.setEventName(String.valueOf(param.get("eventName")));
				event.setEventDescription(String.valueOf(param.get("eventDescription")));
				event.setIsIndoor(String.valueOf(param.get("indoor")));
				event.setSuppleAddress(String.valueOf(param.get("suppleAddress")));
				event.setConstructionType(String.valueOf(param.get("constructionType")));
				event.setBuildingMaterials(String.valueOf(param.get("buildingMaterials")));
				if(!CommonUtils.isObjEmpty(param.get("floorArea"))) {
					event.setFloorArea(Float.valueOf(String.valueOf(param.get("floorArea"))));
				}
				if(!CommonUtils.isObjEmpty(param.get("layersNumber"))) {
					event.setFloorArea(Float.valueOf(String.valueOf(param.get("layersNumber"))));
				}
				event.setLiveSituation(String.valueOf(param.get("liveSituation")));
				event.setImportant(String.valueOf(param.get("important")));
				event.setLinkNow("2");
				event.setPeronPatrol(String.valueOf(param.get("patrol")));
			}
			eventMainMapper.updateById(event);	//更新事件数据
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public List<Person> getPersonList() {
		// TODO Auto-generated method stub
		Person person = ThreadLocalUtil.getResources();
		person = (Person) personMapper.selectById(person.getUuid());
//		QueryWrapper qw = new QueryWrapper(); 
//		qw.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//		qw.eq("dept_id", person.getDeptId());
//		List<Person> list = personMapper.selectList(qw);
		Map param = new HashMap();
		param.put("deptId", person.getDeptId());
		List<Person> list = personMapper.getPersonBydeptAndChild(param);
		return list;
	}

	@Override
	public void add(Map<String, Object> param) {
		// TODO Auto-generated method stub
		EventMain event = new EventMain();
		event.setLatitude(Double.parseDouble((String) param.get("latitude")));
		event.setLongitude(Double.parseDouble((String) param.get("longitude")));
		event.setVillageId(String.valueOf(param.get("villageId")));
		event.setEventCategory(String.valueOf(param.get("eventCategory")));
		event.setEventName(String.valueOf(param.get("eventName")));
		event.setEventDescription(String.valueOf(param.get("eventDescription")));
		event.setIsIndoor(String.valueOf(param.get("indoor")));
		event.setSuppleAddress(String.valueOf(param.get("suppleAddress")));
		event.setConstructionType(String.valueOf(param.get("constructionType")));
		event.setBuildingMaterials(String.valueOf(param.get("buildingMaterials")));
		if(!CommonUtils.isObjEmpty(param.get("floorArea"))) {
			event.setFloorArea(Float.valueOf(String.valueOf(param.get("floorArea"))));
		}
		if(!CommonUtils.isObjEmpty(param.get("layersNumber"))) {
			event.setFloorArea(Float.valueOf(String.valueOf(param.get("layersNumber"))));
		}
		event.setLiveSituation(String.valueOf(param.get("liveSituation")));
		event.setImportant(String.valueOf(param.get("important")));
		event.setSource("2");
		event.setLinkNow("3");
		event.setStatus("1");
		event.setPersonReform(String.valueOf(param.get("patrol")));
		Person person = ThreadLocalUtil.getResources();
		person = (Person) personMapper.selectById(person.getUuid());
		event.setDeptId(person.getDeptId());
		eventMainMapper.insert(event);		//先保存事件信息
		String eventUuid = event.getUuid();
//		ReformRecord rr = new ReformRecord();
//		rr.setEventId(eventUuid);
//		rr.setType("1");
//		reformRecordMapper.insert(rr);		//生成整改记录
		List<Map> files = (List<Map>) param.get("fileBus");
		int sort = 0;
		for (Map map : files) {
			FileBus fb = new FileBus();
			fb.setBusId(eventUuid);
			String fileName = String.valueOf(map.get("fileName"));
			if(fileName.toLowerCase().endsWith("flv") || fileName.toLowerCase().endsWith("mkv") || fileName.toLowerCase().endsWith("mpg") 
					|| fileName.toLowerCase().endsWith("mpeg") || fileName.toLowerCase().endsWith("mp4") || fileName.toLowerCase().endsWith("3gp")
					|| fileName.toLowerCase().endsWith("mov") || fileName.toLowerCase().endsWith("rm") || fileName.toLowerCase().endsWith("rmvb")
					|| fileName.toLowerCase().endsWith("swf") || fileName.toLowerCase().endsWith("avi") || fileName.toLowerCase().endsWith("asf")
					|| fileName.toLowerCase().endsWith("asx") ) {
				fb.setModule("event_vedio");	//视频
			}else {
				fb.setModule("event_img");		//图片
			}
			fb.setSort(sort+1);
			fb.setFileId(String.valueOf(map.get("fileId")));
			fileBusMapper.insert(fb);		//保存事件图片
			sort++;
		}
	}

	@Override
	public List<Map> getAllDeptPosition() {
		return eventMainMapper.getAllDeptPosition();
	}

	@Override
	public IPage<Map> getEventPage2(Page page, Map<String, Object> param) {
		if(!ThreadLocalUtil.isSystemRole()) {
			param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
		}
		return eventMainMapper.getEventPage2(page,param);
	}

	@Override
	public Map addInit() {
		// TODO Auto-generated method stub
		Map map = new HashMap();
		map.put("personList", getPersonList());
		List<Village> villageList = villageMapper.selectList(null);
		map.put("villageList", villageList);
		return map;
	}

	/**
	 * 预警过滤
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings("unchecked")
	@Override
	public void warningFilter(String warningId) throws Exception {
		//先进行规则过滤，通过后进行白名单过滤
		//规则过滤：先查出预警关联的场景，再查出场景关联的规则，再查出规则关联的数据集 
//		log.info("过滤预警：{}",warningId);
//		WarningInfo warningInfo = warnInfoService.getById(warningId);
//		Scene scene = sceneService.getSceneByDomainCodeAndSceneCode(warningInfo.getSceneType(), warningInfo.getEarlyType());
//		List<Rule> rules = ruleService.list(CommonUtils.getEqQueryWrapper("scene_id",scene.getUuid()));
//		List<String> setIds = new ArrayList<String>();
//		for(Rule rule : rules) {
//			if(!setIds.contains(rule.getSetId())) {
//				setIds.add(rule.getSetId());
//			}
//		}
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("warningId", warningId);
//		param.put("warning_id", warningId);
//		String filterType = "1";
//		String successId = null;//成功的规则或白名单id
//		for(String setId : setIds) {
//			DataSet dataSet = dataSetService.getById(setId);
//			List<Map<String,Object>> result = jdbcUtilService.getResult(dataSet.getSqlContent(), param);
//			if(result != null && !result.isEmpty()) {
//				//groupCode=数据集id#场景ID
//				Map<String, Object> map = result.get(0);
//				map.put("droolsFilter", false);
//				RuleExecutor.execute(map, setId+"#"+scene.getUuid());
//				if((boolean) map.get("droolsFilter")) {
//					//通过了规则
//					filterType = "2";
//					successId = (String) map.get("ruleId");
//					log.info("预警{}通过规则，规则id：{}, 数据：{}",warningId, successId, map);
//					break;
//				}else {
//					log.info("预警{}未通过规则，数据：{}",warningId, map);
//				}
//			}
//		}
//		//规则过滤结束，开始白名单过滤
//		log.info("规则过滤结束");
//		if("1".equals(filterType)) {
//			//规则过滤失败，进行白名单过滤
//			String whiteIds = whiteListMapper.getFilterWhiteIds(DroolsConstant.getWhiteParam(), warningInfo);
//			if(StringUtils.isNotBlank(whiteIds)) {
//				filterType = "3";
//				successId = whiteIds;
//				log.info("预警{}属于白名单：{}",warningId, whiteIds);
//			}
//		}
//		warningInfo.setFilterType(filterType);
//		warningInfo.setFilterRule(successId);
//		warnInfoService.updateById(warningInfo);
//		//白名单过滤结束
//		log.info("白名单过滤结束");
//		if("1".equals(filterType)) {
//			//插入事件
//			log.info("开始插入事件");
//			EventMain em = insertEvent(warningInfo);
//			FileBus warningfileBus = fileBusService.getOne(CommonUtils.getEqQueryWrapper("data_status","module","bus_id", "1", "warning_img", warningId));
//			//绑定预警图片
//			FileBus fb = new FileBus();
//			fb.setBusId(em.getUuid());
//			fb.setFileId(warningfileBus.getFileId());
//			fb.setModule("event_img");
//			fileBusService.save(fb);
//			//开始推送webSocket
//			JSONObject socketJson = new JSONObject();
//			socketJson.put("earlyInfo", em);
//			socketJson.put("imgId", warningfileBus.getFileId());
//			JSONObject jsb = new JSONObject ();
//			jsb.put ("key", "homeEarly");
//			jsb.put ("msg", socketJson);
//			WebSocketServer.sendInfo("homeEarly", jsb.toJSONString());
//			//结束推送webSocket
//		}
	}
	/**
	 * 过滤预警移除
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void warningFilterRemove(String warningId) throws Exception {
		Warning warning = warningService.getById(warningId);
		warning.setFilterRemove("1");
		warningService.updateById(warning);
		//TODO 预警转事件
//		WarningInfo warningInfo = warnInfoService.getById(warningId);
//		warningInfo.setFilterRemove("1");
//		warnInfoService.updateById(warningInfo);
//		EventMain em = insertEvent(warningInfo);
//		FileBus warningfileBus = fileBusService.getOne(CommonUtils.getEqQueryWrapper("data_status","module","bus_id", "1", "warning_img", warningId));
//		//绑定预警图片
//		FileBus fb = new FileBus();
//		fb.setBusId(em.getUuid());
//		fb.setFileId(warningfileBus.getFileId());
//		fb.setModule("event_img");
//		fileBusService.save(fb);
//		//开始推送webSocket
//		JSONObject socketJson = new JSONObject();
//		socketJson.put("earlyInfo", em);
//		socketJson.put("imgId", warningfileBus.getFileId());
//		JSONObject jsb = new JSONObject ();
//		jsb.put ("key", "homeEarly");
//		jsb.put ("msg", socketJson);
//		WebSocketServer.sendInfo("homeEarly", jsb.toJSONString());
		//结束推送webSocket
	}
	/**
	 * 插入事件
	 * @param warningInfo
	 * @throws Exception 
	 */
	private EventMain insertEvent(WarningInfo warningInfo) throws Exception {
		EventMain em = new EventMain();
		em.setWarningId(warningInfo.getUuid());
		em.setEarlyType(warningInfo.getEarlyType());
		em.setVersion(warningInfo.getVersion());
		em.setAccuracy(warningInfo.getAccuracy());
		em.setSceneType(warningInfo.getSceneType());
		em.setAddress(warningInfo.getAddress());
		em.setDisPerson(warningInfo.getDisPerson());
		em.setPointId(warningInfo.getPointId());
		em.setPathId(warningInfo.getPathId());
		em.setLatitude(warningInfo.getLatitude());
		em.setLongitude(warningInfo.getLongitude());
		em.setDeptId(warningInfo.getDeptId());
		em.setStatus("0");
		em.setTaskId(warningInfo.getTaskId());
		this.save(em);
		return em;
	}

	
}
