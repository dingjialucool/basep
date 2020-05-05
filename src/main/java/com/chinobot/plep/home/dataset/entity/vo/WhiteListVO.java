package com.chinobot.plep.home.dataset.entity.vo;

import java.time.LocalDate;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "白名单列表")
@Data
public class WhiteListVO {

	@ApiModelProperty(value = "主键")
    private String uuid;
	
	@ApiModelProperty(value = "截至时间")
    private LocalDate invalidTime;
	
	@ApiModelProperty(value = "事件主键")
    private String eventId;

    @ApiModelProperty(value = "状态")
    private String businessStatus;
    
    @ApiModelProperty(value = "事件名称")
    private String eventName;

    @ApiModelProperty(value = "预警时间")
    private String warningTime;
    
    @ApiModelProperty(value = "预警内容")
    private String warningName;
    
    @ApiModelProperty(value = "预警地址")
    private String address;
    
    @ApiModelProperty(value = "事件等级")
    private String eventLevel;
    
    @ApiModelProperty(value = "飞行员")
    private String pname;
    
    @ApiModelProperty(value = "航线名称")
    private String fligntName;
    
    @ApiModelProperty(value = "预警图例图片id，通过文件id获取图片")
    private String fileId;
}
