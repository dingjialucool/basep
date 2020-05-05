package com.chinobot.plep.home.building.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.building.entity.Village;
import com.chinobot.plep.home.building.service.IVillageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

	/**
	 * <p>
	 *   
	 * </p>小区 前端控制器
	 *
	 * @author zll
	 * @since 2019-07-09
	 */
	@Api(tags= {"小区接口"})
	@RestController
	@RequestMapping("/api/base/village")
	public class VillageController  extends BaseController {
		
		@Autowired
		private IVillageService villageService;
		
		@ApiOperation(value = "获取小区列表 -N(暂未使用)", notes = "参数-分页page,Map param")
		@GetMapping("/villageAddrPage")
		public Result villageAddrPage(Page page, @RequestParam Map<String, Object> param) throws Exception {
			return ResultFactory.success(villageService.getVillageAddrPage(page, param));
		}
		
		@ApiOperation(value = "初始化部门", notes = "无参数")
		@GetMapping("/deptInfo")
//		@RequestMapping("/deptInfo")
		public Result deptInfo() throws Exception {
			return ResultFactory.success(villageService.deptInfo());
		}
		
		@ApiOperation(value = "编辑小区 -N(暂未使用)", notes = "参数-小区地址包装类voAddressBase")
		@PostMapping("/edit")
		public Result edit(@RequestBody VoAddressBase<Village> voAddressBase) {
			
			return villageService.editVillage(voAddressBase);
		}
		
		
		@ApiOperation(value = "删除小区 -N(暂未使用)", notes = "参数-小区village")
		@PostMapping("/delVillage")
		public Result delVillage(@RequestBody Village village) {
			
			return ResultFactory.success(villageService.delVillage(village));
		}
	}


