package com.chinobot.plep.home.meeting.entity.vo;

import java.math.BigInteger;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "视频会议信息")
@Data
public class MeetingVO {

	@ApiModelProperty(value = "主键")
	private String uuid;
	
	@ApiModelProperty(value = "会议名称")
	private String meetingName;
	
	@ApiModelProperty(value = "会议描述")
	private String remark;

	@ApiModelProperty(value = "发起人")
	private String pname;
	
	@ApiModelProperty(value = "参与者ID")
	private String userId;

	@ApiModelProperty(value = "房间号")
	private BigInteger houseNum;
	
	@ApiModelProperty(value = "发起时间")
	private LocalDateTime promotTime;
	
	@ApiModelProperty(value = "是否已读")
	private Boolean isRead;
	
	@ApiModelProperty(value = "是否关闭")
	private Boolean isClosed;
	
	@ApiModelProperty(value = "参会人数")
	private Long personNum;
}
