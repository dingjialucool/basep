package com.chinobot.aiuas.bot_collect.algorithm.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: ApproveVo   
 * @Description: 审批信息
 * @author: djl  
 * @date:2020年3月6日 下午5:18:21
 */
@ApiModel(description = "审批信息")
@Data
public class ApproveVo {

	@ApiModelProperty(value = "审批意见" )
	private String approvalIdea;
	
	@ApiModelProperty(value = "审批人" )
	private String personName;
	
	@ApiModelProperty(value = "审批时间" )
	private String approveTime;
    
}
