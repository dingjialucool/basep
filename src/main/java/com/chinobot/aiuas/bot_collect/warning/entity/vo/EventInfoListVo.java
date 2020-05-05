package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: djl
 * @create: 2020-01-25 10:22
 */
@ApiModel(description = "预警信息列表")
@Data
public class EventInfoListVo {

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
    
    @ApiModelProperty(value = "预警类型")
    private String eventType;
    
    @ApiModelProperty(value = "航线名称")
    private String fligntName;
    
    @ApiModelProperty(value = "预警图例图片id，通过文件id获取图片")
    private String fileId;
    
    @ApiModelProperty(value = "无人机")
    private String ename;
    
    @ApiModelProperty(value = "主办机构（防范单位）")
    private String ffDeptId;
    
    @ApiModelProperty(value = "主办机构（救援单位）")
    private String jyDeptId;
    
    @ApiModelProperty(value = "可以显示的列表操作（1.查看 2.确认）")
    private List<String> buttonType;
    
    @ApiModelProperty(value = "采查对象")
    private String objectName;
    
    @ApiModelProperty(value = "流转部门")
    private String deptName;
    
    @ApiModelProperty(value = "线索等级")
    private String eventLevel;
    
    
}
