package com.chinobot.aiuas.bot_collect.info.entity.dto;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "采查基本信息+地理信息+标签")
@Data
public class CollectObjectAndGeoWithStringDTO {

	@ApiModelProperty(value = "采查+地址")
	private CollectObjectWithAddressDTO coAddress;
	
	@ApiModelProperty(value = "地址库对象")
	private GeographyDTO geography;
	
	@ApiModelProperty(value = "标签字符串")
	private String tagNames;
	
	@ApiModelProperty(value = "文件id字符串")
	private String fileIds;
	
	@ApiModelProperty(value = "父类多边形")
	private String parentLnglats;
}
