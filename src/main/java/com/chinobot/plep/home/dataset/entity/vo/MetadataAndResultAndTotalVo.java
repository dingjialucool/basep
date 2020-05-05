package com.chinobot.plep.home.dataset.entity.vo;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "数据集元数据和预览数据集和数据总条数")
@Data
public class MetadataAndResultAndTotalVo {

	@ApiModelProperty(value = "元数据集合")
	List<MetadataVo> Metadatas;
	
	@ApiModelProperty(value = "预览数据集")
	List<Map> result;
	
	@ApiModelProperty(value = "数据总条数")
	Long totalNum;
}
