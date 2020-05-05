package com.chinobot.plep.home.homepage.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页预警图表部门月份信息")
@Data
public class HomePageEarlyWarningDeptMonthVo {

	@ApiModelProperty(value = "月份")
	private String mon;
	
	@ApiModelProperty(value = "预警数量")
	private Long count;
}
