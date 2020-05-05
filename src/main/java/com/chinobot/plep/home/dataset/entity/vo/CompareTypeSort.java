package com.chinobot.plep.home.dataset.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "比较类型排序字段")
@Data
public class CompareTypeSort {
	
	@ApiModelProperty(value = "字段代码")
	private String fieldcode;
	
	@ApiModelProperty(value = "字段名称")
	private String fieldname;
	
}
