package com.chinobot.aiuas.bot_collect.warning.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: QuestionWarningOfWarnDeptVo   
 * @Description: 问题预警总况数据
 * @author: djl  
 * @date:2020年3月16日 下午3:08:34
 */
@ApiModel(description = "问题预警总况-问题预警情况-部门——分拨数量")
@Data
public class QuestionWarningOfWarnDeptVo {

    @ApiModelProperty(value = "部门名称---返回的是码值（bot_collect_organization），就是机构类型的名称")
    private String deptName;
    
    @ApiModelProperty(value = "分拨数量")
    private Long allocateCount;
    
}
