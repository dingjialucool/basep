package com.chinobot.aiuas.bot_event.urgent.entity.vo;


import com.chinobot.aiuas.bot_prospect.flight.entity.vo.WorkFilesVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "紧急调度任务状态")
@Data
public class UrgentWorkStatusVo {

	@ApiModelProperty("紧急调度主键")
	private String urgentId;
	
	@ApiModelProperty("操作类型")
	private String type;

	@ApiModelProperty("航班作业文件")
	private WorkFilesVo workFilesVo;

}
