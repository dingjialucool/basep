package com.chinobot.aiuas.bot_event.urgent.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: HistoryUrgentDTO   
 * @Description: 历史调度列表
 * @author: djl  
 * @date:2020年3月25日 上午10:57:22
 */
@ApiModel(description = "历史调度列表")
@Data
public class HistoryUrgentVo {

	@ApiModelProperty(value = "调度主键")
	private String uuid;

	@ApiModelProperty(value = "调度名称")
    private String urgentName;
	
	@ApiModelProperty(value = "目标地址" )
    private String address;
	
	@ApiModelProperty(value = "发起人" )
    private String promoter;
	
	@ApiModelProperty(value = "发起时间" )
	private String startTime;
	
	@ApiModelProperty(value = "执行人" )
	private String doPerson;
}
