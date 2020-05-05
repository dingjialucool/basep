package com.chinobot.plep.home.building.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.CommunicationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.AddressBase;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.DeptGrid;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.cityle.base.entity.vo.VoAddressBaseBuildingDept;
import com.chinobot.cityle.base.entity.vo.VoAddressBaseScenesDeptGrids;
import com.chinobot.cityle.base.service.IAddressBaseService;
import com.chinobot.cityle.base.service.IDeptGridService;
import com.chinobot.cityle.base.service.IDeptService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.entity.Regions;
import com.chinobot.framework.web.service.IRegionsService;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.building.entity.Building;
import com.chinobot.plep.home.building.entity.VoDeptAddressBase;
import com.chinobot.plep.home.building.mapper.BuildingMapper;
import com.chinobot.plep.home.building.service.IBuildingService;

/**
 * <p>
 * 网格 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-03-26
 */
@Service
public class BuildingServiceImpl extends BaseService<BuildingMapper, Building> implements IBuildingService {

	@Autowired
	private BuildingMapper buildingMapper;
	@Autowired
	private IBuildingService buildingService;
	@Autowired
	private IAddressBaseService addressBaseService;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IDeptGridService deptGridService;
	@Autowired
	private IRegionsService regionsService;
	@Autowired
	private IDeptService deptService;
	
	@Override
	public List<Map> getBuildingAddrList(Map<String, String> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		
		return buildingMapper.getBuildingAddrList(param);
	}
	
	@Override
	public IPage<VoAddressBase<Map>> getBuildingAddrPage(Page page, Map<String, String> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		IPage<VoAddressBase<Map>> buildingP = buildingMapper.getBuildingAddr(page, param);
		return buildingP;
	}

	@Override
	public VoAddressBase<Map> getBuildingAddrById(String uuid) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		param.put("uuid", uuid);
		
		return buildingMapper.getBuildingAddr(param);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Result editGrid(VoAddressBaseBuildingDept<Building> voAddressBaseScenesDeptGrids) {
		
		VoAddressBase<Building> voAddressBase = voAddressBaseScenesDeptGrids.getVoAddressBase();
		// 保存地址库
		AddressBase addressBase = voAddressBase.getAddressBase();
		addressBase.setAtype("p_building");
		addressBaseService.saveOrUpdate(addressBase);
//		
		// 保存网格
		Building building = (Building) voAddressBase.getEntity();
		building.setAbsId(addressBase.getUuid());
		buildingService.saveOrUpdate(building);
		
		// 删除部门网格关联
		QueryWrapper<DeptGrid> deptGridWrapper = new QueryWrapper<>();
		deptGridWrapper.eq("grid_id", building.getUuid());
		deptGridService.remove(deptGridWrapper);
		// 保存部门关联
		List<DeptGrid> deptGrids = voAddressBaseScenesDeptGrids.getDeptGrids();
		
		if(0 < deptGrids.size()) {
			for (int i = 0, size =deptGrids.size(); i < size; i++) {
				deptGrids.get(i).setGridId(building.getUuid());
			}
			deptGridService.saveOrUpdateBatch(deptGrids);
		}
//		
		// 文件关联
		List<FileBus> fileBus = voAddressBase.getFileBus();
		QueryWrapper<FileBus> queryWrapper = new QueryWrapper<FileBus>();
		queryWrapper.eq("bus_id", building.getUuid());
//		// 清空文件关联
		fileBusService.remove(queryWrapper);
		if(0 < fileBus.size()) {
			for (int i = 0, size = fileBus.size(); i < size; i++) {
				fileBus.get(i)
					.setBusId(building.getUuid())
					.setSort(i + 1)
					.setModule("building_album");
			}
			// 保存关联
			fileBusService.saveOrUpdateBatch(fileBus);
		}
//		
//		// 更新场景的网格id
//		List<Scene> scenes = voAddressBaseScenesDeptGrids.getScenes();
//		if(0 < scenes.size()) {
//			for (int i = 0, size = scenes.size(); i < size; i++) {
//				if(!"".equals(scenes.get(i).getGridId())) {
//					scenes.get(i).setGridId(grid.getUuid());
//				}
//			}
//			sceneService.updateBatchById(scenes);
//		}
//		
		return ResultFactory.success(building.getUuid());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Result delGrid(Building building) {
		// 删除网格
		building.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		buildingService.updateById(building);
		
		Building buildingInfo = buildingService.getById(building.getUuid());
		AddressBase addressBase = new AddressBase();
		addressBase.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		addressBase.setUuid(buildingInfo.getAbsId());
		addressBaseService.updateById(addressBase);
		return ResultFactory.success();
	}

	@Override
	public List<LinkedHashMap<String, Object>> areaData(Map<String, String> param) {
		return buildingMapper.areaData(param);
	}

	@Override
	public IPage<VoAddressBase<Map>> getBuildingWeiAddrPage(Page page, Map<String, String> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		IPage<VoAddressBase<Map>> buildingP = buildingMapper.getBuildingWeiAddr(page, param);
		return buildingP;
	}

	@Override
	public IPage<VoAddressBase<Map>> getBuildingYiAddrPage(Page page, Map<String, String> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		IPage<VoAddressBase<Map>> buildingP = buildingMapper.getBuildingYiAddr(page, param);
		return buildingP;
	}

	@Override
	public List<Map> getNearbyBuilding(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return buildingMapper.getNearbyBuilding(param);
	}

	@Override
	public List<Map> getBuildingAddrPolicy(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return buildingMapper.getBuildingAddrPolicy(param);
	}

	@Override
	public Result addBuild(VoDeptAddressBase<Building> voAddressBase) {
		
		// 保存地址库
		AddressBase addressBase = voAddressBase.getAddressBase();
		//设置省市区
		setProvinceCity(addressBase);
		if(StringUtils.isNotEmpty(addressBase.getPolyline())) {
			addressBase.setCenter(getCenter(addressBase.getPolyline()));
		}
		addressBase.setAtype("p_building");
		addressBaseService.saveOrUpdate(addressBase);
		
		//保存建筑信息
		Building building = (Building) voAddressBase.getEntity();
		building.setAbsId(addressBase.getUuid());
		buildingService.saveOrUpdate(building);
		
		//清空部门网格关联
		QueryWrapper<DeptGrid> queryWrapperBuid = new QueryWrapper<DeptGrid>();
		queryWrapperBuid.eq("grid_id", building.getUuid());
		deptGridService.remove(queryWrapperBuid);
		
		//保存部門建筑表
		DeptGrid deptGrid = new DeptGrid();
		deptGrid.setDeptId(voAddressBase.getDeptGrid().getDeptId());
		deptGrid.setGridId(building.getUuid());
		deptGridService.save(deptGrid);
		
		// 文件关联
		List<FileBus> fileBus = voAddressBase.getFileBus();
		QueryWrapper<FileBus> queryWrapper = new QueryWrapper<FileBus>();
		queryWrapper.eq("bus_id", building.getUuid());
		// 清空文件关联
		fileBusService.remove(queryWrapper);
		if(0 < fileBus.size()) {
			for (int i = 0, size = fileBus.size(); i < size; i++) {
				fileBus.get(i)
					.setBusId(building.getUuid())
					.setSort(i + 1)
					.setModule("building_album");
			}
			// 保存关联
			fileBusService.saveOrUpdateBatch(fileBus);
		}
		return ResultFactory.success(building.getUuid());
	}
	
	private void setProvinceCity(AddressBase addressBase) {
		if(!CommonUtils.isEmpty(addressBase.getProvince())) {
			String provinceCity = addressBase.getProvince();
			String proviceName = provinceCity.substring(0, provinceCity.indexOf("省")+1);
			
			if(!CommonUtils.isEmpty(proviceName)) {
				QueryWrapper<Regions> queryWrapper = new QueryWrapper<Regions>();
				queryWrapper.eq("name", proviceName);
				List<Regions> proviceList = regionsService.list(queryWrapper);
				if(proviceList.size() > 0) {
					addressBase.setProvince(proviceList.get(0).getCode());
				}
			}
			String cityName = provinceCity.substring(provinceCity.indexOf("省")+1, provinceCity.indexOf("市")+1);
			if(!CommonUtils.isEmpty(cityName)) {
				QueryWrapper<Regions> queryWrapper = new QueryWrapper<Regions>();
				queryWrapper.eq("name", cityName);
				List<Regions> cityList = regionsService.list(queryWrapper);
				if(cityList.size() > 0) {
					addressBase.setCity(cityList.get(0).getCode());
				}
			}
			String districtName = provinceCity.substring(provinceCity.indexOf("市")+1, provinceCity.indexOf("区")+1);
			if(!CommonUtils.isEmpty(districtName)) {
				QueryWrapper<Regions> queryWrapper = new QueryWrapper<Regions>();
				queryWrapper.eq("name", districtName);
				List<Regions> districtList = regionsService.list(queryWrapper);
				if(districtList.size() > 0) {
					addressBase.setDistrict(districtList.get(0).getCode());
				}
			}
		}
	}
	
	//根据边界获取中心点
	private String getCenter(String polyline) {
		String[] polylineArray = polyline.split(";");
		float lng = 0;
		float lat = 0;
		 for (int f=0;f< polylineArray.length;f++) {
           String[] f2Arr = polylineArray[f].split(",");
           lng += Float.valueOf(f2Arr[0]);
           lat += Float.valueOf(f2Arr[1]);
       }
		 String center = String.valueOf(lng/polylineArray.length) +','+String.valueOf(lat/polylineArray.length);
		return center;
	}

	@Override
	public Map getBuilds(Page page, Map<String, Object> param) {
		
		Map map = new HashMap();
		Person person = ThreadLocalUtil.getResources();
		if(CommonUtils.isObjEmpty(param.get("deptId"))) {
			if(!ThreadLocalUtil.isShenz()) {
				Dept dept = ThreadLocalUtil.getResources().getDept();
				param.put("deptId", dept.getUuid());
			}
		}else
		{
			Dept dept = deptService.getById(String.valueOf(param.get("deptId")));
			if(dept.getDname().equals(GlobalConstant.SUPER_DEPT_NAME)) {
				param.put("deptId", "");
			}
		}
		IPage<VoAddressBase<Map>> buildListPage = buildingMapper.getBuildAddr(page, param);
		
		//根据建筑id获取部门建筑
		String uuid =(String)param.get("uuid");
		DeptGrid deptGrid = null;
		if(!CommonUtils.isObjEmpty(uuid)) {
			QueryWrapper<DeptGrid> queryWrapper = new QueryWrapper<DeptGrid>();
			queryWrapper.eq("grid_id", uuid);
			queryWrapper.select("dept_id","grid_id","uuid");
			deptGrid = deptGridService.getOne(queryWrapper);
		}
		
		
		List<VoAddressBase<Map>> buildList = buildListPage.getRecords();
		for(VoAddressBase voAddressBase:buildList) {
			Map hashMap = (Map) voAddressBase.getEntity();
			if(!CommonUtils.isObjEmpty(hashMap.get("uuid"))) {
				hashMap.put("imageList", buildingMapper.getImgListByBusId("building_album",String.valueOf(hashMap.get("uuid"))));
			}
			if(!CommonUtils.isObjEmpty(uuid)) {
				hashMap.put("deptGrid",deptGrid);
			}
		}
		map.put("buildList", buildListPage);
		return map;
	}

	@Override
	public Result deleteBuid(Building building) {
		// 删除建筑
		building.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		buildingService.updateById(building);
		
		//删除建筑对应的地址库
		Building buildingInfo = buildingService.getById(building.getUuid());
		AddressBase addressBase = new AddressBase();
		addressBase.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		addressBase.setUuid(buildingInfo.getAbsId());
		addressBaseService.updateById(addressBase);
		
		//删除建筑部门关系表
		QueryWrapper<DeptGrid> queryWrapperBuid = new QueryWrapper<DeptGrid>();
		queryWrapperBuid.eq("grid_id", building.getUuid());
		deptGridService.remove(queryWrapperBuid);
		
		return ResultFactory.success();
	}

}
