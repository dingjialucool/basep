package com.chinobot.aiuas.bot_collect.info.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "采查对象")
@Data
public class CollectObjectDTO {

	@ApiModelProperty(value = "主键")
    private String id;
	
	@ApiModelProperty(value = "名称")
    private String title;
	
	@ApiModelProperty(value = "父主键")
    private String parentUUid;
	
	@ApiModelProperty(value = "多边形")
    private String lnglats;
	
	@ApiModelProperty(value = "类型：1是领域(不可删除) 2是采查对象(可删除)")
    private String type;
}
