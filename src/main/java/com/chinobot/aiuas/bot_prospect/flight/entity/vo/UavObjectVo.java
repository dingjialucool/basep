package com.chinobot.aiuas.bot_prospect.flight.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author huangw
 *
 */
@ApiModel("无人机作业采查对象")
@Data
public class UavObjectVo {

	@ApiModelProperty("*****采查对象主键")
	private String objectId;
	
	@ApiModelProperty("采查对象名称")
	private String objectName;
	
	@ApiModelProperty("*****采查对象边界")
	private String lnglats;
	
	@ApiModelProperty("*****采查任务集合")
	private List<UavTaskVo> tasks;
}
