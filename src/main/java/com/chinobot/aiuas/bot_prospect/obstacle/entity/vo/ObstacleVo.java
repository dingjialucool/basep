package com.chinobot.aiuas.bot_prospect.obstacle.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2020-02-24 11:17
 */
@ApiModel(description = "障碍物")
@Data
public class ObstacleVo {

    @ApiModelProperty("主键")
    private String uuid;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("经纬度")
    private String center;

    @ApiModelProperty("类型id")
    private String parentId;

    @ApiModelProperty("图标")
    private String icon;

    @ApiModelProperty("类型名称")
    private String typeName;

    @ApiModelProperty("高度")
    private String height;

    @ApiModelProperty("海拔")
    private String altitude;

    @ApiModelProperty("地址")
    private String address;
}
