package com.chinobot.aiuas.bot_event.urgent.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: HistoryUrgentDTO   
 * @Description: 历史调度查询条件  
 * @author: djl  
 * @date:2020年3月25日 上午10:57:22
 */
@ApiModel(description = "历史调度查询条件")
@Data
public class HistoryUrgentDTO {

	@ApiModelProperty(value = "每页条数")
    private long size ;
	
	@ApiModelProperty(value = "当前页")
    private long current ;

	@ApiModelProperty(value = "调度名称")
    private String urgentName;
	
	@ApiModelProperty(value = "目标地址" )
    private String address;
	
	@ApiModelProperty(value = "发起人" )
    private String promoterId;
	
	@ApiModelProperty(value = "发起时间-开始" )
	private String start;
	
	@ApiModelProperty(value = "发起时间-结束" )
	private String end;
	
	@ApiModelProperty(value = "执行人" )
	private String executorId;
}
