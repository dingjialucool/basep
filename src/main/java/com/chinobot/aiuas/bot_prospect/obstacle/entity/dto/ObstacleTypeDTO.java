package com.chinobot.aiuas.bot_prospect.obstacle.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "障碍物类型")
@Data
public class ObstacleTypeDTO {

	@ApiModelProperty(value = "主键")
    private String uuid;
	
	@ApiModelProperty(value = "图标")
    private String icon;
	
	@ApiModelProperty(value = "名称")
    private String typeName;
	
	
	
}
