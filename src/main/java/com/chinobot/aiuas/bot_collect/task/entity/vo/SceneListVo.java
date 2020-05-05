package com.chinobot.aiuas.bot_collect.task.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-09 14:22
 */
@ApiModel(description = "采查场景列表")
@Data
public class SceneListVo {

    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "名称")
    private String sName;

    @ApiModelProperty(value = "操作时间")
    private String operateTime;

    @ApiModelProperty(value = "操作人")
    private String pName;

    @ApiModelProperty(value = "部门名称")
    private String dName;

}
