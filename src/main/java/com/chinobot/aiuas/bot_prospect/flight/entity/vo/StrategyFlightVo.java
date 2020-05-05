package com.chinobot.aiuas.bot_prospect.flight.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-02-21 18:04
 */
@ApiModel(description = "采查策略航班")
@Data
public class StrategyFlightVo {

    @ApiModelProperty(value = "航班主键")
    private String uuid;

    @ApiModelProperty(value = "航班名称")
    private String fligntName;

    @ApiModelProperty(value = "航线经纬度集合：;分隔")
    private String routeLnglats;

    @ApiModelProperty(value = "机场id")
    private String airportUuid;

    @ApiModelProperty(value = "机场名称")
    private String airportName;

    @ApiModelProperty(value = "机型id")
    private String uavTypeUuid;

    @ApiModelProperty(value = "机型名称")
    private String uavTypeName;

    @ApiModelProperty(value = "模式")
    private String mode;

    @ApiModelProperty(value = "航线文件名称")
    private String routeFileName;

    @ApiModelProperty(value = "航线文件id")
    private String routeFileUuid;

    @ApiModelProperty(value = "基准图名称")
    private String referenceFileName;

    @ApiModelProperty(value = "基准图id")
    private String referenceFileId;
}
