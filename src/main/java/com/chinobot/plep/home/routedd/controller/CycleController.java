/*
 * package com.chinobot.plep.home.routedd.controller;
 * 
 * 
 * import java.util.Map;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.web.bind.annotation.GetMapping; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RequestParam; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * import com.baomidou.mybatisplus.extension.plugins.pagination.Page; import
 * com.chinobot.common.constant.GlobalConstant; import
 * com.chinobot.common.domain.Result; import
 * com.chinobot.common.utils.ResultFactory; import
 * com.chinobot.common.utils.ThreadLocalUtil; import
 * com.chinobot.framework.web.controller.BaseController; import
 * com.chinobot.plep.home.routedd.entity.Cycle; import
 * com.chinobot.plep.home.routedd.entity.dto.CycleDto; import
 * com.chinobot.plep.home.routedd.entity.vo.CyclesVo; import
 * com.chinobot.plep.home.routedd.entity.vo.PageAndCyclesVo; import
 * com.chinobot.plep.home.routedd.service.ICycleService;
 * 
 * import io.swagger.annotations.Api; import
 * io.swagger.annotations.ApiOperation; import io.swagger.annotations.ApiParam;
 * 
 *//**
	 * <p>
	 * 周期计划表 前端控制器
	 * </p>
	 *
	 * @author huangw
	 * @since 2019-10-23
	 *//*
		 * @Api(tags = "周期计划表接口")
		 * 
		 * @RestController
		 * 
		 * @RequestMapping("/api/routedd/cycle") public class CycleController extends
		 * BaseController {
		 * 
		 * @Autowired ICycleService cycleService;
		 * 
		 * @ApiOperation(value = "周期计划表分页查询", notes = "参数 - Page page分页，周期dto")
		 * 
		 * @PostMapping("/getCycles") public Result<PageAndCyclesVo> getCycles(Page
		 * page,@RequestBody(required=false) CycleDto dto) { if(dto == null) { dto = new
		 * CycleDto(); }
		 * dto.setDeptId(ThreadLocalUtil.getResources().getDept().getUuid()); return
		 * ResultFactory.success(cycleService.getCycles(page,dto)); }
		 * 
		 * @ApiOperation(value = "周期计划表修改回显", notes = "参数 - 周期计划主键")
		 * 
		 * @GetMapping("/getCycle") public Result<CyclesVo> getCycle(@ApiParam(name =
		 * "uuid", value = "周期计划主键", required = true) @RequestParam(value = "uuid",
		 * required = true) String uuid) {
		 * 
		 * return ResultFactory.success(cycleService.getCycle(uuid)); }
		 * 
		 * @ApiOperation(value = "周期计划表修改保存", notes = "参数 - 周期计划cycle")
		 * 
		 * @PostMapping("/editCycle") public Result editCycle(@RequestBody Cycle cycle)
		 * {
		 * 
		 * return ResultFactory.success(cycleService.updateById(cycle)); }
		 * 
		 * @ApiOperation(value = "周期计划表删除", notes = "参数 - 周期计划主键")
		 * 
		 * @GetMapping("/delCycle") public Result delCycle(@ApiParam(name = "uuid",
		 * value = "周期计划主键", required = true) @RequestParam(value = "uuid", required =
		 * true) String uuid) {
		 * 
		 * return ResultFactory.success(cycleService.delCycle(uuid)); } }
		 */