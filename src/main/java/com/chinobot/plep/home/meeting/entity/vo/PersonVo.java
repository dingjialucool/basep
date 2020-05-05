package com.chinobot.plep.home.meeting.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "视频会议人员信息")
@Data
public class PersonVo {
	
	@ApiModelProperty(value = "用户id")
	private String uuid;

	@ApiModelProperty(value = "姓名")
	private String pname;
	
	@ApiModelProperty(value = "微信OPENID")
	private String openId;
	
	@ApiModelProperty(value = "头像文件id")
	private String fileId;
	
	@ApiModelProperty(value = "职务")
	private String duties;
	
}
