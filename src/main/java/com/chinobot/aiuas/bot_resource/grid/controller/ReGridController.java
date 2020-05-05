package com.chinobot.aiuas.bot_resource.grid.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_resource.grid.entity.vo.GridGeographyVo;
import com.chinobot.aiuas.bot_resource.grid.entity.vo.GridVo;
import com.chinobot.aiuas.bot_resource.grid.service.IReGridService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chinobot.framework.web.controller.BaseController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网格表 前端控制器
 * </p>
 *
 * @author shizt
 * @since 2020-02-26
 */
@Api("网格接口")
@RestController
@RequestMapping("/api/bot/resource/grid")
public class ReGridController extends BaseController {

    @Autowired
    private IReGridService iGridService;

    /**
     * @Author: shizt
     * @Date: 2020/2/26 11:35
     */
    @ApiOperation("分页列表")
    @GetMapping("/getPage")
    public Result<IPage<GridVo>> getPage(Page page, @RequestParam Map<String, Object> param) {
        return ResultFactory.success(iGridService.getPage(page, param));
    }

    /**
     * @Author: shizt
     * @Date: 2020/2/26 11:59
     */
    @ApiOperation("编辑网格")
    @PostMapping("/edit")
    public Result edit(@RequestBody GridGeographyVo gridGeographyVo){
        iGridService.edit(gridGeographyVo);
        return ResultFactory.success();
    }

    /**
     * @Author: shizt
     * @Date: 2020/2/26 11:55
     */
    @ApiOperation("网格详细")
    @GetMapping("/getInfo")
    public Result<GridGeographyVo> getInfo(String gridId){
        return ResultFactory.success(iGridService.getInfo(gridId));
    }

    /**
     * @Author: shizt
     * @Date: 2020/2/27 9:25
     */
    @ApiOperation("周边网格")
    @GetMapping("/getAroundGrid")
    public Result<List<GridGeographyVo>> getAroundGrid(@RequestParam Map<String, Object> param){
        return ResultFactory.success(iGridService.getAroundGrid(param));
    }

}
