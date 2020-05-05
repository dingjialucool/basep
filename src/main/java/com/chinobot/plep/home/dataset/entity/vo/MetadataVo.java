package com.chinobot.plep.home.dataset.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "数据集元数据")
@Data
public class MetadataVo {
	
	@ApiModelProperty(value = "元数据名称")
	private String field;
	
	@ApiModelProperty(value = "元数据类型")
	private Integer type;
	
	@ApiModelProperty(value = "元数据类型名")
	private String fieldType;
}
