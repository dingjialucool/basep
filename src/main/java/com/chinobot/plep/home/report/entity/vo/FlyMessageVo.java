package com.chinobot.plep.home.report.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ApiModel(description = "采查报告-飞行巡查")
@Data
public class FlyMessageVo {
	
    @ApiModelProperty(value = "航线数量")
    private String routeNum;

    @ApiModelProperty(value = "计划飞行次数")
    private Long flyPlanNum;
    
    @ApiModelProperty(value = "飞行次数")
    private Long flyNum;

    @ApiModelProperty(value = "预警数量")
    private Long warnNum;
    
    @ApiModelProperty(value = "里程")
    private double milgage;
    
    @ApiModelProperty(value = "飞行执行比例")
    private String flyRatio;
}
