package com.chinobot.plep.home.dataset.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "规则明细")
@Data
public class DetailDto {

	@ApiModelProperty(value = "主键")
    private String uuid;

	@ApiModelProperty(value = "规则外键")
    private String ruleId;

	@ApiModelProperty(value = "关系类型（&& 并且 || 或者）")
    private String relationType;

//	@ApiModelProperty(value = "元数据外键")
//    private String metadataId;

	@ApiModelProperty(value = "数据集主键")
    private String setId;
    
	@ApiModelProperty(value = "字段名")
    private String field;
	
	@ApiModelProperty(value = "比较类型(>大于 , >= 大于等于 等等")
    private String compareType;

	@ApiModelProperty(value = "比较值")
    private String compareValue;

	@ApiModelProperty(value = "组数")
    private Integer groupNum;
	
	@ApiModelProperty(value = "组关系类型(&& 并且， || 或者 )")
    private String groupType;
	
	@ApiModelProperty(value = "组内排序")
    private String innerSort;
}
