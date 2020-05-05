package com.chinobot.aiuas.bot_prospect.warning2info.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.aiuas.bot_collect.task.entity.Task;
import com.chinobot.aiuas.bot_collect.task.entity.Unit;
import com.chinobot.aiuas.bot_collect.task.service.ITaskService;
import com.chinobot.aiuas.bot_collect.task.service.IUnitService;
import com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant;
import com.chinobot.aiuas.bot_collect.warning.entity.EventInfo;
import com.chinobot.aiuas.bot_collect.warning.entity.Warning;
import com.chinobot.aiuas.bot_collect.warning.entity.WarningInfo;
import com.chinobot.aiuas.bot_collect.warning.service.IEventInfoService;
import com.chinobot.aiuas.bot_collect.warning.service.IWarningInfoService;
import com.chinobot.aiuas.bot_collect.warning.service.IWarningService;
import com.chinobot.aiuas.bot_prospect.warning2info.entity.dto.DeptAreaDTO;
import com.chinobot.aiuas.bot_prospect.warning2info.entity.dto.DeptGridDTO;
import com.chinobot.aiuas.bot_prospect.warning2info.service.IWarning2InfoService;
import com.chinobot.aiuas.bot_resource.grid.mapper.ReGridMapper;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.utils.Base64Utils;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.GPSUtil;
import com.chinobot.common.utils.HttpUtils;
import com.chinobot.drools.constant.DroolsConstant;
import com.chinobot.drools.executor.RuleExecutor;
import com.chinobot.plep.home.dataset.entity.DataSet;
import com.chinobot.plep.home.dataset.entity.Rule;
import com.chinobot.plep.home.dataset.mapper.WhiteListMapper;
import com.chinobot.plep.home.dataset.service.IDataSetService;
import com.chinobot.plep.home.dataset.service.IJdbcUtilService;
import com.chinobot.plep.home.dataset.service.IRuleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Warning2InfoServiceImpl implements IWarning2InfoService {

	@Autowired
	private IWarningService warningService;
	
	@Autowired
	private ITaskService taskService;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
	@Autowired
	private IFileBusService fileBusService;
	
	@Autowired
	private IRuleService ruleService;
	
	@Autowired
	private IDataSetService dataSetService;
	
	@Autowired
	private IJdbcUtilService jdbcUtilService;
	
	@Autowired
	private WhiteListMapper whiteListMapper;
	
	@Autowired
	private IWarningInfoService warningInfoService;
	
	@Autowired
	private IEventInfoService eventInfoService;
	
	@Autowired
	private ReGridMapper gridMapper;
	
	@Autowired
	private IUnitService unitService;
	
	@Transactional
	@Override
	public void receiveWarning(String msg) {
		Warning warning = JSON.parseObject(msg, Warning.class);
		QueryWrapper<Warning> eqQueryWrapper = CommonUtils.getEqQueryWrapper("flight_task_uuid","longitude_origin","latitude_origin","coordinate_x","coordinate_y","indicia_w","indicia_h",
				warning.getFlightTaskUuid(), warning.getLongitudeOrigin(), warning.getLatitudeOrigin(), warning.getCoordinateX(), warning.getCoordinateY(), warning.getIndiciaW(), warning.getIndiciaH());
		List<Warning> list = warningService.list(eqQueryWrapper);
		String failReason = "";
		if(list.size()>0) {
			return ;
		}
		if(StringUtils.isBlank(warning.getTaskUuid())) {
			failReason += "采查任务主键为空！";
		}
		Task task = taskService.getById(warning.getTaskUuid());
		if(task == null) {
			failReason += "无效采查任务主键！";
		}else {
			//预警名称
			warning.setWarningName(task.getResultName());
			//预警类型
			warning.setWarningType(task.getResultType());
		}
		
		if(warning.getLatitudeOrigin() !=null && warning.getLongitudeOrigin()!=null) {
			//坐标需要转换
			double[] gd = GPSUtil.gps84_To_Gcj02(warning.getLatitudeOrigin(), warning.getLongitudeOrigin());
			warning.setLatitude(gd[0]);
			warning.setLongitude(gd[1]);
			//预警地址
			String address = HttpUtils.lnglatToAddress(warning.getLongitude().toString(), warning.getLatitude().toString());
			if(address.equals("[]")) {
				address = "";	
				log.error("not found address: {},{}" + warning.getLongitude().toString(), warning.getLatitude().toString());
			}
			warning.setAddress(address);
		}else {
			failReason += "预警经纬度为空！";
		}		
		//预警时间
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		warning.setWarningTime(LocalDateTime.parse(warning.getWarningTimeStr(), formatter));
		//判断部分必要数据是否为空
		if(StringUtils.isBlank(warning.getCollectObjectUuid())) {
			failReason += "采查对象主键为空！";
		}
		if(StringUtils.isBlank(warning.getAlgorithmInfoUuid())) {
			failReason += "算法主键为空！";
		}
		
		if(!"".equals(failReason)) {
			warning.setFailReason(failReason);
			warning.setBusinessStatus(GlobalConstant.FAIL_TYPE);
		}
		List<Warning> listAgain = warningService.list(eqQueryWrapper);
		if(listAgain.size()>0) {
			return ;
		}
		warningService.save(warning);
		//预警图片
		UploadFile imgFile = uploadWarningFile(warning.getWarningImg());
		if(imgFile != null) {
			FileBus fb = new FileBus();
			fb.setBusId(warning.getUuid());
			fb.setFileId(imgFile.getUuid());
			fb.setModule(GlobalConstant.WARN_IMG);
			fileBusService.save(fb);
		}
		//基准图片
		if(StringUtils.isNotBlank(warning.getBaseImg())) {
			UploadFile baseImgFile = uploadWarningFile(warning.getBaseImg());
			if(baseImgFile != null) {
				FileBus fb = new FileBus();
				fb.setBusId(warning.getUuid());
				fb.setFileId(baseImgFile.getUuid());
				fb.setModule(GlobalConstant.BASE_IMG_WARN);
				fileBusService.save(fb);
			}
		}
		//TODO 预警视频
		
		
		if(!"".equals(failReason)) {
			return ;//预警已经失败不进行过滤
		}
		//预警过滤
		if(!warningFilter(warning.getUuid())) {
			//事件分拨
			eventAllocation(warning.getUuid());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean warningFilter(String warningId) {
		log.info("过滤预警：{}",warningId);
		Warning warning = warningService.getById(warningId);
		List<Rule> rules = ruleService.list(CommonUtils.getEqQueryWrapper("task_id","data_status","status",warning.getTaskUuid(), "1", "1"));
		List<Rule> rulesGlobal = ruleService.list(CommonUtils.getEqQueryWrapper("is_global","data_status","status","1", "1", "1"));//全局
		List<String> setIds = new ArrayList<String>();
		List<String> setIdsGlobal = new ArrayList<String>();//全局
		for(Rule rule : rules) {
			if(!setIds.contains(rule.getSetId())) {
				setIds.add(rule.getSetId());
			}
		}
		for(Rule rule : rulesGlobal) {//全局
			if(!setIdsGlobal.contains(rule.getSetId())) {
				setIdsGlobal.add(rule.getSetId());
			}
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("warningId", warningId);
		param.put("warning_id", warningId);
		String filterType = "0";
		String successId = null;//成功的规则或白名单id
		for(String setId : setIdsGlobal) {//全局
			DataSet dataSet = dataSetService.getById(setId);
			List<Map<String,Object>> result = jdbcUtilService.getResult(dataSet.getSqlContent(), param);
			if(result != null && !result.isEmpty()) {
				//groupCode=数据集id
				Map<String, Object> map = result.get(0);
				map.put("droolsFilter", false);
				RuleExecutor.execute(map, setId);
				if((boolean) map.get("droolsFilter")) {
					//通过了规则
					filterType = "2";
					successId = (String) map.get("ruleId");
					log.info("预警{}通过规则，规则id：{}, 数据：{}",warningId, successId, map);
					break;
				}
			}
		}
		if("0".equals(filterType)) {
			for(String setId : setIds) {
				DataSet dataSet = dataSetService.getById(setId);
				List<Map<String,Object>> result = jdbcUtilService.getResult(dataSet.getSqlContent(), param);
				if(result != null && !result.isEmpty()) {
					//groupCode=数据集id#任务ID
					Map<String, Object> map = result.get(0);
					map.put("droolsFilter", false);
					RuleExecutor.execute(map, setId+"#"+warning.getTaskUuid());
					if((boolean) map.get("droolsFilter")) {
						//通过了规则
						filterType = "2";
						successId = (String) map.get("ruleId");
						log.info("预警{}通过规则，规则id：{}, 数据：{}",warningId, successId, map);
						break;
					}
				}
			}
		}
		
		//规则过滤结束，开始白名单过滤
		log.info("规则过滤结束");
		if("0".equals(filterType)) {
			//规则过滤失败，进行白名单过滤
			String whiteIds = whiteListMapper.getFilterWhiteIdsNew(DroolsConstant.getWhiteParam(), warning);
			if(StringUtils.isNotBlank(whiteIds)) {
				filterType = "3";
				successId = whiteIds;
				log.info("预警{}属于白名单：{}",warningId, whiteIds);
			}
		}
		//白名单过滤结束
		log.info("白名单过滤结束");
		if(!"0".equals(filterType)) {
			warning.setFilterRule(successId);
			warning.setBusinessStatus(filterType);
			warningService.updateById(warning);
			return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void eventAllocation(String warningId) {
		log.info("开始事件分派");
		Warning warning = warningService.getById(warningId);
		EventInfo info = new EventInfo();
		WarningInfo warningInfo = new WarningInfo();
		info.setEventName(warning.getWarningName());
		info.setEventType(warning.getWarningType());
		Task task = taskService.getById(warning.getTaskUuid());
		info.setEventLevel(task.getDangerGrade());
		info.setBusinessStatus(GlobalConstant.TO_BE_CONFIRMED);
		info.setEventSource(GlobalConstant.SF_SOURCE);
		QueryWrapper eqQueryWrapper = CommonUtils.getEqQueryWrapper("is_deleted","task_uuid", 0, task.getUuid());
		eqQueryWrapper.isNotNull("organization");
		List<Unit> units = unitService.list(eqQueryWrapper);
		warning.setBusinessStatus(GlobalConstant.WARN_TYPE);
		String failReasonAll = "";
		for(Unit unit : units) {
			String findRst = findDeptOrPerson(info, unit, warning.getLongitude(), warning.getLatitude());
			if(!"1".equals(findRst)) {
				String failReason = "";
				if("2".equals(findRst)) {
					failReason += ", 找不到！";
				}
				if("3".equals(findRst)) {
					failReason += ", 找到多个！";
				}
				if(GlobalConstant.GUARD_UNIT_TYPE.equals(unit.getUnitType())) {//防范
					failReason = "防范单位分派失败" + failReason;
				}
				if(GlobalConstant.GOVERN_UNIT_TYPE.equals(unit.getUnitType())) {//治理
					failReason = "治理单位分派失败" + failReason;
				}
				if(GlobalConstant.RESCUE_UNIT_TYPE.equals(unit.getUnitType())) {//救援
					failReason = "救援单位分派失败" + failReason;
				}
				failReasonAll += failReason + ";";
			}
		}
		if(!"".equals(failReasonAll)) {
			warning.setFailReason(failReasonAll);
			warning.setBusinessStatus(GlobalConstant.FAIL_TYPE);
		}
		warningService.updateById(warning);
		eventInfoService.save(info);
		//预警与事件关系
		warningInfo.setEventUuid(info.getUuid());
		warningInfo.setWarningUuid(warningId);
		warningInfoService.save(warningInfo);
		//预警图片
		FileBus warningfileBus = fileBusService.getOne(CommonUtils.getEqQueryWrapper("data_status","module","bus_id", "1", GlobalConstant.WARN_IMG, warningId));
		//绑定预警图片
		if(warningfileBus != null) {
			FileBus fb = new FileBus();
			fb.setBusId(info.getUuid());
			fb.setFileId(warningfileBus.getFileId());
			fb.setModule(GlobalConstant.YJMODULE_IMG);
			fileBusService.save(fb);
		}
		log.info("事件分派结束");
		//TODO 预警视频
	}

	/**
	 * 根据经纬度定位主办机构
	 * @param info
	 * @param unit
	 * @param lng
	 * @param lat
	 */
	String findDeptOrPerson(EventInfo info, Unit unit, Double lng, Double lat){
		String deptId = null;//主办机构
		String personId = null;//主办人
		String gridId = null;//网格
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("organization", unit.getOrganization());
		param.put("lng", lng);
		param.put("lat", lat);
		if((GlobalConstant.AREA_UNIT_PERSON.equals(unit.getUnitPersonType()) || GlobalConstant.STREET_UNIT_PERSON.equals(unit.getUnitPersonType()) 
				|| GlobalConstant.COMMUNITY_UNIT_PERSON.equals(unit.getUnitPersonType())) && StringUtils.isNotBlank(unit.getOrganization())) {
			//区、街道、社区
			param.put("level", unit.getUnitPersonType());
			List<DeptAreaDTO> dtos = gridMapper.findAreaForEvent(param);
			if(dtos.size()>1) {
				return "3";//分派失败,找到多个；
			}
			if(dtos.size()>0) {
				deptId = dtos.get(0).getDeptId();
			}
		}
		if(GlobalConstant.GRID_UNIT_PERSON.equals(unit.getUnitPersonType()) && StringUtils.isNotBlank(unit.getOrganization())) {
			//网格
			List<DeptGridDTO> dtos = gridMapper.findGridForEvent(param);
			if(dtos.size()>1) {
				return "3";//分派失败,找到多个；
			}
			if(dtos.size()>0) {
				deptId = dtos.get(0).getDeptId();
				gridId = dtos.get(0).getGridId();
			}
		}
		if(GlobalConstant.ASSIGN_UNIT_PERSON.equals(unit.getUnitPersonType())) {
			//指定人
			personId = unit.getUnitPersonUuid();
		}
		if(GlobalConstant.GUARD_UNIT_TYPE.equals(unit.getUnitType())) {//防范
			info.setHostUnit(deptId);
			info.setHostBy(personId);
			info.setGrid(gridId);
		}
		if(GlobalConstant.GOVERN_UNIT_TYPE.equals(unit.getUnitType())) {//治理
			info.setHostUnitGovern(deptId);
			info.setHostByGovern(personId);
			info.setGridGovern(gridId);			
		}
		if(GlobalConstant.RESCUE_UNIT_TYPE.equals(unit.getUnitType())) {//救援
			info.setHostUnitRescue(deptId);
			info.setHostByRescue(personId);
			info.setGridRescue(gridId);
		}
		log.info("单次分派结果：infoId-{},unitType-{},unitPersonType-{},organization-{},lng-{},lat-{}, deptId-{}, gridId-{}, personId-{}", info.getUuid(),
				unit.getUnitType(), unit.getUnitPersonType(), unit.getOrganization(), lng, lat, deptId, gridId, personId);
		if(StringUtils.isBlank(deptId) && StringUtils.isBlank(personId)) {
			return "2";//分派失败,找不到；
		}
		return "1";
	}
	/**
	 * 上传base64的预警图片
	 * @param base64Img
	 * @return
	 */
	private UploadFile uploadWarningFile(String base64Img) {
		try {
			File tmpFile = File.createTempFile(CommonUtils.getUUID(), ".jpg");	//创建临时文件
			Base64Utils.Base64ToImage(base64Img, tmpFile.getAbsolutePath());	//生成临时图片内容
			List<UploadFile> files = uploadFileService.save(new File[] {tmpFile});//上传图片文件
			return files.get(0);
		} catch (Exception e) {
			log.error("上传预警图片出错：{}", e);
		}
		return null;
	}
}
