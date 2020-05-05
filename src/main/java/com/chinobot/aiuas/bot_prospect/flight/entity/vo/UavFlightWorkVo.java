package com.chinobot.aiuas.bot_prospect.flight.entity.vo;


import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author huangw
 *
 */
@ApiModel("无人机作业任务详情")
@Data
public class UavFlightWorkVo {

	@ApiModelProperty("*****航班作业主键")
	private String workId;
	
	@ApiModelProperty("*****作业类型：1-常规  2-紧急调度")
	private String workType;
	
	@ApiModelProperty("*****模式: photo、vedio")
	private String mode;
	
	@ApiModelProperty("*****航线下载路径")
	private String flightDownloadUrl;
	
	@ApiModelProperty("*****采查对象集合")
	private List<UavObjectVo> objects;
	
	@ApiModelProperty("作业日期")
	private String flightDate;
	
	@ApiModelProperty("无人机主键")
	private String uavId;
	
	@ApiModelProperty("无人机名称")
	private String uavName;
	
	@ApiModelProperty("飞行员主键")
	private String personId;
	
	@ApiModelProperty("飞行员名称")
	private String personName;
	
	@ApiModelProperty("操作人主键")
	private String operateBy;
	
	/**
     * 起飞时间
     */
	@ApiModelProperty("起飞时间")
    private String flightTime;

    /**
     * 作业状态(1:待下达 2:已下达 3: 已完成 4：已取消)
     */
	@ApiModelProperty("作业状态(1:待安排 2:待执行 3: 待完成 4：已完成 0：已取消)")
    private String workStatus;

    /**
     * 作业时长 （单位：分钟）
     */
	@ApiModelProperty("作业时长 （单位：分钟）")
    private Float workMinute;

	@ApiModelProperty("航班名称")
	private String flightName;
	
	@ApiModelProperty("航班主键")
	private String flightId;
	
	
	@ApiModelProperty("策略名称")
	private String strategyName;
	
	@ApiModelProperty("策略主键")
	private String strategyId;
	
	@ApiModelProperty("机场名称")
	private String airportName;
	
	@ApiModelProperty("机场主键")
	private String airportId;
	
	@ApiModelProperty(value = "航班文件ID", hidden = true)
	private String routeFileUuid;
	
	@ApiModelProperty("*****基准图下载路径")
	private String baseDownloadUrl;
	
}
