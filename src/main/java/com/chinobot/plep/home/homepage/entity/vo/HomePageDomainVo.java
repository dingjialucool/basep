package com.chinobot.plep.home.homepage.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页领域信息")
@Data
public class HomePageDomainVo {
	@ApiModelProperty(value = "主键")
	private String uuid;
	
	@ApiModelProperty(value = "名称")
	private String name;
	
	@ApiModelProperty(value = "该领域下的场景集合")
	private List<HomePageSceneVo> scenes;
	
}
