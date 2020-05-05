package com.chinobot.aiuas.bot_resource.uav.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavInfoVo;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavListVo;
import com.chinobot.aiuas.bot_resource.uav.service.IReUavService;
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
 * 无人机 前端控制器
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
@Api(tags= {"无人机接口"})
@RestController
@RequestMapping("/api/bot/resource/uav/uav")
public class ReUavController extends BaseController {

    @Autowired
    private IReUavService iReUavService;

    /**
     * 列表
     * @Author: shizt
     * @Date: 2020/1/17 10:34
     */
    @ApiOperation(value = "列表", notes = "")
    @GetMapping("/list")
    public Result<IPage<UavListVo>> list(Page page, @RequestParam Map<String, Object> param){
        return ResultFactory.success(iReUavService.getUavList(page, param));
    }

    /**
     * 详细
     * @Author: shizt
     * @Date: 2020/1/17 10:34
     */
    @ApiOperation(value = "详细", notes = "")
    @GetMapping("/")
    public Result<UavInfoVo> info(@RequestParam Map<String, Object> param){
        return ResultFactory.success(iReUavService.getUavInfo(param));
    }

    /**
     * 编辑
     * @Author: shizt
     * @Date: 2020/1/17 10:34
     */
    @ApiOperation(value = "编辑", notes = "")
    @PostMapping("/edit")
    public Result edit(@RequestBody UavInfoVo uavInfoVo){
        return ResultFactory.success(iReUavService.editUav(uavInfoVo));
    }

    /**
     * 删除
     * @Author: shizt
     * @Date: 2020/1/17 10:34
     */
    @ApiOperation(value = "删除", notes = "")
    @PostMapping("/del")
    public Result del(String uuid){
        iReUavService.delUav(uuid);

        return ResultFactory.success();
    }
}
