package com.chinobot.plep.home.meeting.entity.vo;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "视频会议详细信息")
@Data
public class MeetingDetailVO {

	@ApiModelProperty(value = "主键")
	private String uuid;
	
	@ApiModelProperty(value = "会议名称")
	private String meetingName;
	
	@ApiModelProperty(value = "会议描述")
	private String remark;

	@ApiModelProperty(value = "发起人")
	private String pname;
	
	@ApiModelProperty(value = "发起人主键uuid")
	private String puuid;
	
	@ApiModelProperty(value = "房间号")
	private BigInteger houseNum;
	
	@ApiModelProperty(value = "发起时间")
	private LocalDateTime promotTime;
	
	@ApiModelProperty(value = "参会人员集合")
	private List<PersonVo> person;
	
	@ApiModelProperty(value = "无人机集合")
	private List<MeetingUavVO> uavs;
	
	/**
	 * 地址
	 */
	@ApiModelProperty(value = "地址")
	private String address;
	
	/**
	 * 纬度
	 */
	@ApiModelProperty(value = "纬度")
	private Double meetingLatitude;
	
	/**
	 * 经度
	 */
	@ApiModelProperty(value = "经度")
	private Double meetingLongitude;
}
