package com.chinobot.plep.home.routedd.entity.vo;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(description = "周期、分页")
@Data
public class PageAndCyclesVo {

	@ApiModelProperty(value = "周期列表")
    private List<CyclesVo> list;
	
	@ApiModelProperty(value = "周期列表总数")
    private long total;
}
