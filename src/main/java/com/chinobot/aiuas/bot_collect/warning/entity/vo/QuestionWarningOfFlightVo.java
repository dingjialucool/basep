package com.chinobot.aiuas.bot_collect.warning.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: QuestionWarningOfFlightVo   
 * @Description: 问题预警总况-在飞航班总数，已非航班总数...
 * @author: djl  
 * @date:2020年3月16日 下午3:08:34
 */
@ApiModel(description = "问题预警总况-在飞航班总数，已非航班总数...")
@Data
public class QuestionWarningOfFlightVo {

    @ApiModelProperty(value = "在飞航班总数")
    private Long fligntingCount;
    
    @ApiModelProperty(value = "已飞航班总数")
    private Long flightedCount;
    
    @ApiModelProperty(value = "采查场景总数")
    private Long sceneCount;
    
    @ApiModelProperty(value = "采查任务总数")
    private Long taskCount;
    
    @ApiModelProperty(value = "检测对象总数")
    private Long objectCount;
    
}
