package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: FeedBackHistoryVo   
 * @Description: 历史反馈信息 
 * @author: djl  
 * @date:2020年2月26日 下午4:57:15
 */
@ApiModel(description = "历史反馈信息")
@Data
public class FeedBackHistoryVo {

    @ApiModelProperty(value = "反馈时间")
    private String feedbackTime;
    
    @ApiModelProperty(value = "反馈内容")
    private String feedbackContent;
    
}
