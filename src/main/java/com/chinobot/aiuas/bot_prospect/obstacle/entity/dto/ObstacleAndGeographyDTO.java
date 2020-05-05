package com.chinobot.aiuas.bot_prospect.obstacle.entity.dto;


import java.util.List;

import com.chinobot.aiuas.bot_collect.info.entity.dto.GeographyDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "障碍物+地址库")
@Data
public class ObstacleAndGeographyDTO {

	
	@ApiModelProperty(value = "障碍物对象")
	private ObstacleDTO obstacleDTO;
	
	@ApiModelProperty(value = "地址库对象")
	private GeographyDTO geography;
	
	@ApiModelProperty(value = "文件id数组")
	private List<String> idList;
}
