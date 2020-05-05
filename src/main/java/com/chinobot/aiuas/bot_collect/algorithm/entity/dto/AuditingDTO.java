package com.chinobot.aiuas.bot_collect.algorithm.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: AuditingDTO   
 * @Description: 算法审批
 * @author: djl  
 * @date:2020年3月6日 下午2:08:23
 */
@ApiModel(description = "算法审批")
@Data
public class AuditingDTO {

	@ApiModelProperty(value = "算法主键")
    private String algorithmId;
	
	@ApiModelProperty(value = "审批状态：2.退回   3-已通过 " )
	private String businessStatus;
	
	@ApiModelProperty(value = "审批人(当前登录用户)" )
    private String personId;
	
	@ApiModelProperty(value = "审批意见" )
    private String auditingIdea;
	
}
