package com.chinobot.aiuas.bot_collect.warning.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: QuestionWarningOfWarnVo   
 * @Description: 问题预警总况数据
 * @author: djl  
 * @date:2020年3月16日 下午3:08:34
 */
@ApiModel(description = "问题预警总况-预警经纬度及类型")
@Data
public class QuestionWarningOfWarnLocationVo {

    @ApiModelProperty(value = "经度")
    private double longitude;
    
    @ApiModelProperty(value = "纬度")
    private double latitude;
    
    @ApiModelProperty(value = "预警类型 - 1 事件 2 线索")
    private String warnType;
    
    @ApiModelProperty(value = "事件id")
    private String eventId;
    
    @ApiModelProperty(value = "预警id")
    private String warnId;
    
}
