package com.chinobot.aiuas.bot_collect.warning.entity.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: SuperviseDTO   
 * @Description: 添加督办对象 
 * @author: djl  
 * @date:2020年2月26日 下午4:57:15
 */
@ApiModel(description = "添加督办")
@Data
public class SuperviseDTO {

	@ApiModelProperty(value = "预警事件主键")
    private String uuid;
	
    @ApiModelProperty(value = "督办人")
    private String superviseBy;
    
    @ApiModelProperty(value = "督办时间")
    private String superviseTime;
    
    @ApiModelProperty(value = "督办内容")
    private String superviseContent;
    
}
