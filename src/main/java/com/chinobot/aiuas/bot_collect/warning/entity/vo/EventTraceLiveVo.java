package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import java.util.List;

import com.chinobot.aiuas.bot_collect.warning.entity.dto.FileMessageDTO;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.CollectResultVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: yuanwanggui
 * @create: 2020-01-16 10:22
 */
@ApiModel(description = "事件跟踪详情列表")
@Data
public class EventTraceLiveVo {

    @ApiModelProperty(value = "预警事件主键")
    private String uuid;
    
    @ApiModelProperty(value = "基准图")
    private String warnBaseImg;
    
    @ApiModelProperty(value = "预警图例")
    private String warnImg;
    
    @ApiModelProperty(value = "预警视频")
    private String warnVio;
    
    @ApiModelProperty(value = "事件名称")
    private String eventName;
    
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
    
    @ApiModelProperty(value = "任务流水号")
    private String hbuuid;
    
    @ApiModelProperty(value = "采查对象id")
    private String objectId;
    
    @ApiModelProperty(value = "航线名称")
    private String fligntName;
    
    @ApiModelProperty(value = "无人机")
    private String ename;
    
    @ApiModelProperty(value = "飞行员id")
    private String personUuid;
    
    @ApiModelProperty(value = "飞行员")
    private String pname;
    
    @ApiModelProperty(value = "对象名称")
    private String oName;
    
    @ApiModelProperty(value = "预警类型")
    private String warningType;
    
    @ApiModelProperty(value = "主办信息集合")
    private List<HostMessageVo> hostMessageVo;
    
    @ApiModelProperty(value = "线索数量监测")
    private List<CollectResultVo> resultVo;
   
}
