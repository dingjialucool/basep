package com.chinobot.aiuas.bot_resource.uav.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-15 17:01
 */
@ApiModel(description = "机型列表")
@Data
public class UavTypeListVo {

    @ApiModelProperty(value = "机型主键")
    private String uuid;

    @ApiModelProperty(value = "型号名称")
    private String moduleName;

    @ApiModelProperty(value = "类型（1.固定翼、2.多旋翼）")
    private String type;

    @ApiModelProperty(value = "厂商名称")
    private String firmName;

    @ApiModelProperty(value = "无人机数量")
    private Integer uavCount;
}
