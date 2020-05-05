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
@ApiModel(description = "预警信息详情列表")
@Data
public class WarningVo {

    @ApiModelProperty(value = "主键")
    private String uuid;
    
    @ApiModelProperty(value = "预警时间")
    private String warningTime;
    
    @ApiModelProperty(value = "预警内容")
    private String warningName;
    
    @ApiModelProperty(value = "预警地址")
    private String address;
    
    @ApiModelProperty(value = "预警坐标x")
    private String coordinateX;
    
    @ApiModelProperty(value = "预警坐标y")
    private String coordinateY;
    
    @ApiModelProperty(value = "预警标注w")
    private String indiciaW;
    
    @ApiModelProperty(value = "预警标注h")
    private String indiciaH;
    
    @ApiModelProperty(value = "飞行经度")
    private String flyLongitude;
    
    @ApiModelProperty(value = "飞行纬度")
    private String flyLatitude;
    
    @ApiModelProperty(value = "飞行高度")
    private String flyHeight;
    
    @ApiModelProperty(value = "飞行速度")
    private String flySpeed;
    
    @ApiModelProperty(value = "算法名称")
    private String algorithmName;
    
    @ApiModelProperty(value = "算法版本")
    private String algorithmVersion;
    
    @ApiModelProperty(value = "准确率")
    private String accuracy;
    
    @ApiModelProperty(value = "航线名称")
    private String fligntName;
    
    @ApiModelProperty(value = "预警视频id")
    private String warnVio;
    
    @ApiModelProperty(value = "检查图")
    private String warnImg;
    
    @ApiModelProperty(value = "基准图")
    private String warnBasicImg;
    
    @ApiModelProperty(value = "飞行员")
    private String pname;
    
    @ApiModelProperty(value = "无人机")
    private String ename;
    
    @ApiModelProperty(value = "任务流水号")
    private String hbuuid;
    
    @ApiModelProperty(value = "失败原因")
    private String failReason;
    
    @ApiModelProperty(value = "预警状态")
    private String status;
    
    @ApiModelProperty(value = "过滤原因")
    private String ruleName;
    
    @ApiModelProperty(value = "任务Id")
    private String taskId;
    
    @ApiModelProperty(value = "防范单位主办部门")
    private String hostUnit;
    
    @ApiModelProperty(value = "防范单位主办人")
    private String hostBy;
    
    @ApiModelProperty(value = "治理单位主办部门")
    private String hostUnitGovern;
    
    @ApiModelProperty(value = "治理单位主办人")
    private String hostByGovern;
    
    @ApiModelProperty(value = "救援单位主办部门")
    private String hostUnitRescue;
    
    @ApiModelProperty(value = "救援单位主办人")
    private String hostByRescue;
    
    @ApiModelProperty(value = "防范单位")
    private String ffDept;
    
    @ApiModelProperty(value = "治理单位")
    private String zlDept;
    
    @ApiModelProperty(value = "救援单位")
    private String jyDept;
}
