package com.chinobot.plep.home.report.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ApiModel(description = "采查报告-航点")
@Data
public class FlyPointsVo {
	
    @ApiModelProperty(value = "文件id")
    private String fileId;
    
    @ApiModelProperty(value = "basic_img基准图,event_img预警图 ")
    private String module;
    
    @ApiModelProperty(value = "预警时间 ")
    private String warnTime;
    
    
}
