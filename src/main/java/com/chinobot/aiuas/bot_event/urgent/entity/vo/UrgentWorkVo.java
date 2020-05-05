package com.chinobot.aiuas.bot_event.urgent.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "紧急调度任务下达")
@Data
public class UrgentWorkVo {

	@ApiModelProperty("紧急调度主键")
	private String workId;
	
	@ApiModelProperty("作业类型-2:紧急调度")
	private String workType;
	
	@ApiModelProperty("调度名称")
	private String urgentName;
	
	@ApiModelProperty("航线下载路径")
	private String flightDownloadUrl;
	
	@ApiModelProperty("目标坐标")
	private String targetLnglat;
	
	@ApiModelProperty("目标地址")
	private String targetAddress;
	
	@ApiModelProperty("飞行距离")
	private Float flyDistance;
	
	@ApiModelProperty("预计最快耗时")
	private Float speedSecond;
	
	@ApiModelProperty("最快到达时间(秒)")
	private String arrivalTime;
	
	@ApiModelProperty("简要说明")
	private String remark;
	
	@ApiModelProperty("航线文件ID")
	private String routeFileId;
	
	@ApiModelProperty("作业状态-1:已下达 2: 待完成 3：已完成")
	private String workStatus;
	
	@ApiModelProperty("飞行速度")
	private Float flySpeed;
	
	@ApiModelProperty("飞行高度")
	private Float flyHeight;
	
	@ApiModelProperty("无人机名称")
	private String uavName;
	
	@ApiModelProperty("无人机主键")
	private String uavId;
	
	@ApiModelProperty("飞行员名称")
	private String pname;
	
	@ApiModelProperty("飞行员主键")
	private String personId;
	
	@ApiModelProperty("下达时间")
	private String createTime;
	
	@ApiModelProperty("操作人主键")
	private String operateBy;

}
