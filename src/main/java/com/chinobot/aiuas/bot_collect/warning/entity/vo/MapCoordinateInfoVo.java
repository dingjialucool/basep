package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: 周莉莉
 * @create: 2020-03-24 14:22
 */
@ApiModel(description = "地图坐标数据")
@Data
public class MapCoordinateInfoVo {
	@ApiModelProperty(value = "对象名称")
	private String collectName;
	
	@ApiModelProperty(value = "预警名称")
	private String warningName;
	
	@ApiModelProperty(value = "飞行时间")
	private String flightDate;
	
	@ApiModelProperty(value = "对象地址")
	private String address;

	@ApiModelProperty(value = "预警类型")
	private String eventType;
	
	@ApiModelProperty(value = "中心点经度")
	private double lng;
	
	@ApiModelProperty(value = "中心点纬度")
	private double lat;

	@ApiModelProperty(value = "车次或者人数集合")
	private List<String> rsValueList;
	
	
	@ApiModelProperty(value = "车次或者人数")
	private long rsValue;

//	@ApiModelProperty(value = "经纬度集合")
//	private String lnglats;
	
	@ApiModelProperty(value = "预警经度")
	private double longitude;
	
	@ApiModelProperty(value = "预警纬度")
	private double latitude;
}
