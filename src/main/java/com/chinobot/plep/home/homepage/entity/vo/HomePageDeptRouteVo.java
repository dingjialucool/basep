package com.chinobot.plep.home.homepage.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页各部门及该部门下定点的数量")
@Data
public class HomePageDeptRouteVo {

	@ApiModelProperty(value = "部门主键")
	private String uuid;
	
	@ApiModelProperty(value = "部门名称")
	private String dname;
	
	@ApiModelProperty(value = "航线数量")
	private Long count;
	
}
