package com.chinobot.plep.home.report.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinobot.plep.home.report.entity.vo.FlyMessageVo;
import com.chinobot.plep.home.report.entity.vo.FlyPointsVo;
import com.chinobot.plep.home.report.entity.vo.ReportInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.report.entity.Report;
import com.chinobot.plep.home.report.entity.dto.FixedPointOfTimeDTO;
import com.chinobot.plep.home.report.entity.vo.ReportVo;
import com.chinobot.plep.home.report.entity.vo.WarnImgVo;
import com.chinobot.plep.home.report.mapper.ReportMapper;
import com.chinobot.plep.home.report.service.IReportService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author djl
 * @since 2019-12-24
 */
@Api(tags= {"采查报告接口"})
@RestController
@RequestMapping("/api/report/report")
public class ReportController extends BaseController {

	@Autowired
	private IReportService reportService;
	@Autowired
	private ReportMapper reportMapper;
	
	/**
	 * 自动生成方式
	 */
	public static final String AUTO_WAY = "1";
	
	/**
	 * 自定义生成方式
	 */
	public static final String CUSTOMIZE_WAY = "2";
	
	@ApiOperation(value = "获取所有采查报告", notes = "参数-无")
	@GetMapping("/getAllReports")
	public Result<IPage<ReportVo>> getAllReports(Page page,@RequestParam Map<String,Object> param) {
		
		Dept dept = ThreadLocalUtil.getResources().getDept();
		param.put("deptId", dept.getUuid());
		return ResultFactory.success(reportService.getAllReports(page,param));
	}

	@ApiOperation(value = "增加自定义报告", notes = "参数-report对象")
	@PostMapping("/addReport")
	public Result addReport(@RequestBody Report report) {

		return ResultFactory.success(reportService.save(report));
	}

	@ApiOperation(value = "获取采查报告详细", notes = "参数-采查报告主键")
	@GetMapping("/getInfo")
	public Result<ReportInfoVO> getInfo(String uuid) {
		Report report = reportService.getById(uuid);
		
		String type = report.getType();
		ReportInfoVO vo = new ReportInfoVO();
		
		Map mapRouteParam = new HashMap();
		mapRouteParam.put("deptId", report.getAreaId());
		mapRouteParam.put("startTime", report.getStartTime());
		mapRouteParam.put("endTime", report.getEndTime());
		
//		//自动生成方式
//		if(AUTO_WAY.equals(type)) {
//			// 数量、同比、环比
//			vo.setChart(reportService.getChart(uuid));
//		}else if(CUSTOMIZE_WAY.equals(type)) {
//			//自定义生成方式
			vo.setChart(reportService.getChartByCustomizeWay(mapRouteParam));
//		}
		
		
		// 航线规划
		vo.setMapRoute(reportService.getRouteLineOfCheckPoint(mapRouteParam));
		
		// 飞行巡查
		FlyMessageVo flyMessageVo = reportService.getFlyMessage(mapRouteParam);
		vo.setFlyMessageVo(flyMessageVo);
		
		//预警信息
		List<WarnImgVo> warnList = reportService.getWarnMeaage(mapRouteParam);
		vo.setWarnList(warnList);

		return ResultFactory.success(vo);
	}
	
	@ApiOperation(value = "采查报告-预警信息点击更多", notes = "参数-page，定点主键")
	@GetMapping("/getPointMore")
	public Result<IPage<FlyPointsVo>> getPointMore(Page page,@ApiParam(name = "uuid", value = "定点主键", required = true) @RequestParam(value = "uuid", required = true) String uuid,
			@ApiParam(name = "startTime", value = "开始时间", required = true) @RequestParam(value = "startTime", required = true) String startTime,
			@ApiParam(name = "endTime", value = "结束时间", required = true) @RequestParam(value = "endTime", required = true) String endTime) {
		
		Map param = new HashMap();
		param.put("pointId", uuid);
		param.put("startTime", startTime);
		param.put("endTime", endTime);
		return ResultFactory.success(reportService.getPointMore(page,param));
	}
	
	@ApiOperation(value = "采查报告-存量动工明细", notes = "参数-page")
	@GetMapping("/getDetailOfPoint")
	public Result getDetailOfPoint(Page page,@ApiParam(name = "uuid", value = "采查报告主键", required = true) @RequestParam(value = "uuid", required = true) String uuid,
			@ApiParam(name = "type", value = "是否分页", required = false) @RequestParam(value = "type",defaultValue="true", required = false) boolean type) {
		
		Report report = reportService.getById(uuid);
		Map param = new HashMap();
		param.put("deptId", report.getAreaId());
		param.put("startTime", report.getStartTime());
		param.put("endTime", report.getEndTime());
		
		return ResultFactory.success(reportService.getDetailOfPoint(page,param,type));
	}
	
	@ApiOperation(value = "采查报告-存量动工明细-点击更多", notes = "参数-page")
	@GetMapping("/getMoreDetailOfPoint")
	public Result<IPage<FixedPointOfTimeDTO>> getMoreDetailOfPoint(Page page,@ApiParam(name = "uuid", value = "采查报告主键", required = true) @RequestParam(value = "uuid", required = true) String uuid,
			@ApiParam(name = "pointId", value = "定点主键", required = true) @RequestParam(value = "pointId", required = true) String pointId) {
		
		Report report = reportService.getById(uuid);
		Map param = new HashMap();
		param.put("deptId", report.getAreaId());
		param.put("startTime", report.getStartTime());
		param.put("endTime", report.getEndTime());
		param.put("pointId", pointId);
		
		return ResultFactory.success(reportMapper.getFlyImg(page, param));
	}
	
	@ApiOperation(value = "采查报告-列表删除", notes = "参数-报告主键")
	@GetMapping("/removeReportById")
	public Result removeReportById(@ApiParam(name = "uuid", value = "采查报告主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		return ResultFactory.success(reportService.update(new LambdaUpdateWrapper<Report>().eq(Report::getUuid, uuid).set(true, Report::getDataStatus, GlobalConstant.DATA_STATUS_INVALID)));
	}
	
}
