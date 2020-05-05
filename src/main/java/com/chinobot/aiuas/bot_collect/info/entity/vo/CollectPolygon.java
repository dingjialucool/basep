package com.chinobot.aiuas.bot_collect.info.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "采查对象经纬度集合(多边形)")
@Data
public class CollectPolygon {

	@ApiModelProperty(value = "采查对象主键")
    private String infoId;
	
	@ApiModelProperty(value = "采查对象名称")
    private String infoName;
	
	@ApiModelProperty(value = "经纬度集合：;分隔")
    private String lnglats;
}
