package com.chinobot.aiuas.bot_collect.algorithm.entity.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: AlgorithmSerachDTO   
 * @Description: 算法管理列表查询参数  
 * @author: djl  
 * @date:2020年3月6日 下午5:23:51
 */
@ApiModel(description = "算法管理列表查询参数")
@Data
public class AlgorithmSerachDTO {
	
	@ApiModelProperty(value = "每页条数")
    private long size ;
	
	@ApiModelProperty(value = "当前页")
    private long current ;

	@ApiModelProperty(value = "算法名称")
    private String algorithmName;
	
	@ApiModelProperty(value = "算法描述" )
    private String algorithmDesc;
	
	@ApiModelProperty(value = "审批人id" )
    private String personId;
	
	@ApiModelProperty(value = "操作人id" )
	private String operator;
	
	@ApiModelProperty(value = "操作时间-开始" )
	private String start;
	
	@ApiModelProperty(value = "操作时间-结束" )
	private String end;
	
	@ApiModelProperty(value = "算法状态(0-待提交 1-待审批  2-已退回  3-已通过 )" )
	private String businessStatus;
	
	@ApiModelProperty(value = "算法状态集合" )
	private List businessStatusArr;
}
