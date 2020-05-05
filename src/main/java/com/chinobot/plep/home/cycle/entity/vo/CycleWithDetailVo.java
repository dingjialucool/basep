package com.chinobot.plep.home.cycle.entity.vo;


import java.util.List;

import com.chinobot.plep.home.routedd.entity.Cycle;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@ApiModel(description = "周期和周期明细信息")
@Data
@EqualsAndHashCode(callSuper = true)
public class CycleWithDetailVo extends Cycle {
	@ApiModelProperty(value = "路线主键集合", required = true)
	private List<String> list;
}
