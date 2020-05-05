package com.chinobot.aiuas.bot_collect.result.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.result.service.IResultService;
import com.chinobot.aiuas.bot_collect.result.service.impl.ResultServiceImpl;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.chinobot.framework.web.controller.BaseController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 作业采集结果表 前端控制器
 * </p>
 *
 * @author shizt
 * @since 2020-03-17
 */
@Api(tags= {"作业采集结果表接口"})
@RestController
@RequestMapping("/api/bot/collect/result")
public class ResultController extends BaseController {

    @Autowired
    private IResultService iResultService;
    /**
     * @Author: shizt
     * @Date: 2020/3/16 17:11
     */
    @ApiOperation(value = "进度检测总况 - 工程下拉框列表")
    @GetMapping("/getProgressCollectOptions")
    public Result<List<Map>> getProgressCollectOptions(){
        return ResultFactory.success(iResultService.getProgressCollectOptions());
    }

    /**
     * @Author: shizt
     * @Date: 2020/3/16 17:11
     */
    @ApiOperation(value = "进度检测总况 - 工程列表数据")
    @GetMapping("/getProgressCollectData")
    public Result<Map> getProgressCollectData(Page page, String[] collectIds){
        return ResultFactory.success(iResultService.getProgressCollectData(page, collectIds));
    }

    /**
     * @Author: shizt
     * @Date: 2020/3/18 15:24
     */
    @ApiOperation(value = "进度检测总况 - 飞行作业情况")
    @GetMapping("/getFlightWork")
    public Result<List> getFlightWork(Page page, String[] collectIds){
        return ResultFactory.success(iResultService.getFlightWork(page, collectIds));
    }

    /**
     * @Author: shizt
     * @Date: 2020/3/17 11:49
     */
    @ApiOperation(value = "进度检测总况 - 工程详细")
    @GetMapping("/getProgressCollectInfo")
    public Result getProgressCollectInfo(String collectId){
        return ResultFactory.success(iResultService.getProgressCollectInfo(collectId));
    }
}
