package com.chinobot.aiuas.bot_prospect.flight.entity.vo;

import com.chinobot.aiuas.bot_prospect.flight.entity.Flight;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2020-03-10 14:26
 */
@Data
@ApiModel("航班基准图")
public class FlightReferenceVo {
    @ApiModelProperty("航班")
    private Flight flight;
    @ApiModelProperty("基准图文件id")
    private String referenceFileId;
}
