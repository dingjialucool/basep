package com.chinobot.aiuas.bot_collect.strategy.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.AddFlightDto;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.ChangeStatusDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.DelStategyDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.ListInfoDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.ListTaskDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.PageStrategyDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.StrategySpecialDto;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.DomainWithSceneVO;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.FlightDeatilVO;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.InfoVO;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.PageStrategyVO;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyDetailVO;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyListVO;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategySpecialVO;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.TaskVO;
import com.chinobot.aiuas.bot_collect.strategy.service.IStrategyService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.entity.vo.TreeOptionVo;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 采查策略 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Api(tags="采查策略接口")
@RestController
@RequestMapping("/api/bot/collect/strategy")
public class StrategyController extends BaseController {

	@Autowired
	private IStrategyService strategyService;
	
	@ApiOperation(value = "获取策略分页", notes = "获取策略分页")
	@GetMapping("/pageStrategy")
	public Result<IPage<PageStrategyVO>> pageStrategy(Page page, PageStrategyDTO dto){
		
		return ResultFactory.success(strategyService.pageStrategy(page, dto));
	}
	
	@ApiOperation(value = "获取所有领域和场景", notes = "获取所有领域和场景")
	@GetMapping("/listAllDomainAndScene")
	public Result<List<DomainWithSceneVO>> listAllDomainAndScene(){
		
		return ResultFactory.success(strategyService.listAllDomainAndScene());
	}
	
	@ApiOperation(value = "获取场景 下的任务", notes = "获取场景 下的任务， 名称查询可选")
	@GetMapping("/listTask")
	public Result<List<TaskVO>> listTask(ListTaskDTO dto){
		return ResultFactory.success(strategyService.listTask(dto));
	}
	
	@ApiOperation(value = "获取领域下的对象", notes = "获取领域下的对象， 名称查询可选")
	@GetMapping("/listInfo")
	public Result<List<InfoVO>> listInfo(ListInfoDTO dto){
		return ResultFactory.success(strategyService.listInfo(dto));
	}
	
	@ApiOperation(value = "获取策略详细", notes = "获取策略详细")
	@ApiImplicitParams({
		@ApiImplicitParam(name="strategyId",value="策略主键",required=true,paramType="query")
	})
//	paramType：参数放在哪个地方
//    · header --> 请求参数的获取：@RequestHeader
//    · query --> 请求参数的获取：@RequestParam
//    · path（用于restful接口）--> 请求参数的获取：@PathVariable
//    · body（不常用）
//    · form（不常用）
	@GetMapping("/getStrategyDetail")
	public Result<StrategyDetailVO> getStrategyDetail(String strategyId){
		return ResultFactory.success(strategyService.getStrategyDetail(strategyId));
	}
	
	@ApiOperation(value = "保存策略", notes = "保存策略，返回主键")
	@PostMapping("/saveStrategy")
	public Result<String> saveStrategy(@RequestBody StrategyDetailVO vo){
		return ResultFactory.success(strategyService.saveStrategy(vo));
	}
	@ApiOperation(value = "改变策略状态", notes = "改变策略状态")
	@PostMapping("/changeStrategyStatus")
	public Result changeStrategyStatus(@RequestBody ChangeStatusDTO dto) {
		strategyService.changeStrategyStatus(dto);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "删除策略", notes = "删除策略")
	@PostMapping("/delStrategy")
	public Result delStrategy(@RequestBody DelStategyDTO dto) {
		strategyService.delStrategy(dto);
		return ResultFactory.success();
	}

	/**
	 * @Author: shizt
	 * @Date: 2020/2/21 16:09
	 */
	@ApiOperation(value = "采查策略 列表")
	@GetMapping("/getStrategyList")
	public Result<List<StrategyListVO>> getStrategyList(String strategyName){
		return ResultFactory.success(strategyService.getStrategyList(strategyName));
	}

	/**
	 * @Author: shizt
	 * @Date: 2020/2/24 14:32
	 */
	@ApiOperation(value = "采查策略 初始化数据（航班/机场/对象/障碍物）")
	@GetMapping("/getStrategyInitData")
	public Result<Map<String, Object>> getStrategyInitData(String strategyId, String... refreshModule){
		return ResultFactory.success(strategyService.getStrategyInitData(strategyId, refreshModule));
	}

	/**
	 * @Author: shizt
	 * @Date: 2020/2/24 14:32
	 */
	@ApiOperation(value = "采查策略 任务场景")
	@GetMapping("/getTask")
	public Result<List<TreeOptionVo>> getTask(String strategyId, String infoId){
		return ResultFactory.success(strategyService.getTaskTree(strategyId, infoId));
	}

//	/**
//	 * @Author: shizt
//	 * @Date: 2020/2/25 10:22
//	 */
//	@ApiOperation("采查策略 下拉列表")
//	@GetMapping("/getSelect")
//	public Result<List<Strategy>> getSelect(){
//		return ResultFactory.success(strategyService
//				.lambdaQuery()
//				.select(Strategy::getUuid, Strategy::getStrategyName)
//				.eq(Strategy::getIsDeleted, "0")
//				.list());
//	}
	@ApiOperation(value = "获取专项策略明细")
	@GetMapping("/getSpecialStrategyDetail")
	public Result<StrategySpecialVO> getSpecialStrategyDetail(@ApiParam(name = "strategyId", value = "策略主键", required = true) String strategyId){
		StrategySpecialVO vo = strategyService.getSpecialStrategyDetail(strategyId);
		return ResultFactory.success(vo);
	}
	
	@ApiOperation(value = "专项策略保存,返回策略主键")
	@PostMapping("/saveSpecialStrategy")
	public Result<String> saveSpecialStrategy(@RequestBody StrategySpecialDto dto) {
		String uuid = strategyService.saveSpecialStrategy(dto);
		return ResultFactory.success(uuid);
	}
	
	@ApiImplicitParams({
		@ApiImplicitParam(name="strategyId",value="策略主键",required=false,paramType="query"),
		@ApiImplicitParam(name="uuid",value="航班主键",required=false,paramType="query")
	})
	@ApiOperation(value = "获取策略的航班")
	@GetMapping("/listFlight")
	public Result<List<FlightDeatilVO>> listFlight(String strategyId, String uuid){
		List<FlightDeatilVO> list = strategyService.listFlight(strategyId, uuid);
		return ResultFactory.success(list);
	}


	@ApiOperation(value = "新增航班保存,返回航班主键")
	@PostMapping("/addFight")
	public Result<String> addFight(@RequestBody AddFlightDto dto) {
		String uuid = strategyService.addFight(dto);
		return ResultFactory.success(uuid);
	}
	
	@ApiOperation(value = "获取UUID")
	@GetMapping("/getUUID")
	public Result<String> getUUID(){
		return ResultFactory.success(CommonUtils.getUUID());
	}

}
