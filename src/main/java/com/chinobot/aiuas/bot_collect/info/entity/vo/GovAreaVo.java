package com.chinobot.aiuas.bot_collect.info.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "采查对象经纬度集合(多边形)")
@Data
public class GovAreaVo {

	@ApiModelProperty(value = "行政区划主键")
    private String id;
	
	@ApiModelProperty(value = "行政区划名称")
    private String title;
	
	@ApiModelProperty(value = "父id")
    private String parent;
	
	@ApiModelProperty(value = "是否有children(1表示有，0表示无)")
    private boolean hasChild;
	
	@ApiModelProperty(value = "行政区划code")
    private String govCode;
}
