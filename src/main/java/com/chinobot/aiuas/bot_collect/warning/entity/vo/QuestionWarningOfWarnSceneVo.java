package com.chinobot.aiuas.bot_collect.warning.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: QuestionWarningOfWarnSceneVo   
 * @Description: 问题预警总况数据
 * @author: djl  
 * @date:2020年3月16日 下午3:08:34
 */
@ApiModel(description = "问题预警总况-问题预警情况-场景——预警次数")
@Data
public class QuestionWarningOfWarnSceneVo {

    @ApiModelProperty(value = "场景名称")
    private String sceneName;
    
    @ApiModelProperty(value = "场景下预警次数")
    private Long sceneWarnCount;
    
}
