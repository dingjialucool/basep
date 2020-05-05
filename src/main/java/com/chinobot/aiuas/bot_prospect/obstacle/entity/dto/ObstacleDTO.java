package com.chinobot.aiuas.bot_prospect.obstacle.entity.dto;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "障碍物")
@Data
public class ObstacleDTO {

	@ApiModelProperty(value = "障碍物主键")
    private String uuid;
	
	@ApiModelProperty(value = "障碍物名称")
    private String obstacleName;
	
	@ApiModelProperty(value = "描述")
    private String remark;
	
	@ApiModelProperty(value = "障碍物高度")
    private String obstacleHeight;
    
	@ApiModelProperty(value = "障碍物海拔")
    private String obstacleAltitude;
    
	@ApiModelProperty(value = "障碍物类型外键")
    private String obstacleType;
	
	@ApiModelProperty(value = "地址")
    private String oAddress;

	@ApiModelProperty(value = "类型名称")
    private String typeName;

	@ApiModelProperty(value = "图标")
    private String icon;
}
