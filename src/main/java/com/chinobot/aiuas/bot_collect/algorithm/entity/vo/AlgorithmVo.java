package com.chinobot.aiuas.bot_collect.algorithm.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: AlgorithmVo   
 * @Description: 算法管理列表 
 * @author: djl  
 * @date:2020年3月6日 下午5:18:21
 */
@ApiModel(description = "算法管理列表")
@Data
public class AlgorithmVo {
	
	@ApiModelProperty(value = "算法主键")
	private String uuid;

	@ApiModelProperty(value = "算法名称")
    private String algorithmName;
	
	@ApiModelProperty(value = "算法描述" )
    private String algorithmDesc;
	
	@ApiModelProperty(value = "算法版本" )
    private String algorithmVersion;
	
	@ApiModelProperty(value = "业务状态：0-待提交 1-待审批  2-已退回  3-已通过 " )
	private String businessStatus;
	
	@ApiModelProperty(value = "审批人" )
	private String personName;
	
	@ApiModelProperty(value = "操作人" )
	private String operator;
	
	@ApiModelProperty(value = "操作时间" )
	private String operateTime;
    
}
