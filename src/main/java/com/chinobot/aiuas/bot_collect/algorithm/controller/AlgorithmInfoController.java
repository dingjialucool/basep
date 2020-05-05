package com.chinobot.aiuas.bot_collect.algorithm.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinobot.aiuas.bot_collect.algorithm.entity.dto.AlgorithmDTO;
import com.chinobot.aiuas.bot_collect.algorithm.entity.dto.AlgorithmSerachDTO;
import com.chinobot.aiuas.bot_collect.algorithm.entity.dto.AuditingDTO;
import com.chinobot.aiuas.bot_collect.algorithm.entity.vo.AlgorithmOfAllVo;
import com.chinobot.aiuas.bot_collect.algorithm.entity.vo.AlgorithmVo;
import com.chinobot.aiuas.bot_collect.algorithm.service.IAlgorithmInfoService;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo;
import com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant;
import com.chinobot.aiuas.bot_collect.warning.mapper.WarningMapper;
import com.chinobot.aiuas.bot_collect.warning.service.IWarningService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author djl
 * @since 2020-03-06
 */
@Api(tags="算法管理接口")
@RestController
@RequestMapping("/api/bot/collect/algorithm-info")
public class AlgorithmInfoController extends BaseController {
	
	@Autowired
	private IAlgorithmInfoService  algorithmInfoService;
	@Autowired
	private WarningMapper warningMapper;
	@ApiOperation(value = "新增/编辑算法", notes = "编辑操作-->业务状态：0-待提交 1-待审批  2-已退回  时才有编辑按钮")
	@PostMapping("/updateAlgorithm")
	public Result updateAlgorithm(@RequestBody AlgorithmDTO dto) {
		
		algorithmInfoService.addAlgorithm(dto);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "获取领域-场景-任务树", notes = "获取领域-场景-任务树")
	@GetMapping("/getDomainTaskTree")
	public Result<List<DomainSceneVo>> getDomainTaskTree() {
		
		return ResultFactory.success(algorithmInfoService.getDomainTaskTree());
	}
	
	@ApiOperation(value = "算法管理列表与查询", notes = "算法管理列表与查询")
	@GetMapping("/getAlgorithmList")
	public Result<IPage<AlgorithmVo>> getAlgorithmList(AlgorithmSerachDTO dto) {
		
		return ResultFactory.success(algorithmInfoService.getAlgorithmList(dto));
	}
	
	@ApiOperation(value = "算法审批", notes = "只有待审批-1   的算法才能审批")
	@PostMapping("/auditing")
	public Result auditing(@RequestBody AuditingDTO dto) {
		
		algorithmInfoService.auditing(dto);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "算法管理编辑回显 | 查看", notes = "算法管理编辑回显")
	@GetMapping("/getAlgorithmById")
	public Result<AlgorithmOfAllVo> getAlgorithmById(@ApiParam(name = "uuid", value = "算法主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {

		return ResultFactory.success(algorithmInfoService.getAlgorithmById(uuid));
	}
	
	@ApiOperation(value = "通过采查内容id获取图例", notes = "通过采查内容id获取图例")
	@GetMapping("/getImgByTaskId")
	public Result getImgByTaskId(@ApiParam(name = "taskId", value = "任务id", required = true) @RequestParam(value = "taskId", required = true) String taskId) {

		Map<String,String> param = new HashMap<>();
		param.put("busId", taskId);
		param.put("module","collect_task_album");
		List<String> taskImgList = warningMapper.getWarnByBusId(param);
		if(taskImgList.size()>0) {
			return ResultFactory.success(taskImgList.get(0));
		}
		
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "算法删除", notes = "算法删除")
	@GetMapping("/removeAlgorithm")
	public Result removeAlgorithm(@ApiParam(name = "uuid", value = "算法主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		algorithmInfoService.removeAlgorithm(uuid);
		return ResultFactory.success();
	}
}
