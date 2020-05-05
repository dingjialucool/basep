package com.chinobot.plep.home.meeting.entity.vo;


import java.time.LocalDateTime;

import lombok.Data;

/**
 * 
 * @ClassName: MeetingModelVO   
 * @Description: 视屏会议通知模板
 * @author: djl  
 * @date:2019年12月11日 下午6:59:21
 */

@Data
public class MeetingModelVO {

	private String meetingName;
	
	private LocalDateTime promotTime;
	
	private String pname;
	
	private String proName;
}
