package com.chinobot.plep.home.meeting.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "视频会议无人机信息")
@Data
public class MeetingUavVO {

	@ApiModelProperty(value = "主键")
	private String uuid;
	
	@ApiModelProperty(value = "无人机名称")
	private String ename;
	
	@ApiModelProperty(value = "无人机编码")
	private String ecode;
	
	@ApiModelProperty(value = "运行状态")
	private String runStatus;
	
	@ApiModelProperty(value = "经度")
	private Double longitude;
	
	@ApiModelProperty(value = "纬度")
	private Double latitude;

	@ApiModelProperty(value = "图片ID")
	private String fileId;
	
	@ApiModelProperty(value = "地址，小程序的 无人机列表才有此属性")
	private String address;
}
