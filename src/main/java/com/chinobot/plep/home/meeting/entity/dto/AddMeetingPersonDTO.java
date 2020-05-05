package com.chinobot.plep.home.meeting.entity.dto;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AddMeetingPersonDTO {

	@ApiModelProperty(value = "参会人员集合，[{'userId':'', 'openId':''}]，加人必填")
	private List<Map<String, String>> person;
	
	@ApiModelProperty(value = "参会无人机ID集合，加无人机必填")
	private List<String> uavs;
	
	@ApiModelProperty(value = "要跳向的小程序页面，加人必填")
	private String page;
	
	@ApiModelProperty(value = "模板参数填充，加人必填")
	private JSONObject data;
	
	@ApiModelProperty(value = "房间号，必填")
	private BigInteger houseNum;
	
}
