package com.chinobot.aiuas.bot_prospect.obstacle.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "障碍物对象经纬度集合(多边形)")
@Data
public class ObstacletPolygon {

	@ApiModelProperty(value = "障碍物主键")
    private String obstacleId;
	
	@ApiModelProperty(value = "障碍物名称")
    private String obstacleName;
	
	@ApiModelProperty(value = "经纬度集合：;分隔")
    private String lnglats;
}
