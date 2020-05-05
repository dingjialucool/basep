package com.chinobot.plep.app.logs.controller;


import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.plep.app.logs.entity.AppExceptionLogs;
import com.chinobot.plep.app.logs.service.IAppExceptionLogsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import com.chinobot.framework.web.controller.BaseController;

/**
 * <p>
 * app错误日志 前端控制器
 * </p>
 *
 * @author shizt
 * @since 2019-10-27
 */
@Api(tags="app错误日志接口")
@RestController
@RequestMapping("/api/app/logs")
public class AppLogsController extends BaseController {

    @Autowired
    private IAppExceptionLogsService appExceptionLogsService;

    /**
     * 保存错误日志
     * @Param: [appExceptionLogs]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2019/10/27 16:54
     */
    @ApiOperation(value = "保存错误日志", notes = "保存错误日志")
    @RequestMapping("/save")
    public Result save(@RequestBody AppExceptionLogs appExceptionLogs){

        return ResultFactory.success(appExceptionLogsService.saveOrUpdate(appExceptionLogs));
    };

}
