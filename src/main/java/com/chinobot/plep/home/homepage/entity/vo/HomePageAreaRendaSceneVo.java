package com.chinobot.plep.home.homepage.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页场景信息")
@Data
public class HomePageAreaRendaSceneVo {
	@ApiModelProperty(value = "场景主键")
	private String uuid;
	
	@ApiModelProperty(value = "场景名称")
	private String name;
	
	@ApiModelProperty(value = "区域航线数量")
	private Long count;
}
