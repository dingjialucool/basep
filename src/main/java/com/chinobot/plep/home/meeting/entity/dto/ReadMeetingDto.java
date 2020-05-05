package com.chinobot.plep.home.meeting.entity.dto;

import java.math.BigInteger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "视频会议消息已读传参")
@Data
public class ReadMeetingDto {

	@ApiModelProperty(value = "房间号")
	private BigInteger houseNum;
	
}
