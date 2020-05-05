package com.chinobot;

import java.util.ArrayList;
import java.util.List;

import com.chinobot.common.utils.CommonUtils;

/**
 * 	金科路旁建筑工地
 * @author chinobot
 *
 */
public class TestLonLat2 {
	
	private static String host = "192.168.0.11";
	private static String sceneUuid = "8eca78868d6e7323b076eaa747e02e16";
	private static String taskUuid = "df04c4c71f83c52eecb81d4c0748b3da";
	private static String taskCode = "T20190428205910361";
	
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
			Thread.sleep(1 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	//设备采集任务轨迹
	public static void robotFlight() {
		List<String[]> l = new ArrayList<String[]>();
		l.add(new String[] {"113.879776","22.545013", "100", "180"});
		l.add(new String[] {"113.879766","22.54453", "100", "180"});
		l.add(new String[] {"113.879723","22.544057", "100", "180"});
		l.add(new String[] {"113.879825","22.543578", "100", "180"});
		l.add(new String[] {"113.879841","22.543094", "100", "180"});
		l.add(new String[] {"113.880125","22.543049", "100", "85"});
		l.add(new String[] {"113.880607","22.543035", "100", "85"});
		l.add(new String[] {"113.881122","22.543059", "100", "85"});
		l.add(new String[] {"113.881694","22.543153", "100", "85"});
		l.add(new String[] {"113.882194","22.543262", "100", "85"});
		l.add(new String[] {"113.882617","22.543436", "100", "85"});
		l.add(new String[] {"113.882515","22.543684", "100", "0"});
		l.add(new String[] {"113.882416","22.544033", "100", "0"});
		l.add(new String[] {"113.882415","22.544411", "100", "0"});
		l.add(new String[] {"113.882417","22.544821", "100", "0"});
		l.add(new String[] {"113.882414","22.545226", "100", "0"});
		l.add(new String[] {"113.882014","22.545226", "100", "270"});
		l.add(new String[] {"113.881514","22.545226", "100", "270"});
		l.add(new String[] {"113.881014","22.545226", "100", "270"});
		l.add(new String[] {"113.880514","22.545226", "100", "270"});
		
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
		l.add(new String[] {"113.878864", "22.546946", "0"});
		l.add(new String[] {"113.879057","22.546653", "0"});
		l.add(new String[] {"113.879074","22.546609", "0"});
		l.add(new String[] {"113.879106","22.546534", "0"});
		l.add(new String[] {"113.87917","22.546401", "0"});
		l.add(new String[] {"113.879235","22.546252", "0"});
		l.add(new String[] {"113.879304","22.546083", "0"});
		l.add(new String[] {"113.879385","22.545935", "0"});
		l.add(new String[] {"113.87946","22.545811", "0"});
		l.add(new String[] {"113.879551","22.545667", "0"});
		l.add(new String[] {"113.879658","22.545429", "0"});
		l.add(new String[] {"113.879819","22.545197", "0"});
		
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
				"    \"roundsUuid\": \"28ede0bf8db9c36050c184580915d7e0\",\r\n" + 
				"    \"robotNo\": \"uav-001\",\r\n" + 
				"    \"mountingEquipmentUuid\":\"75f56e9ec5a440eaf108ba438eb423ec\",\r\n" + 
				"    \"trailType\":\"2\",\r\n" + 
				"    \"dataFileUuid\": \"08f92bbaea44037c56956cb12e6093d3\",\r\n" + 
				"    \"textContent\": \"无\",\r\n" + 
				"    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n" + 
				"    \"operateTime\": \"%s\",\r\n" + 
				"    \"isWarning\": \"1\",\r\n" + 
				"    \"warnContent\": \"发现有人未戴安全帽\",\r\n" + 
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
		String exp = "正在在执行金科路旁建筑工地巡查任务";
		if(status == 4) {
			exp = "已完成金科路旁建筑工地巡查任务";
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
	
