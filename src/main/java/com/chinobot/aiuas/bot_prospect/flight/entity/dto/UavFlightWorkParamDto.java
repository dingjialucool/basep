package com.chinobot.aiuas.bot_prospect.flight.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author huangw
 *
 */
@ApiModel("查询无人机作业任务参数")
@Data
public class UavFlightWorkParamDto {

	@ApiModelProperty(value="无人机主键")
	private String uavId;
	
	@ApiModelProperty("飞行员主键")
	private String personId;
	
	@ApiModelProperty("作业日期")
	private String flightDate;
	
	@ApiModelProperty("作业状态 , 默认为2 (1:待安排 2:待执行 3: 待完成 4：已完成 0：已取消)")
    private String workStatus;
	
	@ApiModelProperty("机场主键")
	private String airportId;
	
	@ApiModelProperty("策略主键")
	private String strategyId;
	
	@ApiModelProperty("航班名称模糊查询")
	private String flightName;
	
	@ApiModelProperty("作业主键")
	private String workId;
}
