package com.chinobot.plep.home.routedd.entity.dto;



import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(description = "周期计划集合")
@Data
public class CycleLeDto {

	
	@ApiModelProperty(value = "周期明细主键")
    private String uuid;

	@ApiModelProperty(value = "时间起")
    private LocalDate timeStart;

	@ApiModelProperty(value = "时间止")
    private LocalDate timeEnd;

	@ApiModelProperty(value = "周期")
    private Integer cycle;
	
	@ApiModelProperty(value = "提前天数")
    private Integer earlyDay;

	@ApiModelProperty(value = "周期单位：1天 2周 3月")
    private String cycleUnit;

	@ApiModelProperty(value = "上一次任务生成的时间")
    private LocalDate last;
	
	@ApiModelProperty(value = "无人机调度id")
	private String uavDspId;

	@ApiModelProperty(value = "飞行日期")
    private LocalDate flyTime;
}
