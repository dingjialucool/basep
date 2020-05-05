package com.chinobot.aiuas.bot_resource.uav.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-16 11:27
 */
@ApiModel(description = "配件信息")
@Data
public class FittingVo {

    @ApiModelProperty(value = "配件信息主键")
    private String uuid;

    @ApiModelProperty(value = "配件信息名称")
    private String fitName;

    @ApiModelProperty(value = "配件数量")
    private Integer fitNumber;
}
