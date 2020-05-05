package com.chinobot.plep.home.event.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.chinobot.aiuas.bot_prospect.warning2info.service.IWarning2InfoService;
import com.chinobot.framework.web.constant.KafkaConstant;
import com.chinobot.framework.web.event.KafkaMsgEvent;
import com.chinobot.plep.home.event.service.IDoKafkaMsgService;

/**
 * 
 * @author huangw
 *kafka消息监听
 */
@Component
public class KafkaDataListener {
	@Autowired
	private IDoKafkaMsgService doKafkaMsgService;
	
	@Autowired
	private IWarning2InfoService warning2InfoService;
	
	@EventListener
	void handelMsg(KafkaMsgEvent kafkaMsgEvent) throws Exception{
		String key = kafkaMsgEvent.getRecord().key();
		String value = kafkaMsgEvent.getRecord().value();
		if("warning".equals(key)) {
			doKafkaMsgService.doEarlyWarnning(value);
		}
		if("robot_flight".equals(key)) {
			doKafkaMsgService.updateDataBase(value);
		}
		if("robot_status".equals(key)) {
			doKafkaMsgService.DoUavStatus(value);
		}
		if("warning_upload".equals(key)) {
			doKafkaMsgService.warningUpload(value);
		}
		if("record_upload".equals(key)) {
			doKafkaMsgService.recordUpload(value);
		}
		if(KafkaConstant.PIPE_PUSH_WARNING_STOCK.equals(key)) {		//存量变换预警
			doKafkaMsgService.pushWarningStock(value);
		}
		if(KafkaConstant.PIPE_PUSH_WARNING_ADDITION.equals(key)) {		//楼顶加建预警
			doKafkaMsgService.pushWarningAddition(value);
		}
		//人员行动轨迹
        if (KafkaConstant.PIPE_PERSON_TRAJECTORY.equals(key)){
        	 doKafkaMsgService.doPersonTrajectory(value);
        }
        
        if(KafkaConstant.PIPE_PUSH_WARNING.equals(key)) {  //接收预警
        	warning2InfoService.receiveWarning(value);
        }
        
        if(KafkaConstant.TASK_CONFIRM.equals(key)) {  //作业任务应答
        	doKafkaMsgService.taskConfirm(value);
        }
        
        
        if(KafkaConstant.NUMBER_POST.equals(key)) {  //数量监测推送
        	doKafkaMsgService.numerPost(value);
        }
	}
}
