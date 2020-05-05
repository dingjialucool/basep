package com.chinobot.aiuas.bot_collect.task.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.entity.Scene;
import com.chinobot.aiuas.bot_collect.task.entity.vo.SceneListVo;
import com.chinobot.aiuas.bot_collect.task.service.ISceneService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chinobot.framework.web.controller.BaseController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 采查场景 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@RestController
@RequestMapping("/api/bot/collect/task/scene")
public class SceneController extends BaseController {
    @Autowired
    private ISceneService iSceneService;

    /**
     * 列表
     * @Param: [param]
     * @Return: com.chinobot.common.domain.Result<java.util.List<com.chinobot.aiuas.bot_collect.task.entity.vo.SceneListVo>>
     * @Author: shizt
     * @Date: 2020/1/9 14:50
     */
    @ApiOperation(value = "列表", notes = "")
    @GetMapping("/list")
    public Result<IPage<SceneListVo>> getList(Page page, @RequestParam Map<String, Object> param){
        return ResultFactory.success(iSceneService.getSceneList(page, param));
    }

    /**
     * 详细
     * @Param: [param]
     * @Return: com.chinobot.common.domain.Result<com.chinobot.aiuas.bot_collect.task.entity.vo.SceneListVo>
     * @Author: shizt
     * @Date: 2020/1/9 14:50
     */
    @ApiOperation(value = "详细", notes = "")
    @GetMapping("")
    public Result<Scene> info(String uuid){
        return ResultFactory.success(iSceneService.getSceneById(uuid));
    }

    /**
     * 编辑
     * @Param: [scene]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/9 14:52
     */
    @ApiOperation(value = "编辑", notes = "")
    @PostMapping("/edit")
    public Result edit(@RequestBody Scene scene){
        return iSceneService.editScene(scene);
    }

    /**
     * 删除
     * @Param: [uuid]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/10 18:02
     */
    @ApiOperation(value = "删除")
    @PostMapping("/del")
    public Result del(@RequestParam String uuid){
        return ResultFactory.success(iSceneService.delScene(uuid));
    }
}
