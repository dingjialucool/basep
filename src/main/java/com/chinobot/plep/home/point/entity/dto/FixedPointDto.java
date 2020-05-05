package com.chinobot.plep.home.point.entity.dto;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(description = "需改定点地址")
@Data
public class FixedPointDto {

	
	@ApiModelProperty(value = "定点uuid",required = true)
    private String uuid;

	@ApiModelProperty(value = "定点地址address",required = true)
    private String address;
	
}
