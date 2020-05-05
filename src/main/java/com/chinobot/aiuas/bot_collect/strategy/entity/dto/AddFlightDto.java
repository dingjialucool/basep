package com.chinobot.aiuas.bot_collect.strategy.entity.dto;

import java.util.List;

import com.chinobot.aiuas.bot_collect.strategy.entity.vo.SceneWithIdsVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("新增航班信息")
@Data
public class AddFlightDto {

	@ApiModelProperty(value = "航班名称")
	private String flightName;
	
	@ApiModelProperty(value = "策略主键")
	private String strategyId;
	
	@ApiModelProperty(value = "场景集合")
	private List<SceneWithIdsVO> scenes;
}
