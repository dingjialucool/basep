package com.chinobot.aiuas.bot_collect.algorithm.entity.vo;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: AlgorithmOfAllVo   
 * @Description: 算法管理查看
 * @author: djl  
 * @date:2020年3月6日 下午5:18:21
 */
@ApiModel(description = "算法管理列表")
@Data
public class AlgorithmOfAllVo {
	
	@ApiModelProperty(value = "算法主键")
	private String uuid;

	@ApiModelProperty(value = "算法名称")
    private String algorithmName;
	
	@ApiModelProperty(value = "描述" )
    private String algorithmDesc;
	
	@ApiModelProperty(value = "版本" )
    private String algorithmVersion;
	
	@ApiModelProperty(value = "采查内容" )
	private List<AlgorithmTaskVo> taskVo;
	
	@ApiModelProperty(value = "设计思路" )
    private String  algorithmicThinking;
	
	@ApiModelProperty(value = "设计人" )
	private String designer;
	
	@ApiModelProperty(value = "设计时间" )
	private String designTime;
	
	@ApiModelProperty(value = "数据标准" )
    private String  numberStandard;
	
	@ApiModelProperty(value = "作业标准" )
    private String  operationStandard;
	
	@ApiModelProperty(value = "审批信息--只有审批完查看时才不为null，编辑回显时不需要这个信息" )
	private ApproveVo approveVo;
	
}
