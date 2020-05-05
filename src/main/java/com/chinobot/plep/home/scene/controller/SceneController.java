package com.chinobot.plep.home.scene.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.scene.entity.Scene;
import com.chinobot.plep.home.scene.service.ISceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 场景表 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-08-22
 */
@Api(tags= {"场景表接口"})
@RestController("SceneController")
@RequestMapping("/api/scene/scene")
public class SceneController extends BaseController {
	@Autowired
	private ISceneService sceneService;
	
	@ApiOperation(value = "获取场景树形结构", notes = "参数对象param")
	@GetMapping("/getAllSceneTree")
//	@RequestMapping("/getAllSceneTree")
	public Result getAllSceneTree(@RequestParam Map<String, Object> param) {
		return ResultFactory.success(sceneService.getAllSceneTree(param));
	}
	
	@ApiOperation(value = "获取所有领域 - N(暂未使用)", notes = "无参数")
	@GetMapping("/getAllDomain")
//	@RequestMapping("/getAllDomain")
	public Result getAllDomain() {
		return ResultFactory.success(sceneService.getAllScene());
	}
	
	@ApiOperation(value = "删除场景", notes = "参数- 场景scene")
	@PostMapping("/del")
//	@RequestMapping("/del")
	public Result del(@RequestBody Scene scene) {
		sceneService.del(scene);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "获取场景信息", notes = "参数- 场景id")
	@GetMapping("/getSceneById")
//	@RequestMapping("/getSceneById")
	public Result getSceneById(String uuid) {
		Scene entity = sceneService.getById(uuid);
		Scene parent = sceneService.getById(entity.getParentId());
		Map map = new HashMap();
		map.put("entity", entity);
		map.put("parent", parent);
		return ResultFactory.success(map);
	}
	
	@ApiOperation(value = "保存场景", notes = "参数- 场景scene")
	@PostMapping("/save")
//	@RequestMapping("/save")
	public Result save(@RequestBody Scene scene) {
		sceneService.saveOrUpdate(scene);
		return ResultFactory.success(scene);
	}
}
