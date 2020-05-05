package com.chinobot.plep.home.report.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ApiModel(description = "采查报告-存量动工明细-时间范围内所有存量")
@Data
public class FixedPointOfTimeDTO {
	
	@ApiModelProperty(value = "定点id")
    private String pointId;
    
    @ApiModelProperty(value = "定点名称 ")
    private String pointName;
    
    @ApiModelProperty(value = "定点下所有航点的飞行数据 ")
    private List<Map> flyImgList;
    
    @ApiModelProperty(value = "定点下所有航点的飞行数据总数 ")
    private Long flyImgNum;
}
