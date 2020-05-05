package com.chinobot.plep.home.plan.service.impl;

import com.chinobot.plep.home.plan.entity.CameraConfig;
import com.chinobot.plep.home.plan.entity.FlyArea;
import com.chinobot.plep.home.plan.entity.FlyConfig;
import com.chinobot.plep.home.plan.entity.FlyPath;
import com.chinobot.plep.home.plan.entity.OutputParamter;
import com.chinobot.plep.home.plan.entity.vo.VoPathPlan;
import com.chinobot.plep.home.plan.mapper.FlyAreaMapper;
import com.chinobot.plep.home.plan.service.ICameraConfigService;
import com.chinobot.plep.home.plan.service.IFlyAreaService;
import com.chinobot.plep.home.plan.service.IFlyConfigService;
import com.chinobot.plep.home.plan.service.IFlyPathService;
import com.chinobot.plep.home.plan.service.IOutputParamterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-06-17
 */
@Service
public class FlyAreaServiceImpl extends BaseService<FlyAreaMapper, FlyArea> implements IFlyAreaService {
	@Autowired
	private IFlyConfigService flyConfigService;
	@Autowired
	private ICameraConfigService cameraConfigService;
	@Autowired
	private IFlyPathService flyPathService;
	@Autowired
	private IOutputParamterService outParamterService;
	@Autowired
	private FlyAreaMapper flyAreaMapper;
	
	@Override
	public void saveConfigAndPath(VoPathPlan vo) {
		flyConfigService.saveOrUpdate(vo.getFlyConfig());
		cameraConfigService.saveOrUpdate(vo.getCameraConfig());
		List<FlyPath> paths = vo.getPaths();
		List<OutputParamter> outputParamters = vo.getOutputParamters();
		//航线集合
		QueryWrapper pathWrapper = new QueryWrapper();
		pathWrapper.eq("area_id", vo.getFlyConfig().getAreaId());
		pathWrapper.orderByAsc("path_type");
		List<FlyPath> list = flyPathService.list(pathWrapper);
		//输出参数
		List<OutputParamter> ps = new ArrayList<OutputParamter>();
		for(FlyPath path : list) {
			QueryWrapper paramWrapper = new QueryWrapper();
			paramWrapper.eq("path_id", path.getUuid());
			ps.add(outParamterService.getOne(paramWrapper));
		}
		for(int i=0;i<paths.size();i++) {
			FlyPath flyPath = paths.get(i);
			OutputParamter outputParamter = outputParamters.get(i);
			if(list != null && list.size()>0) {
				flyPath.setUuid(list.get(i).getUuid());
			}
			flyPathService.saveOrUpdate(flyPath);
			outputParamter.setPathId(flyPath.getUuid());
			if(list != null && list.size()>0) {
				outputParamter.setUuid(ps.get(i).getUuid());
			}
			outParamterService.saveOrUpdate(outputParamter);
		}
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public VoPathPlan getConfigAndPath(String areaId) {
		VoPathPlan vo = new VoPathPlan();
		//飞行配置
		QueryWrapper flyConfigWrapper = new QueryWrapper();
		flyConfigWrapper.eq("area_id", areaId);
		FlyConfig flyConfig = flyConfigService.getOne(flyConfigWrapper);
		if(flyConfig == null) {
			return null;
		}
		vo.setFlyConfig(flyConfig);
		//相机配置
		QueryWrapper cameraWrapper = new QueryWrapper();
		cameraWrapper.eq("area_id", areaId);
		vo.setCameraConfig(cameraConfigService.getOne(cameraWrapper));
		//航线集合
		QueryWrapper pathWrapper = new QueryWrapper();
		pathWrapper.eq("area_id", areaId);
		pathWrapper.orderByAsc("path_type");
		List<FlyPath> list = flyPathService.list(pathWrapper);
		vo.setPaths(list);
		//输出参数
		List<OutputParamter> ps = new ArrayList<OutputParamter>();
		for(FlyPath path : list) {
			QueryWrapper paramWrapper = new QueryWrapper();
			paramWrapper.eq("path_id", path.getUuid());
			ps.add(outParamterService.getOne(paramWrapper));
		}
		vo.setOutputParamters(ps);
		return vo;
	}

	@Override
	public Map getPathConfig(Map<String, Object> param) {
		
		Map map = new HashMap();
		
		String rangeId = (String) param.get("rangeId");
		String areaId = (String) param.get("areaId");
//		String pathIndex = (String) param.get("pathIndex");
		//飞行参数
//		QueryWrapper<FlyConfig> queryFlyconfig = new QueryWrapper<FlyConfig>();
//		queryFlyconfig.eq("area_id", rangeId).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//		FlyConfig flyConfig = flyConfigService.getOne(queryFlyconfig);
//		map.put("flyConfig", flyConfig);
		
		//航线
		QueryWrapper<FlyPath> queryWrapper = new QueryWrapper<FlyPath>();
		queryWrapper.eq("area_id", areaId).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		queryWrapper.orderByAsc("path_type");
		List<FlyPath> flyPathList = flyPathService.list(queryWrapper);
		map.put("flyPaths", flyPathList);
		
		List<OutputParamter> outputParamterList = new ArrayList<OutputParamter>();
		//输出参数
		for (FlyPath flyPath : flyPathList) {
			String pathId = flyPath.getUuid();
			QueryWrapper<OutputParamter> queryWrapper2 = new QueryWrapper<OutputParamter>();
			queryWrapper2.eq("path_id", pathId).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			OutputParamter outputParamter = outParamterService.getOne(queryWrapper2);
			outputParamterList.add(outputParamter);
		}
		map.put("outputParamters", outputParamterList);
		
		return map;
	}

	@Override
	public IPage<Map> getFlyAreaPage(Page page,Map param) {
		
		return flyAreaMapper.getFlyAreaPage(page,param);
	}

}
