package com.chinobot.plep.home.homepage.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.chinobot.aiuas.bot_resource.uav.entity.Uav;
import com.chinobot.aiuas.bot_resource.uav.service.IReUavService;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.common.constant.GlobalConstant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.beust.jcommander.internal.Maps;
import com.chinobot.cityle.base.entity.AddressBase;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.service.IAddressBaseService;
import com.chinobot.cityle.base.service.IDeptService;
import com.chinobot.cityle.base.service.IUavService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.BeanUtils;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.GeoUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.plep.home.area.entity.GovArea;
import com.chinobot.plep.home.area.service.IGovAreaService;
import com.chinobot.plep.home.event.mapper.EarlyWarningMapper;
import com.chinobot.plep.home.event.service.IEarlyWarningService;
import com.chinobot.plep.home.homepage.entity.vo.HomePageAreaRendaVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageDeptPointVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageDeptRangeAreaVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageDeptRouteVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageDomainVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageEarlyWarningVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageFlyDataVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageInfoOfLineVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageInfoOfPoint;
import com.chinobot.plep.home.homepage.entity.vo.HomePagePathAndPointCountVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageSceneVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageTaskCountOfDeptAndPointVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageTaskEchartInfo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageUavVo;
import com.chinobot.plep.home.homepage.entity.vo.HomepageEarlyWarningDeptVo;
import com.chinobot.plep.home.homepage.entity.vo.OnlinePersonDetailVo;
import com.chinobot.plep.home.homepage.entity.vo.OnlinePersonVo;
import com.chinobot.plep.home.homepage.entity.vo.OnlineUavDetailVo;
import com.chinobot.plep.home.monitor.entity.PersonOnline;
import com.chinobot.plep.home.monitor.service.IPersonOnlineService;
import com.chinobot.plep.home.point.mapper.FixedPointMapper;
import com.chinobot.plep.home.point.service.IFixedPointService;
import com.chinobot.plep.home.route.service.ICheckPointService;
import com.chinobot.plep.home.scene.entity.Scene;
import com.chinobot.plep.home.scene.service.ISceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author 周莉莉
 *
 */
@Api(tags="查违首页接口")
@RestController
@RequestMapping("/api/home")
public class HomePageController {

	
	@Autowired
	private IUavService uavService;
	
	@Autowired
	private EarlyWarningMapper earlyWarningMapper;
	
	@Autowired
	private IEarlyWarningService earlyWarningService;
	
	@Autowired
	private ISceneService sceneService;
	
	@Autowired
	private IDeptService deptService;
	
	@Autowired
	private ICheckPointService checkPointService;
	
	@Autowired
	private IAddressBaseService addressBaseService;
	
	@Autowired
	private IFixedPointService fixPointService;
	
	@Autowired
	private IGovAreaService govAreaService;
	
	@Autowired
	private FixedPointMapper fixedPointMapper;
	
	@Autowired
	private IPersonOnlineService personOnlineService;

	@Autowired
	private IPersonService iPersonService;


	@Autowired
	private IReUavService iReUavService;
	
	/**
	 * 	查询预警事件
	 * @param param
	 * @return
	 */
	@ApiOperation(value = "查询预警事件", notes = "参数为领域类型")
	@GetMapping("/searchEventInfo")
	public Result searchEarlyEvent(@RequestParam Map<String, Object> param) {
		HashMap map = new HashMap();
		if(param.get("type") != null){
			String type = (String) param.get("type");
			if("early".equals(type)){
				//查询预警信息
				map.put("earlyInfo", searchEarly(param));
			}
		}else{
			//查询预警信息
			map.put("earlyInfo", searchEarly(param));
			//查询无人机信息
//			map.put("uavInfo", searchUavInfo());
			map.put("uavInfo", searchReUavInfo());
			//查询预警事件根据新政区划的划分
			searchEaryWarning(map);
		}
        return ResultFactory.success(map);
	}
	private void searchEaryWarning(HashMap map) {
		HashMap param = new HashMap();
		param.put("earlyType", "01");
		List<Map> earInfoOne = earlyWarningMapper.getEarlyInfoBar(param);
		map.put("01", earInfoOne);
		
		param.put("earlyType", "02");
		List<Map> earInfoTwo = earlyWarningMapper.getEarlyInfoBar(param);
		map.put("02", earInfoTwo);
		
		map.put("deptNames", earlyWarningMapper.getDeptName());
		
	}
	/**
	 * 查询无人机信息
	 */
	private List<Uav> searchUavInfo() {
//		QueryWrapper<Uav> uavWrapper = new QueryWrapper<Uav>();
//		uavWrapper.eq("etype", "1");//只查询无人机
//		List<Uav> uavList = uavService.list(uavWrapper);
		Map param = new HashMap();
		param.put("etype", "1");
		return uavService.getUavList(param);
	}
	private List<Map> searchReUavInfo() {
		return iReUavService.getReUavList(new HashMap<>());
	}
	/**
	 * 查询预警信息
	 * @param param
	 */
	private List<Map> searchEarly(Map<String, Object> param) {
//		String uuid = (String) param.get("userId");
//		QueryWrapper<Person> queryWrapper = new QueryWrapper<Person>();
//		queryWrapper.eq("uuid", uuid);
//		Person person = personService.getOne(queryWrapper);
//		if(person!=null) {
//			param.put("deptId", person.getDeptId());
//		}
		return  earlyWarningService.getEvents2(param);
	}
	
//	@RequestMapping("/searchFlyInfo")
//	public Result searchFlyInfo() {
//		HashMap map = new HashMap();
//		//查询预警信息
//		QueryWrapper<DispatchDetailPath> queryWrapper = new QueryWrapper<DispatchDetailPath>();
//		queryWrapper.eq("status", "3");
//		List<DispatchDetailPath> dPathList = dispatchDetailPathService.list(queryWrapper);
//		
//		QueryWrapper<DispatchDetailRoute> disqueryWrapper = new QueryWrapper<DispatchDetailRoute>();
//		disqueryWrapper.eq("status", "3");
//		List<DispatchDetailRoute> dRouteList = dispatchDetailRouteService.list(disqueryWrapper);
//		map.put("flyNum", dPathList.size() + dRouteList.size());
//	
//		List<Map> dPathInfo = dispatchDetailPathService.getPathFlightTotal();
//		float flyLong = 0;
//		for (Map map2 : dPathInfo) {
//			flyLong = flyLong + Float.valueOf(map2.get("flight_total_long").toString());
//		}
//		List<Map> dRouteInfo = dispatchDetailRouteService.getRouteFlightTotal();
//		for (Map map2 : dRouteInfo) {
//			flyLong = flyLong + Float.valueOf(map2.get("fly_speed").toString()) * Float.valueOf(map2.get("fly_time").toString());
//		}
//		if(!CommonUtils.isObjEmpty(flyLong) && flyLong > 0) {
//			map.put("flyTotal", Math.round(flyLong/1000));
//		}else {
//			map.put("flyTotal", "0");
//		}
//		
//        return ResultFactory.success(map);
//	}

	@ApiOperation(value = "首页飞行次数 飞行里程-DJL", notes = "飞行次数,飞行里程")
	@GetMapping("/searchFlyInfo")
	public Result<List<HomePageFlyDataVo>> getFlyNum(@ApiParam(name = "sceneId", value = "场景主键", required = false) @RequestParam(value = "sceneId", required = false) String sceneId, 
			@ApiParam(name = "domainId", value = "领域主键", required = true) @RequestParam(value = "domainId", required = true) String domainId) {
		
		Dept dept = ThreadLocalUtil.getResources().getDept();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deptId", dept.getUuid());
		param.put("domainId", domainId);
		param.put("sceneId", sceneId);
		HomePageFlyDataVo vo = new HomePageFlyDataVo();
		//飞行次数
		List<Map> list = checkPointService.getFlyNum(param);
		int flyNum = list.size();
		vo.setFlyNum(flyNum);
		float flyLong = 0;//飞行里程
		for (Map map2 : list) {
			flyLong = flyLong + Float.valueOf(Float.valueOf(map2.get("flightTotal").toString()));
		}
		if(!CommonUtils.isObjEmpty(flyLong) && flyLong > 0) {
			vo.setFlyTotal(Math.round(flyLong/1000));
		}else {
			vo.setFlyTotal(0);
		}
		
		return ResultFactory.success(vo);
	}
	
	
	@ApiOperation(value = "首页获取领域和场景-HW", notes = "用于首页右上角领域和场景下拉的联动切换")
	@GetMapping("/getDomainAndScene")
	public Result<List<HomePageDomainVo>> getDomainAndScene() {
		QueryWrapper<Scene> wrapper = new QueryWrapper<Scene>();
		wrapper.eq("data_status", "1");
		wrapper.isNull("parent_id");
		wrapper.orderByAsc("create_time");
		List<Map<String, Object>> listMaps = sceneService.listMaps(wrapper);//获取所有领域
		List<HomePageDomainVo> domains = BeanUtils.mapsToBeans(listMaps, HomePageDomainVo.class);
		for(HomePageDomainVo domain : domains) {
			QueryWrapper<Scene> sonWrapper = new QueryWrapper<Scene>();
			sonWrapper.eq("data_status", "1");
			sonWrapper.eq("parent_id", domain.getUuid());
			sonWrapper.orderByAsc("create_time");
			List<HomePageSceneVo> scenes = BeanUtils.mapsToBeans(sceneService.listMaps(sonWrapper), HomePageSceneVo.class);
			domain.setScenes(scenes);
		}
		return ResultFactory.success(domains);
	}
	
	
	@ApiOperation(value = "首页获取各（街道）航线和起飞点的数量-HW", notes = "若有子部门，获取各子部门的航线和起飞点数量；若无，则获取兄弟节点部门的航线和起飞点数量；场景选中全部时，sceneId为空")
	@GetMapping("/getPathAndPointCount")
	public Result<List<HomePagePathAndPointCountVo>> getPathAndPointCount(@ApiParam(name = "sceneId", value = "场景主键", required = false) @RequestParam(value = "sceneId", required = false) String sceneId, 
			@ApiParam(name = "domainId", value = "领域主键", required = true) @RequestParam(value = "domainId", required = true) String domainId) {
		Dept dept = ThreadLocalUtil.getResources().getDept();
//		Dept dept = new Dept();
//		dept.setUuid("8ff207f2698311e9881d0242ac110005");
		QueryWrapper<Dept> sonWrapper = new QueryWrapper<Dept>();
		sonWrapper.eq("data_status", "1");
		sonWrapper.eq("parent_id", dept.getUuid());
		List<Map<String, Object>> rs = deptService.listMaps(sonWrapper);//子节点
		if(rs==null || rs.size()==0) {//子节点为空，查兄弟节点
			QueryWrapper<Dept> broWrapper = new QueryWrapper<Dept>();
			broWrapper.eq("data_status", "1");
			broWrapper.eq("parent_id", dept.getParentId());
			rs = deptService.listMaps(broWrapper);
		}else {
			//子节点中插入本身
			Map<String, Object> mapDept = new HashMap<String, Object>();
			mapDept.put("uuid", dept.getUuid());
			mapDept.put("dname", dept.getDname());
			mapDept.put("ab_id", dept.getAbId());
			rs.add(0, mapDept);
		}
		List<HomePagePathAndPointCountVo> list = BeanUtils.mapsToBeans(rs, HomePagePathAndPointCountVo.class);
		Map<String, String> param = new HashMap<String, String>();
		param.put("domainId", domainId);
		param.put("sceneId", sceneId);
		for(HomePagePathAndPointCountVo vo : list) {
			param.put("deptId", vo.getUuid());
			vo.setPathCount(checkPointService.getCountOfPath(param));
			vo.setPointCount(checkPointService.getCountOfCheckPoint(param));
		}
		return ResultFactory.success(list);
	}
	
	@ApiOperation(value = "首页获取各（街道）各起飞点的任务数-HW", notes = "若有子部门，获取各子部门的各起飞点的任务数；若无，则获取兄弟节点部门的各起飞点的任务数；场景选中全部时，sceneId为空")
	@GetMapping("/getPointTaskOfDept")
	public Result<HomePageTaskEchartInfo> getPointTaskOfDept(@ApiParam(name = "sceneId", value = "场景主键", required = false) @RequestParam(value = "sceneId", required = false) String sceneId,
			@ApiParam(name = "domainId", value = "领域主键", required = true) @RequestParam(value = "domainId", required = true) String domainId) {
		Dept dept = ThreadLocalUtil.getResources().getDept();
		QueryWrapper<Dept> sonWrapper = new QueryWrapper<Dept>();
		sonWrapper.eq("data_status", "1");
		sonWrapper.eq("parent_id", dept.getUuid());
		List<Map<String, Object>> rs = deptService.listMaps(sonWrapper);//子节点
		String parentId = null;
		if(rs==null || rs.size()==0) {//子节点为空，查兄弟节点
			QueryWrapper<Dept> broWrapper = new QueryWrapper<Dept>();
			broWrapper.eq("data_status", "1");
			broWrapper.eq("parent_id", dept.getParentId());
			rs = deptService.listMaps(broWrapper);
		}else {
			//子节点中插入本身
			Map<String, Object> mapDept = new HashMap<String, Object>();
			mapDept.put("uuid", dept.getUuid());
			mapDept.put("dname", dept.getDname());
			mapDept.put("ab_id", dept.getAbId());
			mapDept.put("area_id", dept.getAreaId());
			rs.add(0, mapDept);
			//parentId = dept.getUuid();
		}
		List<HomePageTaskCountOfDeptAndPointVo> list = BeanUtils.mapsToBeans(rs, HomePageTaskCountOfDeptAndPointVo.class);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("domainId", domainId);
		param.put("sceneId", sceneId);
		List<Map<String, Object>> geoParam = new ArrayList<Map<String,Object>>();
		for(HomePageTaskCountOfDeptAndPointVo vo : list) {
			//获取部门坐标
			AddressBase addressBase = addressBaseService.getById(vo.getAbId());
			if(addressBase != null) {
				vo.setLnglat(addressBase.getCenter());
			}
			//获取各起飞点坐标及任务数
			param.put("deptId", vo.getUuid());
			vo.setPoints(checkPointService.getCheckPointAndTaskCount(param));
			if(!vo.getUuid().equals(parentId)) {
				GovArea area = govAreaService.getById(vo.getAreaId());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", area.getId());
				map.put("lnglats",area.getTargetBoundary());
				map.put("name",vo.getDname());
				map.put("center",addressBase.getCenter());
				geoParam.add(map);
			}
			
		}
		HomePageTaskEchartInfo vo = new HomePageTaskEchartInfo();
		vo.setList(list);
		vo.setGeoJson(GeoUtils.String2Geojson(geoParam));
		return ResultFactory.success(vo);
	}
	
	@ApiOperation(value = "首页获取部门面积和已规划的面积-HW", notes = "若有子部门，获取各子部门的部门面积和已规划的面积；若无，则获取兄弟节点部门的部门面积和已规划的面积；场景选中全部时，sceneId为空")
	@GetMapping("/getAreaOfDeptAndRange")
	public Result<List<HomePageDeptRangeAreaVo>> getAreaOfDeptAndRange(@ApiParam(name = "sceneId", value = "场景主键", required = false) @RequestParam(value = "sceneId", required = false) String sceneId,
			@ApiParam(name = "domainId", value = "领域主键", required = true) @RequestParam(value = "domainId", required = true) String domainId) {
		Dept dept = ThreadLocalUtil.getResources().getDept();
		QueryWrapper<Dept> sonWrapper = new QueryWrapper<Dept>();
		sonWrapper.eq("data_status", "1");
		sonWrapper.eq("parent_id", dept.getUuid());
		List<Map<String, Object>> rs = deptService.listMaps(sonWrapper);//子节点
		if(rs==null || rs.size()==0) {//子节点为空，查兄弟节点
			QueryWrapper<Dept> broWrapper = new QueryWrapper<Dept>();
			broWrapper.eq("data_status", "1");
			broWrapper.eq("parent_id", dept.getParentId());
			rs = deptService.listMaps(broWrapper);
		}else {
			//子节点中插入本身
			Map<String, Object> mapDept = new HashMap<String, Object>();
			mapDept.put("uuid", dept.getUuid());
			mapDept.put("dname", dept.getDname());
			mapDept.put("ab_id", dept.getAbId());
			rs.add(0, mapDept);
		}
		List<HomePageDeptRangeAreaVo> list = BeanUtils.mapsToBeans(rs, HomePageDeptRangeAreaVo.class);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("domainId", domainId);
		param.put("sceneId", sceneId);
		for(HomePageDeptRangeAreaVo vo : list) {
			param.put("deptId", vo.getUuid());
			//TODO 部门边界,行政区划边界？
			vo.setPolygon(checkPointService.getDeptPolygon(param));
			//部门下的区域边界
			vo.setList(checkPointService.getRangePolygon(param));
		}
		return ResultFactory.success(list);
	}
	
	@ApiOperation(value = "首页获取各街道定点数量-DJL", notes = "若有子部门，获取各子部门的部门定点数量；若无，则获取兄弟节点部门的部门定点数量；场景选中全部时，sceneId为空")
	@GetMapping("/getCountOfFixPoint")
	public Result<List<HomePageDeptPointVo>> getCountOfFixPoint(@ApiParam(name = "sceneId", value = "场景主键", required = false) @RequestParam(value = "sceneId", required = false) String sceneId,
			@ApiParam(name = "domainId", value = "领域主键", required = true) @RequestParam(value = "domainId", required = true) String domainId) {
		Dept dept = ThreadLocalUtil.getResources().getDept();
		QueryWrapper<Dept> sonWrapper = new QueryWrapper<Dept>();
		sonWrapper.eq("data_status", "1");
		sonWrapper.eq("parent_id", dept.getUuid());
		List<Map<String, Object>> rs = deptService.listMaps(sonWrapper);//子节点
		if(rs==null || rs.size()==0) {//子节点为空,查兄弟节点
			QueryWrapper<Dept> broWrapper = new QueryWrapper<Dept>();
			broWrapper.eq("data_status", "1");
			broWrapper.eq("parent_id", dept.getParentId());
			rs = deptService.listMaps(broWrapper);
		}else {
			//子节点中插入本身
			Map<String, Object> mapDept = new HashMap<String, Object>();
			mapDept.put("uuid", dept.getUuid());
			mapDept.put("dname", dept.getDname());
			mapDept.put("ab_id", dept.getAbId());
			rs.add(0, mapDept);
		}
		List<HomePageDeptPointVo> list = BeanUtils.mapsToBeans(rs, HomePageDeptPointVo.class);
		Map<String, Object> param = new HashMap();
		param.put("sceneId", sceneId);
		param.put("domainId", domainId);
		List<Long> listMaxs = new ArrayList<Long>(); 
		for(HomePageDeptPointVo vo : list) {
			param.put("deptId", vo.getUuid());
			Map map = fixPointService.getFixPointCount(param);
			listMaxs.add((Long) map.get("pointCount"));
			vo.setCount((Long)map.get("pointCount"));//定点数量
		}
		Collections.sort(listMaxs);
		Long maxCount = listMaxs.get(listMaxs.size()-1);
		for(HomePageDeptPointVo vo : list) {
			vo.setMaxCount(maxCount);//最大数量
		}
		return ResultFactory.success(list);
	}
	
	@ApiOperation(value = "首页获取各街道航点数量-DJL", notes = "若有子部门，获取各子部门的部门航线数量；若无，则获取兄弟节点部门的部门航线数量；场景选中全部时，sceneId为空")
	@GetMapping("/getCountOfLine")
	public Result<List<HomePageDeptRouteVo>> getCountOfLine(@ApiParam(name = "sceneId", value = "场景主键", required = false) @RequestParam(value = "sceneId", required = false) String sceneId,
			@ApiParam(name = "domainId", value = "领域主键", required = true) @RequestParam(value = "domainId", required = true) String domainId) {
		Dept dept = ThreadLocalUtil.getResources().getDept();
		QueryWrapper<Dept> sonWrapper = new QueryWrapper<Dept>();
		sonWrapper.eq("data_status", "1");
		sonWrapper.eq("parent_id", dept.getUuid());
		List<Map<String, Object>> rs = deptService.listMaps(sonWrapper);//子节点
		if(rs==null || rs.size()==0) {//子节点为空,查兄弟节点
			QueryWrapper<Dept> broWrapper = new QueryWrapper<Dept>();
			broWrapper.eq("data_status", "1");
			broWrapper.eq("parent_id", dept.getParentId());
			rs = deptService.listMaps(broWrapper);
		}else {
			//子节点中插入本身
			Map<String, Object> mapDept = new HashMap<String, Object>();
			mapDept.put("uuid", dept.getUuid());
			mapDept.put("dname", dept.getDname());
			mapDept.put("ab_id", dept.getAbId());
			rs.add(0, mapDept);
		}
		List<HomePageDeptRouteVo> list = BeanUtils.mapsToBeans(rs, HomePageDeptRouteVo.class);
		Map<String, Object> param = new HashMap();
		param.put("sceneId", sceneId);
		param.put("domainId", domainId);
		for(HomePageDeptRouteVo vo : list) {
			param.put("deptId", vo.getUuid());
			Map map = fixPointService.getCountOfLine(param);
			Long count = (Long) map.get("lineCount");
			vo.setCount(count);//航线数量
		}
		return ResultFactory.success(list);
	}
	
	@ApiOperation(value = "首页获取区域雷达信息-HW", notes = "若有子部门，获取各子部门的各场景扫描航线数；若无，则获取兄弟节点部门的各场景扫描航线数；场景选中全部时，sceneId为空")
	@GetMapping("/getRangeRadarInfo")
	public Result<List<HomePageAreaRendaVo>> getRangeRadarInfo(@ApiParam(name = "sceneId", value = "场景主键", required = false) @RequestParam(value = "sceneId", required = false) String sceneId,
			@ApiParam(name = "domainId", value = "领域主键", required = true) @RequestParam(value = "domainId", required = true) String domainId) {
		Dept dept = ThreadLocalUtil.getResources().getDept();
		QueryWrapper<Dept> sonWrapper = new QueryWrapper<Dept>();
		sonWrapper.eq("data_status", "1");
		sonWrapper.eq("parent_id", dept.getUuid());
		List<Map<String, Object>> rs = deptService.listMaps(sonWrapper);//子节点
		if(rs==null || rs.size()==0) {//子节点为空，查兄弟节点
			QueryWrapper<Dept> broWrapper = new QueryWrapper<Dept>();
			broWrapper.eq("data_status", "1");
			broWrapper.eq("parent_id", dept.getParentId());
			rs = deptService.listMaps(broWrapper);
		}else {
			//子节点中插入本身
			Map<String, Object> mapDept = new HashMap<String, Object>();
			mapDept.put("uuid", dept.getUuid());
			mapDept.put("dname", dept.getDname());
			mapDept.put("ab_id", dept.getAbId());
			rs.add(0, mapDept);
		}
		List<Scene> scenes = new ArrayList<Scene>();
		if(StringUtils.isBlank(sceneId)) {
			//TODO 飞行模式未配置，暂时写死 屋顶加建场景, 应查出该领域下区域扫描飞行模式下的所有场景
			Scene scene = new Scene();
			scene.setUuid("sfsnb7823df");
			scenes.add(scene);
		}else {
			Scene scene = new Scene();
			scene.setUuid(sceneId);
			scenes.add(scene);
		}
		List<HomePageAreaRendaVo> list = BeanUtils.mapsToBeans(rs, HomePageAreaRendaVo.class);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("domainId", domainId);
		param.put("sceneId", sceneId);
		param.put("depts", rs);
		param.put("scenes", scenes);
		Long maxCount = checkPointService.getMaxCount(param);
		if(maxCount == null) {
			maxCount = (long) 0;
		}
		for(HomePageAreaRendaVo vo : list) {
			vo.setMax(maxCount + 1);
			param.put("deptId", vo.getUuid());
			vo.setList(checkPointService.getPathCountByDeptAndScene(param));
		}
		return ResultFactory.success(list);
	}
	@ApiOperation(value = "首页获取预警图表信息-HW", notes = "若有子部门，获取各子部门预警数量信息；若无，则获取兄弟节点部门的预警数量信息；场景选中全部时，sceneId为空")
	@GetMapping("/getEarlyWarningEchatInfo")
	public Result<HomePageEarlyWarningVo> getEarlyWarningEchatInfo(@ApiParam(name = "sceneId", value = "场景主键", required = false) @RequestParam(value = "sceneId", required = false) String sceneId,
			@ApiParam(name = "domainId", value = "领域主键", required = true) @RequestParam(value = "domainId", required = true) String domainId) {
		Dept dept = ThreadLocalUtil.getResources().getDept();
		QueryWrapper<Dept> sonWrapper = new QueryWrapper<Dept>();
		sonWrapper.eq("data_status", "1");
		sonWrapper.eq("parent_id", dept.getUuid());
		List<Map<String, Object>> rs = deptService.listMaps(sonWrapper);//子节点
		String parentId = null;
		if(rs==null || rs.size()==0) {//子节点为空，查兄弟节点
			QueryWrapper<Dept> broWrapper = new QueryWrapper<Dept>();
			broWrapper.eq("data_status", "1");
			broWrapper.eq("parent_id", dept.getParentId());
			rs = deptService.listMaps(broWrapper);
		}else {
			//子节点中插入本身
			Map<String, Object> mapDept = new HashMap<String, Object>();
			mapDept.put("uuid", dept.getUuid());
			mapDept.put("dname", dept.getDname());
			mapDept.put("ab_id", dept.getAbId());
			mapDept.put("area_id", dept.getAreaId());
			//parentId = dept.getUuid();
			rs.add(0, mapDept);
		}
		List<HomepageEarlyWarningDeptVo> list = BeanUtils.mapsToBeans(rs, HomepageEarlyWarningDeptVo.class);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("domainId", domainId);
		param.put("sceneId", sceneId);
		List<Map<String, Object>> geoParam = new ArrayList<Map<String,Object>>();
		for(HomepageEarlyWarningDeptVo vo : list) {
			//获取部门坐标
			AddressBase addressBase = addressBaseService.getById(vo.getAbId());
			if(addressBase != null) {
				vo.setLnglat(addressBase.getCenter());
			}
			param.put("deptId", vo.getUuid());
			vo.setList(checkPointService.getHomePageEarlyWarningInfo(param));
			if(!vo.getUuid().equals(parentId)) {
				GovArea area = govAreaService.getById(vo.getAreaId());
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", area.getId());
				map.put("lnglats",area.getTargetBoundary());
				map.put("name",vo.getDname());
				map.put("center",addressBase.getCenter());
				geoParam.add(map);
			}
		}
		HomePageEarlyWarningVo vo = new HomePageEarlyWarningVo();
		vo.setList(list);
		vo.setGeoJson(GeoUtils.String2Geojson(geoParam));
		return ResultFactory.success(vo);
	}
	
	@ApiOperation(value = "首页覆盖率-DJL", notes = "场景选中全部时，sceneId为空")
	@GetMapping("/getAreaOfPolygon")
	public Result<HomePageDeptRangeAreaVo> getAreaOfPolygon(@ApiParam(name = "sceneId", value = "场景主键", required = false) @RequestParam(value = "sceneId", required = false) String sceneId,
			@ApiParam(name = "domainId", value = "领域主键", required = true) @RequestParam(value = "domainId", required = true) String domainId) {
		Dept dept = ThreadLocalUtil.getResources().getDept();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("domainId", domainId);
		param.put("sceneId", sceneId);
		HomePageDeptRangeAreaVo vo = new HomePageDeptRangeAreaVo();
		param.put("deptId", dept.getUuid());
		vo.setUuid(dept.getUuid());
		vo.setDname(dept.getDname());
		//TODO 部门边界,行政区划边界？
		vo.setPolygon(checkPointService.getDeptPolygon(param));
		//部门下的区域边界
		vo.setList(checkPointService.getRangePolygon(param));
		
		return ResultFactory.success(vo);
	}
	
	@ApiOperation(value = "航线规划-区域扫描-DJL", notes = "航线规划覆盖率，航线数量")
	@GetMapping("/getInfoOfLine")
	public Result<HomePageInfoOfLineVo> getInfoOfLine(@ApiParam(name = "sceneId", value = "场景主键", required = false) @RequestParam(value = "sceneId", required = false) String sceneId,
			@ApiParam(name = "domainId", value = "领域主键", required = true) @RequestParam(value = "domainId", required = true) String domainId) {
		Dept dept = ThreadLocalUtil.getResources().getDept();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("domainId", domainId);
		param.put("sceneId", sceneId);
		param.put("deptId", dept.getUuid());
		
		HomePageInfoOfLineVo vo = new HomePageInfoOfLineVo();
		
		//TODO 部门边界,行政区划边界？
		vo.setPolygon(checkPointService.getDeptPolygon(param));
		//区域航线下的覆盖边界
		vo.setList(checkPointService.getRangeByLinePolygon(param));
		//航线数量
		vo.setNum(checkPointService.getCountOfLine(param));
		
		return ResultFactory.success(vo);
	}
	
	@ApiOperation(value = "航线规划-定点跟踪-DJL", notes = "航线平均点，航线总耗时，定点数量")
	@GetMapping("/getInfoOfPoint")
	public Result<HomePageInfoOfPoint> getInfoOfPoint(@ApiParam(name = "sceneId", value = "场景主键", required = false) @RequestParam(value = "sceneId", required = false) String sceneId,
			@ApiParam(name = "domainId", value = "领域主键", required = true) @RequestParam(value = "domainId", required = true) String domainId) {
		Dept dept = ThreadLocalUtil.getResources().getDept();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("domainId", domainId);
		param.put("sceneId", sceneId);
		param.put("deptId", dept.getUuid());
		
		HomePageInfoOfPoint vo = new HomePageInfoOfPoint();
		
		//航线数量
		Map map = fixedPointMapper.getCountOfLineWithPoint(param);
		Long countLine = (Long) map.get("lineCount");
		//与航线有关的定点数量
		Map fixPointCountMap = fixPointService.getFixPointCounts(param);
		Long countPointOfLine = (Long) fixPointCountMap.get("pointCount");
		//定点数量
		Map fixPointCount = fixPointService.getFixPointCount(param);
		Long countPoint = (Long) fixPointCount.get("pointCount");
		
		//航线总耗时
		String totalOfFlyTime = checkPointService.getTotalOfFlyTime(param);
		
		vo.setTotalTime(totalOfFlyTime);
		vo.setLineNum(countLine);
		vo.setPointNum(countPoint);
		vo.setPointNumOfline(countPointOfLine);
		return ResultFactory.success(vo);
	}
	@ApiOperation(value = "首页无人机接口-HW", notes = "根据当前用户返回部门及子部门下的无人机")
	@GetMapping("/getUavByDept")
	public Result<IPage<HomePageUavVo>> getUavByDept(Page page, @ApiParam(name = "uuid", value = "无人机主键", required = false) @RequestParam(value = "uuid", required = false) String uuid) {
		
		return ResultFactory.success(checkPointService.getUavByDept(page, uuid));
	}
	
	@ApiOperation(value = "首页人员接口-DJL", notes = "根据当前用户返回部门及子部门下的人员")
	@GetMapping("/getPersonByDept")
	public Result<OnlinePersonVo> getPersonByDept() {
		
		Dept dept = ThreadLocalUtil.getResources().getDept();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deptId", dept.getUuid());
		return ResultFactory.success(personOnlineService.getPersonByDept(param));
	}
	
	@ApiOperation(value = "实时调度搜索-DJL", notes = "模糊查询无人机和人")
	@GetMapping("/getPersonAndUav")
	public Result getPersonAndUav(@ApiParam(name = "name", value = "查询名称", required = false) @RequestParam(value = "name", required = false) String name) {
		
		Dept dept = ThreadLocalUtil.getResources().getDept();
		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("deptId", dept.getUuid());
		param.put("name", name);
		//人员
		List<OnlinePersonVo> personList = personOnlineService.getPersonByDept(param);
		//飞行员
		List<OnlinePersonVo> flyPersonList = new ArrayList<OnlinePersonVo>();
		//网格员
		List<OnlinePersonVo> gridPersonList = new ArrayList<OnlinePersonVo>();
		if (personList.size() > 0) {
			flyPersonList = personList.stream().filter(m->m.getDuties().equals("duties_fly")).collect(Collectors.toList());
			gridPersonList = personList.stream().filter(m->m.getDuties().equals("duties_grid")).collect(Collectors.toList());
		}
		//无人机
//		List<HomePageUavVo> uavList = personOnlineService.getUavByDept(param);
		List<HomePageUavVo> uavList = iReUavService.getReUavPage(param);
		
		Map map = Maps.newHashMap();
		map.put("flyPersonList", flyPersonList);
		map.put("gridPersonList", gridPersonList);
		map.put("uavList", uavList);
		map.put("allFlyPersonList", iPersonService.lambdaQuery()
				.select(Person::getUuid,Person::getPname)
				.eq(Person::getDataStatus, GlobalConstant.DATA_STATUS_VALID)
				.eq(Person::getDuties, "duties_fly")
				.list());
		return ResultFactory.success(map);
	}
	
	@ApiOperation(value = "立体调度页面-点击人弹出框信息", notes = "点击人弹出框信息")
	@GetMapping("/getPersonOnlinwDetail")
	public Result<OnlinePersonDetailVo> getPersonDetail(@ApiParam(name = "uuid", value = "人id", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		return ResultFactory.success(personOnlineService.getPersonDetail(uuid));
	}
	
	@ApiOperation(value = "立体调度页面-点击无人机弹出框信息", notes = "点击无人机弹出框信息")
	@GetMapping("/getUavOnlineDetail")
	public Result<OnlineUavDetailVo> getUavOnlineDetail(@ApiParam(name = "uuid", value = "无人机id", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		return ResultFactory.success(personOnlineService.getUavOnlineDetail(uuid));
	}
}
