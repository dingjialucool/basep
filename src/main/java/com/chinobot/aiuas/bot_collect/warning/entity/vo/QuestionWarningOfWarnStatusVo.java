package com.chinobot.aiuas.bot_collect.warning.entity.vo;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: QuestionWarningOfWarnStatusVo   
 * @Description: 问题预警总况数据
 * @author: djl  
 * @date:2020年3月16日 下午3:08:34
 */
@ApiModel(description = "问题预警总况-问题解决情况")
@Data
public class QuestionWarningOfWarnStatusVo {

    @ApiModelProperty(value = "已确认占比率")
    private Long confirmRate;
    
    @ApiModelProperty(value = "未确认")
    private Long noConfirmCount;
    
    @ApiModelProperty(value = "已安排整改")
    private Long reformedCount;
    
    @ApiModelProperty(value = "不需要整改")
    private Long noReformCount;
    
    @ApiModelProperty(value = "已整改完成")
    private Long reformCompleteCount;
    
    @ApiModelProperty(value = "部门预警处理情况")
    private List<QuestionWarningOfDeptStatusVo> deptStatusVo;
}
