package com.chinobot.aiuas.bot_collect.info.entity.vo;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "数量监测总况页面历史监控")
@Data
public class HistoryVedioVo {
	@ApiModelProperty(value = "对象UUID")
	private String collectUuid;
	
	@ApiModelProperty(value = "对象名称")
	private String collectName;
	
	@ApiModelProperty(value = "视频上传时间")
	private LocalDateTime createTime;
	
	@ApiModelProperty(value = "文件Id")
	private String fileId;
	
	@ApiModelProperty(value = "作业日期")
	private String flightDate;
}
