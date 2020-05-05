package com.chinobot.aiuas.bot_collect.warning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.chinobot.aiuas.bot_collect.warning.entity.EventFlow;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.QuestionWarningDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfAreaLocation;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfBoundary;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfDeptStatusVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfFlightVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfFlyVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfWarnDeptVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfWarnLocationVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfWarnSceneVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfWarnStatusVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfWarnVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningOfWranAllocateVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.QuestionWarningVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.SearchParamVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.SearchParamsVo;
import com.chinobot.aiuas.bot_collect.warning.mapper.EventFlowMapper;
import com.chinobot.aiuas.bot_collect.warning.service.IEventFlowService;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.mapper.DeptMapper;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.TimeUtil;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.area.entity.GovArea;
import com.chinobot.plep.home.area.mapper.GovAreaMapper;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 预警事件流转记录 服务实现类
 * </p>
 *
 * @author djl
 * @since 2020-02-24
 */
@Service
public class EventFlowServiceImpl extends BaseService<EventFlowMapper, EventFlow> implements IEventFlowService {

	@Autowired
	private EventFlowMapper eventFlowMapper;
	@Autowired
	private GovAreaMapper govAreaMapper;
	@Autowired
	private DeptMapper deptMapper;
	
	
	@Override
	public SearchParamVo getSerachParam() {
		SearchParamVo vo = new SearchParamVo();
		
		//场景
		Map sceneMap = new HashMap();
		List<Map> sceneList = eventFlowMapper.getSceneList();
		for (Map map : sceneList) {
			sceneMap.put(map.get("key"), map.get("names"));
		}
		vo.setSceneMap(sceneMap);
		
		//区划
		Map areaMap = new HashMap();
		List<Map> areaList = eventFlowMapper.getAreaList();
		for (Map map : areaList) {
			areaMap.put(map.get("key"), map.get("names"));
		}
		vo.setAreaMap(areaMap);
		
		//部门
		Map deptMap = new HashMap();
		List<Map> deptList = eventFlowMapper.getDeptList();
		for (Map map : deptList) {
			deptMap.put(map.get("key"), map.get("names"));
		}
		vo.setDeptMap(deptMap);
		
		return vo;
	}

	@Override
	public QuestionWarningVo getWarningData(QuestionWarningDTO dto) {
		
		SearchParamVo serachParam = getSerachParam();
		
		QuestionWarningVo vo = new QuestionWarningVo();
		//封装查询条件
		toPackSearch(dto,serachParam);
		//飞行采查情况
		List<QuestionWarningOfFlyVo> flyDataVo = eventFlowMapper.getFlyData(dto);
		vo.setFlyDataVo(flyDataVo);
		//问题预警情况
		QuestionWarningOfWarnVo warnVo = getWarnData(dto,serachParam);
		vo.setWarnVo(warnVo);
		//预警信息的经纬度以及类型
		List<QuestionWarningOfWarnLocationVo> locationVo = eventFlowMapper.getWarnLocation(dto);
		vo.setLocationVo(locationVo);
		//分拨处置情况
		QuestionWarningOfWranAllocateVo warnAllocate = getWarnAllocate(dto);
		vo.setAllocateVo(warnAllocate);
		//问题解决情况
		QuestionWarningOfWarnStatusVo warnStatusVo = getWarnDeptStatus(dto);
		vo.setWarnStatusVo(warnStatusVo);
		//问题预警总况-在飞航班总数，已非航班总数...
		QuestionWarningOfFlightVo flightVo = getFlightMessage(dto);;
		vo.setFlightVo(flightVo);
		//四个区划的名称与经纬度
		List<QuestionWarningOfAreaLocation> areaVo = getAreaLocation();
		vo.setAreaLocation(areaVo);
		
		return vo;
	}
    
	/**
	 * 四个区划的名称与经纬度
	 * @return
	 */
	private List<QuestionWarningOfAreaLocation> getAreaLocation() {
		
		List<QuestionWarningOfAreaLocation> list = new ArrayList<QuestionWarningOfAreaLocation>();
		QuestionWarningOfAreaLocation area = new QuestionWarningOfAreaLocation();
		area.setName("小漠镇"); 
		area.setLatitude(22.774984);
		area.setLongitude(115.038400);
		list.add(area);
		
		QuestionWarningOfAreaLocation area2 = new QuestionWarningOfAreaLocation();
		area2.setName("鲘门镇"); 
		area2.setLatitude(22.811299);
		area2.setLongitude(115.162209);
		list.add(area2);
		
		QuestionWarningOfAreaLocation area3 = new QuestionWarningOfAreaLocation();
		area3.setName("鹅埠镇"); 
		area3.setLatitude(22.825835);
		area3.setLongitude(115.003687);
		list.add(area3);
		
		QuestionWarningOfAreaLocation area4 = new QuestionWarningOfAreaLocation();
		area4.setName("赤石镇"); 
		area4.setLatitude(22.874553);
		area4.setLongitude(115.073538);
		list.add(area4);
		
		return list;
	}

	/**
	 * 问题预警总况-在飞航班总数，已非航班总数...
	 * @param dto
	 * @return
	 */
	private QuestionWarningOfFlightVo getFlightMessage(QuestionWarningDTO dto) {
		
		QuestionWarningOfFlightVo vo = new QuestionWarningOfFlightVo();
		//任务总数 
		Long taskCount = eventFlowMapper.getFlightTaskObjectCount(dto);
		vo.setTaskCount(taskCount);
		//对象总数
		Long objectCount = eventFlowMapper.getObjectCount(dto);
		vo.setObjectCount(objectCount);
		//在飞航班总数
		Long flightingCount = eventFlowMapper.getFlightingCount(dto);
		vo.setFligntingCount(flightingCount);
		//已飞航班总数
		Long flightedCount = eventFlowMapper.getFlightedCount(dto);
		vo.setFlightedCount(flightedCount);
		//场景总数
		vo.setSceneCount(eventFlowMapper.getSceneCount(dto));
		
		
		return vo;
	}

	/**
	 * 问题解决情况数据封装
	 * @return
	 */
	private QuestionWarningOfWarnStatusVo getWarnDeptStatus(QuestionWarningDTO dto) {
		
		QuestionWarningOfWarnStatusVo vo = new QuestionWarningOfWarnStatusVo();
		Long noConfirmCount = (long) 0;
		Long noReformCount = (long) 0;
		Long reformCompleteCount = (long) 0;
		Long reformedCount = (long) 0;
		List<QuestionWarningOfDeptStatusVo> list = eventFlowMapper.getWarnDeptStatus(dto);
		vo.setDeptStatusVo(list);
		for (QuestionWarningOfDeptStatusVo statusVo : list) {
			noConfirmCount += statusVo.getNoConfirmDeptCount();
			noReformCount += statusVo.getNoReformDeptCount();
			reformCompleteCount += statusVo.getReformCompleteDeptCount();
			reformedCount += statusVo.getReformedDeptCount();
		}
		vo.setNoConfirmCount(noConfirmCount);
		vo.setNoReformCount(noReformCount);
		vo.setReformCompleteCount(reformCompleteCount);
		vo.setReformedCount(reformedCount);
		
		Long yesConfirm = noReformCount + noReformCount + reformedCount;
		Long all = noConfirmCount + noReformCount + noReformCount + reformedCount;
		if (all == 0) {
			vo.setConfirmRate((long) 0);
		}else {
			vo.setConfirmRate((yesConfirm*100)/all);
		}
		
		return vo;
	}

	/**
	 * 分拨处置情况数据封装
	 * @param dto
	 * @return
	 */
	private QuestionWarningOfWranAllocateVo getWarnAllocate(QuestionWarningDTO dto) {
		
		QuestionWarningOfWranAllocateVo allocateVo = new QuestionWarningOfWranAllocateVo();
		List<QuestionWarningOfWarnDeptVo> warnDeptVoList = eventFlowMapper.getWarnAllocate(dto);
		allocateVo.setDeptCount(warnDeptVoList.size());
		allocateVo.setWarnDeptVo(warnDeptVoList);
		long warnCount = 0;
		for (QuestionWarningOfWarnDeptVo questionWarningOfWarnDeptVo : warnDeptVoList) {
			warnCount += questionWarningOfWarnDeptVo.getAllocateCount();
		}
		allocateVo.setWarnCount(warnCount);
		return allocateVo;
	}
	
	
	/**
	 * 问题预警情况数据封装
	 * @param dto
	 * @param serachParam
	 * @return
	 */
	private QuestionWarningOfWarnVo getWarnData(QuestionWarningDTO dto,SearchParamVo serachParam) {
		
		List<QuestionWarningOfWarnSceneVo> warnData = eventFlowMapper.getWarnData(dto);
		QuestionWarningOfWarnVo warnVo = new QuestionWarningOfWarnVo();
		
		warnVo.setSceneCount(warnData.size());
		long warnCount = 0;
		warnVo.setWarnSceneVo(warnData);
		for (QuestionWarningOfWarnSceneVo questionWarningOfWarnSceneVo : warnData) {
			warnCount = warnCount + questionWarningOfWarnSceneVo.getSceneWarnCount();
		}
			
		warnVo.setWarnCount(warnCount);
		warnVo.setWarnSceneVo(warnData);
		return warnVo;
	}
	
	
	/**
	 * 封装查询条件
	 * @param timeType
	 */
	private void toPackSearch(QuestionWarningDTO dto,SearchParamVo serachParam) {
		if (dto == null) {
			dto = new QuestionWarningDTO();
		}
		int deptsize = dto.getDeptIdList().size();
		if (deptsize == 0) {
			//当部门为空时，查机构
			for(String key:serachParam.getDeptMap().keySet()) {
				//根据部门（机构类型）的code，获取所有属于 机构类型下的部门的级联code
				List<Dept> deptList = new LambdaQueryChainWrapper<Dept>(deptMapper).eq(Dept::getOrganization, key).eq(Dept::getDataStatus, GlobalConstant.DATA_STATUS_VALID).list();
				for(Dept dept:deptList) {
					dto.getDeptIdList().add(dept.getDeptCode());
				}
			}
		}else {
			List<String> deptIdList = dto.getDeptIdList();
			List<String> list = new ArrayList<String>();
			for(String key :deptIdList) {
				//根据部门（机构类型）的code，获取所有属于 机构类型下的部门的级联code
				List<Dept> deptList = new LambdaQueryChainWrapper<Dept>(deptMapper).eq(Dept::getOrganization, key).eq(Dept::getDataStatus, GlobalConstant.DATA_STATUS_VALID).list();
				for(Dept dept:deptList) {
					list.add(dept.getDeptCode());
				}
				
			}
			dto.setDeptIdList(list);
		}
			
		
		//根据时间类型算出开始时间与结束时间
		String timeType = dto.getTimeType();
		LocalDateTime start = null;
		LocalDateTime end = null;
		switch (timeType) {
		case "day":
			
			start = TimeUtil.getDayBegin().toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
			end = TimeUtil.getDayEnd().toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
			break;
		case "week":
				
			start = TimeUtil.getBeginDayOfWeek().toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
			end = TimeUtil.getEndDayOfWeek().toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
			break;
		case "month":
			
			start = TimeUtil.getBeginDayOfMonth().toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
			end = TimeUtil.getEndDayOfMonth().toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
			break;
		case "quarter":
			
			start = TimeUtil.getCurrentQuarterStartTime().toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
			end = TimeUtil.getCurrentQuarterEndTime().toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
			break;
		case "year":
			
			start = TimeUtil.getBeginDayOfYear().toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
			end = TimeUtil.getEndDayOfYear().toInstant().atZone(ZoneOffset.ofHours(8)).toLocalDateTime();
			break;
		default:
			break;
		}
		dto.setStart(start);
		dto.setEnd(end);
	}

	@Override
	public List<SearchParamsVo> getSerachParams() {
		
		List<SearchParamsVo> list = new ArrayList<SearchParamsVo>();
		//场景
		SearchParamsVo sceneVo = new SearchParamsVo();
		sceneVo.setKey("scene");
		sceneVo.setName("场景");
		List<Map> sceneList = eventFlowMapper.getSceneList();
		List<Map> listMaps = new ArrayList<Map>();
		for (Map map : sceneList) {
			Map sceneMap = new HashMap();
			sceneMap.put("name", map.get("names"));
			sceneMap.put("key", map.get("key"));
			listMaps.add(sceneMap);
		}
		sceneVo.setList(listMaps);
		list.add(sceneVo);
		
		//区划
		SearchParamsVo areaVo = new SearchParamsVo();
		areaVo.setKey("area");
		areaVo.setName("行政区划");
		List<Map> areaList = eventFlowMapper.getAreaList();
		List<Map> listAreaMaps = new ArrayList<Map>();
		for (Map map : areaList) {
			Map areaMap = new HashMap();
			areaMap.put("name", map.get("names"));
			areaMap.put("key", map.get("key"));
			listAreaMaps.add(areaMap);
		}
		areaVo.setList(listAreaMaps);
		list.add(areaVo);
		
		//部门
		SearchParamsVo deptVo = new SearchParamsVo();
		deptVo.setKey("dept");
		deptVo.setName("业务机构");
		List<Map> deptList = eventFlowMapper.getDeptList();
		List<Map> listDeptMaps = new ArrayList<Map>();
		for (Map map : deptList) {
			Map deptMap = new HashMap();
			deptMap.put("name", map.get("names"));
			deptMap.put("key", map.get("key"));
			listDeptMaps.add(deptMap);
		}
		deptVo.setList(listDeptMaps);
		list.add(deptVo);
		//时间
		SearchParamsVo timeVo = new SearchParamsVo();
		timeVo.setKey("time");
		timeVo.setName("时间");
		List<Map> listTimeMaps = new ArrayList<Map>();
		Map timeMap1 = new HashMap();
		timeMap1.put("name", "当天");
		timeMap1.put("key", "day");
		listTimeMaps.add(timeMap1);
		Map timeMap2 = new HashMap();
		timeMap2.put("name", "本周");
		timeMap2.put("key", "week");
		listTimeMaps.add(timeMap2);
		Map timeMap3 = new HashMap();
		timeMap3.put("name", "本月");
		timeMap3.put("key", "month");
		listTimeMaps.add(timeMap3);
		Map timeMap4 = new HashMap();
		timeMap4.put("name", "本季度");
		timeMap4.put("key", "quarter");
		listTimeMaps.add(timeMap4);
		Map timeMap5 = new HashMap();
		timeMap5.put("name", "本年");
		timeMap5.put("key", "year");
		listTimeMaps.add(timeMap5);
		
		timeVo.setList(listTimeMaps);
		list.add(timeVo);
		//预警类型
		SearchParamsVo typeVo = new SearchParamsVo();
		typeVo.setKey("type");
		typeVo.setName("预警类型");
		List<Map> listTypeMaps = new ArrayList<Map>();
		Map typeMap1 = new HashMap();
		typeMap1.put("name", "事件");
		typeMap1.put("key", "1");
		listTypeMaps.add(typeMap1);
		Map typeMap2 = new HashMap();
		typeMap2.put("name", "线索");
		typeMap2.put("key", "2");
		listTypeMaps.add(typeMap2);
		typeVo.setList(listTypeMaps);
		list.add(typeVo);
		
		return list;
	}

	@Override
	public List<QuestionWarningOfBoundary> getBounday(List<String> areaIdList) {
		if (areaIdList == null || areaIdList.size() == 0) {
			return null;
		}
		List<String> listString = new ArrayList<String>();
		for (String string : areaIdList) {
			listString.add(string);
		}
		
		List<String> lists = new ArrayList<>();
		for(String key :listString) {
			List<GovArea> areaList = new LambdaQueryChainWrapper<GovArea>(govAreaMapper).eq(GovArea::getId, key).eq(GovArea::getDataStatus, GlobalConstant.DATA_STATUS_VALID).list();
			for(GovArea area:areaList) {
				lists.add(area.getCode()+"-");
			}
			
		}
		List<QuestionWarningOfBoundary> list = eventFlowMapper.getBounday(lists);
		
		return list;
	}

	@Override
	public List<QuestionWarningOfAreaLocation> getArea() {
		
		return getAreaLocation();
	}
}
