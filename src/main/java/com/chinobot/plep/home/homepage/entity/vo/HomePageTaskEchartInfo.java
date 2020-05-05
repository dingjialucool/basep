package com.chinobot.plep.home.homepage.entity.vo;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "首页任务图表信息")
@Data
public class HomePageTaskEchartInfo {

	@ApiModelProperty(value = "地图信息")
	private JSONObject geoJson;
	
	@ApiModelProperty(value = "部门任务信息集合")
	private List<HomePageTaskCountOfDeptAndPointVo> list;
}
