package com.chinobot.aiuas.bot_collect.warning.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.info.entity.Info;
import com.chinobot.aiuas.bot_collect.info.entity.vo.HistoryVedioVo;
import com.chinobot.aiuas.bot_collect.info.mapper.CollectDataMapper;
import com.chinobot.aiuas.bot_collect.info.mapper.InfoMapper;
import com.chinobot.aiuas.bot_collect.result.mapper.ResultMapper;
import com.chinobot.aiuas.bot_collect.strategy.entity.Strategy;
import com.chinobot.aiuas.bot_collect.strategy.mapper.StrategyMapper;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EchartDateVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.FlightWorkInfoVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.MapCoordinateInfoVo;
import com.chinobot.aiuas.bot_prospect.flight.mapper.FlightWorkMapper;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "数量监控总况页面接口")
@RestController
@RequestMapping("/api/warning/quantity-monitor")
public class QuantityMonitorController {

	@Autowired
	private FlightWorkMapper flightWorkMapper;

	@Autowired
	private CollectDataMapper collectDataMapper;

	@Autowired
	private ResultMapper resultMapper;

	@Autowired
	private StrategyMapper strategyMapper;

	@Autowired
	private InfoMapper infoMapper;

	/**
	 * 车辆统计任务ID
	 */
	public static String CAR_TASK_UUID = "744dea4f4290fa7402d3f8d6cfe1a7ca";
	/**
	 * 工程统计任务ID
	 */
	public static String PROJECT_PERSON_TASK_UUID = "a2b9f03e890c066d3a0f87f84fafe3cd";
	/**
	 * 车辆统计结果表 rs_key totalCarNum
	 */
	public static String CAR_RS_KEY = "totalCarNum";
	/**
	 * 人次统计结果表 rs_key totalPersonNum
	 */
	public static String PROJECT_RS_KEY = "totalPersonNum";

	/**
	 * 列表
	 * 
	 * @Param:
	 * @Return: 查询车流监控数据
	 * @Author: zhoulili
	 * @Date:
	 */
	@ApiOperation(value = "车流监控-查看详情", notes = "车流监控-查看详情")
	@GetMapping("/getMonitorCarDetail")
	public Result getMonitorCarDetail(Page page,
			@ApiParam(name = "params", value = "筛选条件", required = false) @RequestParam(required = false) Map<String, Object> params) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		paramMap.put("taskUuid", CAR_TASK_UUID);
		if (!CommonUtils.isObjEmpty(params)) {
			if (!CommonUtils.isObjEmpty(params.get("timeType"))) {
				paramMap.put("timeType", params.get("timeType"));
			}
		}

		// 获取车辆监控航班信息
		IPage<FlightWorkInfoVo> flightVo = flightWorkMapper.getMonitorCarNumber(page, paramMap);
		for (FlightWorkInfoVo flightWorkInfoVo : flightVo.getRecords()) {
			double workMinute = this.formatMinute(flightWorkInfoVo.getWorkMinute());
			flightWorkInfoVo.setWorkMinute(workMinute);
		}
		resultMap.put("flightWorkInfo", flightVo);// 获取航班信息

		return ResultFactory.success(resultMap);
	}

	/**
	 * 列表
	 * 
	 * @Param:
	 * @Return: 查询车流监控数据
	 * @Author: zhoulili
	 * @Date:
	 */
	@ApiOperation(value = "重点检测路段，车流监测数据，以及车流分析采查策略", notes = "重点检测路段，车流监测数据，以及车流分析采查策略")
	@GetMapping("/getMonitorCarNumber")
	public Result getMonitorCarNumber(
			@ApiParam(name = "params", value = "筛选条件", required = false) @RequestParam(required = false) Map<String, Object> params) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("taskUuid", CAR_TASK_UUID);
		List<Strategy> strategies = strategyMapper.getStrategyInfoByTaskId(paramMap);
		if (!CommonUtils.isObjEmpty(params)) {
			if (!CommonUtils.isObjEmpty(params.get("timeType"))) {
				paramMap.put("timeType", params.get("timeType"));
			}
		}
		// 获取车辆监控航班信息
		List<FlightWorkInfoVo> flighVoAll = flightWorkMapper.getMonitorCarNumber(paramMap);

		List<String> collectUuid = new ArrayList<String>();
		double minutCount = 0;
		for (FlightWorkInfoVo flightWorkInfoVo : flighVoAll) {
			collectUuid.add(flightWorkInfoVo.getCollectUuid());
			minutCount = minutCount + flightWorkInfoVo.getWorkMinute();
		}
		List<String> newCollect = collectUuid.stream().distinct().collect(Collectors.toList());
		resultMap.put("flightWorkCount", flighVoAll.size());// 获取航班个数
		resultMap.put("collectCount", newCollect.size());// 获取对象个数

		resultMap.put("timeCount", countMinute(minutCount));// 求得总时长
		resultMap.put("carStrategy", strategies);// 车辆策略

		paramMap.put("rsKey", CAR_RS_KEY);
		int carCount = 0;
		List<Map> carNumberInfo = resultMapper.getResultNumberInfo(paramMap);
		for (Map map : carNumberInfo) {
			carCount = carCount + Integer.valueOf(String.valueOf(map.get("rs_value")));
		}
		resultMap.put("carCount", carCount);// 封装车辆总数
		List<Map> newList = new ArrayList<Map>();
		if (carNumberInfo.size() > 3) {
			newList = carNumberInfo.subList(0, 3);
			resultMap.put("topThree", newList); // 封装top3
		} else {
			resultMap.put("topThree", carNumberInfo);
		}
		return ResultFactory.success(resultMap);
	}

	/**
	 * 列表
	 * 
	 * @Param:
	 * @Return: 查询工程人数监控数据
	 * @Author: zhoulili
	 * @Date:
	 */
	@ApiOperation(value = "工程人数检测列表", notes = "工程人数监测列表")
	@GetMapping("/getMonitorProjectDetail")
	public Result getMonitorProjectDetail(Page page,
			@ApiParam(name = "params", value = "筛选条件", required = false) @RequestParam(required = false) Map<String, Object> params) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskUuid", PROJECT_PERSON_TASK_UUID);
		if (!CommonUtils.isObjEmpty(params)) {
			if (!CommonUtils.isObjEmpty(params.get("timeType"))) {
				paramMap.put("timeType", params.get("timeType"));
			}
		}
		// 获取工程监控航班信息
		IPage<FlightWorkInfoVo> flightVo = flightWorkMapper.getMonitorCarNumber(page, paramMap);
		for (FlightWorkInfoVo flightWorkInfoVo : flightVo.getRecords()) {
			double workMinute = this.formatMinute(flightWorkInfoVo.getWorkMinute());
			flightWorkInfoVo.setWorkMinute(workMinute);
		}
		resultMap.put("flightWorkInfo", flightVo);// 获取航班信息

		return ResultFactory.success(resultMap);
	}

	/**
	 * 列表
	 * 
	 * @Param:
	 * @Return: 查询工程监控数据
	 * @Author: zhoulili
	 * @Date:
	 */
	@ApiOperation(value = "工程人数监测总数", notes = "工程人数监测总数")
	@GetMapping("/getMonitorProjectNumber")
	public Result getMonitorProjectNumber(
			@ApiParam(name = "params", value = "筛选条件", required = false) @RequestParam(required = false) Map<String, Object> params) {
		// 744dea4f4290fa7402d3f8d6cfe1a7ca 车辆数量
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskUuid", PROJECT_PERSON_TASK_UUID);
		List<Strategy> strategies = strategyMapper.getStrategyInfoByTaskId(paramMap);
		if (!CommonUtils.isObjEmpty(params)) {
			// 获取车辆监控航班信息
			if (!CommonUtils.isObjEmpty(params.get("timeType"))) {
				paramMap.put("timeType", params.get("timeType"));
			}
		}
		List<FlightWorkInfoVo> flighVoAll = flightWorkMapper.getMonitorCarNumber(paramMap);

		List<String> collectUuid = new ArrayList<String>();
		double minutCount = 0;
		for (FlightWorkInfoVo flightWorkInfoVo : flighVoAll) {
			collectUuid.add(flightWorkInfoVo.getCollectUuid());
			minutCount = minutCount + flightWorkInfoVo.getWorkMinute();
		}
		List<String> newCollect = collectUuid.stream().distinct().collect(Collectors.toList());
		resultMap.put("flightWorkCount", flighVoAll.size());// 获取航班个数
		resultMap.put("collectCount", newCollect.size());// 获取对象个数

		resultMap.put("timeCount", countMinute(minutCount));// 求得总时长
		resultMap.put("projectStrategy", strategies);// 车辆策略

		paramMap.put("rsKey", PROJECT_RS_KEY);
		int carCount = 0;
		List<Map> carNumberInfo = resultMapper.getResultNumberInfo(paramMap);
		for (Map map : carNumberInfo) {
			carCount = carCount + Integer.valueOf(String.valueOf(map.get("rs_value")));
		}
		resultMap.put("personCount", carCount);
		return ResultFactory.success(resultMap);
	}

	@ApiOperation(value = "历史监控", notes = "车流监测-历史监控")
	@GetMapping("/getVedioList")
	public Result getVedioList(Page page,
			@ApiParam(name = "params", value = "筛选条件", required = false) @RequestParam(required = false) Map<String, Object> params) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskUuid", CAR_TASK_UUID);
		if (!CommonUtils.isObjEmpty(params)) {
			if (!CommonUtils.isObjEmpty(params.get("timeType"))) {
				paramMap.put("timeType", params.get("timeType"));
			}
		}
		IPage<HistoryVedioVo> hisotryVedioVos = collectDataMapper.searchInfoCheckVedioFile(page, paramMap);
		resultMap.put("vedioInfoList", hisotryVedioVos);
		return ResultFactory.success(resultMap);
	}

	/**
	 * 列表
	 * 
	 * @Param:
	 * @Return:车流分析
	 * @Author: zhoulili
	 * @Date:
	 */
	@ApiOperation(value = "车流分析-检测路段", notes = "车流分析-检测路段")
	@GetMapping("/getCarCollectListInfo")
	public Result getCarCollectListInfo(
			@ApiParam(name = "paramMap", value = "筛选条件", required = false) @RequestParam(required = false) Map<String, Object> params) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		paramMap.put("taskUuid", CAR_TASK_UUID);
		if (!CommonUtils.isObjEmpty(params)) {
			if (!CommonUtils.isObjEmpty(params.get("timeType"))) {
				paramMap.put("timeType", params.get("timeType"));
			}
			if (!CommonUtils.isObjEmpty(params.get("strategyId"))) {
				paramMap.put("strategyUuid", params.get("strategyId"));
			}
		}
		List<Info> infos = infoMapper.getInfosByTaskStragy(paramMap);
		
		List<Info> dupliInfo = this.removeDupliByUuid(infos);
		resultMap.put("carCollectInfoList", dupliInfo);
		
		return ResultFactory.success(resultMap);
	}

	/**
	 * 列表
	 * 
	 * @Param:
	 * @Return: 人数分析
	 * @Author: zhoulili
	 * @Date:
	 */
	@ApiOperation(value = "人数分析-工程名称", notes = "人数分析-工程名称")
	@GetMapping("/getPersonCollectListInfo")
	public Result getPersonCollectListInfo(Page page,
			@ApiParam(name = "params", value = "筛选条件", required = false) @RequestParam(required = false) Map<String, Object> params) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskUuid", PROJECT_PERSON_TASK_UUID);
		if (!CommonUtils.isObjEmpty(params)) {
			if (!CommonUtils.isObjEmpty(params.get("timeType"))) {
				paramMap.put("timeType", params.get("timeType"));
			}
			if (!CommonUtils.isObjEmpty(params.get("strategyId"))) {
				paramMap.put("strategyUuid", params.get("strategyId"));
			}
		}
		List<Info> infos = infoMapper.getInfosByTaskStragy(paramMap);
	
		List<Info> dupliInfo = this.removeDupliByUuid(infos);
		resultMap.put("projectCollectInfoList", dupliInfo);
		return ResultFactory.success(resultMap);
	}
	  
	/**
	 * 根据uuid去重
	 * @param userCars
	 * @return
	 */
	private List<Info> removeDupliByUuid(List<Info> userCars) {
	        Set<Info> personSet = new TreeSet<Info>((o1, o2) ->             
	        o1.getUuid().compareTo(o2.getUuid()));
	        personSet.addAll(userCars);
	        return new ArrayList<Info>(personSet);
	    }
	

	/**
	 * 列表
	 * 
	 * @Param:
	 * @Return: 车流分析
	 * @Author: zhoulili
	 * @Date:
	 */
	@ApiOperation(value = "车流分析柱状图数据", notes = "车流分析柱状图数据")
	@PostMapping("/getAnalysisCar")
	public Result getAnalysisCar(
			@ApiParam(name = "params", value = "筛选条件", required = false) @RequestBody(required = false) Map<String, Object> params) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskUuid", CAR_TASK_UUID);
		paramMap.put("rsKey", CAR_RS_KEY);
		List<String> collectList = new ArrayList<String>();
		if (!CommonUtils.isObjEmpty(params)) {
			if (!CommonUtils.isObjEmpty(params.get("timeType"))) {
				paramMap.put("timeType", params.get("timeType"));
			}
			if (!CommonUtils.isObjEmpty(params.get("strategyId"))) {
				paramMap.put("strategyUuid", params.get("strategyId"));
			}
			if (!CommonUtils.isObjEmpty(params.get("collectList"))) {
				collectList = (List<String>) params.get("collectList");
			}
		}
		List<Info> infos = new ArrayList<Info>();

		if (!CommonUtils.isObjEmpty(collectList) && collectList.size() > 0) {
			paramMap.put("collectList", collectList);
			infos = infoMapper.getInfosByInfoList(paramMap);
		} else {
			infos = infoMapper.getInfosByTaskStragy(paramMap);
		}
		String[] collectIdList = new String[infos.size()];
		for (int i = 0; i < infos.size(); i++) {
			collectIdList[i] = infos.get(i).getUuid();
		}
		paramMap.put("collectList", collectIdList);
		List<EchartDateVo> dateVos = flightWorkMapper.getEchartData(paramMap);
		List<String> dateList = new ArrayList<String>();
		List<String> collectNameList = new ArrayList<String>();
		for (EchartDateVo echartDateVo : dateVos) {
			dateList.add(echartDateVo.getFlightDate());
			collectNameList.add(echartDateVo.getCollectName());
			List<String> rsValueList = echartDateVo.getRsValueList();
			if (!CommonUtils.isObjEmpty(rsValueList) && rsValueList.size() > 0) {
				long rsValue = 0;
				for (String string : rsValueList) {
					rsValue = rsValue + Long.valueOf(string);
				}
				echartDateVo.setRsValue(rsValue);
			}
		}
		List<String> newCollect = dateList.stream().distinct().collect(Collectors.toList());
		List<String> newNameCollect = collectNameList.stream().distinct().collect(Collectors.toList());
		resultMap.put("collectNameList", newNameCollect);
		resultMap.put("flightDateList", newCollect);
		resultMap.put("echartDateInfo", dateVos);
		return ResultFactory.success(resultMap);
	}

	/**
	 * 列表
	 * 
	 * @Param:
	 * @Return:人数分析
	 * @Author: zhoulili
	 * @Date:
	 */
	@ApiOperation(value = "人数分析堆叠折线图数据", notes = "人数分析堆叠折线图数据")
	@PostMapping("/getAnalysisPersonNumber")
	public Result getAnalysisPersonNumber(
			@ApiParam(name = "params", value = "筛选条件", required = false) @RequestBody(required = false) Map<String, Object> params) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskUuid", PROJECT_PERSON_TASK_UUID);
		paramMap.put("rsKey", PROJECT_RS_KEY);
		List<String> collectList = new ArrayList<String>();
		if (!CommonUtils.isObjEmpty(params)) {
			if (!CommonUtils.isObjEmpty(params.get("timeType"))) {
				paramMap.put("timeType", params.get("timeType"));
			}
			if (!CommonUtils.isObjEmpty(params.get("strategyId"))) {
				paramMap.put("strategyUuid", params.get("strategyId"));
			}
			if (!CommonUtils.isObjEmpty(params.get("collectList"))) {
				collectList = (List<String>) params.get("collectList");
			}
			
		}
		List<Info> infos = new ArrayList<Info>();
		
		if (!CommonUtils.isObjEmpty(collectList) && collectList.size() > 0) {
			paramMap.put("collectList", collectList);
			infos = infoMapper.getInfosByInfoList(paramMap);
		} else {
			infos = infoMapper.getInfosByTaskStragy(paramMap);
		}

		String[] collectIdList = new String[infos.size()];

		for (int i = 0; i < infos.size(); i++) {
			collectIdList[i] = infos.get(i).getUuid();
		}
		paramMap.put("collectList", collectIdList);
		List<EchartDateVo> dateVos = flightWorkMapper.getEchartData(paramMap);
		List<String> dateList = new ArrayList<String>();
		List<String> collectNameList = new ArrayList<String>();
		for (EchartDateVo echartDateVo : dateVos) {
			dateList.add(echartDateVo.getFlightDate());
			collectNameList.add(echartDateVo.getCollectName());
			List<String> rsValueList = echartDateVo.getRsValueList();
			if (!CommonUtils.isObjEmpty(rsValueList) && rsValueList.size() > 0) {
				long rsValue = 0;
				for (String string : rsValueList) {
					rsValue = rsValue + Long.valueOf(string);
				}
				echartDateVo.setRsValue(rsValue);
			}
		}
		List<String> newCollect = dateList.stream().distinct().collect(Collectors.toList());
		List<String> newName = collectNameList.stream().distinct().collect(Collectors.toList());
		resultMap.put("collectNameList", newName);
		resultMap.put("flightDateList", newCollect);
		resultMap.put("echartDateInfo", dateVos);

		return ResultFactory.success(resultMap);
	}

	/**
	 * 传入时间，进行计算
	 * 
	 * @param minutCount
	 * @return
	 */
	private String countMinute(double minutCount) {

		DecimalFormat df = new DecimalFormat("#.#");
		if (minutCount < 24) {
			return df.format(minutCount) + "h";
		} else if (minutCount > 24 && minutCount < (24 * 30)) {
			double minuCount = minutCount / 24;
			return df.format(minuCount) + "day";
		} else if (minutCount > (24 * 30) && minutCount < (24 * 30 * 12)) {
			double minuCount = minutCount / (24 * 30);
			return df.format(minuCount) + "mon";
		} else {
			double minuCount = minutCount / (365 * 24);
			return df.format(minuCount) + "year";
		}
	}
	/**
	 * 传入时间，进行计算
	 * 
	 * @param minutCount
	 * @return
	 */
	private double formatMinute(double minutCount) {

		DecimalFormat df = new DecimalFormat("#.#");
		return Double.valueOf(df.format(minutCount));
	}

	/**
	 * 列表
	 * 
	 * @Param:
	 * @Return:工程人数地图数据
	 * @Author: zhoulili
	 * @Date:
	 */
	@ApiOperation(value = "工程人数地图数据", notes = "工程人数地图数据")
	@GetMapping("/getMapAddressPerson")
	public Result getMapAddressPerson(Page page,
			@ApiParam(name = "params", value = "筛选条件", required = false) @RequestParam(required = false) Map<String, Object> params) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskUuid", PROJECT_PERSON_TASK_UUID);
		paramMap.put("rsKey", PROJECT_RS_KEY);
		if (!CommonUtils.isObjEmpty(params)) {
			if (!CommonUtils.isObjEmpty(params.get("timeType"))) {
				paramMap.put("timeType", params.get("timeType"));
			}
		}
		List<MapCoordinateInfoVo> dateVos = mapDataSetRsValue(paramMap);
		resultMap.put("personMapAddressInfo", dateVos);

		return ResultFactory.success(resultMap);
	}

	/**
	 * 列表
	 * 
	 * @Param:
	 * @Return:车流地图数据
	 * @Author: zhoulili
	 * @Date:
	 */
	@ApiOperation(value = "车流车次地图数据", notes = "车流车次地图数据")
	@GetMapping("/getMapAddressCar")
	public Result getMapAddressCar(Page page,
			@ApiParam(name = "params", value = "筛选条件", required = false) @RequestParam(required = false) Map<String, Object> params) {
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("taskUuid", CAR_TASK_UUID);
		paramMap.put("rsKey", CAR_RS_KEY);
		if (!CommonUtils.isObjEmpty(params)) {
			if (!CommonUtils.isObjEmpty(params.get("timeType"))) {
				paramMap.put("timeType", params.get("timeType"));
			}
		}
		List<MapCoordinateInfoVo> dateVos = mapDataSetRsValue(paramMap);
		resultMap.put("carMapAddressInfo", dateVos);

		return ResultFactory.success(resultMap);
	}

	/**
	 * 根据数组得到对象数量或人数
	 * 
	 * @param paramMap
	 * @return
	 */
	private List<MapCoordinateInfoVo> mapDataSetRsValue(Map<String, Object> paramMap) {
		List<MapCoordinateInfoVo> dateVos = flightWorkMapper.getMapAddressInfoData(paramMap);
		for (MapCoordinateInfoVo mapCoordinateInfoVo : dateVos) {
			List<String> rsValueList = mapCoordinateInfoVo.getRsValueList();
			if (!CommonUtils.isObjEmpty(rsValueList) && rsValueList.size() > 0) {
				long rsValue = 0;
				for (String string : rsValueList) {
					rsValue = rsValue + Long.valueOf(string);
				}
				mapCoordinateInfoVo.setRsValue(rsValue);
			}
		}
		return dateVos;
	}

}
