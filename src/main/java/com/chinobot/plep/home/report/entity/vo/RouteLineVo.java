package com.chinobot.plep.home.report.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinobot.plep.home.report.entity.dto.PointDTO;


@ApiModel(description = "航线详细")
@Data
public class RouteLineVo {
	
    @ApiModelProperty(value = "航线主键")
    private String uuid;

    @ApiModelProperty(value = "航点")
    private List<PointDTO> pointList;
}
