package com.chinobot.aiuas.bot_collect.algorithm.entity.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: AlgorithmDTO   
 * @Description: 算法管理新增
 * @author: djl  
 * @date:2020年3月6日 下午2:08:23
 */
@ApiModel(description = "算法管理编辑/新增")
@Data
public class AlgorithmDTO {

	@ApiModelProperty(value = "算法主键" )
    private String uuid;
	
	@ApiModelProperty(value = "业务状态：0-待提交 1-待审批 , 当选择保存按钮时，业务状态为0，当选择提交按钮时，业务状态为1" )
	private String businessStatus;
	
	@ApiModelProperty(value = "算法名称-->输入框" ,required = true)
    private String algorithmName;
	
	@ApiModelProperty(value = "算法描述-->文本域" )
    private String algorithmDesc;
	
	@ApiModelProperty(value = "算法版本-->输入框" )
    private String algorithmVersion;
	
	@ApiModelProperty(value = "采查任务主键集合-->一个算法对应多个采查任务" )
    private List<String> collectTaskIdList;
	
	@ApiModelProperty(value = "设计思路-->富文本" )
    private String  algorithmicThinking;
	
	@ApiModelProperty(value = "数据标准-->富文本" )
    private String  numberStandard;
	
	@ApiModelProperty(value = "作业标准-->富文本" )
    private String  operationStandard;
    
}
