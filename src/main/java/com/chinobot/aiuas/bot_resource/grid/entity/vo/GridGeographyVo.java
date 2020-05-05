package com.chinobot.aiuas.bot_resource.grid.entity.vo;

import com.chinobot.aiuas.bot_collect.info.entity.dto.GeographyDTO;
import com.chinobot.aiuas.bot_resource.grid.entity.ReGrid;
import com.chinobot.cityle.base.entity.Grid;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2020-02-26 11:50
 */
@ApiModel("网格地理信息")
@Data
public class GridGeographyVo {

    @ApiModelProperty("网格")
    private GridVo grid;

    @ApiModelProperty("地理信息")
    private GeographyDTO geography;

}
