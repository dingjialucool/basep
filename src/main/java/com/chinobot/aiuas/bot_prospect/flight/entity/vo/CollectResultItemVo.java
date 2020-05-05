package com.chinobot.aiuas.bot_prospect.flight.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "采查结果子项")
@Data
public class CollectResultItemVo {
	
	@ApiModelProperty(value = "键")
	private String rsKey;
	
	@ApiModelProperty(value = "标题")
    private String title;
	
	@ApiModelProperty(value = "值")
    private String val;
	
	@ApiModelProperty(value = "码值类型code")
    private String baseDataCode;

}
