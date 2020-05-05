package com.chinobot.aiuas.bot_collect.airport.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "无人机分页输出对象")
@Data
public class UavOfAirportVO {

	@ApiModelProperty(value = "无人机和机场联合主键或者无人机主键")
    private String airportAndUavId;
	
	@ApiModelProperty(value = "无人机名称")
    private String ename;
	
	@ApiModelProperty(value = "序列号")
    private String serialNumber;
	
	@ApiModelProperty(value = "文件id")
    private String fileId;
	
	@ApiModelProperty(value = "类型（1.固定翼、2.多旋翼）")
    private String type;
	
	@ApiModelProperty(value = "型号")
    private String moduleName;
	
	@ApiModelProperty(value = "续航(单位min)")
    private String maxFlyTimeMin;
	
	
}
