//package com.chinobot.common.utils;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.kafka.core.KafkaTemplate;
//
//import com.alibaba.fastjson.JSON;
//import com.chinobot.cityle.ddjk.controller.EquipmentOnlineInfoController;
//import com.chinobot.cityle.ddjk.entity.RobotFlight;
//import com.chinobot.framework.web.ApplicationContextRegister;
//import com.chinobot.framework.web.constant.KafkaConstant;
//
//public class KafkaPushThread extends Thread{
//	
//	private String taskUuid;
//	
//    private List<RobotFlight> list;
//    
//	public KafkaPushThread(String taskUuid, List<RobotFlight> list) {
//		// TODO Auto-generated constructor stub
//		this.taskUuid = taskUuid;
//		this.list = list;
//	}
//	
//	@Override
//	public void run() {
//		ApplicationContext context = ApplicationContextRegister.getApplicationContext();
//		KafkaTemplate<String, String> kafkaTemplate = (KafkaTemplate<String, String>) context.getBean("kafkaTemplate");
//		// TODO Auto-generated method stub
//		for (int i = 0; i < list.size(); i++) {
//    		try {
//    			Map<String, Object> history = EquipmentOnlineInfoController.history;
//    			int status = (int) history.get(taskUuid);
//    			if(status == 1) {	//判断是否需要继续推送
//    				kafkaTemplate.sendDefault(KafkaConstant.PIPE_HISTORY_DATA, JSON.toJSONString(list.get(i)));
//    				Thread.sleep(1 * 1000);
//    			}else {
//    				history.remove(taskUuid);
//    			}
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//	}
//}
