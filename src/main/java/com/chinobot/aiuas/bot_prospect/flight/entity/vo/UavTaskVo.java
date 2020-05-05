package com.chinobot.aiuas.bot_prospect.flight.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author huangw
 *
 */
@ApiModel("无人机作业采查任务")
@Data
public class UavTaskVo {

	@ApiModelProperty("*****采查任务主键")
	private String taskId;
	
	@ApiModelProperty("采查任务名称")
	private String taskName;
	
	@ApiModelProperty("*****算法主键")
	private String algorithmId;
	
	@ApiModelProperty("算法名称")
	private String algorithmName;
}
