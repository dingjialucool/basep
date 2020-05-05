package com.chinobot.aiuas.bot_prospect.obstacle.entity.dto;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "障碍物位置信息")
@Data
public class ObstacleLocationDTO {

	@ApiModelProperty(value = "障碍物类型id")
    private String typeId;
	
	@ApiModelProperty(value = "经度-小")
    private double lngMin;
	
	@ApiModelProperty(value = "经度-大")
    private double lngMax;
	
	@ApiModelProperty(value = "纬度-小")
    private double latMin;
	
	@ApiModelProperty(value = "纬度-大")
    private double latMax;
    
}
