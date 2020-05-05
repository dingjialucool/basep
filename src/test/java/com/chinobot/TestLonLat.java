package com.chinobot;

import java.util.ArrayList;
import java.util.List;

import com.chinobot.common.utils.CommonUtils;

/**
 *     甲岸路旁建筑工地
 * @author chinobot
 *
 */
public class TestLonLat {

	private static String host = "192.168.0.11";
	private static String sceneUuid = "e351339cefdd86e8767994554fd2e83e";
	private static String taskUuid = "547e9f58da746c6a379140c1b745fd3f";
	private static String taskCode = "T20190428205910127";
	
	public static void main(String[] args) {
//		robotTrail();
//		robotStatus(1);
//		taskStatus(3);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
//				taskWaring();
			}
		}).start();
		//robotFlight();
//		taskStatus(4);
//		robotStatus(0);
	}
	
	//设备开关机状态
	public static void robotStatus(int status) {
		String format = "{\r\n" + 
		"    \"uuid\": \"9a45cd7302ef004a99ba1a88d1e27c86\",\r\n" + 
		"    \"ecode\": \"uav-001\",\r\n" + 
		"    \"runStatus\": \"%d\",\r\n" + 
		"    \"operateExplain\": \"宝安中心区巡查任务开机\",\r\n" + 
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
		l.add(new String[] {"113.889418", "22.544198", "100", "30"});
		l.add(new String[] {"113.889482", "22.544792", "100", "30"});
		l.add(new String[] {"113.889568", "22.545129", "100", "30"});
		l.add(new String[] {"113.889707", "22.545367", "100", "30"});
		l.add(new String[] {"113.889954", "22.545783", "100", "30"});
		l.add(new String[] {"113.890319", "22.546259", "100", "30"});
		l.add(new String[] {"113.890512", "22.546625", "100", "30"});
		l.add(new String[] {"113.890812", "22.547032", "100", "30"});
		l.add(new String[] {"113.891209", "22.547616", "100", "30"});
		l.add(new String[] {"113.891638", "22.548131", "100", "30"});
		l.add(new String[] {"113.892153", "22.548627", "100", "30"});
		l.add(new String[] {"113.892078", "22.549073", "100", "300"});
		l.add(new String[] {"113.891617", "22.549608", "100", "300"});
		l.add(new String[] {"113.891231", "22.549945", "100", "300"});
		l.add(new String[] {"113.890791", "22.550282", "100", "300"});
		
		String format = "{\r\n" + 
				"    \"sceneUuid\": \"%s\",\r\n" + 
				"    \"taskUuid\": \"%s\",\r\n" + 
				"    \"robotNo\": \"uav-001\",\r\n" + 
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
		l.add(new String[] {"113.885079", "22.549537", "0"});
		l.add(new String[] {"113.885122", "22.547813", "0"});
		l.add(new String[] {"113.88525", "22.546723", "0"});
		l.add(new String[] {"113.885594", "22.545554", "0"});
		l.add(new String[] {"113.886666", "22.544781", "0"});
		l.add(new String[] {"113.887718", "22.544464", "0"});
		l.add(new String[] {"113.888276", "22.544424", "0"});
		l.add(new String[] {"113.88937", "22.544167", "0"});
		
		String format = "{\r\n" + 
				"    \"robotNo\": \"uav-001\",\r\n" + 
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
		l.add(new String[] { "113.93247750000033", "22.529393499999387" });
		String format = "{\r\n" + 
				"    \"sceneUuid\": \"%s\",\r\n" + 
				"    \"taskUuid\": \"%s\",\r\n" + 
				"    \"roundsUuid\": \"defa72033535f19286ce15b85203d5d4\",\r\n" + 
				"    \"robotNo\": \"uav-001\",\r\n" + 
				"    \"mountingEquipmentUuid\":\"75f56e9ec5a440eaf108ba438eb423ec\",\r\n" + 
				"    \"trailType\":\"2\",\r\n" + 
				"    \"dataFileUuid\": \"08f92bbaea44037c56956cb12e6093d3\",\r\n" + 
				"    \"textContent\": \"无\",\r\n" + 
				"    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n" + 
				"    \"operateTime\": \"%s\",\r\n" + 
				"    \"isWarning\": \"1\",\r\n" + 
				"    \"warnContent\": \"发现高空作业未佩戴安全帽\",\r\n" + 
				"    \"longitude\": %s,\r\n" + 
				"    \"latitude\": %s\r\n" + 
				"}";
		
		for (int i = 0; i < l.size(); i++) {
			try {
				String[] d = l.get(i);
				String lon = d[0];
				String lat = d[1];
				String json = String.format(format, sceneUuid, taskUuid, CommonUtils.getDateyyyyMMddHHmmss(), lon, lat);
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
		String exp = "正在执行甲岸路旁建筑工地巡查任务";
		if(status == 4) {
			exp = "已完成甲岸路旁建筑工地巡查任务";
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
	
