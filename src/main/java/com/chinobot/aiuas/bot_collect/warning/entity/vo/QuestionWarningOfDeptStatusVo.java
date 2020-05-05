package com.chinobot.aiuas.bot_collect.warning.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: QuestionWarningOfDeptStatusVo   
 * @Description: 问题预警总况数据
 * @author: djl  
 * @date:2020年3月16日 下午3:08:34
 */
@ApiModel(description = "问题预警总况-部门预警处理状态")
@Data
public class QuestionWarningOfDeptStatusVo {

    @ApiModelProperty(value = "部门名称---返回的是码值（bot_collect_organization），就是机构类型的名称")
    private String deptName;
    
    @ApiModelProperty(value = "部门-未确认")
    private Long noConfirmDeptCount;
    
    @ApiModelProperty(value = "部门-已安排整改")
    private Long reformedDeptCount;
    
    @ApiModelProperty(value = "部门-不需要整改")
    private Long noReformDeptCount;
    
    @ApiModelProperty(value = "部门-已整改完成")
    private Long reformCompleteDeptCount;
    
    
}
