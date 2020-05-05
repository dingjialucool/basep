package com.chinobot.plep.flyPlan.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.flyPlan.service.IFlyPlanService;
import com.chinobot.plep.home.building.entity.Building;
import com.chinobot.plep.home.plan.entity.Range;
import com.chinobot.plep.home.plan.service.IRangeService;

import io.swagger.annotations.Api;

/**
 * <p>
 * 飞行计划表 前端控制器
 * </p>
 *
 * @author djl
 * @since 2019-06-18
 */
@Api(tags= {"飞行计划表接口-N(未使用)"})
@RestController
@RequestMapping("/api/plan/fly-plan")
public class FlyPlanController extends BaseController {

	@Autowired
	private IFlyPlanService flyPlanService;
	
	/**
	 *  获取巡查计划列表
	 * @param param
	 * @return
	 */
	@RequestMapping("/getList")
	public Result getFlyPlanList(Page page,@RequestParam Map<String, Object> param) {
		
		return ResultFactory.success(flyPlanService.getFlyPlanList(page,param));
		
	}
	
	/**
	 *  获取巡查范围和巡查建筑
	 * @param param
	 * @return
	 */
	@RequestMapping("/range")
	public Result getRangeData(@RequestParam(required=false) Map<String, Object> param) {
		List<Range> rangeData = flyPlanService.getRangeData(param);
		List<Building> buildData = flyPlanService.getBuildData(param);
		Map result = new HashMap();
		result.put("rangeData", rangeData);
		result.put("buildData", buildData);
		return ResultFactory.success(result);
		
	}
	
	/**
	 *  更新巡查计划
	 * @param param
	 * @return
	 */
	@RequestMapping("/edit")
	public Result editFlyPlan(@RequestBody Map<String, Object> param) {
		
		boolean bo = flyPlanService.saveOrUpdates(param);
		if(!bo) {
			return ResultFactory.error();
		}
		return ResultFactory.success();
		
	}
	
	/**
	 *  删除巡查计划
	 * @param param
	 * @return
	 */
	@RequestMapping("/del")
	public Result delFlyPlan(@RequestParam Map<String, Object> param) {
		
		boolean bo = flyPlanService.delFlyPlan(param);
		if(!bo) {
			return ResultFactory.error();
		}
		return ResultFactory.success();
		
	}
	
	/**
	 * 改变计划的状态
	 * @param param
	 * @return
	 */
	@RequestMapping("/change")
	public Result changeUseStatus(@RequestBody Map<String, Object> param) {
		
		return ResultFactory.success(flyPlanService.changeUseStatus(param));
		
	}
	
	/**
	 *  获取巡查范围 或 巡查建筑所在的坐标中心点
	 * @param param
	 * @return
	 */
	@RequestMapping("/getCenter")
	public Result getCenter(@RequestBody Map<String, Object> param) {
		
		return ResultFactory.success(flyPlanService.getCenter(param));
		
	}
	
	
}
