package com.chinobot.plep.home.homepage.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页各部门及该部门下定点的数量")
@Data
public class HomePageDeptPointVo {

	@ApiModelProperty(value = "部门主键")
	private String uuid;
	
	@ApiModelProperty(value = "部门名称")
	private String dname;
	
	@ApiModelProperty(value = "定点数量")
	private Long count;
	
	@ApiModelProperty(value = "最大数量")
	private Long maxCount;
}
