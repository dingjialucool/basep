package com.chinobot.plep.home.point.entity.vo;


import java.util.List;

import com.chinobot.plep.home.point.entity.FixedFlyPoint;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "特定检测点信息")
@Data
public class FixedPointVo {

	@ApiModelProperty(value = "主键")
	private String uuid;
	
	@ApiModelProperty(value = "名称")
	private String name;
	
	@ApiModelProperty(value = "经纬度")
	private String center;
	
	@ApiModelProperty(value = "地址")
	private String address;
	
	@ApiModelProperty(value = "是否需要规划")
	private String need_plan;
	
	@ApiModelProperty(value = "边界")
	private String polygon;
	
	@ApiModelProperty(value = "部门主键")
	private String dept_id;
	
	@ApiModelProperty(value = "航点集合")
	private List<FixedFlyPoint> flyPoints;
}
