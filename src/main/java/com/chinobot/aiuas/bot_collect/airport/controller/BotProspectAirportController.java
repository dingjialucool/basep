package com.chinobot.aiuas.bot_collect.airport.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
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
import com.chinobot.aiuas.bot_collect.airport.entity.BotProspectAirport;
import com.chinobot.aiuas.bot_collect.airport.entity.BotProspectAirportUav;
import com.chinobot.aiuas.bot_collect.airport.entity.dto.AirportGeographyDTO;
import com.chinobot.aiuas.bot_collect.airport.entity.dto.UavAirportDTO;
import com.chinobot.aiuas.bot_collect.airport.entity.vo.AirportVO;
import com.chinobot.aiuas.bot_collect.airport.entity.vo.UavOfAirportVO;
import com.chinobot.aiuas.bot_collect.airport.service.IBotProspectAirportService;
import com.chinobot.aiuas.bot_collect.airport.service.IBotProspectAirportUavService;
import com.chinobot.aiuas.bot_collect.info.constant.PartConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 机场 前端控制器
 * </p>
 *
 * @author djl
 * @since 2020-01-15
 */
@Api(tags="机场管理接口")
@RestController
@RequestMapping("/api/airport/bot-prospect-airport")
public class BotProspectAirportController extends BaseController {
	
	@Autowired
	private IBotProspectAirportService botProspectAirportService;
	@Autowired
	private IBotProspectAirportUavService botProspectAirportUavService;

	@ApiOperation(value = "新增/编辑机场", notes = "新增/编辑机场")
	@PostMapping("/addAirport")
	public Result addAirport(@RequestBody AirportGeographyDTO dto) {
		
		botProspectAirportService.addAirport(dto);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "删除机场", notes = "机场下有无人机时(uavNum不为0)，提示：机场下有无人机是否删除，选择删除则发送删除请求")
	@GetMapping("/deleteAirport")
	public Result deleteAirport(@ApiParam(name = "airportId", value = "机场主键", required = true) @RequestParam(value = "airportId", required = true) String airportId) {
		
		botProspectAirportService.deleteAirport(airportId);
		
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "机场查询", notes = "机场查询")
	@GetMapping("/selectAirport")
	public Result<List<AirportVO>> selectAirport() {
		
		return ResultFactory.success(botProspectAirportService.selectAirport());
	}
	
	@ApiOperation(value = "机场回显", notes = "机场回显")
	@GetMapping("/getAirport")
	public Result<AirportGeographyDTO> getAirport(@ApiParam(name = "airportId", value = "机场主键", required = true) @RequestParam(value = "airportId", required = true) String airportId) {
		
		
		return ResultFactory.success(botProspectAirportService.getAirport(airportId));
	}
	
	@ApiOperation(value = "删除机场下的无人机", notes = "删除机场下的无人机")
	@GetMapping("/deleteUavOfAirport")
	public Result deleteUavOfAirport(@ApiParam(name = "airportAndUavId", value = "机场无人机联合主键", required = true) @RequestParam(value = "airportAndUavId", required = true) String airportAndUavId) {
		
		botProspectAirportUavService.update(new LambdaUpdateWrapper<BotProspectAirportUav>().eq(BotProspectAirportUav::getUuid, airportAndUavId).set(BotProspectAirportUav::getIsDeleted, PartConstant.DATA_YES_DELETE));
		
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "无人机列表（分页 10条数据每页）", notes = "无人机列表")
	@GetMapping("/getUavList")
	public Result<IPage<UavOfAirportVO>> getUavList(Page page,@RequestParam(required = false)  Map<String, Object> param) {
		
		return ResultFactory.success(botProspectAirportService.getUavList(page,param));
		
	}
	
	@ApiOperation(value = "机场增加无人机", notes = "机场增加无人机")
	@PostMapping("/addUavAirport")
	public Result addUavAirport(@RequestBody UavAirportDTO dto) {
		if (dto.getUavList().size() == 0) {
			return ResultFactory.fail(null);
		}
		botProspectAirportService.addUavAirport(dto);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "机场下无人机列表（分页 9条数据每页）", notes = "参数中有一个 机场主键 airportId 必传")
	@GetMapping("/getUavOfAirportList")
	public Result<IPage<UavOfAirportVO>> getUavOfAirportList(Page page,@RequestParam  Map<String, Object> param) {
		
		return ResultFactory.success(botProspectAirportService.getUavOfAirportList(page,param));
		
	}
}
