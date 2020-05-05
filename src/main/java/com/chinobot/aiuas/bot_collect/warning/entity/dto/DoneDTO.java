package com.chinobot.aiuas.bot_collect.warning.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: DoneDTO   
 * @Description: 治理信息
 * @author: djl  
 * @date:2020年2月26日 下午4:57:15
 */
@ApiModel(description = "治理信息")
@Data
public class DoneDTO {

	@ApiModelProperty(value = "预警事件主键")
    private String uuid;
	
	@ApiModelProperty(value = "治理状态（20 表示选择治理中，30表示选择治理完毕）")
    private String status;
	
    @ApiModelProperty(value = "治理人")
    private String hostBy;
    
    @ApiModelProperty(value = "治理信息")
    private String hostIdea;
    
    @ApiModelProperty(value = "治理附件id")
    private String fileId;
    
}
