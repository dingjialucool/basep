package com.chinobot.aiuas.bot_collect.warning.entity.vo;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: QuestionWarningOfWarnVo   
 * @Description: 问题预警总况数据
 * @author: djl  
 * @date:2020年3月16日 下午3:08:34
 */
@ApiModel(description = "问题预警总况-问题预警情况")
@Data
public class QuestionWarningOfWarnVo {

    @ApiModelProperty(value = "全区预警次数")
    private long warnCount;
    
    @ApiModelProperty(value = "预警场景数")
    private Integer sceneCount;
    
    @ApiModelProperty(value = "场景预警次数")
    private List<QuestionWarningOfWarnSceneVo> warnSceneVo;
    
}
