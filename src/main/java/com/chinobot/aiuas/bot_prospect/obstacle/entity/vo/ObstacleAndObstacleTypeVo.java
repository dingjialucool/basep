package com.chinobot.aiuas.bot_prospect.obstacle.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "障碍物类型下的障碍物")
@Data
public class ObstacleAndObstacleTypeVo {

	@ApiModelProperty(value = "障碍物主键")
    private String uuid;
	
	@ApiModelProperty(value = "障碍物名称")
    private String obstacleName;
	
	@ApiModelProperty(value = "障碍物高度")
    private String obstacleHeight;
    
	@ApiModelProperty(value = "障碍物海拔")
    private String obstacleAltitude;
	
	@ApiModelProperty(value = "地址")
    private String address;
	
	@ApiModelProperty(value = "经度")
    private double lng;
	
	@ApiModelProperty(value = "纬度")
    private double lat;
	
	@ApiModelProperty(value = "障碍物多边形")
    private String lnglats;
	
	@ApiModelProperty(value = "障碍物类型图标")
    private String icon;
	
	
	
	
}
