package com.chinobot.plep.home.homepage.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页预警图表部门信息")
@Data
public class HomepageEarlyWarningDeptVo {

	@ApiModelProperty(value = "部门主键")
	private String uuid;
	
	@ApiModelProperty(value = "部门名称")
	private String dname;
	
	@ApiModelProperty(value = "部门坐标")
	private String lnglat;
	
	@ApiModelProperty(value = "地址库主键", hidden= true)
	private String abId;
	
	@ApiModelProperty(value = "行政区划主键", hidden= true)
	private Integer areaId;
	
	@ApiModelProperty(value = "部门预警数量信息")
	private List<HomePageEarlyWarningDeptMonthVo> list;
}
