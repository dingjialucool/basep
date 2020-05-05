package com.chinobot.plep.home.point.entity.vo;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlyPointFileVo {

	@ApiModelProperty(value = "航点序号")
    private Integer sort;

	@ApiModelProperty(value = "航点飞行数据")
    private List<FlyPointVo> flyPointFileList;
	
	@ApiModelProperty(value = "航电飞行数据总数")
    private Long total ;
}
