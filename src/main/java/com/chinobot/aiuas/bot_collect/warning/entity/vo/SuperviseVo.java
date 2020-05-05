package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: FeedBackVo   
 * @Description: 视图督办对象 
 * @author: djl  
 * @date:2020年2月26日 下午4:57:15
 */
@ApiModel(description = "事件跟踪详情中督办信息")
@Data
public class SuperviseVo {

    @ApiModelProperty(value = "督办人")
    private String superviseBy;
    
    @ApiModelProperty(value = "督办时间")
    private String superviseTime;
    
    @ApiModelProperty(value = "督办内容")
    private String superviseContent;
    
}
