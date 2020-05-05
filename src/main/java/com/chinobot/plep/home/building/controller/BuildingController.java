package com.chinobot.plep.home.building.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.cityle.base.entity.vo.VoAddressBaseBuildingDept;
import com.chinobot.cityle.base.service.IDeptService;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.building.entity.Building;
import com.chinobot.plep.home.building.entity.VoDeptAddressBase;
import com.chinobot.plep.home.building.service.IBuildingService;

import io.swagger.annotations.Api;

	/**
	 * <p>
	 * 违章建筑 前端控制器
	 * </p>
	 *
	 * @author shizt
	 * @since 2019-03-26
	 */
	@Api(tags= {"违章建筑接口-N(未使用)"})
	@RestController
	@RequestMapping("/api/base/building")
	public class BuildingController  extends BaseController {
		
		@Autowired
		private IBuildingService buildingService;
		@Autowired
		private IFileBusService fileBusService;
		@Autowired
		private IPersonService personService;
		
		@Autowired
		private IDeptService deptService;
		/**
		 * 网格/地址 列表
		 * @return
		 * @author shizt  
		 * @date 2019年3月27日
		 * @company chinobot
		 */
		@RequestMapping("/buildingAddrList")
		public Result gridAddrList(@RequestParam Map<String, String> param) {
			
			return ResultFactory.success(buildingService.getBuildingAddrList(param));
		}
		
		/**
		 * 网格/地址 分页列表
		 * @return
		 * @author shizt  
		 * @throws Exception 
		 * @date 2019年3月27日
		 * @company chinobot
		 */
		@RequestMapping("/buildingAddrPage")
		public Result gridAddrPage(Page page, @RequestParam Map<String, String> param) throws Exception {
			Person person = ThreadLocalUtil.getResources();
			if(!ThreadLocalUtil.isSystemRole()) {
				param.put("deptId", person.getDeptId());
			}
			return ResultFactory.success(buildingService.getBuildingAddrPage(page, param));
		}
		
		/**
		 * 未处理 分页列表
		 * @return
		 * @author shizt  
		 * @throws Exception 
		 * @date 2019年3月27日
		 * @company chinobot
		 */
		@RequestMapping("/buildingWeiAddrPage")
		public Result gridWeiAddrPage(Page page, @RequestParam Map<String, String> param) throws Exception {
			
			return ResultFactory.success(buildingService.getBuildingWeiAddrPage(page, param));
		}
		
		/**
		 * 已处理 分页列表
		 * @return
		 * @author shizt  
		 * @throws Exception 
		 * @date 2019年3月27日
		 * @company chinobot
		 */
		@RequestMapping("/buildingYiAddrPage")
		public Result gridYiAddrPage(Page page, @RequestParam Map<String, String> param) throws Exception {
			
			return ResultFactory.success(buildingService.getBuildingYiAddrPage(page, param));
		}

		/**
		 * 根据id获取网格
		 * @param uuid
		 * @return
		 * @author shizt  
		 * @date 2019年3月26日
		 * @company chinobot
		 */
		@RequestMapping("")
		public Result getGridAddr(String uuid) {
			Person person = ThreadLocalUtil.getResources();
			Map map = new HashMap();
			if(CommonUtils.isNotEmpty(uuid)) {
				map.put("info", buildingService.getBuildingAddrById(uuid));
				Map param = new HashMap<>();
				param.put("busId", uuid);
				param.put("url", GlobalConstant.IO_READ_IMAGE_PREFIX);
				param.put("module", "building_album");
				map.put("files", fileBusService.getFileIdByBusId(param));
			}
			if(!CommonUtils.isObjEmpty(person)) {
				// 部门列表
				QueryWrapper<Dept> deptWrapper = new QueryWrapper<>();
				deptWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
				map.put("deptList", deptService.list(deptWrapper));
				
				// 已关联违章建筑的部门列表
				Map paramDept = new HashMap<>();
				QueryWrapper<Dept> deptListWrapper = new QueryWrapper<>();
				Person personInfo = personService.getById(person.getUuid());
				deptListWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
				deptListWrapper.eq("uuid", personInfo.getDeptId());
				map.put("deptGridList", deptService.list(deptListWrapper));
				
			}
			
			return ResultFactory.success(map);
		}
		
		/**
		 * 网格 新增修改
		 * @param voAddressBase
		 * @return
		 * @author shizt  
		 * @date 2019年3月27日
		 * @company chinobot
		 */
		@RequestMapping("/edit")
		public Result edit(@RequestBody VoAddressBaseBuildingDept<Building> voAddressBase) {
			
			return buildingService.editGrid(voAddressBase);
		}
		
		/**
		 * 删除
		 * @param grid
		 * @return
		 * @author shizt
		 * @date 2019年3月18日
		 * @company chinobot
		 */
		@RequestMapping("/del")
		public Result del(@RequestBody Building building) {
			
			return buildingService.delGrid(building);
		}
		@RequestMapping("/buildingAddrPolicy")
		public Result getBuildingAddrPolicy(@RequestParam Map<String, Object> params) {
			HashMap resultMap = new HashMap();
		
			if(!CommonUtils.isObjEmpty(params.get("lngArray")) && !CommonUtils.isObjEmpty(params.get("latArray"))) {
				String[]  lngArray =  params.get("lngArray").toString().split(",");
				String[] latArray = params.get("latArray").toString().split(",");
				List<Float> lngList = new ArrayList();
				for(String string:lngArray) {
					lngList.add(Float.valueOf(string));
				}
				
				List<Float> latList = new ArrayList();
				for(String string:latArray) {
					latList.add(Float.valueOf(string));
				}
			  Collections.sort(lngList);
			  Collections.sort(latList);
			  HashMap param = new HashMap();
			  param.put("minLng",  lngList.get(0));
			  param.put("maxLng",  lngList.get(lngList.size()-1));
			  param.put("minLat",  latList.get(0));
			  param.put("maxLat",  latList.get(latList.size()-1));
				Person person = ThreadLocalUtil.getResources();
				if(!ThreadLocalUtil.isSystemRole()) {
					param.put("deptId", person.getDeptId());
				}
			  
			  resultMap.put("buildingInfoList", buildingService.getBuildingAddrPolicy(param));
			  
			}
			return ResultFactory.success(resultMap);
		}
		
		/**
		 *  新增/修改违章建筑物
		 * @param voAddressBase
		 * @return
		 */
		@RequestMapping("/addBuild")
		public Result addBuild(@RequestBody VoDeptAddressBase<Building> voAddressBase) {
			
			return buildingService.addBuild(voAddressBase);
		}
		
		/**
		 *  获取违章建筑
		 * @param page
		 * @param param
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/getBuilds")
		public Result getBuilds(Page page, @RequestParam Map<String, Object> param) throws Exception {
			return ResultFactory.success(buildingService.getBuilds(page, param));
		}
		
		@RequestMapping("/deleteBuid")
		public Result deleteBuid(@RequestBody Building building) {
			
			return buildingService.deleteBuid(building);
		}
	}


