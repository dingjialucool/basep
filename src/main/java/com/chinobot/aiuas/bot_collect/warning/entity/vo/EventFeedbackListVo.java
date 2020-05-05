package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: yuanwanggui
 * @create: 2020-01-16 14:22
 */
@ApiModel(description = "事件反馈列表")
@Data
public class EventFeedbackListVo {

    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "业务状态")
    private String businessStatus;

    @ApiModelProperty(value = "是否删除")
    private String isDeleted;
    
    @ApiModelProperty(value = "预警时间")
    private String warningTime;
    
    @ApiModelProperty(value = "预警内容")
    private String warningName;
    
    @ApiModelProperty(value = "预警地址")
    private String address;
    
    @ApiModelProperty(value = "无人机")
    private String ename;
    
    @ApiModelProperty(value = "飞行员")
    private String personUuid;
    
    @ApiModelProperty(value = "航线名称")
    private String fligntName;
    
    @ApiModelProperty(value = "准确率")
    private String accuracy;
    
    @ApiModelProperty(value = "预警图例")
    private String path;
    
    @ApiModelProperty(value = "事件等级")
    private String eventLevel;
}
