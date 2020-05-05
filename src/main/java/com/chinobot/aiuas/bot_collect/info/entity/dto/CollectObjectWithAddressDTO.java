package com.chinobot.aiuas.bot_collect.info.entity.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "采查基本信息")
@Data
public class CollectObjectWithAddressDTO {

	@ApiModelProperty(value = "主键")
    private String uuid;
	
	@ApiModelProperty(value = "名称")
    private String oName;
	
	@ApiModelProperty(value = "父主键")
    private String parentUuid;
	
	@ApiModelProperty(value = "地址")
    private String oAddress;
	
	@ApiModelProperty(value = "描述")
    private String oDesc;
	
	@ApiModelProperty(value = "位置行政区划")
	private String addressAreaCode;
	
	@ApiModelProperty(value = "领域主键")
    private String domainUuid;
	
	@ApiModelProperty(value = "采查对象级联code")
    private String collectCode;
}
