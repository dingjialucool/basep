package com.chinobot.aiuas.bot_collect.warning.entity.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: QuestionWarningDto   
 * @Description: 问题预警总况 查询条件 
 * @author: djl  
 * @date:2020年3月16日 下午3:30:21
 */
@ApiModel(description = "问题预警总况 查询条件")
@Data
public class QuestionWarningDTO {

	@ApiModelProperty(value = "预警场景）")
    private List<String> sceneIdList = new ArrayList<String>();
	
	@ApiModelProperty(value = "区划")
    private List<String> areaIdList = new ArrayList<String>();
    
	@ApiModelProperty(value = "部门")
    private List<String> deptIdList = new ArrayList<String>();
	
	@ApiModelProperty(value = "时间")
    private String timeType = "";
	
	@ApiModelProperty(value = "预警类型 1.事件 2.线索")
    private List<String> typeIdList = new ArrayList<String>();
	
	@ApiModelProperty(value = "开始时间----前端不用传")
    private LocalDateTime start;
	
	@ApiModelProperty(value = "结束时间----前端不用传")
    private LocalDateTime end;
}
