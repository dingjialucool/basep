package com.chinobot.aiuas.bot_collect.warning.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.warning.entity.EventSupervise;
import com.chinobot.aiuas.bot_collect.warning.entity.Feedback;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.DoneDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.SuperviseDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventFeedbackListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventFeedbackVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventInfoListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceLiveVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningVo;
import com.chinobot.aiuas.bot_collect.warning.service.IEventSuperviseService;
import com.chinobot.aiuas.bot_collect.warning.service.IWarningService;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 预警信息 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2020-01-13
 */
@Api(tags = "预警信息接口")
@RestController
@RequestMapping("/api/bot/warning/warning")
public class WarningController extends BaseController {

	@Autowired
	private IWarningService iWarningService;
	@Autowired
	private IEventSuperviseService eventSuperviseService;
	
	
	/**
	 * 列表
	 * 
	 * @Param: [param]
	 * @Return: com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningListVo>>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/14 14:50
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "查询及获取预警信息List列表", notes = "获取预警信息List列表")
	@GetMapping("/getWarningList")
	public Result<IPage<WarningListVo>> getWarningList(Page page, @RequestParam Map<String, Object> param) {
		
		return ResultFactory.success(iWarningService.getWarningList(page, param));
	}

	/**
	 * 详细
	 * 
	 * @Param: [param]
	 * @Return: com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningListVo>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/14 14:50
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "详细", notes = "")
	@GetMapping("getWarningDetail")
	public Result<WarningVo> getWarningDetail(String uuid) {
		return ResultFactory.success(iWarningService.getWarningDetail(uuid));
	}

	/**
	 * 列表
	 * 
	 * @Param: [param]
	 * @Return: com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningListVo>>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/14 14:50
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "预警事件List列表", notes = "获取分拨实况List列表")
	@GetMapping("/getDistributionLiveList")
	public Result<IPage<EventInfoListVo>> getDistributionLiveList(Page page, @RequestParam Map<String, Object> param) {
			
		  //获取登录账户所在部门以及子部门
//		  Dept dept = ThreadLocalUtil.getResources().getDept(); 
//		  param.put("deptCode",dept.getDeptCode());
		 
		return ResultFactory.success(iWarningService.getDistributionLiveList(page, param));
	}
	
	/**
	 * 列表
	 * 
	 * @Param: [param]
	 * @Return: com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningListVo>>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/14 14:50
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@ApiOperation(value = "事件分拨List列表", notes = "获取事件跟踪List列表")
	@GetMapping("/geteventTraceList")
	public Result<IPage<EventTraceListVo>> geteventTraceList(Page page, @RequestParam Map<String, Object> param) {
		  //获取登录账户所在部门
		  String deptId = (String) param.get("deptId");
		  if (StringUtils.isEmpty(deptId)) {
			  Dept dept = ThreadLocalUtil.getResources().getDept(); 
			  param.put("deptId",dept.getUuid());
		  }
		 
		return ResultFactory.success(iWarningService.geteventTraceList(page, param));
	}
	
	
	/**
	 * 详情
	 * 
	 * @Param: [param]
	 * @Return: com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveVo>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/15 14:50
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "预警事件详情", notes = "")
	@GetMapping("getDistributionLiveDetail")
	public Result<DistributionLiveVo> getDistributionLiveDetail(@ApiParam(name = "uuid", value = "预警事件主键", required = true) @RequestParam(value = "uuid", required = true) String uuid,
			@ApiParam(name = "warnId", value = "预警消息主键", required = true) @RequestParam(value = "warnId", required = true) String warnId) {
		return ResultFactory.success(iWarningService.getDistributionLiveDetail(uuid,warnId));
	}
	
	/**
	 * 详情
	 * 
	 * @Param: [param]
	 * @Return: com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceLiveVo>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/15 14:50
	 */
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "事件分拨详情", notes = "")
	@GetMapping("geteventTraceDetail")
	public Result<EventTraceLiveVo> geteventTraceDetail(String uuid,
			@ApiParam(name = "warnId", value = "预警消息主键", required = true) @RequestParam(value = "warnId", required = true) String warnId) {
		return ResultFactory.success(iWarningService.geteventTraceDetail(uuid,warnId));
	}
	
	
	/**
	 * 分拨实况撤销
	 * 
	 * @Param: [param]
	 * @Return: Result
	 * @Author: yuanwanggui
	 * @Date: 2020/1/15 14:50
	 */
	@ApiOperation(value = "分拨实况撤销", notes = "分拨实况撤销")
	@PostMapping("getCancellationExamine")
	public Result updateBIBS(@ApiParam(name = "hostBy", value = "确认人", required = true) @RequestParam(value = "hostBy", required = true) String hostBy,
			@ApiParam(name = "uuid", value = "预警事件主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		return iWarningService.updateEvent(hostBy,uuid);
	}

	/**
	 * 添加白名单
	 * 
	 * @Param: [param]
	 * @Return: Result
	 * @Author: yuanwanggui
	 * @Date: 2020/1/20 14:50
	 */
	@ApiOperation(value = "添加白名单", notes = "添加白名单")
	@PostMapping("addWhiteList")
	public Result addWhiteList(@ApiParam(name = "uuid", value = "预警事件主键", required = true) @RequestParam(value = "uuid", required = true) String uuid,
			@ApiParam(name = "invalidTime", value = "截止时间", required = true) @RequestParam(value = "invalidTime", required = true) String invalidTime,
			@ApiParam(name = "hostBy", value = "确认人", required = true) @RequestParam(value = "hostBy", required = true) String hostBy) {
		
		LocalDate parseDate = LocalDate.parse(invalidTime, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		return iWarningService.addWhiteList(uuid,parseDate,hostBy);
	}
	
	
	/**
	 * 添加督办记录
	 * 
	 * @Param: [SuperviseDTO]
	 * @Author: yuanwanggui
	 * @Date: 2020/1/20 14:50
	 */
	@ApiOperation(value = "添加督办记录", notes = "添加督办记录")
	@PostMapping("addSupervise")
	public Result addSupervise(@RequestBody SuperviseDTO dto) {
		
//		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//		LocalDateTime dateTime = LocalDateTime.parse(dto.getSuperviseTime(),df);
		Result result = new Result();
		EventSupervise supervise=new EventSupervise();
		supervise.setEventUuid(dto.getUuid());
		supervise.setSuperviseBy(dto.getSuperviseBy());
		supervise.setSuperviseContent(dto.getSuperviseContent());
		supervise.setSuperviseTime(LocalDateTime.now());
		boolean bo = eventSuperviseService.save(supervise);
		if(bo) {
			result.setMsg("督办成功!");
		}else {
			result.setMsg("督办失败!");
		}
		result.setCode(200);
		return result;
	}
	
}
