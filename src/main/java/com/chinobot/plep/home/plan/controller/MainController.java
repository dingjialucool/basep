package com.chinobot.plep.home.plan.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.plan.service.IMainService;

import io.swagger.annotations.Api;

/**
 *  
 * @ClassName: MainController   
 * @Description: 主页面 前端控制器  
 * @author: dingjialu  
 * @date:2019年7月9日 上午11:06:35
 */
@Api(tags= {"主页面接口-N(未使用)"})
@RestController
@RequestMapping("/api/main")
public class MainController extends BaseController{

	@Autowired
	public IMainService mainService;
	
	/**
	 *  页面初始化数据 无人机与事件数据
	 * @param param
	 * @return
	 */
	@Deprecated
	@RequestMapping("")
	public Result getMainData(Page page,@RequestParam Map<String, Object> param) {
		
		
		return ResultFactory.success(mainService.getMainData(page,param));
	}
}
