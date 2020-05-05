package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: yuanwanggui
 * @create: 2020-01-16 10:22
 */
@ApiModel(description = "预警信息列表")
@Data
public class WarningListVo {

    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "业务状态")
    private String businessStatus;

    @ApiModelProperty(value = "预警时间")
    private String warningTime;
    
    @ApiModelProperty(value = "预警内容")
    private String warningName;
    
    @ApiModelProperty(value = "预警地址")
    private String address;
    
    @ApiModelProperty(value = "无人机")
    private String ename;
    
    @ApiModelProperty(value = "飞行员")
    private String pname;
    
    @ApiModelProperty(value = "航线名称")
    private String fligntName;
    
    @ApiModelProperty(value = "准确率")
    private String accuracy;
    
    @ApiModelProperty(value = "采查对象")
    private String objectName;
    
    @ApiModelProperty(value = "预警图例图片id，通过文件id获取图片")
    private String fileId;
    
}
