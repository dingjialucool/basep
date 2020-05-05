package com.chinobot.plep.home.plan.controller;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.cityle.base.service.IRoleService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.flyPlan.entity.FlyPlan;
import com.chinobot.plep.flyPlan.service.IFlyPlanService;
import com.chinobot.plep.home.event.entity.EventMain;
import com.chinobot.plep.home.event.service.IEventMainService;
import com.chinobot.plep.home.plan.entity.FlyTask;
import com.chinobot.plep.home.plan.entity.ReformRecord;
import com.chinobot.plep.home.plan.entity.ReviewRecord;
import com.chinobot.plep.home.plan.service.IFlyTaskService;
import com.chinobot.plep.home.plan.service.IReformRecordService;
import com.chinobot.plep.home.plan.service.IReviewRecordService;

import io.swagger.annotations.Api;

/**
 * <p>
 * 飞行任务表 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-06-19
 */
@Api(tags= {"飞行任务表接口  -N(未使用)"})
@RestController
@RequestMapping("/api/plan/fly-task")
public class FlyTaskController extends BaseController {
	@Autowired
	private IFlyTaskService flyTaskService;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IFlyPlanService planService;
	@Autowired
	private IReviewRecordService reviewService;
	@Autowired
	private IEventMainService eventService;
	@Autowired
	private IReformRecordService reformService;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IRoleService roleService;
	
	@RequestMapping("/getTaskPape")
	public Result getTaskPape(Page page, @RequestParam Map<String, String> param) {
		Person person = ThreadLocalUtil.getResources();
		if(person != null) {
			person = personService.getById(person.getUuid());
		}
		param.put("dept_id",person.getDeptId());
		List<Map> roles = roleService.getRoleByPersonId(person.getUuid());
		for(Map map : roles) {
			if(GlobalConstant.SUPER_ADMIN_ID.equals(map.get("roleId"))) {
				param.remove("dept_id");//超级管理员查看所有
			}
		}
		return ResultFactory.success(flyTaskService.getTaskPage(page, param));
	}
	@RequestMapping("/recieveTask")
	public Result recieveTask(@RequestBody FlyTask task) {
		task.setStatus("1");
		task.setRecieveId(ThreadLocalUtil.getResources().getUuid());
		task.setRecieveTime(LocalDateTime.now());
		flyTaskService.updateById(task);
		return ResultFactory.success();
	}
	@RequestMapping("/doComplete")
	public Result doComplete(String taskId) {
		FlyTask task = flyTaskService.getById(taskId);
		task.setStatus("3");
		flyTaskService.updateById(task);
		//判断是否需要复核
		FlyPlan plan = planService.getById(task.getPlanId());
		if(StringUtils.isNotBlank(plan.getReformId())) {
			QueryWrapper<ReviewRecord> wrapper = new QueryWrapper<ReviewRecord>();
			wrapper.eq("reform_id",plan.getReformId());
			ReviewRecord reviewRecord = reviewService.getOne(wrapper);
			if(reviewRecord == null) {
				ReviewRecord review = new ReviewRecord();
				review.setIsWarning("0");
				review.setReformId(plan.getReformId());
				reviewService.save(review);//偷懒，不在一个事务，有空再改
				ReformRecord reformRecord = reformService.getById(plan.getReformId());
				EventMain eventMain = eventService.getById(reformRecord.getEventId());
				eventMain.setStatus("2");//整改完成
				eventService.updateById(eventMain);
			}
		}
		return ResultFactory.success();
	}
	@RequestMapping("/doUpload")
	public Result doUpload(@RequestBody VoAddressBase<FlyTask> vo,String moduleName) {
		flyTaskService.doUpload(vo, moduleName);
		return ResultFactory.success();
	}
	
	@RequestMapping("/doReview")
	public Result doReview(String taskId) {
		FlyTask task = flyTaskService.getById(taskId);
		FlyPlan plan = planService.getById(task.getPlanId());
		ReviewRecord review = new ReviewRecord();
		review.setIsWarning("0");
		review.setReformId(plan.getReformId());
		reviewService.save(review);//偷懒，不在一个事务，有空再改
		ReformRecord reformRecord = reformService.getById(plan.getReformId());
		EventMain eventMain = eventService.getById(reformRecord.getEventId());
		eventMain.setStatus("2");//整改完成
		eventService.updateById(eventMain);
		return ResultFactory.success();
	}
	@RequestMapping("/getTaskById")
	public Result getTaskById(String uuid) {
		return ResultFactory.success(flyTaskService.getTaskById(uuid));
	}
	@RequestMapping("/getUpload")
	public Result getUpload(String taskId, String module) {
		Map param = new HashMap<>();
		param.put("busId", taskId);
		param.put("module", module);
		return ResultFactory.success(fileBusService.getFileIdByBusId(param));
	}
	@RequestMapping("/getUav")
	public Result getUav(Page page, @RequestParam Map<String, String> param) {
		//TODO  后期加入权限验证
		return ResultFactory.success(flyTaskService.getUavList(page, param));
	}
}
