package com.chinobot.aiuas.bot_collect.strategy.entity.vo;


import java.util.List;

import com.chinobot.aiuas.bot_prospect.flight.entity.vo.CollectResultVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "策略报告-查看信息")
@Data
public class StrategyReportAllVo {

	@ApiModelProperty(value = "策略信息")
	private StrategyReportTaskVo taskVo;
	
	@ApiModelProperty(value = "航线规划情况")
	private List<StrategyReportFlightVo> flightVo;
	
	@ApiModelProperty(value = "飞行巡查情况")
	private List<StrategyReportFlyVo> flyVo;
	
	@ApiModelProperty(value = "预警信息统计")
	private List<StrategyReportOfEventVo> eventVo;
	
	@ApiModelProperty(value = "预警信息明细")
	private List<StrategyReportOfWarnDetailVo> warnDetailVo;
	
	@ApiModelProperty(value = "数量监测")
	private List<CollectResultVo> resultListVo;
}
