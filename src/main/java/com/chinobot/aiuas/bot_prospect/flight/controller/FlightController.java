package com.chinobot.aiuas.bot_prospect.flight.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.FlightReferenceVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.UavWorkVo;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightService;
import com.chinobot.aiuas.bot_resource.uav.entity.Uav;
import com.chinobot.aiuas.bot_resource.uav.service.IReUavService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 航班 前端控制器
 * </p>
 *
 * @author shizt
 * @since 2020-02-21
 */
@Api(tags="航班接口")
@RestController
@RequestMapping("/api/bot/prospect/flight")
public class FlightController extends BaseController {
    @Autowired
    private IFlightService iFlightService;
    @Autowired
    private IReUavService uavService;

    /**
     * @Author: shizt
     * @Date: 2020/2/24 15:21
     */
    @ApiOperation("新增修改航班")
    @PostMapping("/edit")
    public Result addFlight(@RequestBody FlightReferenceVo flightReferenceVo) throws IOException {
        return iFlightService.edit(flightReferenceVo);
    }
    
    @ApiImplicitParams({
    	@ApiImplicitParam(name="uavCode",value="uav主键",required=true,paramType="query"),
    })
    @ApiOperation("获取无人机作业信息， 不传参数代表获取所有无人机的作业信息")
    @GetMapping("/getUavWorkInfo")
    public Result<List<UavWorkVo>> getUavWorkInfo(String uavCode){
    	List<UavWorkVo> list = new ArrayList<UavWorkVo>();
    	if(StringUtils.isBlank(uavCode)) {
    		QueryWrapper eqQueryWrapper = CommonUtils.getEqQueryWrapper("is_deleted",0);
    		eqQueryWrapper.select("uuid");
    		List<Uav> uavs = uavService.list(eqQueryWrapper);
    		for(Uav uav : uavs) {
    			list.add(iFlightService.getUavWorkInfo(uav.getUuid()));
    		}
    	}else{
    		list.add(iFlightService.getUavWorkInfo(uavCode));
    	}
    	return ResultFactory.success(list);
    }

}
