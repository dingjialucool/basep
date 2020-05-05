package com.chinobot.plep.home.help.controller;


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
import com.chinobot.cityle.base.service.IDeptService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.help.entity.Help;
import com.chinobot.plep.home.help.service.IHelpService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author djl
 * @since 2019-08-31
 */
@Api(tags= {"帮助类接口"})
@RestController
@RequestMapping("/api/help")
public class HelpController extends BaseController {
	
	@Autowired
	private IHelpService helpService;
	
	@ApiOperation(value = "获取帮助信息", notes = "参数对象param")
	@PostMapping("/searchHelp")
//	@RequestMapping("/searchHelp")
	public Result searchHelp(@RequestBody Map<String, Object> param) {
		
		QueryWrapper<Help> queryWrapper = new QueryWrapper<Help>();
		if(CommonUtils.isNotEmpty(param.get("uuid").toString())) {
			queryWrapper.eq("uuid", param.get("uuid").toString());
			queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			 Help help = helpService.getOne(queryWrapper);
			return ResultFactory.success(help);
		}
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "修改保存帮助信息", notes = "参数对象param")
	@PostMapping("/editHelp")
//	@RequestMapping("/editHelp")
	public Result editHelp(@RequestBody Map<String, Object> param) {
		Help help = new Help();
		if(CommonUtils.isNotEmpty(param.get("uuid").toString())) {
			help.setUuid(param.get("uuid").toString());
		}
		if(CommonUtils.isNotEmpty(param.get("title").toString())) {
			help.setTitle(param.get("title").toString());
		}
		if(CommonUtils.isNotEmpty(param.get("content").toString())) {
			help.setContent(param.get("content").toString());
		}
		helpService.saveOrUpdate(help);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "删除帮助信息", notes = "参数对象param")
	@PostMapping("/deleteHelp")
//	@RequestMapping("/deleteHelp")
	public Result deleteHelp(@RequestBody Map<String, Object> param) {
		Help help = new Help();
		if(CommonUtils.isNotEmpty(param.get("uuid").toString())) {
			help.setUuid(param.get("uuid").toString());
		}
		help.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		helpService.saveOrUpdate(help);
		return ResultFactory.success();
	}
	
}
