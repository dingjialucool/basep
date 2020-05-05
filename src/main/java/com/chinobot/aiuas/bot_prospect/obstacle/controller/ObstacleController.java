package com.chinobot.aiuas.bot_prospect.obstacle.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleAndGeographyDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleLocationDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleTypeDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacleAndObstacleTypeVo;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacleAndTypeVo;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacletPolygon;
import com.chinobot.aiuas.bot_prospect.obstacle.service.IObstacleService;
import com.chinobot.aiuas.bot_prospect.obstacle.service.IProspectObstacleTypeService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 障碍物表 前端控制器
 * </p>
 *
 * @author shizt
 * @since 2020-02-24
 */
@Api(tags = "障碍物接口")
@RestController
@RequestMapping("/api/bot/obstacle/obstacle")
public class ObstacleController extends BaseController {
	
	@Autowired
	private IProspectObstacleTypeService  obstacleTypeService;
	@Autowired
	private IObstacleService obstacleService;
	
	@ApiOperation(value = "新增/修改障碍物类型", notes = "新增/修改障碍物类型")
	@PostMapping("/addObstacleType")
	public Result addObstacleType(@RequestBody ObstacleTypeDTO dto) {
		
		obstacleTypeService.addObstacleType(dto);
		
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "删除障碍物类型", notes = "删除障碍物类型")
	@GetMapping("/removeObstacleType")
	public Result removeObstacleType(@ApiParam(name = "typeId", value = "障碍物类型id", required = true) @RequestParam(value = "typeId", required = true) String typeId) {
		
		obstacleTypeService.removeObstacleType(typeId);
		
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "新增/修改障碍物", notes = "新增/修改障碍物")
	@PostMapping("/addObstacle")
	public Result addObstacle(@RequestBody ObstacleAndGeographyDTO dto) {
		
		obstacleService.addObstacle(dto);
		
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "删除障碍物", notes = "删除障碍物")
	@GetMapping("/removeObstacle")
	public Result removeObstacle(@ApiParam(name = "obstacleId", value = "障碍物id", required = true) @RequestParam(value = "obstacleId", required = true) String obstacleId) {
		
		obstacleService.removeObstacle(obstacleId);
		
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "获取树形结构", notes = "删除障碍物")
	@GetMapping("/getTreeObstacle")
	public Result<List<ObstacleAndTypeVo>> getTreeObstacle(@ApiParam(name = "name", value = "名称", required = false) @RequestParam(value = "name", required = false) String name) {
		
		return ResultFactory.success(obstacleService.getTreeObstacle(name));
	}
	
	@ApiOperation(value = "回显障碍物类型", notes = "回显障碍物类型")
	@GetMapping("/getObstacleType")
	public Result<ObstacleTypeDTO> getObstacleType(@ApiParam(name = "typeId", value = "障碍物类型id", required = true) @RequestParam(value = "typeId", required = true) String typeId) {
		
		return ResultFactory.success(obstacleTypeService.getObstacleType(typeId));
	}
	
	@ApiOperation(value = "回显障碍物", notes = "回显障碍物")
	@GetMapping("/getObstacle")
	public Result<ObstacleAndGeographyDTO> getObstacle(@ApiParam(name = "obstacleId", value = "障碍物id", required = true) @RequestParam(value = "obstacleId", required = true) String obstacleId) {
		
		return ResultFactory.success(obstacleService.getObstacle(obstacleId));
	}
	
	@ApiOperation(value = "复选框选中的障碍物", notes = "复选框选中的障碍物")
	@PostMapping("/checkedObstacletPolygon")
	public Result<List<ObstacletPolygon>> checkedObstacletPolygon(@RequestBody List<String> idList) {
		
		
		return ResultFactory.success(obstacleService.checkedObstacletPolygon(idList));
	}
	
	@ApiOperation(value = "获取所有障碍物类型", notes = "获取所有障碍物类型")
	@GetMapping("/getAllObstacleType")
	public Result<List<ObstacleTypeDTO>> getAllObstacleType() {
		
		return ResultFactory.success(obstacleTypeService.getAllObstacleType());
	}
	
	@ApiOperation(value = "获取障碍物类型下的所有障碍物", notes = "获取障碍物类型下的所有障碍物")
	@PostMapping("/getAllObstacleByType")
	public Result<List<ObstacleAndObstacleTypeVo>> getAllObstacleByType(@RequestBody ObstacleLocationDTO dto) {
		
		return ResultFactory.success(obstacleTypeService.getAllObstacleByType(dto));
	}
}
