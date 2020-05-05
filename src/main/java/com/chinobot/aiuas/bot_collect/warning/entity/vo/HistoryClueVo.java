package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import java.util.List;

import com.chinobot.aiuas.bot_collect.warning.entity.dto.FileMessageDTO;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.CollectResultVo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: HistoryClueVo   
 * @Description: 历史线索详情  
 * @author: djl  
 * @date:2020年3月6日 上午9:52:51
 */
@ApiModel(description = "历史线索详情")
@Data
public class HistoryClueVo {

    @ApiModelProperty(value = "主键")
    private String uuid;
    
    @ApiModelProperty(value = "检查图")
    private String warnImg;
    
    @ApiModelProperty(value = "基准图")
    private String warnBaseImg;
    
    @ApiModelProperty(value = "视频")
    private String warnVio;

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
    
    @ApiModelProperty(value = "飞行经度")
    private String flyLongitude;
    
    @ApiModelProperty(value = "飞行纬度")
    private String flyLatitude;
    
    @ApiModelProperty(value = "预警坐标x")
    private String coordinateX;
    
    @ApiModelProperty(value = "预警坐标y")
    private String coordinateY;
    
    @ApiModelProperty(value = "预警标注w")
    private String indiciaW;
    
    @ApiModelProperty(value = "预警标注h")
    private String indiciaH;
    
    @ApiModelProperty(value = "飞行高度")
    private String flyHeight;
    
    @ApiModelProperty(value = "飞行速度")
    private String flySpeed;
    
    @ApiModelProperty(value = "航线名称")
    private String fligntName;
    
    @ApiModelProperty(value = "准确率")
    private String accuracy;
    
    @ApiModelProperty(value = "事件等级")
    private String eventLevel;
    
    @ApiModelProperty(value = "任务流水号")
    private String hbuuid;
    
    @ApiModelProperty(value = "算法名称")
    private String algorithmName;
    
    @ApiModelProperty(value = "算法版本")
    private String algorithmVersion;
    
    @ApiModelProperty(value = "确认人")
    private String qrhostBy;
    
    @ApiModelProperty(value = "确认时间")
    private String qroperateTime;
    
    @ApiModelProperty(value = "确认意见")
    private String qrhostIdea;
    
    @ApiModelProperty(value = "确认文件集合")
    private List<FileMessageDTO> qrFileList;
    
    @ApiModelProperty(value = "预警类型")
    private String warningType;
    
    @ApiModelProperty(value = "线索数量监测")
    private List<CollectResultVo> resultVo;
    
    @ApiModelProperty(value = "采查对象id")
    private String objectId;
}
