package com.chinobot.common.message.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: MessageConfigDTO   
 * @Description: 模板配置列表查询条件
 * @author: djl  
 * @date:2020年3月30日 上午10:57:22
 */
@ApiModel(description = "模板配置列表查询条件")
@Data
public class MessageConfigDTO {

	@ApiModelProperty(value = "每页条数")
    private long size ;
	
	@ApiModelProperty(value = "当前页")
    private long current ;

	@ApiModelProperty(value = "模板名称")
    private String moduleName;
	
	@ApiModelProperty(value = "目标地址" )
    private String moduleCode;
	
}
