package com.chinobot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.chinobot.common.utils.CommonUtils;

/**
 *    
 * @author chinobot
 *
 */
public class TestTiegangShuiku {

	private static String host = "localhost";
	private static String sceneUuid = "46de12a826d3f163b19a42b05f5de8ac";
	private static String taskUuid = "46de12a826d3f163b19a42b05f5de8ac";
	private static String taskCode = "T20190531185320080";
	
	public static void main(String[] args) {
		
//		robotStatus(1);
//		taskStatus(3);
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				taskWaring();
//			}
//		}).start();
//		robotTrail();
//		robotFlight();
//		taskStatus(4);
//		robotStatus(0);
		for (int i = 0; i < 10; i++) {
			System.out.println(UUID.randomUUID().toString().replace("-", ""));
		}
		
	}
	
	//设备开关机状态
	public static void robotStatus(int status) {
		String format = "{\r\n" + 
		"    \"uuid\": \"121797f55f36c8dd14f94796dee38e70\",\r\n" + 
		"    \"ecode\": \"Phantom 4 RTK\",\r\n" + 
		"    \"runStatus\": \"%d\",\r\n" + 
		"    \"operateExplain\": \"巡查任务开机\",\r\n" + 
		"    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n" + 
		"    \"operateTime\": \"%s\"\r\n" + 
		"}";

		try {
			String json = String.format(format, status, CommonUtils.getDateyyyyMMddHHmmss());
			System.out.println("设备开机推送"+json);
			HttpPostUrl.sendPost("http://"+host+"/api/kafka/send/robot_status", json);
			Thread.sleep(2 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//设备采集任务轨迹
	public static void robotFlight() {
		List<String[]> l = new ArrayList<String[]>();
		
		l.add(new String[] {"113.953099", "22.699945", "100", "30"});
		l.add(new String[] {"113.952948", "22.699856", "100", "30"});
		l.add(new String[] {"113.952734", "22.699727", "100", "30"});
		l.add(new String[] {"113.952637", "22.699499", "100", "30"});
		l.add(new String[] {"113.95261", "22.699321", "100", "30"});
		l.add(new String[] {"113.952648", "22.699069", "100", "30"});
		l.add(new String[] {"113.952793", "22.698856", "100", "30"});
		l.add(new String[] {"113.953002", "22.698718", "100", "30"});
		l.add(new String[] {"113.953259", "22.698638", "100", "30"});
		l.add(new String[] {"113.953474", "22.698559", "100", "30"});
		l.add(new String[] {"113.953957", "22.698594", "100", "30"});
		l.add(new String[] {"113.954171", "22.698737", "100", "30"});
		l.add(new String[] {"113.954413", "22.698891", "100", "30"});
		l.add(new String[] {"113.954241", "22.699168", "100", "30"});
		l.add(new String[] {"113.954005", "22.69941", "100", "30"});
		l.add(new String[] {"113.953683", "22.699688", "100", "30"});
		l.add(new String[] {"113.953528", "22.699905", "100", "30"});
		l.add(new String[] {"113.953442", "22.69997", "100", "30"});
	
		String format = "{\r\n" + 
				"    \"sceneUuid\": \"%s\",\r\n" + 
				"    \"taskUuid\": \"%s\",\r\n" + 
				"    \"robotNo\": \"Phantom 4 RTK\",\r\n" + 
				"    \"longitude\": %s,\r\n" + 
				"    \"latitude\": %s,\r\n" + 
				"    \"altitude\": %s,\r\n" + 
				"    \"dumpEnergy\": 45.5,\r\n" + 
				"    \"locationType\": %d,\r\n" + 
				"    \"flightSpeed\": 5.5,\r\n" + 
				"    \"flyingHeight\": 10,\r\n" + 
				"    \"flightOrientation\": %s,\r\n" + 
				"    \"cameraAngle\": 36.5,\r\n" + 
				"    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n" + 
				"    \"operateTime\": \"%s\"\r\n" + 
				"}";
		
		for (int i = 0; i < l.size(); i++) {
			try {
				int lt = 3;
				if(i == 0) {
					lt = 1;
				}else if(i == l.size() - 1) {
					lt = 2;
				}
				String[] d = l.get(i);
				String lon = d[0];
				String lat = d[1];
				String alt = d[2];
				String fo = d[3];
				String json = String.format(format, sceneUuid, taskUuid, lon, lat, alt, lt, fo, CommonUtils.getDateyyyyMMddHHmmss());
				System.out.println("设备采集任务轨迹推送"+json);
				HttpPostUrl.sendPost("http://"+host+"/api/kafka/send/robot_flight", json);
				Thread.sleep(5 * 1000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	//设备移动轨迹
	public static void robotTrail() {
		List<String[]> l = new ArrayList<String[]>();
		l.add(new String[] {"113.953099", "22.699945", "0"});
		l.add(new String[] {"113.952948", "22.699856", "0"});
		l.add(new String[] {"113.952734", "22.699727", "0"});
		l.add(new String[] {"113.952637", "22.699499", "0"});
		l.add(new String[] {"113.95261", "22.699321", "0"});
		l.add(new String[] {"113.952648", "22.699069", "0"});
		l.add(new String[] {"113.952793", "22.698856", "0"});
		l.add(new String[] {"113.953002", "22.698718", "0"});
		l.add(new String[] {"113.953259", "22.698638", "0"});
		l.add(new String[] {"113.953474", "22.698559", "0"});
		l.add(new String[] {"113.953957", "22.698594", "0"});
		l.add(new String[] {"113.954171", "22.698737", "0"});
		l.add(new String[] {"113.954413", "22.698891", "0"});
		l.add(new String[] {"113.954241", "22.699168", "0"});
		l.add(new String[] {"113.954005", "22.69941", "0"});
		l.add(new String[] {"113.953683", "22.699688", "0"});
		l.add(new String[] {"113.953528", "22.699905", "0"});
		l.add(new String[] {"113.953442", "22.69997", "0"});
		String format = "{\r\n" + 
				"    \"robotNo\": \"Phantom 4 RTK\",\r\n" + 
				"    \"trailType\": \"2\",\r\n" + 
				"    \"longitude\": %s,\r\n" + 
				"    \"latitude\": %s,\r\n" + 
				"    \"altitude\": %s,\r\n" + 
				"    \"dumpEnergy\": 100,\r\n" + 
				"    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n" + 
				"    \"operateTime\": \"%s\"\r\n" + 
				"}";
		
		for (int i = 0; i < l.size(); i++) {
			try {
				String[] d = l.get(i);
				String lon = d[0];
				String lat = d[1];
				String alt = d[2];
				String json = String.format(format, lon, lat, alt, CommonUtils.getDateyyyyMMddHHmmss());
				System.out.println("设备移动轨迹推送"+json);
				HttpPostUrl.sendPost("http://"+host+"/api/kafka/send/robot_trail", json);
				Thread.sleep(1 * 1000);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	//巡查任务预警
	public static void taskWaring() {
		List<String[]> l = new ArrayList<String[]>();
		l.add(new String[] {"113.953442", "22.69997"});
		String format = "{\r\n" + 
				"    \"sceneUuid\": \"%s\",\r\n" + 
				"    \"taskUuid\": \"%s\",\r\n" + 
				"    \"roundsUuid\": \"c12dae0f56642a9cebac30a6212da5ec\",\r\n" + 
				"    \"robotNo\": \"Phantom 4 RTK\",\r\n" + 
				"    \"mountingEquipmentUuid\":\"75f56e9ec5a440eaf108ba438eb423ec\",\r\n" + 
				"    \"trailType\":\"2\",\r\n" + 
				"    \"dataFileUuid\": \"48f19cff6d886c10b397d6f890fc968d\",\r\n" + 
				"    \"textContent\": \"无\",\r\n" + 
				"    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n" + 
				"    \"operateTime\": \"2019-05-09 15:01:09\",\r\n" + 
				"    \"isWarning\": \"1\",\r\n" + 
				"    \"warnContent\": \"\",\r\n" + 
				"    \"longitude\": %s,\r\n" + 
				"    \"latitude\": %s\r\n" + 
				"}";
		
		for (int i = 0; i < l.size(); i++) {
			try {
				String[] d = l.get(i);
				String lon = d[0];
				String lat = d[1];
				String json = String.format(format, sceneUuid, taskUuid, lon, lat);
				System.out.println("设备任务预警推送"+json);
				HttpPostUrl.sendPost("http://"+host+"/api/kafka/send/task_warning", json);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	//巡查任务完成状态
	public static void taskStatus(int status) {
		String format = "{\r\n" + 
			"    \"uuid\": \"%s\",\r\n" + 
			"    \"taskCode\": \"%s\",\r\n" + 
			"    \"refSlience\": \"%s\",\r\n" + 
			"    \"taskStatus\": %d,\r\n" + 
			"    \"taskExplain\":\"%s\",\r\n" + 
			"    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n" + 
			"    \"operateTime\": \"%s\"\r\n" + 
			"}";
		String exp = "正在执行巡查任务";
		if(status == 4) {
			exp = "已完成巡查任务";
		}
		try {
			String json = String.format(format, taskUuid, taskCode, sceneUuid, status, exp, CommonUtils.getDateyyyyMMddHHmmss());
			System.out.println("巡查任务完成状态推送"+json);
			HttpPostUrl.sendPost("http://"+host+"/api/kafka/send/task_status", json);
			Thread.sleep(5 * 1000);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
	
