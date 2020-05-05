package com.chinobot.plep.home.event.controller;


import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.event.entity.EventMain;
import com.chinobot.plep.home.event.entity.dto.EvenMainDto;
import com.chinobot.plep.home.event.service.IEarlyWarningService;
import com.chinobot.plep.home.event.service.IEventMainService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 风险预警表 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-06-26
 */
@Api(tags= {"预警事件接口"})
@RestController
@RequestMapping("/api/event/early-warning")
public class EarlyWarningController extends BaseController {
	@Autowired
	private IEarlyWarningService earlyWarningService;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IEventMainService eventMainService;
	
	@ApiOperation(value = "上传预警事件-N(暂未使用)", notes = "参数-FileBus集合")
	@PostMapping("/uploadEarlyWarning")
	public Result uploadEarlyWarning(@RequestBody List<FileBus> fileBus) throws Exception {
		List<Map> result = earlyWarningService.uploadEarlyWarning(fileBus);
		return ResultFactory.success(result);
	}
	
	
	@ApiOperation(value = "预警事件列表", notes = "参数- 分页page, Map param")
	@GetMapping("/getEvents")
	public Result getEvents( Page page,@RequestParam Map<String, Object> param) throws Exception {
		String busType = (String) param.get("bus_type");
		if(CommonUtils.isNotEmpty(busType) && busType.equals("0")) {
			Person person = ThreadLocalUtil.getResources();
			param.put("userId", person.getUuid());
		}
		Dept dept = ThreadLocalUtil.getResources().getDept();
		param.put("deptId", dept.getUuid());
		return ResultFactory.success(earlyWarningService.getEvents(page,param));
	}
	
	
	@ApiOperation(value = "根据人员id获取该人员所在部门下的所有人员", notes = "参数- Map param")
	@GetMapping("/getPersonList")
//	@RequestMapping("/getPersonList")
	public Result getPersonList(@RequestParam Map<String, Object> param)  {
		String uuid = (String) param.get("uuid");
		QueryWrapper<Person> queryWrapper = new QueryWrapper<Person>();
		queryWrapper.eq("uuid", uuid);
		Person person = personService.getOne(queryWrapper);
		//根据部门查询部门下的所有人
		QueryWrapper<Person> queryWrapper2 = new QueryWrapper<Person>();
		queryWrapper2.eq("dept_id", person.getDeptId()).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		List<Person> list = personService.list(queryWrapper2);
		return ResultFactory.success(list);
	}
	
	/**
	 *  获取下一条数据
	 * @param param
	 * @return
	 */
	@ApiOperation(value = "获取下一条数据", notes = "参数- Map param")
	@GetMapping("/getDispath")
//	@RequestMapping("/getDispath")
	public Result getDispath(@RequestParam Map<String, Object> param) {
		Person person = ThreadLocalUtil.getResources();
//		if(!ThreadLocalUtil.isSystemRole()) {
//			param.put("deptId", person.getDeptId());
//		}else {
//			param.put("userId", null);
//		}
		param.put("userId", person.getUuid());
		return ResultFactory.success(earlyWarningService.getDispath(param));
	}
	
	@ApiOperation(value = "保存分派数据", notes = "参数-事件对象eventMain")
	@PostMapping("/saveConfitmPerson")
//	@RequestMapping("/saveConfitmPerson")
	public Result saveConfitmPerson(@RequestBody EventMain vo) {
		if("1".equals(vo.getStatus())) {
			vo.setDisTime(LocalDateTime.now());
		}
		if("3".equals(vo.getStatus())) {
			vo.setResetTime(LocalDateTime.now());
		}
		return ResultFactory.success(eventMainService.updateById(vo));
	}
	
	@ApiOperation(value = "批量分派保存", notes = "参数-dto")
	@PostMapping("/quantityDis")
	public Result quantityDis(@RequestBody EvenMainDto dto) {
		
		List<EventMain> list =new ArrayList<EventMain>();
		List<String> uuids = dto.getList();
		String peronPatrol = dto.getPeronPatrol();
		String status = dto.getStatus();
		EventMain eventMain = null;
		for (String uuid : uuids) {
			eventMain = new EventMain();
			eventMain.setUuid(uuid);
			eventMain.setStatus(status);
			eventMain.setPeronPatrol(peronPatrol);
			list.add(eventMain);
		}
		
		return ResultFactory.success(eventMainService.updateBatchById(list));
	}
	
}
