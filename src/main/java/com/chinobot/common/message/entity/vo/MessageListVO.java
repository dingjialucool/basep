package com.chinobot.common.message.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "信息列表")
@Data
public class MessageListVO {

	@ApiModelProperty(value = "消息主键")
	private String uuid;
	
	@ApiModelProperty(value = "模板名称")
	private String moduleName;
	
	@ApiModelProperty(value = "发送人名称")
	private String sendName;
	
	@ApiModelProperty(value = "接收人名称")
	private String receiveName;
	
	@ApiModelProperty(value = "消息内容")
	private String content;
	
	@ApiModelProperty(value = "链接url")
	private String url;

	@ApiModelProperty(value = "发送时间")
	private String sendTime;
	
	@ApiModelProperty(value = "是1否0已读")
	private String isRead;
	
	
}
