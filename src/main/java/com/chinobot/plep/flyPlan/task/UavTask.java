package com.chinobot.plep.flyPlan.task;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.chinobot.plep.home.monitor.entity.UavOnline;
import com.chinobot.plep.home.monitor.service.IUavOnlineService;

/**
 * 
 * @ClassName: UavTask   
 * @Description: 扫描无人机实时表
 * @author: djl  
 * @date:2019年10月30日 上午11:39:39
 */
@Component
public class UavTask extends QuartzJobBean{

	@Autowired
    private IUavOnlineService uavOnlineService; 
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(UavTask.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		log.info("无人机扫描开始"+new Date());
		
		List<UavOnline> uavOnlines = uavOnlineService.getUavOnlines();
		
		if(uavOnlines.size()>0) {
			pushMessage(uavOnlines);
		}
		
	}

	//符合条件的推送消息
	private void pushMessage(List<UavOnline> uavOnlines) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (UavOnline uavOnline : uavOnlines) {
			LocalDateTime now = LocalDateTime.now();//当前时间
			LocalDateTime operateTime = uavOnline.getOperateTime();//操作时间
			long seconds = operateTime.until(now, ChronoUnit.SECONDS);//当前时间与操作时间间隔多少秒
			if(now.isAfter(operateTime) && seconds>30) { //当前时间在操作时间之后，且间隔时间大于30秒
				
				Map map = new HashMap();
				map.put("ecode", uavOnline.getUavCode());
				map.put("runStatus", "0");
				map.put("operateTime", sdf.format(new Date()));
				//推送消息
				kafkaTemplate.sendDefault("robot_status", JSON.toJSONString(map));
			}
			
		}
		
	}
	
	
	
}
