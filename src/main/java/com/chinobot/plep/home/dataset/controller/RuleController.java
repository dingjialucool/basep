package com.chinobot.plep.home.dataset.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.drools.service.RuleService;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.framework.web.entity.Basedata;
import com.chinobot.framework.web.service.IBasedataService;
import com.chinobot.plep.home.dataset.entity.DataSet;
import com.chinobot.plep.home.dataset.entity.Rule;
import com.chinobot.plep.home.dataset.entity.RuleDetail;
import com.chinobot.plep.home.dataset.entity.dto.RuleAndDetailDto;
import com.chinobot.plep.home.dataset.entity.dto.RuleDto;
import com.chinobot.plep.home.dataset.entity.vo.CompareTypeSort;
import com.chinobot.plep.home.dataset.service.IDataSetService;
import com.chinobot.plep.home.dataset.service.IRuleDetailService;
import com.chinobot.plep.home.dataset.service.IRuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 规则表 前端控制器
 * </p>
 *
 * @author djl
 * @since 2019-10-08
 */
@Api(tags= {"规则表接口"})
@RestController
@RequestMapping("/api/dataset/rule")
public class RuleController extends BaseController {

	@Autowired
	private IRuleService ruleService;
	@Autowired
	private IDataSetService dataService;
	@Autowired
	private IDataSetService dataSetService;
	@Autowired
	private RuleService rService;
	@Autowired
	private IBasedataService basedataService;
	
	@ApiOperation(value = "获取规则列表", notes = "参数 - 分页page,Map param(sceneId(场景id)),返回值中status的状态0表示禁用1表示启用")
	@GetMapping("/getRuleList")
	public Result getRuleList(Page page,@RequestParam  Map<String, Object> param) {
		
		return ResultFactory.success(ruleService.getRuleList(page,param));
		
	}
	
	@ApiOperation(value = "删除规则", notes = "参数 - 规则主键uuid")
	@GetMapping("/deleteRule")
	public Result deleteRule(@ApiParam(name = "uuid", value = "规则主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		//如果规则正在使用中，则不能删除
		Rule rule = ruleService.getById(uuid);
		if(rule!=null && rule.getStatus().equals("1")) {
			return ResultFactory.error(600, "该规则正在使用中，不能删除", null);
		}
		
		UpdateWrapper<Rule> updateWrapper = new UpdateWrapper<Rule>();
		updateWrapper.eq("uuid", uuid);
		updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		boolean bo = ruleService.update(updateWrapper);
		rService.loadRule();//加载规则
		return ResultFactory.success(bo);
	}
	
	@ApiOperation(value = "获取数据集", notes = "参数-sceneId(场景Id) ，返回值以数据集uuid为key，数据集名称name为value")
	@GetMapping("/getDatas")
	public Result getDatas(@ApiParam(name = "taskId", value = "任务Id", required = false) @RequestParam(value = "taskId", required = false) String taskId) {
		
		QueryWrapper<DataSet> queryWrapper = new QueryWrapper<DataSet>();
		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		if(StringUtils.isBlank(taskId)) {
			queryWrapper.eq("is_global", "1");
		}else {
			queryWrapper.eq("task_id", taskId);
		}
		
		queryWrapper.select("uuid","name");
		List<DataSet> list = dataService.list(queryWrapper);
		List<Map> listmap = new ArrayList<Map>();
		for (DataSet dataSet : list) {
			Map map = new HashMap();
			map.put("uuid",dataSet.getUuid());
			map.put("name",dataSet.getName());
			listmap.add(map);
		}
		return ResultFactory.success(listmap);
	}
	
	@ApiOperation(value = "获取元数据", notes = "参数 - 数据集主键uuid(显示在页面上的时fieldTitle值，保存时保存uuid)")
	@GetMapping("/getMetaDatas")
	public Result getMetaDatas(@ApiParam(name = "uuid", value = "数据集主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		List<Map> list = dataSetService.getMetadatas(uuid);
		return ResultFactory.success(list);
	}
	
	@ApiOperation(value = "保存规则", notes = "参数 - 数据集主键uuid")
	@PostMapping("/saveRule")
	public Result saveRule(@RequestBody RuleAndDetailDto dto) {
		
		return ResultFactory.success(ruleService.saveRule(dto));
	}
	
	@ApiOperation(value = "回显规则及规则明细", notes = "参数 - 规则主键uuid")
	@GetMapping("/getRuleDetails")
	public Result getRuleDetails(@ApiParam(name = "uuid", value = "规则主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		return ResultFactory.success(ruleService.getRuleDetails(uuid));
	}
	
	@ApiOperation(value = "状态改变", notes = "参数 - 状态status")
	@GetMapping("/editStatus")
	public Result editStatus(@ApiParam(name = "status", value = "状态", required = true) @RequestParam(value = "status", required = true) String status,
			@ApiParam(name = "uuid", value = "规则主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		UpdateWrapper<Rule> updateWrapper = new UpdateWrapper<Rule>();
		updateWrapper.eq("uuid", uuid);
		updateWrapper.set("status", status);
		boolean bo = ruleService.update(updateWrapper);
		rService.loadRule();//加载规则
		return ResultFactory.success(bo);
	}
	
	@ApiOperation(value = "比较类型排序", notes = "参数 - 无")
	@GetMapping("/compareTypeSort")
	public Result compareTypeSort() {
		
		QueryWrapper<Basedata> queryWrapper = new QueryWrapper<Basedata>();
		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID).eq("typecode", "compare_type");
		queryWrapper.orderByAsc("sort+0");
		queryWrapper.select("fieldcode","fieldname");
		List<Basedata> list = basedataService.list(queryWrapper);
		List<CompareTypeSort> lists = convertList2List(list, CompareTypeSort.class);
		return ResultFactory.success(lists);
	}
	
	/**
     * @param input 输入集合
     * @param clzz  输出集合类型
     * @param <E>   输入集合类型
     * @param <T>   输出集合类型
     * @return 返回集合
     */
    public static <E, T> List<T> convertList2List(List<E> input, Class<T> clzz) {
        List<T> output = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(input)) {
            for (E source : input) {
                T target = BeanUtils.instantiate(clzz);
                BeanUtils.copyProperties(source, target);
                output.add(target);
            }
        }
        return output;
    }
}
