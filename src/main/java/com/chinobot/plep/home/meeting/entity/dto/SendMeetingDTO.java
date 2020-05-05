package com.chinobot.plep.home.meeting.entity.dto;


import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "发起视频会议传参")
@Data
public class SendMeetingDTO {
	
	@ApiModelProperty(value = "参会人员集合，[{'userId':'', 'openId':''}]")
	private List<Map<String, String>> person;
	
	@ApiModelProperty(value = "参会无人机ID集合")
	private List<String> uavs;
	
	@ApiModelProperty(value = "要跳向的小程序页面")
	private String page;
	
	@ApiModelProperty(value = "模板参数填充")
	private JSONObject data;
	
	@ApiModelProperty(value = "房间号")
	private BigInteger houseNum;
	
	@ApiModelProperty(value = "会议名称")
	private String meetingName;
	
	@ApiModelProperty(value = "会议备注")
	private String remark;

	@ApiModelProperty(value = "会议发起时间，yyyy-MM-dd HH:mm:ss")
	private String meetingTime;
	
	//经度
	@ApiModelProperty(value = "经度")
	private Double longitude;
	
	//纬度
	@ApiModelProperty(value = "纬度")
	private Double latitude;
	
	//地址
	@ApiModelProperty(value = "地址")
	private String address;
}
