package com.chinobot.aiuas.bot_collect.warning.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: QuestionWarningOfBoundary   
 * @Description: 问题预警总况数据
 * @author: djl  
 * @date:2020年3月16日 下午3:08:34
 */
@ApiModel(description = "问题预警总况-获取边界")
@Data
public class QuestionWarningOfBoundary {

    @ApiModelProperty(value = "区划名称")
    private String name;
    
    @ApiModelProperty(value = "边界")
    private String bounday;
    
}
