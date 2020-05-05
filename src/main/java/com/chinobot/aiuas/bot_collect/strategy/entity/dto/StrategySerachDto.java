package com.chinobot.aiuas.bot_collect.strategy.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: StrategySerachDto   
 * @Description: 采查报告请求参数  
 * @author: djl  
 * @date:2020年3月10日 上午11:08:26
 */
@ApiModel(description = "采查报告请求参数")
@Data
public class StrategySerachDto {
	
	@ApiModelProperty(value = "每页条数")
    private long size ;
	
	@ApiModelProperty(value = "当前页")
    private long current ;

	@ApiModelProperty(value = "策略名称")
	private String strategyName;
	
	@ApiModelProperty(value = "策略类型")
	private String strategyType;
}
