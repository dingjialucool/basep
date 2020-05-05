package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: 周莉莉
 * @create: 2020-03-18 14:22
 */
@ApiModel(description = "echar图标数据")
@Data
public class EchartDateVo {
	@ApiModelProperty(value = "对象名称")
	private String collectName;

	@ApiModelProperty(value = "作业时间")
	private String flightDate;

	@ApiModelProperty(value = "车次或者人数集合")
	private List<String> rsValueList;
	
	
	@ApiModelProperty(value = "车次或者人数")
	private long rsValue;

}
