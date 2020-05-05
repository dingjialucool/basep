package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: FlightWorkInfo  
 * @Description: 航班作业信息 
 * @author: djl  
 * @date:2020年2月26日 下午4:57:15
 */
@ApiModel(description = "数据监控总况页面车流监测和工程监测左边数据信息")
@Data
public class FlightWorkInfoVo {
	
	@ApiModelProperty(value = "航班uuid")
	private String workUuid;
	
	@ApiModelProperty(value = "航班名称")
	private String flightName;
	
	@ApiModelProperty(value = "作业时长")
	private double workMinute;
	
	@ApiModelProperty(value = "采查对象名称")
	private String collectName;
	
	@ApiModelProperty(value = "采查对象uuid")
	private String collectUuid;
	
	@ApiModelProperty(value = "任务uuid")
	private String taskUuid;
}
