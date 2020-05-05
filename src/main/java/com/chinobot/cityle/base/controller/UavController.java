package com.chinobot.cityle.base.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.EquipmentParts;
import com.chinobot.cityle.base.entity.Parts;
import com.chinobot.cityle.base.entity.Uav;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.cityle.base.service.IEquipmentPartsService;
import com.chinobot.cityle.base.service.IPartsService;
import com.chinobot.cityle.base.service.IUavService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 设备 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-03-27
 */
@Api(tags= {"设备类接口"})
@RestController
@RequestMapping("/api/base/equipment")
public class UavController extends BaseController {

	@Autowired
	private IUavService uavService;
	@Autowired
	private IPartsService partsService;
	@Autowired
	private IEquipmentPartsService equipmentPartsService;
	@Autowired
	private IFileBusService fileBusService;
	
	
	@ApiOperation(value = "设备分页列表", notes = "参数 - 分页page,Map param")
	@GetMapping("/page")
//	@RequestMapping("/page")
	public Result equipmentPage(Page page, @RequestParam Map<String, Object> param) {
		
		param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
		return ResultFactory.success(uavService.getUavPage(page, param));
	}
	
	@ApiOperation(value = "判断设备号是否已存在", notes = "参数 - Map param")
	@GetMapping("/isSame")
//	@RequestMapping("/isSame")
	public Result isSame(@RequestParam Map<String, Object> param) {
		
		String ecode = (String) param.get("ecode");
		boolean bo = uavService.isTheSame(ecode);
		if(bo) {//如果设备编号已存在，则保存失败
			Map resuMap = new HashMap();
			resuMap.put("result", "1");
			return ResultFactory.success(resuMap);
		}
		return ResultFactory.success();
	}

	@ApiOperation(value = "根据id获取设备", notes = "无参数")
	@PostMapping("")
//	@RequestMapping("")
	public Result equipment(String uuid) {
		Map result = new HashMap<>();
		result.put("equipment", uavService.getUavById(uuid));
		
		Map param = new HashMap<>();
		param.put("busId", uuid);
		param.put("url", GlobalConstant.IO_READ_IMAGE_PREFIX);
		param.put("module", "equipment_album");
		result.put("files", fileBusService.getFileIdByBusId(param));//相册
		
		param.put("module", "equipment_video");
		result.put("videoFiles", fileBusService.getFileIdByBusId(param));//视频
		return ResultFactory.success(result);
	}
	
	
	@ApiOperation(value = "设备 新增修改", notes = "参数 - 设备地址包装类")
	@PostMapping("/edit")
//	@RequestMapping("/edit")
	public Result equipmentEdit(@RequestBody VoAddressBase<Uav> voAddressBase) {
		
		return ResultFactory.success(uavService.editUav(voAddressBase));
	}
	
	
	@ApiOperation(value = "设备 删除", notes = "参数 -设备类 uav对象")
	@PostMapping("/del")
//	@RequestMapping("/del")
	public Result equipmentDel(@RequestBody Uav uav) {
		uav.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		
		return ResultFactory.success(uavService.updateById(uav));
	}
	
	
	@ApiOperation(value = "部件 分页列表", notes = "参数 - 分页page,Map param")
	@GetMapping("/parts/page")
//	@RequestMapping("/parts/page")
	public Result partsPage(Page page, @RequestParam Map<String, Object> param) {
		Map result = new HashMap<>();
		result.put("partsList", partsService.getPartsPage(page, param));
		if(CommonUtils.objNotEmpty(param.get("equipmentId"))) {
			result.put("equipment", uavService.getUavById(param.get("equipmentId").toString()));
		}
		
		return ResultFactory.success(result);
	}
	
	
	@ApiOperation(value = "所有部件/关联部件列表", notes = "参数 - Map param")
	@GetMapping("/parts/list")
//	@RequestMapping("/parts/list")
	public Result partsList(@RequestParam Map<String, String> param) {
		Map result = new HashMap<>();
		param.put("mountEquipment", "1");
 		// 所有未挂载部件和当前设备已挂载部件列表
		result.put("partsList", partsService.getPartsList(param));
		
		QueryWrapper<EquipmentParts> equipmentPartsWrapper = new QueryWrapper<EquipmentParts>();
		equipmentPartsWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		equipmentPartsWrapper.eq("equipment_id", param.get("equipmentId"));
		// 关联部件列表
		result.put("equipmentPartsList", equipmentPartsService.list(equipmentPartsWrapper));
		
		return ResultFactory.success(result);
	}
	
	
	@ApiOperation(value = "根据id获取部件", notes = "参数 - 部件id")
	@PostMapping("/parts")
//	@RequestMapping("/parts")
	public Result parts(String uuid) {
		Map result = new HashMap<>();
		result.put("parts", partsService.getPartsById(uuid));
		
		Map param = new HashMap<>();
		param.put("busId", uuid);
		param.put("url", GlobalConstant.IO_READ_IMAGE_PREFIX);
		param.put("module", "parts_album");
		result.put("files", fileBusService.getFileIdByBusId(param));
		
		return ResultFactory.success(result);
	}
	
	
	@ApiOperation(value = "部件 新增修改", notes = "参数 - 部件地址包装类")
	@PostMapping("/parts/edit")
//	@RequestMapping("/parts/edit")
	public Result partsEdit(@RequestBody VoAddressBase<Parts> voAddressBase) {
		
		return ResultFactory.success(partsService.editUav(voAddressBase));
	}
	
	
	@ApiOperation(value = "部件 删除", notes = "参数 - 部件类对象parts")
	@PostMapping("/parts/del")
//	@RequestMapping("/parts/del")
	public Result partsDel(@RequestBody Parts parts) {
		parts.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		
		return ResultFactory.success(partsService.updateById(parts));
	}
	
	@ApiOperation(value = "设备部件关联 新增修改", notes = "参数 - 设备部件集合equipmentParts")
	@PostMapping("/editEquipmentParts")
//	@RequestMapping("/editEquipmentParts")
	public Result editEquipmentParts(@RequestBody List<EquipmentParts> equipmentParts) {
		
		return ResultFactory.success(equipmentPartsService.editEquipmentParts(equipmentParts));
	}
}
