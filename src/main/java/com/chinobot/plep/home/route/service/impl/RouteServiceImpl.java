package com.chinobot.plep.home.route.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.plan.entity.FlyArea;
import com.chinobot.plep.home.plan.entity.FlyPath;
import com.chinobot.plep.home.plan.entity.OutputParamter;
import com.chinobot.plep.home.plan.mapper.FlyAreaMapper;
import com.chinobot.plep.home.plan.service.IFlyAreaService;
import com.chinobot.plep.home.plan.service.IFlyPathService;
import com.chinobot.plep.home.plan.service.IOutputParamterService;
import com.chinobot.plep.home.route.entity.CheckPoint;
import com.chinobot.plep.home.route.entity.Route;
import com.chinobot.plep.home.route.entity.RouteBuilding;
import com.chinobot.plep.home.route.entity.vo.VoPointRelation;
import com.chinobot.plep.home.route.entity.vo.VoRoute;
import com.chinobot.plep.home.route.mapper.RouteMapper;
import com.chinobot.plep.home.route.service.ICheckPointService;
import com.chinobot.plep.home.route.service.IRouteBuildingService;
import com.chinobot.plep.home.route.service.IRouteService;
import com.chinobot.plep.home.routedd.entity.DispatchDetail;
import com.chinobot.plep.home.routedd.entity.DispatchDetailPath;
import com.chinobot.plep.home.routedd.entity.DispatchDetailRoute;
import com.chinobot.plep.home.routedd.service.IDispatchDetailPathService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailRouteService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 路线表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-07-16
 */
@Service
public class RouteServiceImpl extends BaseService<RouteMapper, Route> implements IRouteService {
	@Autowired
	private RouteMapper routeMapper;
	@Autowired
	private ICheckPointService checkPointService;
	@Autowired
	private IRouteBuildingService routeBuildingService;
	@Autowired
	private IFlyAreaService flyAreaService;
	@Autowired
	private IOutputParamterService outParamService;
	@Autowired
	private FlyAreaMapper flyAreaMapper;
	@Autowired
	private IFlyPathService flyPathService;
	@Autowired
	private IDispatchDetailPathService detailPathService;
	@Autowired
	private IDispatchDetailRouteService detailRouteService;
	@Autowired
	private IDispatchDetailService dispatchDetailService;
	
	@Override
	public IPage<Map> getIllegalBuilding(Page page, Map<String, String> param) {
		return routeMapper.getIllegalBuilding(page, param);
	}

	@Override
	public List<Map> getIllegalBuilding(Map<String, String> param) {
		return routeMapper.getIllegalBuilding(param);
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public String save(VoRoute vo) {
		CheckPoint checkPoint = vo.getCheckPoint();
		Route route = vo.getRoute();
		List<RouteBuilding> line = vo.getLine();
		String deptId = ThreadLocalUtil.getResources().getDept().getUuid();
		if(checkPoint != null && route != null && line != null) {
			//新增起飞点新增路线
			checkPoint.setDeptId(deptId);
			checkPointService.save(checkPoint);
			route.setCheckPointId(checkPoint.getUuid());
			route.setDeptId(deptId);
			this.save(route);
			saveLine(line,route.getUuid());
			return route.getUuid()+","+checkPoint.getUuid();
		}
		if(checkPoint == null && route != null && line != null) {
			//原有起飞点新增路线
			route.setDeptId(deptId);
			this.save(route);
			saveLine(line,route.getUuid());
			return route.getUuid()+","+route.getCheckPointId();
		}
		if(checkPoint == null && route != null && line == null) {
			//修改路线名称
			this.updateById(route);
		}
		if(checkPoint != null && route == null && line == null) {
			//修改起飞点名称地址
			checkPointService.updateById(checkPoint);
		}
		return null;
	}
	void saveLine(List<RouteBuilding> line, String routeId) {
		for(RouteBuilding rb : line) {
			rb.setRouteId(routeId);
			routeBuildingService.save(rb);
		}
	}

	@Override
	public List<Map> getRouteLine(String uuid) {
		
		return routeMapper.getRouteLine(uuid);
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delRoute(Route route) {
		route = this.getById(route.getUuid());
		route.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		this.updateById(route);
		//删除路线建筑关联
		QueryWrapper<RouteBuilding> queryWrapper = new QueryWrapper<RouteBuilding>();
		queryWrapper.eq("route_id", route.getUuid());
		routeBuildingService.remove(queryWrapper);
		//判断是否需要删除起飞点
		QueryWrapper<Route> routerapper = new QueryWrapper<Route>();
		routerapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		routerapper.eq("check_point_id", route.getCheckPointId());
		int count = this.count(routerapper);
		QueryWrapper<FlyArea> areaWrapper = new QueryWrapper<FlyArea>();
		areaWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		areaWrapper.eq("point_id", route.getCheckPointId());
		int areacount = flyAreaService.count(areaWrapper);
		if(count == 0 && areacount == 0) {
			CheckPoint p = new CheckPoint();
			p.setUuid(route.getCheckPointId());
			p.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			checkPointService.updateById(p);
		}
		
	}

	@Override
	public List<Map> getRoutePoint(String routeId) {
		return routeMapper.getRoutePoint(routeId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean addPointAndRelations(VoPointRelation vo) {
		
		boolean bo = false;
		CheckPoint point = vo.getPoint();
		List<String> relations = vo.getRelations();
		List<OutputParamter> outParams = vo.getOutParams();
		if(point == null) { //若起飞点为空，直接返回
			return false;
		}
		
		// 用于保存从起飞点出发再回到起飞点的时间
		if(outParams.size()>0) {//若输出参数不为空
			for (OutputParamter outputParamter : outParams) {
				outParamService.updateById(outputParamter);
			}
		}
		
		if(StringUtils.isNotEmpty(point.getUuid())) {//若起飞点主键不为空，则只关联起飞点
			for (String string : relations) {
				String pointId = point.getUuid();
				FlyArea flyArea = new FlyArea();
				flyArea.setUuid(string);
				flyArea.setPointId(pointId);
				bo = flyAreaService.updateById(flyArea);
			}
		}else {//否则新增并关联
			//新增起飞点
			point.setDeptId(ThreadLocalUtil.getResources().getDeptId());
			bo = checkPointService.save(point);
			//关联起飞点
			if(relations.size()>0) {
				for (String string : relations) {
					String pointId = point.getUuid();
					FlyArea flyArea = new FlyArea();
					flyArea.setUuid(string);
					flyArea.setPointId(pointId);
					flyAreaService.updateById(flyArea);
				}
			}
		}
		return bo;
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean deletePoint(String rangId, String pointId) {
		
		boolean bo = false;
		
		//把time_include_point置空
		QueryWrapper<FlyArea> queryWrapper = new QueryWrapper<FlyArea>();
		queryWrapper.eq("rang_id", rangId).eq("point_id", pointId).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		List<FlyArea> list = flyAreaService.list(queryWrapper);
		if(list.size()>0) {
			for (FlyArea flyArea : list) {
				QueryWrapper<FlyPath> flyPathQueryWrapper = new QueryWrapper<FlyPath>();
				flyPathQueryWrapper.eq("area_id", flyArea.getUuid()).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
				List<FlyPath> flyPathList = flyPathService.list(flyPathQueryWrapper);
				for (FlyPath flyPath : flyPathList) {
					Map map = new HashMap();
					map.put("pathId", flyPath.getUuid());
					flyAreaMapper.updateTimePoint(map);
				}
			}
		}
		
		//将范围内的区域 已经关联 了 起飞点的 取消 关联
		Map param = new HashMap();
		param.put("rangId", rangId);
		param.put("pointId", pointId);
		flyAreaMapper.updatePoint(param);
		
		
		//判断是否需要删除起飞点
		QueryWrapper<Route> routerapper = new QueryWrapper<Route>();
		routerapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		routerapper.eq("check_point_id", pointId);
		int count = this.count(routerapper);
		QueryWrapper<FlyArea> areaWrapper = new QueryWrapper<FlyArea>();
		areaWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		areaWrapper.eq("point_id", pointId);
		int areacount = flyAreaService.count(areaWrapper);
		if(count == 0 && areacount == 0) {
			CheckPoint p = new CheckPoint();
			p.setUuid(pointId);
			p.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			bo = checkPointService.updateById(p);
		}
		return bo;
	}

	@Override
	public List<Map> getRouteList(Map<String, String> param) {
		return routeMapper.getRouteList(param);
	}

	@Override
	public List<Map> getAllRouteLine(Map<String, String> param) {
		return routeMapper.getAllRouteLine(param);
	}

	@Override
	public IPage<Map> flyRouteList(Page page, Map<String, Object> param) {
		 if(param.get("domain")!=null && param.get("domain")!="" ) {
	        	String str = (String) param.get("domain");
	        	if(str.indexOf(',')>0) {
	        		String[] split = str.split(",");
	        		param.put("domain", split);
	        	}else {
					String[] split = new String[1];
					split[0] = str;
					param.put("domain", split);
				}
	        }
		return routeMapper.flyRouteList(page,param);
	}

	@Override
	public IPage<Map> flyTaskList(Page page, Map<String, Object> param) {
		if(param.get("fly_module")!=null && param.get("fly_module")!="" ) {
        	String str = (String) param.get("fly_module");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("fly_module", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("fly_module", split);
			}
        }
		if(param.get("taskStatus")!=null && param.get("taskStatus")!="" ) {
        	String str = (String) param.get("taskStatus");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("taskStatus", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("taskStatus", split);
			}
        }
		return routeMapper.flyTaskList(page,param);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean delFlyTask(Map<String, Object> param) {
		boolean bo = false;
		String taskId = (String) param.get("taskId");
		String lineType = (String) param.get("lineType");
		if(lineType.equals("2") || lineType.equals("3")) {//区域扫描
			QueryWrapper<DispatchDetailPath> queryWrapper = new QueryWrapper<DispatchDetailPath>();
			queryWrapper.eq("uuid", taskId);
			DispatchDetailPath dispatchDetailPath = detailPathService.getOne(queryWrapper);
			//删除调度明细路线
			UpdateWrapper<DispatchDetailPath> updateWrapper = new UpdateWrapper<DispatchDetailPath>();
			updateWrapper.eq("uuid", taskId);
			updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
			bo = detailPathService.update(updateWrapper);
			//删除调度明细
			UpdateWrapper<DispatchDetail> update = new UpdateWrapper<DispatchDetail>();
			update.eq("uuid", dispatchDetailPath.getDspdtId());
			update.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
			bo = dispatchDetailService.update(update);
		}
		if(lineType.equals("1")) {//定点跟踪
			QueryWrapper<DispatchDetailRoute> queryWrapper = new QueryWrapper<DispatchDetailRoute>();
			queryWrapper.eq("uuid", taskId);
			DispatchDetailRoute dispatchDetailRoute = detailRouteService.getOne(queryWrapper);
			//删除调度明细路线
			UpdateWrapper<DispatchDetailRoute> updateWrapper = new UpdateWrapper<DispatchDetailRoute>();
			updateWrapper.eq("uuid", taskId);
			updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
			bo = detailRouteService.update(updateWrapper);
			//删除调度明细
			UpdateWrapper<DispatchDetail> update = new UpdateWrapper<DispatchDetail>();
			update.eq("uuid", dispatchDetailRoute.getDspdtId());
			update.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
			bo = dispatchDetailService.update(update);
		}
		return bo;
	}

	@Override
	public List<Route> getRouteByDeptAndName(Map<String, Object> param) {
		
		return routeMapper.getRouteByDeptAndName(param);
	}
	
}
