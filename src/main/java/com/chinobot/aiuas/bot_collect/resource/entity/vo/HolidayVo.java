package com.chinobot.aiuas.bot_collect.resource.entity.vo;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "节假日信息")
@Data
public class HolidayVo {

	@ApiModelProperty(value = "主键")
	private String uuid;
	
	  /**
     * 时点
     */
	@ApiModelProperty(value = "时点")
    private String holiday;

    /**
     * 时点日期
     */
	@ApiModelProperty(value = "日期")
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private LocalDate holidayDate;
    
    /**
     * 工作日或休假（1-工作日 0-休假）
     */
	@ApiModelProperty(value = "工作日或休假（1-工作日 0-休假）")
    private String workVacation;
	
	@ApiModelProperty(value = "时点名称")
    private String holidayName;
	
	@ApiModelProperty(value = "工作日或休假名称")
    private String workVacationName;
}
