package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: yuanwanggui
 * @create: 2020-01-16 14:22
 */
@ApiModel(description = "事件跟踪列表")
@Data
public class EventTraceListVo {

    @ApiModelProperty(value = "主键")
    private String uuid;
    
    @ApiModelProperty(value = "预警主键")
    private String warnId;

    @ApiModelProperty(value = "业务状态")
    private String businessStatus;

    @ApiModelProperty(value = "预警时间")
    private String warningTime;
    
    @ApiModelProperty(value = "事件名称")
    private String eventName;
    
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
    
    @ApiModelProperty(value = "准确率")
    private String accuracy;
    
    @ApiModelProperty(value = "预警图例id")
    private String fileId;
    
    @ApiModelProperty(value = "主办机构（防范单位）")
    private String ffDeptId;
    
    @ApiModelProperty(value = "主办机构（治理单位）")
    private String zlDeptId;
    
    @ApiModelProperty(value = "主办机构（救援单位）")
    private String jyDeptId;
    
    /**
     * 确认（10-待确认）：防范单位
     * 督办（20-待治理）：防范单位
     * 反馈（20-待处理）：治理单位
     * 核查（30-待核查）：防范单位
     * 办结（40-待办结）：防范单位
     */
    @ApiModelProperty(value = "可以显示的列表操作（1.查看 2.督办  4.核查 5.办结 6.确认 7.治理）")
    private List<String> buttonType;
    
    @ApiModelProperty(value = "采查对象")
    private String objectName;
}
