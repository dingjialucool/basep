package com.chinobot.aiuas.bot_collect.strategy.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略报告-预警信息明细")
@Data
public class StrategyReportOfWarnDetailVo {

	@ApiModelProperty(value = "采查对象")
	private String objectName;

	@ApiModelProperty(value = "预警名称")
	private String eventName;

	@ApiModelProperty(value = "预警地址")
	private String address;

	@ApiModelProperty(value = "准确率")
	private String accuracy;

	@ApiModelProperty(value = "预警图片")
	private String warnImg;

	@ApiModelProperty(value = "基准图")
	private String warnBasicImg;

	@ApiModelProperty(value = "视频")
	private String warnVed;
}
