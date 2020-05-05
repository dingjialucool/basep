package com.chinobot.plep.home.cycle.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.cycle.entity.dto.ChangeStateDto;
import com.chinobot.plep.home.cycle.entity.dto.CycleSearhDto;
import com.chinobot.plep.home.cycle.entity.dto.DeleteCycleDto;
import com.chinobot.plep.home.cycle.entity.dto.RouteSearchDto;
import com.chinobot.plep.home.cycle.entity.vo.CycleWithDetailVo;
import com.chinobot.plep.home.cycle.entity.vo.RouteVo;
import com.chinobot.plep.home.cycle.service.ICycleDetailService;
import com.chinobot.plep.home.routedd.entity.Cycle;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-10-25
 */
@Api(tags="周期接口")
@RestController
@RequestMapping("/api/cycle-detail")
public class CycleDetailController extends BaseController {

	@Autowired
	private ICycleDetailService cycleDetailService;
	
	@ApiOperation(value = "查询周期分页", notes = "查询周期分页列表")
	@GetMapping("/searchCyclePage")
	public Result<IPage<Cycle>> searchCyclePage(Page page, CycleSearhDto dto) {
		dto.setDeptId(ThreadLocalUtil.getResources().getDeptId());;
		IPage<Cycle> list = cycleDetailService.searchCyclePage(page, dto);
		return ResultFactory.success(list);
	}
	
	@ApiOperation(value = "查询路线分页", notes = "查询路线分页列表")
	@GetMapping("/searchRoutePage")
	public Result<IPage<RouteVo>> searchRoutePage(Page page, RouteSearchDto dto) {
		dto.setDeptId(ThreadLocalUtil.getResources().getDeptId());;
		IPage<RouteVo> list = cycleDetailService.searchRoutePage(page, dto);
		return ResultFactory.success(list);
	}
	@ApiOperation(value = "保存周期", notes = "保存周期，新增修改共用")
	@PostMapping("/saveCycle")
	public Result saveCycle(@RequestBody CycleWithDetailVo vo) {
		cycleDetailService.saveCycle(vo);
		return ResultFactory.success();
	}
	@ApiOperation(value = "获取周期明细", notes = "获取周期明细信息")
	@GetMapping("/getCycle")
	public Result<CycleWithDetailVo> getCycle(@ApiParam(name = "uuid", value = "周期主键", required = true) String uuid) {
		CycleWithDetailVo vo = cycleDetailService.getCycleVo(uuid);
		return ResultFactory.success(vo);
	}
	@ApiOperation(value = "周期开关", notes = "周期开关,改变周期的状态")
	@PostMapping("/changeState")
	public Result changeState(@RequestBody ChangeStateDto dto) {
		cycleDetailService.changeState(dto);
		return ResultFactory.success();
	}
	@ApiOperation(value = "删除周期", notes = "删除周期，提示是否删除已经生成的当日之后的任务")
	@PostMapping("/deleteCycle")
	public Result deleteCycle(@RequestBody DeleteCycleDto dto) {
		cycleDetailService.deleteCycle(dto);
		return ResultFactory.success();
	}
}
