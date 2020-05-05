package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "区划的位置与名称")
@Data
public class QuestionWarningOfAreaLocation {


    @ApiModelProperty(value = "区划名称")
    private String name;
    
    @ApiModelProperty(value = "经度")
    private double longitude;
    
    @ApiModelProperty(value = "纬度")
    private double latitude;
}
