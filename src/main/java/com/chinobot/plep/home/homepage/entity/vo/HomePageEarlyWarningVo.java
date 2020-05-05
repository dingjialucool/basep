package com.chinobot.plep.home.homepage.entity.vo;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页预警图表信息")
@Data
public class HomePageEarlyWarningVo {

	@ApiModelProperty(value = "地图信息")
	private JSONObject geoJson;
	
	@ApiModelProperty(value = "部门预警集合")
	private List<HomepageEarlyWarningDeptVo> list;
}
