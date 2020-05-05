package com.chinobot.aiuas.bot_collect.strategy.entity.vo;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("主键名称")
@Data
public class IdNameVo {

	@ApiModelProperty(value = "主键")
	private String uuid;
	
	@ApiModelProperty(value = "名称")
	private String name;
}
