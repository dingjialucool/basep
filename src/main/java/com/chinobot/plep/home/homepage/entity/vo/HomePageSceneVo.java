package com.chinobot.plep.home.homepage.entity.vo;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页场景信息")
@Data
public class HomePageSceneVo {

	@ApiModelProperty(value = "主键")
	private String uuid;
	
	@ApiModelProperty(value = "名称")
	private String name;

}
