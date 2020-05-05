package com.chinobot.aiuas.bot_collect.info.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2020-02-24 17:44
 */
@ApiModel("采查策略任务关联列表")
@Data
public class FlightTaskListVo {
    @ApiModelProperty("任务id集合")
    private List<String> taskIds;

    @ApiModelProperty("航班外键")
    private List<String> flightIds;

    @ApiModelProperty("采查对象外键")
    private String collectId;
}
