package com.chinobot.plep.home.building.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.AddressBase;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.cityle.base.service.IAddressBaseService;
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
import com.chinobot.plep.home.building.entity.Village;
import com.chinobot.plep.home.building.mapper.VillageMapper;
import com.chinobot.plep.home.building.service.IVillageService;

@Service
public class VillageServiceImpl  extends BaseService<VillageMapper, Village> implements IVillageService{
	
	@Autowired
	private VillageMapper villageMapper;
	@Autowired
	private IDeptService deptService;
	@Autowired
	private IAddressBaseService addressBaseService;
	@Autowired
	private IVillageService villageService;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IRegionsService regionsService;
	
	@Override
	public Map getVillageAddrPage(Page page, Map<String, Object> param) {
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
		IPage<VoAddressBase<Map>> villageListPage = villageMapper.getVillageAddr(page,param);
		
		List<VoAddressBase<Map>> villageList = villageListPage.getRecords();
		for(VoAddressBase voAddressBase:villageList) {
			Map hashMap = (Map) voAddressBase.getEntity();
			if(!CommonUtils.isObjEmpty(hashMap.get("uuid"))) {
				hashMap.put("imageList", villageMapper.getImgListByBusId("village_img",String.valueOf(hashMap.get("uuid"))));
			}
		}
		map.put("villageList", villageListPage);
		return map;
	}

	@Override
	public Map deptInfo() {
		Map map = new HashMap();
		Person person = ThreadLocalUtil.getResources();
		//if(ThreadLocalUtil.isShenz()) {
			Dept dept = ThreadLocalUtil.getResources().getDept();
			QueryWrapper<Dept> queryWrapper = new QueryWrapper<Dept>();
			queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			queryWrapper.eq("parent_id", dept.getUuid());
			List<Dept> deptList  = deptService.list(queryWrapper);
			List<Map> deptId = new ArrayList();
			HashMap deptMap =  new HashMap();
			deptMap.put("key",dept.getUuid());
			deptMap.put("code", dept.getDname());
			deptId.add(deptMap);
			for (int a=0;a<deptList.size();a++) {
				deptMap =  new HashMap();
				deptMap.put("key",deptList.get(a).getUuid());
				deptMap.put("code", deptList.get(a).getDname());
				deptId.add(deptMap);
			}
			map.put("deptList", deptId);
			
//		}else {
//			Dept dept = ThreadLocalUtil.getResources().getDept();
//			List<Map>  deptId = new ArrayList();
//			HashMap  deptMap = new HashMap();;
//			if(!CommonUtils.isObjEmpty(dept)) {
//				deptMap.put("key",dept.getUuid());
//				deptMap.put("code", dept.getDname());
//				deptId.add(deptMap);
//			}
//			map.put("deptList", deptId);
//		}
		return map;
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
		public Result editVillage(VoAddressBase<Village> voAddressBase) {
			// 保存地址库
			AddressBase addressBase = voAddressBase.getAddressBase();
			//设置省市区
			setProvinceCity(addressBase);
			if(StringUtils.isNotEmpty(addressBase.getPolyline())) {
				addressBase.setCenter(getCenter(addressBase.getPolyline()));
			}
			addressBase.setAtype("p_village");
			addressBaseService.saveOrUpdate(addressBase);
			
			//保存小区信息
			Village village = (Village) voAddressBase.getEntity();
			village.setAbsId(addressBase.getUuid());
			villageService.saveOrUpdate(village);
			
			// 文件关联
			List<FileBus> fileBus = voAddressBase.getFileBus();
			QueryWrapper<FileBus> queryWrapper = new QueryWrapper<FileBus>();
			queryWrapper.eq("bus_id", village.getUuid());
//			// 清空文件关联
			fileBusService.remove(queryWrapper);
			if(0 < fileBus.size()) {
				for (int i = 0, size = fileBus.size(); i < size; i++) {
					fileBus.get(i)
						.setBusId(village.getUuid())
						.setSort(i + 1)
						.setModule("village_img");
				}
				// 保存关联
				fileBusService.saveOrUpdateBatch(fileBus);
			}
			return ResultFactory.success(village.getUuid());
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

		@Override
		public boolean delVillage(Village village) {
			
			return villageService.removeById(village.getUuid());
		}

}
