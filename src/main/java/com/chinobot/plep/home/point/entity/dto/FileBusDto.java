package com.chinobot.plep.home.point.entity.dto;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(description = "上传图片")
@Data
public class FileBusDto {

	
	@ApiModelProperty(value = "定点uuid",required = true)
    private String busId;

	@ApiModelProperty(value = "上传的文件uuid",required = true)
    private String fileId;
	
	@ApiModelProperty(value = "模型名称--这里默认是 'point_album' ",required = true)
    private String module;
}
