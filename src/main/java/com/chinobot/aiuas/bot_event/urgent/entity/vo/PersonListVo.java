package com.chinobot.aiuas.bot_event.urgent.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: PersonListVo   
 * @Description: 获取人id与名称
 * @author: djl  
 * @date:2020年3月25日 上午10:57:22
 */
@ApiModel(description = " 获取人id与名称")
@Data
public class PersonListVo {

	@ApiModelProperty(value = "人id")
	private String id;

	@ApiModelProperty(value = "人名称")
    private String title;
	
}
