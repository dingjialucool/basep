package com.chinobot.aiuas.bot_collect.info.entity.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("采查对象采查图片以及采查视频uuid")
@Data
public class InfoCheckDataFileDTO {
	@ApiModelProperty("文件类型")
	private String file_type;
	
	@ApiModelProperty("文件uuid")
	private List<String> fileIdList;
}
