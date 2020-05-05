package com.chinobot.plep.home.report.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ApiModel(description = "采查报告-预警信息")
@Data
public class WarnImgVo {
	
    @ApiModelProperty(value = "定点主键")
    private String uuid;

    @ApiModelProperty(value = "定点名称")
    private String pointName;
    
    @ApiModelProperty(value = "顶点下所有航点的基准图与预警图")
    private List<FlyPointsVo> flyPointsVo;
    
    @ApiModelProperty(value = "顶点下所有航点的基准图与预警图的数量")
    private int num;

    @ApiModelProperty(value = "顶点下所有航点的预警数")
    private int warnNum;
}
