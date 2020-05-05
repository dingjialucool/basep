package com.chinobot;

import java.util.ArrayList;
import java.util.List;

import com.chinobot.common.utils.CommonUtils;

/**
 * 	万豪酒店公寓建筑群
 * @author chinobot
 *
 */
public class TestLonLat3 {

	private static String host = "localhost/aiuas";
	private static String sceneUuid = "1c2e1789484c7e7f5017b3def2313abc";
	private static String taskUuid = "655d8d91ed45e7e7b480690a290036ac";
	private static String taskCode = "T20190428205910311";
	
	public static void main(String[] args) {
//		robotTrail();
//		robotStatus(1);
//		taskStatus(3);
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//taskWaring();
			}
		}).start();
//		robotFlight();
//		taskStatus(4);
		robotStatus(0);
	}
	
	//设备开关机状态
	public static void robotStatus(int status) {
		String format = "{\r\n" + 
		"    \"uuid\": \"64298feba72c5fd5c68a6dcc8d36feb0\",\r\n" +
		"    \"ecode\": \"64298feba72c5fd5c68a6dcc8d36feb0\",\r\n" +
		"    \"runStatus\": \"%d\",\r\n" + 
		"    \"operateExplain\": \"宝安中心区巡查任务开机\",\r\n" + 
		"    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n" + 
		"    \"operateTime\": \"%s\"\r\n" + 
		"}";

		try {
			String json = String.format(format, status, CommonUtils.getDateyyyyMMddHHmmss());
			System.out.println("设备开机推送"+json);
			HttpPostUrl.sendPost("http://"+host+"/api/kafka/send/robot_status", json);
			Thread.sleep(1 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//设备采集任务轨迹
	public static void robotFlight() {
		List<String[]> l = new ArrayList<String[]>();
		l.add(new String[] {"113.881348","22.546069", "100", "270"});
		l.add(new String[] {"113.880828","22.545989", "100", "270"});
		l.add(new String[] {"113.880447","22.54592", "100", "270"});
		l.add(new String[] {"113.880173","22.545955", "100", "270"});
		l.add(new String[] {"113.880045","22.546123", "100", "0"});
		l.add(new String[] {"113.87998","22.546376", "100", "0"});
		l.add(new String[] {"113.879927","22.546569", "100", "0"});
		l.add(new String[] {"113.87983","22.546886", "100", "0"});
		l.add(new String[] {"113.879878","22.547015", "100", "0"});
		l.add(new String[] {"113.880104","22.547109", "100", "90"});
		l.add(new String[] {"113.880474","22.547277", "100", "90"});
		l.add(new String[] {"113.880865","22.547342", "100", "90"});
		l.add(new String[] {"113.881171","22.547401", "100", "90"});
		l.add(new String[] {"113.881311","22.547391", "100", "90"});
		l.add(new String[] {"113.88153","22.547253", "100", "90"});
		l.add(new String[] {"113.881488","22.546941", "100", "180"});
		l.add(new String[] {"113.881482","22.546827", "100", "180"});
		l.add(new String[] {"113.881488","22.546713", "100", "180"});
		l.add(new String[] {"113.881482","22.546619", "100", "180"});
		l.add(new String[] {"113.88137","22.546202", "100", "180"});
		
		String format = "{\r\n" + 
				"    \"sceneUuid\": \"%s\",\r\n" + 
				"    \"taskUuid\": \"%s\",\r\n" + 
				"    \"uavCode\": \"64298feba72c5fd5c68a6dcc8d36feb0\",\r\n" +
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
		l.add(new String[] {"113.884717","22.549695", "0"});
		l.add(new String[] {"113.884363","22.549755", "0"});
		l.add(new String[] {"113.883966","22.549735", "0"});
		l.add(new String[] {"113.883617","22.549755", "0"});
		l.add(new String[] {"113.883247","22.54976", "0"});
		l.add(new String[] {"113.882947","22.549765", "0"});
		l.add(new String[] {"113.88256","22.549819", "0"});
		l.add(new String[] {"113.882298","22.549804", "0"});
		l.add(new String[] {"113.881783","22.549809", "0"});
		l.add(new String[] {"113.881756","22.549442", "0"});
		l.add(new String[] {"113.88175","22.549329", "0"});
		l.add(new String[] {"113.881745","22.54921", "0"});
		l.add(new String[] {"113.88174","22.549016", "0"});
		l.add(new String[] {"113.88174","22.54864", "0"});
		l.add(new String[] {"113.881718","22.548199", "0"});
		l.add(new String[] {"113.881675","22.547595", "0"});
		l.add(new String[] {"113.881665","22.547163", "0"});
		l.add(new String[] {"113.881659","22.546886", "0"});
		l.add(new String[] {"113.881659","22.546713", "0"});
		
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
		l.add(new String[] { "113.879927", "22.546569" });
		String format = "{\r\n" + 
				"    \"sceneUuid\": \"%s\",\r\n" + 
				"    \"taskUuid\": \"%s\",\r\n" + 
				"    \"roundsUuid\": \"3205b3df2805994ffd289853cd52f0e6\",\r\n" + 
				"    \"robotNo\": \"uav-001\",\r\n" + 
				"    \"mountingEquipmentUuid\":\"75f56e9ec5a440eaf108ba438eb423ec\",\r\n" + 
				"    \"trailType\":\"2\",\r\n" + 
				"    \"dataFileUuid\": \"ad25200699106f310ab74f50146d591a\",\r\n" + 
				"    \"textContent\": \"无\",\r\n" + 
				"    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n" + 
				"    \"operateTime\": \"%s\",\r\n" + 
				"    \"isWarning\": \"1\",\r\n" + 
				"    \"warnContent\": \"发现在建建筑旁及阳台扩建永久性建筑物\",\r\n" + 
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
		String exp = "正在执行万豪酒店公寓建筑群巡查任务";
		if(status == 4) {
			exp = "已完成万豪酒店公寓建筑群巡查任务";
		}
		try {
			String json = String.format(format, taskUuid, taskCode, sceneUuid, status, exp, CommonUtils.getDateyyyyMMddHHmmss());
			System.out.println("巡查任务完成状态推送"+json);
			HttpPostUrl.sendPost("http://"+host+"/api/kafka/send/task_status", json);
			Thread.sleep(1 * 1000);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
	
