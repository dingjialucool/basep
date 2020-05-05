package com.chinobot.plep.home.routedd.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.service.IUavService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.event.service.IEventMainService;
import com.chinobot.plep.home.plan.entity.FlyArea;
import com.chinobot.plep.home.plan.entity.FlyPath;
import com.chinobot.plep.home.plan.service.IFlyAreaService;
import com.chinobot.plep.home.plan.service.IFlyPathService;
import com.chinobot.plep.home.route.entity.Route;
import com.chinobot.plep.home.route.service.IRouteService;
import com.chinobot.plep.home.routedd.entity.DispatchDetail;
import com.chinobot.plep.home.routedd.entity.DispatchDetailPath;
import com.chinobot.plep.home.routedd.entity.DispatchDetailRoute;
import com.chinobot.plep.home.routedd.entity.DispatchDetailType;
import com.chinobot.plep.home.routedd.entity.RouteCollect;
import com.chinobot.plep.home.routedd.entity.UavDispatch;
import com.chinobot.plep.home.routedd.entity.vo.DispatchVo;
import com.chinobot.plep.home.routedd.mapper.UavDispatchMapper;
import com.chinobot.plep.home.routedd.service.IDispatchDetailPathService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailRouteService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailTypeService;
import com.chinobot.plep.home.routedd.service.IRouteCollectService;
import com.chinobot.plep.home.routedd.service.IUavDispatchService;

/**
 * <p>
 * 无人机调度表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-07-24
 */
@Service
public class UavDispatchServiceImpl extends BaseService<UavDispatchMapper, UavDispatch> implements IUavDispatchService {
	@Autowired
	private UavDispatchMapper uavDispatchMapper;
	@Autowired
	private IDispatchDetailService dispatchDetailService;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IRouteService routeService;
	@Autowired
	private IDispatchDetailRouteService dispatchRouteService;
	@Autowired
	private IUavService uavService;
	@Autowired
	private IRouteCollectService routeCollectService;
	@Autowired
	private IDispatchDetailTypeService typeService;
	@Autowired
	private IFlyAreaService areaService;
	@Autowired
	private IFlyPathService pathService;
	@Autowired
	private IDispatchDetailPathService detailPathService;
	@Autowired
	private IEventMainService eventMainService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map> getAllUav(Map<String, String> para) throws Exception {
		List<Map> allUav = uavDispatchMapper.getAllUav(para);
		for(Map map : allUav) {
			String uuid = (String) map.get("uuid");
			//查询相册
			Map param = new HashMap();
			param.put("busId", uuid);
			param.put("module", "equipment_album");
			param.put("limit",1);
			map.put("imgs", fileBusService.getFileIdByBusId(param));
			
			//是否展开
			map.put("isShow", false);
			map.put("textshow","展开");
			//查询日期状态
			List<Map> dateArray = uavDispatchMapper.getUavDateState(uuid);
			map.put("dateArray",dateArray);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

			Date time=null;
			time= sdf.parse(sdf.format(new Date()));
			map.put("beforeDate",(time.getTime()+"").substring(0, 10));
			if(dateArray.size()>0) {
				Date beforeDate = (Date) dateArray.get(0).get("time");
				if(beforeDate.before(new Date())) {
					map.put("beforeDate",(beforeDate.getTime()+"").substring(0, 10));
				}
			}
		}
		allUav.get(0).put("isShow", true);
		allUav.get(0).put("textshow","收起");
		return allUav;
	}

	@Override
	public List<Map> getNotFlyPoints(Map<String, Object> param) {
		List<Map> notFlyPoints = uavDispatchMapper.getNotFlyPoints(param);
		
		return notFlyPoints;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map> getFlyPoints(Map<String, Object> param) {
		List<Map> flyPoints = uavDispatchMapper.getFlyPoints(param);//现在是查出所有起飞点
		for(Map map : flyPoints) {
			String uuid = (String) map.get("uuid");
			//飞行路线
			List<Map> routesOfPoint = uavDispatchMapper.getRoutesOfPoint(uuid);
			map.put("routes",routesOfPoint);
			//巡查范围
			List<Map> flyAreaOfPoint = uavDispatchMapper.getFlyAreaOfPoint(uuid);
			map.put("areas",flyAreaOfPoint);
			
		}
		return flyPoints;
	}
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map getOneDay(String uavId, String time) {
		Map result = new HashMap();
		LocalDate localDate = LocalDate.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("uav_id", uavId);
		queryWrapper.eq("time", time);
		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		UavDispatch uavDispatch = this.getOne(queryWrapper);
		if(uavDispatch == null) {
			uavDispatch = new UavDispatch();
			uavDispatch.setUavId(uavId);
			uavDispatch.setTime(localDate);
			uavDispatch.setEventNum(0);
			uavDispatch.setAreaNum(0);
			uavDispatch.setTimeAll(Float.valueOf(0));
			this.save(uavDispatch);
		}
		result.put("uavDispatch",uavDispatch);
		//获取调度明细
		List<Map> dispatchDetail = uavDispatchMapper.getDispatchDetail(uavDispatch.getUuid());
		result.put("dispatchDetail",dispatchDetail);
		//是否可编辑
		result.put("isEdit",false);
		localDate = localDate.minusDays(-1);//今天可编辑
		if(LocalDate.now().isBefore(localDate)) {
			result.put("isEdit",true);
		}
		//是否被收藏
		QueryWrapper collectWrapper = new QueryWrapper();
		collectWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		collectWrapper.eq("user_id",ThreadLocalUtil.getResources().getUuid());
		collectWrapper.eq("uav_dsp_id",uavDispatch.getUuid());
		RouteCollect routeCollect = routeCollectService.getOne(collectWrapper);
		String isCollect = "0";
		if(routeCollect != null) {
			isCollect = "1";
		}
		result.put("isCollect", isCollect);
		//无人机信息
		result.put("uav",uavService.getById(uavId));
		return result;
	}
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addPoint(DispatchVo vo) {
		DispatchDetail dispatchDetail = vo.getDispatchDetail();
		UavDispatch uavDispatch = vo.getUavDispatch();
		List<DispatchDetailType> types = vo.getType();
		String typeString  = "";
		//更新uavDispatch的总耗时和事件数,区域数
		this.updateById(uavDispatch);
		//新增调度明细
		dispatchDetailService.save(dispatchDetail);
		//新增调度类型
		for(DispatchDetailType type : types) {
			typeString += type.getType()+",";
			type.setDspdtId(dispatchDetail.getUuid());
			typeService.save(type);
		}
		//新增调度明细路线
		if(typeString.contains("1")) {
			QueryWrapper queryWrapper = new QueryWrapper();
			queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			queryWrapper.eq("check_point_id", dispatchDetail.getPointId());
			List<Route> routes = routeService.list(queryWrapper);
			for(Route route : routes) {
				DispatchDetailRoute dispatchRoute = new DispatchDetailRoute();
				dispatchRoute.setDspdtId(dispatchDetail.getUuid());
				dispatchRoute.setRouteId(route.getUuid());
				dispatchRoute.setStatus("1");
				dispatchRouteService.save(dispatchRoute);
			}
		}
		//新增调度明细区域巡查航线
		if(typeString.contains("2") || typeString.contains("3")) {
			QueryWrapper areaWrapper = new QueryWrapper();
			areaWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			areaWrapper.eq("point_id", dispatchDetail.getPointId());
			List<FlyArea> areas = areaService.list(areaWrapper);
			for(FlyArea area : areas) {
				QueryWrapper pathWrapper = new QueryWrapper();
				pathWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
				pathWrapper.eq("area_id",area.getUuid());
				if(typeString.contains("2")) {
					pathWrapper.eq("path_type","1");//只是正射
				}
				List<FlyPath> paths = pathService.list(pathWrapper);
				for(FlyPath path : paths) {
					DispatchDetailPath detailPath = new DispatchDetailPath();
					detailPath.setDspdtId(dispatchDetail.getUuid());
					detailPath.setStatus("1");
					detailPath.setPathId(path.getUuid());
					detailPathService.save(detailPath);
				}
			}
		}
	}
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void delPoint(DispatchDetail dispatchDetail) {
		//将调度明细置为无效
		dispatchDetail = dispatchDetailService.getById(dispatchDetail.getUuid());
		dispatchDetail.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		dispatchDetailService.updateById(dispatchDetail);
		//更新调度表的总耗时和事件数
		UavDispatch uavDispatch = this.getById(dispatchDetail.getUavDspId());
		uavDispatch.setTimeAll(uavDispatch.getTimeAll()-(dispatchDetail.getGetTime()==null?0:dispatchDetail.getGetTime())-dispatchDetail.getCheckTime());
		uavDispatch.setEventNum(uavDispatch.getEventNum()-dispatchDetail.getEventNum());
		uavDispatch.setAreaNum(uavDispatch.getAreaNum()-dispatchDetail.getAreaNum());
		uavDispatch.setTime(null);//修复日期封装Bug
		this.updateById(uavDispatch);
		//将调度明细类型置为无效
		UpdateWrapper typeWrapper = new UpdateWrapper();
		typeWrapper.eq("dspdt_id", dispatchDetail.getUuid());
		DispatchDetailType type = new DispatchDetailType();
		type.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		typeService.update(type, typeWrapper);
		//将调度明细路线置为无效
		UpdateWrapper updateWrapper = new UpdateWrapper();
		updateWrapper.eq("dspdt_id", dispatchDetail.getUuid());
		DispatchDetailRoute dispatchRoute = new DispatchDetailRoute();
		dispatchRoute.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		dispatchRouteService.update(dispatchRoute, updateWrapper);
		//将调度明细巡查航线置为无效
		UpdateWrapper pathWrapper = new UpdateWrapper();
		pathWrapper.eq("dspdt_id", dispatchDetail.getUuid());
		DispatchDetailPath detailPath = new DispatchDetailPath();
		detailPath.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		detailPathService.update(detailPath, pathWrapper);
	}
	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<Map> getPointByParam(Map<String, String> param) {
		
		return uavDispatchMapper.getPointByParam(param);
	}
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void cleanOneDay(UavDispatch uavDispatch) {
		//将事件数和总耗时置0
		//uavDispatch.setEventNum(0);
		//uavDispatch.setAreaNum(0);
		//uavDispatch.setTimeAll(Float.valueOf(0));
		uavDispatch.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);//改动：删除路线
		this.updateById(uavDispatch);
		//将调度明细和调度明细路线置空
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		queryWrapper.eq("uav_dsp_id", uavDispatch.getUuid());
		List<DispatchDetail> dds = dispatchDetailService.list(queryWrapper);
		for(DispatchDetail dd : dds) {
			dd.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			dispatchDetailService.updateById(dd);
			UpdateWrapper updateWrapper = new UpdateWrapper();
			updateWrapper.eq("dspdt_id", dd.getUuid());
			DispatchDetailRoute dispatchRoute = new DispatchDetailRoute();
			dispatchRoute.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			dispatchRouteService.update(dispatchRoute, updateWrapper);
			//将调度明细类型置为无效
			UpdateWrapper typeWrapper = new UpdateWrapper();
			typeWrapper.eq("dspdt_id", dd.getUuid());
			DispatchDetailType type = new DispatchDetailType();
			type.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			typeService.update(type, typeWrapper);
			//将调度明细巡查航线置为无效
			UpdateWrapper pathWrapper = new UpdateWrapper();
			pathWrapper.eq("dspdt_id", dd.getUuid());
			DispatchDetailPath detailPath = new DispatchDetailPath();
			detailPath.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			detailPathService.update(detailPath, pathWrapper);
		}
		
	}
	public void cleanOneDayBak(UavDispatch uavDispatch) {
		//将事件数和总耗时置0
		uavDispatch.setEventNum(0);
		uavDispatch.setAreaNum(0);
		uavDispatch.setTimeAll(Float.valueOf(0));
		//uavDispatch.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);//改动：删除路线
		this.updateById(uavDispatch);
		//将调度明细和调度明细路线置空
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		queryWrapper.eq("uav_dsp_id", uavDispatch.getUuid());
		List<DispatchDetail> dds = dispatchDetailService.list(queryWrapper);
		for(DispatchDetail dd : dds) {
			dd.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			dispatchDetailService.updateById(dd);
			UpdateWrapper updateWrapper = new UpdateWrapper();
			updateWrapper.eq("dspdt_id", dd.getUuid());
			DispatchDetailRoute dispatchRoute = new DispatchDetailRoute();
			dispatchRoute.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			dispatchRouteService.update(dispatchRoute, updateWrapper);
			//将调度明细类型置为无效
			UpdateWrapper typeWrapper = new UpdateWrapper();
			typeWrapper.eq("dspdt_id", dd.getUuid());
			DispatchDetailType type = new DispatchDetailType();
			type.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			typeService.update(type, typeWrapper);
			//将调度明细巡查航线置为无效
			UpdateWrapper pathWrapper = new UpdateWrapper();
			pathWrapper.eq("dspdt_id", dd.getUuid());
			DispatchDetailPath detailPath = new DispatchDetailPath();
			detailPath.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			detailPathService.update(detailPath, pathWrapper);
		}
		
	}
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void doCollect(String uuid) {
		String personId = ThreadLocalUtil.getResources().getUuid();
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		queryWrapper.eq("user_id", personId);
		queryWrapper.eq("uav_dsp_id", uuid);
		RouteCollect routeCollect = routeCollectService.getOne(queryWrapper);
		if(routeCollect  == null) {
			routeCollect = new RouteCollect();
			routeCollect.setUavDspId(uuid);
			routeCollect.setUserId(personId);
			routeCollect.setDataStatus(GlobalConstant.DATA_STATUS_VALID);
		}else {
			routeCollect.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		}
		routeCollectService.saveOrUpdate(routeCollect);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPage<Map> getHistoryList(Page page, Map<String, Object> param) {
		IPage<Map> historyList = uavDispatchMapper.getHistoryList(page, param);
		for(Map map : historyList.getRecords()) {
			List<Map> dispatchDetail = uavDispatchMapper.getDispatchDetail((String)map.get("uuid"));
			map.put("dispatchDetail", dispatchDetail);
		}
		return historyList;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public IPage<Map> getCollectList(Page page, Map<String, Object> param) {
		param.put("userId", ThreadLocalUtil.getResources().getUuid());
		IPage<Map> collectList = uavDispatchMapper.getCollectList(page, param);
		for(Map map : collectList.getRecords()) {
			List<Map> dispatchDetail = uavDispatchMapper.getDispatchDetail((String)map.get("uuid"));
			map.put("dispatchDetail", dispatchDetail);
		}
		return collectList;
	}
	@Transactional(rollbackFor = Exception.class)
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void doQuote(String targetId, String sourceId) {
		//置空原来调度信息
		UavDispatch uavDispatch = new UavDispatch();
		uavDispatch.setUuid(targetId);
		cleanOneDayBak(uavDispatch);
		//查询新的调度信息
		UavDispatch sourceUavDispatch = this.getById(sourceId);
		uavDispatch.setTime(null);//修复bug
		uavDispatch.setEventNum(sourceUavDispatch.getEventNum());
		uavDispatch.setTimeAll(sourceUavDispatch.getTimeAll());
		uavDispatch.setAreaNum(sourceUavDispatch.getAreaNum());
		//改动
		uavDispatch.setFlyPerson(sourceUavDispatch.getFlyPerson());
		uavDispatch.setUavId(sourceUavDispatch.getUavId());
		this.updateById(uavDispatch);
		QueryWrapper queryWrapper = new QueryWrapper();
		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		queryWrapper.eq("uav_dsp_id", sourceUavDispatch.getUuid());
		List<DispatchDetail> dds = dispatchDetailService.list(queryWrapper);
		for(DispatchDetail dd : dds) {
			QueryWrapper typeWrapper = new QueryWrapper();
			typeWrapper.eq("dspdt_id", dd.getUuid());
			typeWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			List<DispatchDetailType> types = typeService.list(typeWrapper);
			QueryWrapper pathWrapper = new QueryWrapper();
			pathWrapper.eq("dspdt_id", dd.getUuid());
			pathWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			List<DispatchDetailPath> paths = detailPathService.list(pathWrapper);
			//新增调度明细
			dd.setUuid(null);
			dd.setUavDspId(targetId);
			dispatchDetailService.save(dd);
			//新增调度明细类型
			for(DispatchDetailType type : types) {
				type.setUuid(null);
				type.setDspdtId(dd.getUuid());
				typeService.save(type);
			}
			//新增调度明细路线
			QueryWrapper routeWrapper = new QueryWrapper();
			routeWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			routeWrapper.eq("check_point_id", dd.getPointId());
			List<Route> routes = routeService.list(routeWrapper);
			for(Route route : routes) {
				DispatchDetailRoute dispatchRoute = new DispatchDetailRoute();
				dispatchRoute.setDspdtId(dd.getUuid());
				dispatchRoute.setRouteId(route.getUuid());
				dispatchRoute.setStatus("1");
				dispatchRouteService.save(dispatchRoute);
			}
			//新增调度明细
			for(DispatchDetailPath path : paths) {
				path.setUuid(null);
				path.setDspdtId(dd.getUuid());
				path.setStatus("1");
				detailPathService.save(path);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map init(Map<String, String> param) throws Exception {
		Map map = new HashMap();
		List<Map> dateArray = uavDispatchMapper.getDateState(param);
		map.put("dateArray",dateArray);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

		Date time=null;
		time= sdf.parse(sdf.format(new Date()));
		map.put("beforeDate",(time.getTime()+"").substring(0, 10));
		if(dateArray.size()>0) {
			Date beforeDate = (Date) dateArray.get(0).get("time");
			if(beforeDate.before(new Date())) {
				map.put("beforeDate",(beforeDate.getTime()+"").substring(0, 10));
			}
		}
		//无人机列表
		map.put("allUav", uavDispatchMapper.getAllUav(param));
		//飞行员列表
		map.put("personList", eventMainService.getPersonList());
		return map;
	}

	@Override
	public Map getRouteDetail(String uuid) {
		
		//获取调度明细
		Map result = new HashMap();
		List<Map> dispatchDetail = uavDispatchMapper.getDispatchDetail(uuid);
		result.put("dispatchDetail",dispatchDetail);
		//是否可编辑
		UavDispatch uavDispatch = this.getById(uuid);
		result.put("uavDispatch",uavDispatch);
		result.put("isEdit",false);
		String timeById = uavDispatchMapper.getTimeById(uuid);
		LocalDate localDate = LocalDate.parse(timeById, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		System.out.println("##1111111" + localDate);
		localDate = localDate.minusDays(-1);//今天可编辑
		System.out.println("##2222222" + localDate);
		System.out.println("##3333333" + LocalDate.now());
		if(LocalDate.now().isBefore(localDate)) {
			result.put("isEdit",true);
		}
		//是否被收藏
		QueryWrapper collectWrapper = new QueryWrapper();
		collectWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		collectWrapper.eq("user_id",ThreadLocalUtil.getResources().getUuid());
		collectWrapper.eq("uav_dsp_id",uuid);
		RouteCollect routeCollect = routeCollectService.getOne(collectWrapper);
		String isCollect = "0";
		if(routeCollect != null) {
			isCollect = "1";
		}
		result.put("isCollect", isCollect);
		
		return result;
	}

	@Override
	public List<Map> getUavByFlyPerson(String pid) {

		return uavDispatchMapper.getUavByFlyPerson(pid);
	}

	@Override
	public List<UavDispatch> getRouteListByDate(Map<String, Object> param) {
		return uavDispatchMapper.getRouteListByDate(param);
	}
}
