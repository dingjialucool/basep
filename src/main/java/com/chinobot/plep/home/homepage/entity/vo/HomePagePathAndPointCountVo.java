package com.chinobot.plep.home.homepage.entity.vo;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页各街道（子部门）起飞点和航线数量")
@Data
public class HomePagePathAndPointCountVo {

	@ApiModelProperty(value = "部门主键")
	private String uuid;
	
	@ApiModelProperty(value = "部门名称")
	private String dname;
	
	@ApiModelProperty(value = "起飞点数量")
	private Long pointCount;
	
	@ApiModelProperty(value = "航线数量")
	private Long pathCount;
}
