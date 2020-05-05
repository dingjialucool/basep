package com.chinobot.plep.home.homepage.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页各部门及该部门下区域的面积")
@Data
public class HomePageDeptRangeAreaVo {
	
	@ApiModelProperty(value = "主键")
	private String uuid;
	
	@ApiModelProperty(value = "部门名称")
	private String dname;
	
	@ApiModelProperty(value = "边界坐标字符串")
	private String polygon;
	
	@ApiModelProperty(value = "区域边界坐标集合")
	private List<String> list;
}
