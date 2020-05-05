package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: djl
 * @create: 2020-01-25 10:22
 */
@ApiModel(description = "预警事件和线索列表")
@Data
public class EventInfoTypeListVo {

    @ApiModelProperty(value = "主键")
    private String uuid;
    
    @ApiModelProperty(value = "预警消息主键")
    private String warnId;

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
    
    @ApiModelProperty(value = "预警类型1-事件 2-线索")
    private String eventType;
    
    @ApiModelProperty(value = "无人机")
    private String ename;
    
    @ApiModelProperty(value = "采查对象")
    private String objectName;
    
}
