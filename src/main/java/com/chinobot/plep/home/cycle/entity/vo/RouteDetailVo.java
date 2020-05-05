package com.chinobot.plep.home.cycle.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "路线明细信息")
@Data
public class RouteDetailVo {

	@ApiModelProperty(value = "主键")
	private String uuid;
	
	@ApiModelProperty(value = "到达距离")
	private Float distance;
	
	@ApiModelProperty(value = "到达时间")
	private Float getTime;
	
	@ApiModelProperty(value = "采查时间")
	private Float checkTime;
	
	@ApiModelProperty(value = "起飞点名称")
	private String pointName;
	
	@ApiModelProperty(value = "起飞点地址")
	private String address;
	
	@ApiModelProperty(value = "序号")
	private Integer sort;
}
