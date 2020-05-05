package com.chinobot.common.message.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 
 * @ClassName: MessagesDTO   
 * @Description: 消息列表查询条件  
 * @author: djl  
 * @date:2020年3月30日 上午11:09:04
 */
@Data
public class MessagesDTO {
	
	@ApiModelProperty(value = "每页条数")
    private long size ;
	
	@ApiModelProperty(value = "当前页")
    private long current ;
	
	@ApiModelProperty(value = "模板名称")
	private String moduleName;
	
	@ApiModelProperty(value = "开始时间")
	private String start;

    @ApiModelProperty(value = "结束时间")
    private String end;

    @ApiModelProperty(value = "发送人id")
    private String sendPid;

    @ApiModelProperty(value = "接收人id")
    private String receivePid;

    @ApiModelProperty(value = "是否已读（单选）")
    private String isRead;
}
