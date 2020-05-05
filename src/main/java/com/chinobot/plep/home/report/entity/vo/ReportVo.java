package com.chinobot.plep.home.report.entity.vo;


import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "采查报告")
@Data
public class ReportVo {
	
	@ApiModelProperty(value = "主键")
	private String uuid;
	
	@ApiModelProperty(value = "区域")
	private String area;
	
	@ApiModelProperty(value = "区域(部门)Id")
	private String areaId;
	
	@ApiModelProperty(value = "周期")
	private String frequency;
	
	@ApiModelProperty(value = "报告时间")
	private LocalDateTime reportTime;
	
	@ApiModelProperty(value = "开始时间")
	private String startTime;
	
	@ApiModelProperty(value = "结束时间")
	private String endTime;
	
	@ApiModelProperty(value = "生成报告的方式（1.自动生成 2.自定义生成）")
	private String type;
	
	@ApiModelProperty(value = "根据频率判断属于哪周，哪月，哪个季度，哪年")
	private Integer cycle;
	
	@ApiModelProperty(value = "年份，属于哪年")
	private Integer years;
}
