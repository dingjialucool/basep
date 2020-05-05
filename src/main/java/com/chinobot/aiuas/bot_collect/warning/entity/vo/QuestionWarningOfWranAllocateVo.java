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
@ApiModel(description = "问题预警总况-分拨处置情况")
@Data
public class QuestionWarningOfWranAllocateVo {

    @ApiModelProperty(value = "预警分拨总数")
    private long warnCount;
    
    @ApiModelProperty(value = "所涉业务部门")
    private Integer deptCount;
    
    @ApiModelProperty(value = "场景预警次数")
    private List<QuestionWarningOfWarnDeptVo> warnDeptVo;
    
}
