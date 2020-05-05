package com.chinobot.common.message.entity.vo;



import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "系统全局信息")
@Data
public class MessageVO {

	@ApiModelProperty(value = "消息主键")
	private String uuid;
	
	@ApiModelProperty(value = "消息名称")
	private String msgName;
	
	@ApiModelProperty(value = "消息code")
	private String msgCode;
	
	@ApiModelProperty(value = "打开类型：1-当前页面不覆盖菜单栏  2-弹窗 3-新标签页  4-当前页面覆盖导航栏（铺满）")
	private String openType;
	
	@ApiModelProperty(value = "发送人主键")
	private String sendPid;
	
	@ApiModelProperty(value = "发送人名称")
	private String sendName;
	
	@ApiModelProperty(value = "接收人主键")
	private String receivePid;
	
	@ApiModelProperty(value = "接收人名称")
	private String receiveName;
	
	@ApiModelProperty(value = "链接url")
	private String url;
	
	@ApiModelProperty(value = "消息内容")
	private String content;
	
	@ApiModelProperty(value = "是1否0已读")
	private String isRead;
	
	@ApiModelProperty(value = "发送时间")
	private LocalDateTime sendTime;
}
