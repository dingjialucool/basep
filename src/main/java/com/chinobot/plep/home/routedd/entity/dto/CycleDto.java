package com.chinobot.plep.home.routedd.entity.dto;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel(description = "周期查询")
@Data
public class CycleDto {

	@ApiModelProperty(value = "部门deptId")
    private String deptId;
	
	@ApiModelProperty(value = "计划名称")
	private String planName;
	
	@ApiModelProperty(value = "开启关闭状态")
	private String playStatus;
	
	@ApiModelProperty(value = "开启关闭状态集合")
	private String[] split;
}
