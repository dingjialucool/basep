package com.chinobot.aiuas.bot_prospect.flight.entity.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "无人机作业详细信息")
@Data
public class UavWorkDetailVo {

	@ApiModelProperty(value = "计划路径，逗号、分号分隔")
	private String planPath;
	
	@ApiModelProperty(value = "实际路径，逗号、分号分隔")
	private String realPath;
	
	@ApiModelProperty(value = "作业主键")
	private String workId;
	
	@ApiModelProperty(value = "作业类型：1-常规任务  2-紧急调度任务")
	private String workType;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(value = "作业开始时间")
	private LocalDateTime workStartTime;
	
	@ApiModelProperty(value = "作业名称")
	private String workName;
}
