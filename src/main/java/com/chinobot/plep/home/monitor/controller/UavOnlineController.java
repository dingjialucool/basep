package com.chinobot.plep.home.monitor.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.plep.home.monitor.entity.UavOnline;
import com.chinobot.plep.home.monitor.service.IUavOnlineService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.chinobot.framework.web.controller.BaseController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author laihb
 * @since 2019-07-26
 */
@Api(tags= {"实时无人机接口-N(未使用)"})
@RestController
@RequestMapping("/api/monitor/uav")
public class UavOnlineController extends BaseController {

    @Autowired
    IUavOnlineService uavOnlineService;

    @ApiOperation(value = "获取实时无人机 -N(暂未使用)", notes = "参数 - 无")
	@GetMapping("/online")
    public Result uavOnlineList(){
        List<UavOnline> uavList =   uavOnlineService.list ();
        return ResultFactory.success (uavList);
    }
    
    
    @ApiOperation(value = "获取无人机实时信息与无人机信息 -N(暂未使用)", notes = "参数 - 分页page,Map param")
	@GetMapping("/onlines")
    public Result uavList(Page page,@RequestParam Map<String,Object> param){
        List<Map> uavList =   uavOnlineService.uavList(page,param);
        return ResultFactory.success (uavList);
    }
}
