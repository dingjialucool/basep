package com.chinobot.plep.home.homepage.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页区域雷达图信息")
@Data
public class HomePageAreaRendaVo {

	@ApiModelProperty(value = "部门主键")
	private String uuid;
	
	@ApiModelProperty(value = "部门名称")
	private String dname;
	
	@ApiModelProperty(value = "最大值")
	private Long max;
	
	@ApiModelProperty(value = "场景集合")
	private List<HomePageAreaRendaSceneVo> list;
}
