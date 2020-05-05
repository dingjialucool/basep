package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: QuestionWarningVo   
 * @Description: 问题预警总况数据
 * @author: djl  
 * @date:2020年3月16日 下午3:08:34
 */
@ApiModel(description = "问题预警总况数据")
@Data
public class QuestionWarningVo {

    @ApiModelProperty(value = "飞行采查情况")
    private List<QuestionWarningOfFlyVo> flyDataVo;
    
    @ApiModelProperty(value = "问题预警情况")
    private QuestionWarningOfWarnVo warnVo;
    
    @ApiModelProperty(value = "地图上 预警经纬度及类型")
    private List<QuestionWarningOfWarnLocationVo> locationVo;
    
    @ApiModelProperty(value = "分拨处置情况")
    private QuestionWarningOfWranAllocateVo allocateVo;
    
    @ApiModelProperty(value = "问题解决情况")
    private QuestionWarningOfWarnStatusVo warnStatusVo;
    
    @ApiModelProperty(value = "问题预警总况-在飞航班总数，已飞航班总数...")
    private QuestionWarningOfFlightVo flightVo;
    
    @ApiModelProperty(value = "四个区划的位置与名称")
    private List<QuestionWarningOfAreaLocation> areaLocation;
}
