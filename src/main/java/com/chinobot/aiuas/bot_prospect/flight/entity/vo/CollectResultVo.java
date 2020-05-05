package com.chinobot.aiuas.bot_prospect.flight.entity.vo;

import java.util.List;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "采查结果")
@Data
public class CollectResultVo {

	@ApiModelProperty(value = "作业主键")
    private String workId;
	
	@ApiModelProperty(value = "对象主键")
    private String collectId;
	
	@ApiModelProperty(value = "对象名称")
    private String collectName;
	
	@ApiModelProperty(value = "开始时间")
    private String startTime;
	
	@ApiModelProperty(value = "结束时间")
    private String endTime;
	
	@ApiModelProperty("结果子项集合")
	private List<CollectResultItemVo> list;
}
