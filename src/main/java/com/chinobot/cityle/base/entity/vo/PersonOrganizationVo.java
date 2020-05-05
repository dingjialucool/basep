package com.chinobot.cityle.base.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-14 19:54
 */
@ApiModel(description = "防治救单位负责人")
@Data
public class PersonOrganizationVo {

    @ApiModelProperty(value = "部门主键")
    private String deptUuid;

    @ApiModelProperty(value = "人员主键")
    private String key;

    @ApiModelProperty(value = "名称")
    private String title;

    @ApiModelProperty(value = "是否是分组名")
    private Boolean isLabel;
}
