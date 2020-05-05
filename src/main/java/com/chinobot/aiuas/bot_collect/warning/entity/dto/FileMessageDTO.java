package com.chinobot.aiuas.bot_collect.warning.entity.dto;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: FileMessageDTO   
 * @Description: 文件信息  
 * @author: djl  
 * @date:2020年3月6日 上午9:12:13
 */
@ApiModel(description = "文件信息")
@Data
public class FileMessageDTO {

	@ApiModelProperty(value = "文件id")
    private String fileId;
	
    @ApiModelProperty(value = "文件名称")
    private String fileName;
    
}
