package com.chinobot.aiuas.bot_collect.warning.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.info.constant.PartConstant;
import com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant;
import com.chinobot.aiuas.bot_collect.warning.entity.EventInfo;
import com.chinobot.aiuas.bot_collect.warning.entity.Warning;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.BatchRevocationDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.DoneDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventInfoListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventInfoTypeListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.FeedBackHistoryVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.SuperviseVo;
import com.chinobot.aiuas.bot_collect.warning.mapper.EventInfoMapper;
import com.chinobot.aiuas.bot_collect.warning.mapper.WarningMapper;
import com.chinobot.aiuas.bot_collect.warning.service.IEventInfoService;
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
 * 预警事件 前端控制器
 * </p>
 *
 * @author djl
 * @since 2020-02-24
 */
@Api(tags = "新预警事件接口")
@RestController
@RequestMapping("/api/warning/event-info")
public class EventInfoController extends BaseController {

	@Autowired
	private IWarningService iWarningService;
	@Autowired
	private IEventInfoService eventInfoService;
	@Autowired
	private WarningMapper warningMapper;
	@Autowired
	private EventInfoMapper eventInfoMapper;
	
	@ApiOperation(value = "预警信息废除", notes = "预警信息废除")
	@GetMapping("/removeWarningMessage")
	public Result removeWarningMessage(@ApiParam(name = "warnId", value = "预警信息", required = true) @RequestParam(value = "warnId", required = true) String warnId) {
		
		eventInfoService.removeWarningMessage(warnId);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "预警事件废除", notes = "预警事件废除")
	@GetMapping("/removeWarningEvent")
	public Result removeWarningEvent(@ApiParam(name = "eventId", value = "预警事件", required = true) @RequestParam(value = "eventId", required = true) String eventId) {
		
		eventInfoService.update(new LambdaUpdateWrapper<EventInfo>().eq(EventInfo::getUuid, eventId).set(EventInfo::getIsDeleted, PartConstant.DATA_YES_DELETE));
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "预警事件批量分派", notes = "预警事件批量分派")
	@PostMapping("/quantityDis")
	public Result quantityDis(@ApiParam(name = "idList", value = "预警事件id集合", required = true) @RequestParam(value = "idList", required = true) List<String> idList,
			@ApiParam(name = "personId", value = "当前用户", required = true) @RequestParam(value = "personId", required = true) String personId) {
		
		return eventInfoService.quantityDis(idList,personId);
	}
	
	@ApiOperation(value = "飞行员下拉选择", notes = "飞行员下拉选择")
	@GetMapping("/getFlyPerson")
	public Result getFlyPerson() {
		
		Map param = new HashMap();
		Dept dept = ThreadLocalUtil.getResources().getDept(); 
		param.put("deptId",dept.getUuid());
		return ResultFactory.success(eventInfoService.getFlyPerson(param));
	}
	
	@ApiOperation(value = "无人机下拉选择", notes = "无人机下拉选择")
	@GetMapping("/getUav")
	public Result getUav() {
		
		Map param = new HashMap();
		return ResultFactory.success(eventInfoService.getUav(param));
	}
	
	@ApiOperation(value = "预警事件批量撤销", notes = "预警事件批量撤销")
	@PostMapping("/batchRevocation")
	public Result batchRevocation(@RequestBody BatchRevocationDTO dto) {
		Result result = new Result();
		boolean bo = eventInfoService.batchRevocation(dto.getIdList(),dto.getPersonId());
		if(bo) {
			result.setMsg("撤销成功");
		}else {
			result.setMsg("撤销失败");
		}
		result.setCode(200);
		
		return result;
	}
	
	@ApiOperation(value = "督办历史记录", notes = "督办历史记录")
	@GetMapping("/getSuperviseRecord")
	public Result<List<SuperviseVo>> getSuperviseRecord(@ApiParam(name = "uuid", value = "预警事件主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		return ResultFactory.success(warningMapper.getSuperviseList(uuid));
	}
	
	@ApiOperation(value = "核查事件", notes = "核查事件")
	@PostMapping("eventCheck")
	public Result eventCheck(@ApiParam(name = "hostBy", value = "核查人", required = true) @RequestParam(value = "hostBy", required = true) String hostBy,
			@ApiParam(name = "uuid", value = "预警事件主键", required = true) @RequestParam(value = "uuid", required = true) String uuid,
			@ApiParam(name = "businessStatus", value = "核查状态（退回-20，通过-40）", required = true) @RequestParam(value = "businessStatus", required = true) String businessStatus,
			@ApiParam(name = "fileId", value = "图片id", required = false) @RequestParam(value = "fileId", required = false) String fileId,
			@ApiParam(name = "idea", value = "办结意见", required = false) @RequestParam(value = "idea", required = false) String idea) {
		
		return eventInfoService.eventCheck(hostBy,uuid,fileId,idea,businessStatus);
	}
	
	@ApiOperation(value = "事件办结", notes = "事件办结")
	@PostMapping("eventDone")
	public Result eventDone(@ApiParam(name = "hostBy", value = "办结人", required = true) @RequestParam(value = "hostBy", required = true) String hostBy,
			@ApiParam(name = "uuid", value = "预警事件主键", required = true) @RequestParam(value = "uuid", required = true) String uuid,
			@ApiParam(name = "businessStatus", value = "办结状态（退回-20，通过-90）", required = true) @RequestParam(value = "businessStatus", required = true) String businessStatus,
			@ApiParam(name = "fileId", value = "图片id", required = false) @RequestParam(value = "fileId", required = false) String fileId,
			@ApiParam(name = "idea", value = "办结意见", required = false) @RequestParam(value = "idea", required = false) String idea) {
		
		return eventInfoService.eventDone(hostBy,uuid,fileId,idea,businessStatus);
	}
	
	@ApiOperation(value = "治理历史记录", notes = "治理历史记录")
	@GetMapping("/getFeedBackRecord")
	public Result<List<FeedBackHistoryVo>> getFeedBackRecord(@ApiParam(name = "uuid", value = "预警事件主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		return ResultFactory.success(eventInfoMapper.getFeedBackRecord(uuid));
	}
	
	
	@ApiOperation(value = "查询及获取线索分拨List列表", notes = "查询及获取线索分拨List列表")
	@GetMapping("/getClueList")
	public Result<IPage<EventInfoListVo>> getClueList(Page page, @RequestParam Map<String, Object> param) {
		  //获取登录账户所在部门以及子部门
		  String deptId = (String) param.get("deptId");
		  if (StringUtils.isEmpty(deptId)) {
			  Dept dept = ThreadLocalUtil.getResources().getDept(); 
			  param.put("deptId",dept.getUuid());
		  }
		return ResultFactory.success(eventInfoService.getClueList(page, param));
	}
	
	@ApiOperation(value = "线索分拨详情", notes = "线索分拨详情")
	@GetMapping("getClueDEtail")
	public Result<DistributionLiveVo> getDistributionLiveDetail(@ApiParam(name = "uuid", value = "线索主键", required = true) @RequestParam(value = "uuid", required = true) String uuid,
			@ApiParam(name = "warnId", value = "预警消息主键", required = true) @RequestParam(value = "warnId", required = true) String warnId) {
		
		return ResultFactory.success(iWarningService.getDistributionLiveDetail(uuid,warnId));
	}
	
	@ApiOperation(value = "线索废除", notes = "线索废除")
	@GetMapping("removeClue")
	public Result removeClue(@ApiParam(name = "eventId", value = "线索主键", required = true) @RequestParam(value = "eventId", required = true) String eventId) {
		
		eventInfoService.update(new LambdaUpdateWrapper<EventInfo>().eq(EventInfo::getUuid, eventId).set(EventInfo::getIsDeleted, PartConstant.DATA_YES_DELETE));
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "线索确认", notes = "线索确认")
	@PostMapping("clueConfirm")
	public Result clueConfirm(@ApiParam(name = "hostBy", value = "确认人", required = true) @RequestParam(value = "hostBy", required = true) String hostBy,
			@ApiParam(name = "uuid", value = "线索主键", required = true) @RequestParam(value = "uuid", required = true) String uuid,
			@ApiParam(name = "fileId", value = "图片id", required = false) @RequestParam(value = "fileId", required = false) String fileId,
			@ApiParam(name = "idea", value = "确认意见", required = false) @RequestParam(value = "idea", required = false) String idea) {
		
		Result result = new Result();
		int num = iWarningService.updateStatus(uuid, hostBy,fileId, GlobalConstant.QRMODULE_FILE,GlobalConstant.CONFIRMED, idea, GlobalConstant.TO_BE_CONFIRMED);
		if (num > 0) {
			result.setMsg("确认成功!");
		}else {
			result.setMsg("确认失败!");
		}
		result.setCode(200);
		
		return result;
	}
	
	@ApiOperation(value = "查询及获取历史事件List列表", notes = "查询及获取历史事件List列表")
	@GetMapping("/getHistoryEventAndClueList")
	public Result<IPage<EventInfoTypeListVo>> getHistoryEventAndClueList(Page page, @RequestParam Map<String, Object> param) {
		
		//获取登录账户所在部门以及子部门
		/*
		 * Dept dept = ThreadLocalUtil.getResources().getDept(); param.put("deptId",
		 * dept.getUuid());
		 */
		return ResultFactory.success(eventInfoService.getHistoryEventAndClueList(page, param));
	}
	
	@ApiOperation(value = "历史事件和历史线索详情", notes = "历史事件和历史线索详情")
	@GetMapping("getHistoryEventAndClueDetail")
	public Result getHistoryEventAndClueDetail(@ApiParam(name = "uuid", value = "事件主键", required = true) @RequestParam(value = "uuid", required = true) String uuid,
			@ApiParam(name = "eventType", value = "预警类型1-事件 2-线索", required = true) @RequestParam(value = "eventType", required = true) String eventType,
			@ApiParam(name = "warnId", value = "预警消息主键", required = true) @RequestParam(value = "warnId", required = true) String warnId) {
		
		//事件
		if("1".equals(eventType)) {
			return ResultFactory.success(iWarningService.geteventTraceDetail(uuid,warnId));
		}else {
			return ResultFactory.success(iWarningService.getHistoryClueDetail(uuid,warnId));
		}
		
	}
	
	@ApiOperation(value = "查询及获取历史线索List列表", notes = "查询及获取历史线索List列表")
	@GetMapping("/getHistorClueList")
	public Result<IPage<EventInfoTypeListVo>> getHistoryClueList(Page page, @RequestParam Map<String, Object> param) {
		
		//获取登录账户所在部门以及子部门
		/*
		 * Dept dept = ThreadLocalUtil.getResources().getDept(); param.put("deptId",
		 * dept.getUuid());
		 */
		return ResultFactory.success(eventInfoService.getHistoryClueList(page, param));
	}
	
	@ApiOperation(value = "事件确认", notes = "事件确认")
	@PostMapping("eventConfirm")
	public Result eventConfirm(@ApiParam(name = "hostBy", value = "确认人", required = true) @RequestParam(value = "hostBy", required = true) String hostBy,
			@ApiParam(name = "uuid", value = "事件主键", required = true) @RequestParam(value = "uuid", required = true) String uuid,
			@ApiParam(name = "fileId", value = "图片id", required = false) @RequestParam(value = "fileId", required = false) String fileId,
			@ApiParam(name = "idea", value = "确认意见", required = false) @RequestParam(value = "idea", required = false) String idea) {
		
		Result result = new Result();
		int num = iWarningService.updateStatus(uuid, hostBy,fileId,GlobalConstant.QRMODULE_FILE, GlobalConstant.TO_BE_GOVERNED, "已确认", GlobalConstant.TO_BE_CONFIRMED);
		if (num > 0) {
			result.setMsg("确认成功!");
		}else {
			result.setMsg("确认失败!");
		}
		result.setCode(200);
		
		return result;
	}
	
	@ApiOperation(value = "事件治理", notes = "事件治理")
	@PostMapping("eventToDone")
	public Result eventToDone(@RequestBody DoneDTO dto) {
		
		Result result = new Result();
		iWarningService.eventToDone(dto);
		if (true) {
			result.setMsg("治理成功!");
		}else {
			result.setMsg("治理失败!");
		}
		result.setCode(200);
		
		return result;
	}
	
	
}
