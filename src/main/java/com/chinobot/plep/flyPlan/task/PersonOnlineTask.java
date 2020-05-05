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
import com.chinobot.plep.home.monitor.entity.PersonOnline;
import com.chinobot.plep.home.monitor.service.IPersonOnlineService;
import com.chinobot.plep.home.monitor.service.IPersonTrajectoryService;

/**
 * 
 * @ClassName: PersonOnlineTask   
 * @Description: 人员在线监听定时器
 * @author: djl  
 * @date:2019年11月22日 下午6:15:44
 */
@Component
public class PersonOnlineTask extends QuartzJobBean{

	@Autowired
	private IPersonTrajectoryService personTrajectoryService;
	
	@Autowired
	private IPersonOnlineService personOnlineService;
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(PersonOnlineTask.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		log.info("在线监听始"+new Date());
		
		List<PersonOnline> list = personTrajectoryService.getList();
		
		if(list.size()>0) {
			pushMessage(list);
		}
		
	}

	//符合条件的推送消息
	private void pushMessage(List<PersonOnline> personOnlines) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for (PersonOnline personOnline : personOnlines) {
			LocalDateTime now = LocalDateTime.now();//当前时间
			LocalDateTime operateTime = personOnline.getOperateTime();//操作时间
			long minutes = operateTime.until(now, ChronoUnit.MINUTES);//当前时间与操作时间间隔多少秒
			if(now.isAfter(operateTime) && minutes>1) { //当前时间在操作时间之后，且间隔时间大于5分钟
				
				Map map = new HashMap();
				map.put("uuid", personOnline.getUuid());
				map.put("personId", personOnline.getPersonId());
				map.put("onlineStatus", "0");
				map.put("operateTime", sdf.format(new Date()));
				map.put("latitude",personOnline.getLatitude());
				map.put("longitude",personOnline.getLongitude());
				
				//推送消息
				kafkaTemplate.sendDefault("p_person_online", JSON.toJSONString(map));
				
				personOnline.setOnlineStatus("0");
				personOnline.setOperateTime(LocalDateTime.now());
				//更新人员状态
				personOnlineService.saveOrUpdate(personOnline);
				
				
			}
			
		}
		
	}
	
	
	
}
