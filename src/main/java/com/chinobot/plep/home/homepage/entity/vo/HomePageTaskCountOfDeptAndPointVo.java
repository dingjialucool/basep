package com.chinobot.plep.home.homepage.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页各部门各起飞点任务数")
@Data
public class HomePageTaskCountOfDeptAndPointVo {

	@ApiModelProperty(value = "主键")
	private String uuid;
	
	@ApiModelProperty(value = "部门名称")
	private String dname;
	
	@ApiModelProperty(value = "坐标")
	private String lnglat;
	
	@ApiModelProperty(value = "地址库主键，前台忽略", hidden = true)
	private String abId;
	
	@ApiModelProperty(value = "行政区划主键", hidden= true)
	private Integer areaId;
	
	@ApiModelProperty(value = "起飞点集合")
	private List<HomePageTaskCountOfPointVo> points;
	
}
