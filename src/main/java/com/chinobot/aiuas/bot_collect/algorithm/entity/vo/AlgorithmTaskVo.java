package com.chinobot.aiuas.bot_collect.algorithm.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName: AlgorithmVo   
 * @Description: 算法管理-采查内容
 * @author: djl  
 * @date:2020年3月6日 下午5:18:21
 */
@ApiModel(description = "算法管理-采查内容")
@Data
public class AlgorithmTaskVo {

	@ApiModelProperty(value = "采查任务id")
    private String taskId;
	
	@ApiModelProperty(value = "采查任务")
    private String taskName;
	
	@ApiModelProperty(value = "图例id" )
    private String taskFileId;
	
	@ApiModelProperty(value = "所属采查类别" )
    private String sceneName;
	
	@ApiModelProperty(value = "所属采查场景" )
	private String domainName;
	
}
