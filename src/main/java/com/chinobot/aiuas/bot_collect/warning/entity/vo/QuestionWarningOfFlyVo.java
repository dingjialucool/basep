package com.chinobot.aiuas.bot_collect.warning.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: QuestionWarningVo   
 * @Description: 问题预警总况数据
 * @author: djl  
 * @date:2020年3月16日 下午3:08:34
 */
@ApiModel(description = "问题预警总况-飞行采查情况")
@Data
public class QuestionWarningOfFlyVo {

    @ApiModelProperty(value = "航班名称")
    private String fligntName;
    
    @ApiModelProperty(value = "起飞时间")
    private String flightTime;
    
    @ApiModelProperty(value = "作业时长")
    private String workMinute;
    
}
