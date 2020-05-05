//package com.chinobot.cityle.ddjk.service.impl;
//
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.time.LocalDate;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.chinobot.cityle.base.entity.Scene;
//import com.chinobot.cityle.base.entity.Uav;
//import com.chinobot.cityle.base.mapper.SceneMapper;
//import com.chinobot.cityle.base.mapper.UavMapper;
//import com.chinobot.cityle.ddjk.entity.DemoData;
//import com.chinobot.cityle.ddjk.entity.TaskEquipmentDispatch;
//import com.chinobot.cityle.ddjk.entity.TaskXc;
//import com.chinobot.cityle.ddjk.mapper.DemoDataMapper;
//import com.chinobot.cityle.ddjk.mapper.TaskEquipmentDispatchMapper;
//import com.chinobot.cityle.ddjk.mapper.TaskXcMapper;
//import com.chinobot.cityle.ddjk.service.IDemoDataService;
//import com.chinobot.cityle.ddjk.service.IEquipmentOnlineInfoService;
//import com.chinobot.common.file.util.FileClient;
//import com.chinobot.common.utils.CommonUtils;
//import com.chinobot.common.utils.HttpUtils;
//import com.chinobot.common.utils.POIUtil;
//import com.chinobot.framework.web.service.impl.BaseService;
//
//@Service
//@SuppressWarnings({ "unchecked", "rawtypes" })
//public class DemoDataService extends BaseService<DemoDataMapper, DemoData> implements IDemoDataService {
//
////	private final static String host = "localhost:9012";
//	private final static String host = "localhost:8001";
//
//	private static boolean isPause = false;
//	private static boolean isReset = false;
//
//	@Autowired
//	private FileClient fileClient;
//	
//	private String uuid;
//
//	private Map<TaskXc, List<String[]>> m = new LinkedHashMap<TaskXc, List<String[]>>();
//
//	@Autowired
//	private UavMapper uavMapper;
//
//	@Autowired
//	private IEquipmentOnlineInfoService equipmentOnlineInfoService;
//
//	@Autowired
//	private DemoDataMapper demoDataMapper;
//
//	@Autowired
//	private TaskXcMapper taskXcMapper;
//
//	@Autowired
//	private SceneMapper sceneMapper;
//
//	@Autowired
//	private TaskEquipmentDispatchMapper taskEquipmentDispatchMapper;
//
//	@Override
//	public void createFileVer(String fileId) {
//		// TODO Auto-generated method stub
//		DemoData dd = new DemoData();
//		dd.setFileId(fileId);
//		dd.setVname("v." + CommonUtils.getDateByFormat("yyMMdd"));
//		demoDataMapper.insert(dd);
//	}
//
//	@Override
//	public List<Map> getFileVer(Map param) {
//		// TODO Auto-generated method stub
//		List<Map> l = demoDataMapper.getDemoDataList(param);
//		for (int i = 0; i < l.size(); i++) {
//			String id = l.get(i).get("uuid").toString();
//			if (id.equals(uuid)) {
//				l.get(i).put("isselect", true);
//			} else {
//				l.get(i).put("isselect", false);
//			}
//		}
//		return l;
//	}
//
//	@Override
//	public void pause(Map param) {
//		// TODO Auto-generated method stub
//		isPause = true;
//	}
//
//	@Override
//	public void reset(Map param) {
//		// TODO Auto-generated method stub
//		Map<String, Object> map = getExcelData(param);
//		String xcTime = CommonUtils.getDateyyyyMMdd();
//		uuid = "";
//		isReset = true;
//		isPause = false;
//		m.clear();
//		for (String str : map.keySet()) {
//			QueryWrapper wrapper = new QueryWrapper();
//			wrapper.eq("sname", str);
//			Scene scene = (Scene) sceneMapper.selectOne(wrapper);
//			wrapper = new QueryWrapper();
//			wrapper.eq("ref_slience", scene.getUuid());
//			wrapper.eq("xc_time", xcTime);
//			TaskXc taskXc = (TaskXc) taskXcMapper.selectOne(wrapper);
//			if (taskXc != null) { // 重置巡查任务数据
//				taskXc.setTaskStatus("1");
//				taskXc.setCompleteTime(null);
//				taskXc.setTaskExplain("");
//				taskXcMapper.updateById(taskXc);
//
//				param.clear();
//				param.put("sceneId", scene.getUuid());
//				param.put("taskId", taskXc.getUuid());
//				param.put("xcTime", xcTime);
//				// 清除巡查路径数据
//				demoDataMapper.deleteRobotFlight(param);
//
//				// 清除预警数据
//				demoDataMapper.deleteRobotGather(param);
//
//				// 清除移动数据
//				param.clear();
//				param.put("robotno", "uav-001");
//				param.put("xcTime", xcTime);
//				demoDataMapper.deleteRobotTrail(param);
//
//				param.put("equipmentId", "9a45cd7302ef004a99ba1a88d1e27c86");
//				param.put("xcTime", xcTime);
//				demoDataMapper.deleteTaskEquipmentDispatch(param);
//
//				// 重置无人机状态
//				wrapper = new QueryWrapper();
//				wrapper.eq("ecode", "uav-001");
//				Uav uav = (Uav) uavMapper.selectOne(wrapper);
//				if (uav != null) {
//					uav.setRunStatus("0");
//					uavMapper.updateById(uav);
//				}
//			}
//		}
//	}
//
//	@SuppressWarnings("resource")
//	private Map<String, Object> getExcelData(Map param) {
//		Map fileMap = demoDataMapper.getFilePath(param);
//		byte[] bytes = fileClient.downloadFile(fileMap.get("path").toString());
//		File file = null;
//		BufferedOutputStream bos = null;
//		FileOutputStream fos = null;
//		try {
//			file = new File(fileMap.get("origin_name").toString());
//			fos = new FileOutputStream(file);
//			bos = new BufferedOutputStream(fos);
//			bos.write(bytes);
//			return POIUtil.readExcel(file);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	@Override
//	public void startDemo(Map param) {
//		// TODO Auto-generated method stub
//		if (isPause) {
//			isPause = false;
//			return;
//		}
//		uuid = param.get("uuid").toString();
//		isReset = false;
//		Map<String, Object> map = getExcelData(param);
//		new TestThread(map).start();
//	}
//
//	public static void test(String taskId, String taskCode, String sceneId, List<String[]> list) {
//		robotStatus(sceneId, 1);
//		taskStatus(taskId, taskCode, sceneId, 3);
//		int i = 0;
//		while (i < list.size()) {
//			try {
//				if (isReset) {
//					return;
//				}
//				if (!isPause) {
//					int lt = 3;
//					if (i == 0) {
//						lt = 1;
//					} else if (i == list.size() - 1) {
//						lt = 2;
//					}
//					String lon = list.get(i)[0];
//					String lat = list.get(i)[1];
//					String alt = list.get(i)[2];
//					String fo = list.get(i)[3];
//					if (sceneId.equals("1c2e1789484c7e7f5017b3def2313abc")) {
//						if (i == 13) {
//							taskWaring(sceneId, taskId, lon, lat);
//						}
//					} else if (sceneId.equals("8eca78868d6e7323b076eaa747e02e16")) {
//						if (i == 10) {
//							taskWaring(sceneId, taskId, lon, lat);
//						}
//					}
//					robotTrail(lon, lat, alt);
//					robotFlight(sceneId, taskId, lon, lat, alt, fo, lt);
//					Thread.sleep(5 * 1000);
//					i += 1;
//				} else {
//					Thread.sleep(1 * 1000);
//				}
//			} catch (Exception e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//		taskStatus(taskId, taskCode, sceneId, 4);
//		robotStatus(sceneId, 0);
//	}
//
//	// 设备开关机状态
//	public static void robotStatus(String sceneId, int status) {
//		String format = "{\r\n" + "    \"uuid\": \"9a45cd7302ef004a99ba1a88d1e27c86\",\r\n"
//				+ "    \"ecode\": \"uav-001\",\r\n" + "    \"runStatus\": \"%d\",\r\n"
//				+ "    \"operateExplain\": \"%s\",\r\n" + "    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n"
//				+ "    \"operateTime\": \"%s\"\r\n" + "}";
//		String exp = "";
//		if (sceneId.equals("1c2e1789484c7e7f5017b3def2313abc")) {
//			exp = "(演示)万豪酒店公寓建筑群";
//		} else if (sceneId.equals("8eca78868d6e7323b076eaa747e02e16")) {
//			exp = "(演示)金科路旁建筑工地";
//		} else if (sceneId.equals("e351339cefdd86e8767994554fd2e83e")) {
//			exp = "(演示)甲岸路旁建筑工地";
//		}
//		if (status == 1) {
//			exp += "巡查任务开机";
//		} else {
//			exp += "巡查任务关机";
//		}
//		try {
//			String json = String.format(format, status, exp, CommonUtils.getDateyyyyMMddHHmmss());
//			HttpUtils.sendPost("http://" + host + "/api/kafka/send/robot_status", json);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	// 设备移动轨迹
//	public static void robotTrail(String lon, String lat, String alt) {
//		String format = "{\r\n" + "    \"robotNo\": \"uav-001\",\r\n" + "    \"trailType\": \"2\",\r\n"
//				+ "    \"longitude\": %s,\r\n" + "    \"latitude\": %s,\r\n" + "    \"altitude\": %s,\r\n"
//				+ "    \"dumpEnergy\": 100,\r\n" + "    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n"
//				+ "    \"operateTime\": \"%s\"\r\n" + "}";
//
//		try {
//			String json = String.format(format, lon, lat, alt, CommonUtils.getDateyyyyMMddHHmmss());
//			HttpUtils.sendPost("http://" + host + "/api/kafka/send/robot_trail", json);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//
//	// 设备采集任务轨迹
//	public static void robotFlight(String sceneId, String taskId, String lon, String lat, String alt, String fo,
//			int lt) {
//		String format = "{\r\n" + "    \"sceneUuid\": \"%s\",\r\n" + "    \"taskUuid\": \"%s\",\r\n"
//				+ "    \"robotNo\": \"uav-001\",\r\n" + "    \"longitude\": %s,\r\n" + "    \"latitude\": %s,\r\n"
//				+ "    \"altitude\": %s,\r\n" + "    \"dumpEnergy\": 45.5,\r\n" + "    \"locationType\": %d,\r\n"
//				+ "    \"flightSpeed\": 5.5,\r\n" + "    \"flyingHeight\": 10,\r\n"
//				+ "    \"flightOrientation\": %s,\r\n" + "    \"cameraAngle\": 36.5,\r\n"
//				+ "    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n" + "    \"operateTime\": \"%s\"\r\n"
//				+ "}";
//
//		try {
//			String json = String.format(format, sceneId, taskId, lon, lat, alt, lt, fo,
//					CommonUtils.getDateyyyyMMddHHmmss());
//			HttpUtils.sendPost("http://" + host + "/api/kafka/send/robot_flight", json);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//
//	// 巡查任务预警
//	public static void taskWaring(String sceneId, String taskId, String lon, String lat) {
//		String format = "{\r\n" + "    \"sceneUuid\": \"%s\",\r\n" + "    \"taskUuid\": \"%s\",\r\n"
//				+ "    \"roundsUuid\": \"%s\",\r\n" + "    \"robotNo\": \"uav-001\",\r\n"
//				+ "    \"mountingEquipmentUuid\":\"75f56e9ec5a440eaf108ba438eb423ec\",\r\n"
//				+ "    \"trailType\":\"2\",\r\n" + "    \"dataFileUuid\": \"%s\",\r\n"
//				+ "    \"textContent\": \"\",\r\n" + "    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n"
//				+ "    \"operateTime\": \"%s\",\r\n" + "    \"isWarning\": \"1\",\r\n"
//				+ "    \"warnContent\": \"%s\",\r\n" + "    \"longitude\": %s,\r\n" + "    \"latitude\": %s\r\n" + "}";
//		String rid = "";
//		String wct = "";
//		String did = "";
//		if (sceneId.equals("1c2e1789484c7e7f5017b3def2313abc")) {
//			rid = "3205b3df2805994ffd289853cd52f0e6";
//			wct = "在建建筑旁及阳台扩建永久性建筑物";
//			did = "ad25200699106f310ab74f50146d591a";
//		} else if (sceneId.equals("8eca78868d6e7323b076eaa747e02e16")) {
//			rid = "28ede0bf8db9c36050c184580915d7e0";
//			wct = "高空作业未佩戴安全帽";
//			did = "08f92bbaea44037c56956cb12e6093d3";
//		} else if (sceneId.equals("e351339cefdd86e8767994554fd2e83e")) {
//			rid = "2a5e6e83ddb06f77b5b2737b4b152674";
//			wct = "高空作业未佩戴安全帽";
//			did = "08f92bbaea44037c56956cb12e6093d3";
//		}
//		try {
//			String json = String.format(format, sceneId, taskId, rid, did, CommonUtils.getDateyyyyMMddHHmmss(), wct,
//					lon, lat);
//			HttpUtils.sendPost("http://" + host + "/api/kafka/send/task_warning", json);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//
//	// 巡查任务完成状态
//	public static void taskStatus(String taskId, String taskCode, String sceneId, int status) {
//		String format = "{\r\n" + "    \"uuid\": \"%s\",\r\n" + "    \"taskCode\": \"%s\",\r\n"
//				+ "    \"refSlience\": \"%s\",\r\n" + "    \"taskStatus\": %d,\r\n" + "    \"taskExplain\":\"%s\",\r\n"
//				+ "    \"operateBy\": \"fee28e28b124f8ce2c288a99f00bd324\",\r\n" + "    \"operateTime\": \"%s\"\r\n"
//				+ "}";
//
//		String exp = "正在执行";
//		if (status == 4) {
//			exp = "已完成";
//		}
//		if (sceneId.equals("1c2e1789484c7e7f5017b3def2313abc")) {
//			exp += "(演示)万豪酒店公寓建筑群巡查任务";
//		} else if (sceneId.equals("8eca78868d6e7323b076eaa747e02e16")) {
//			exp += "(演示)金科路旁建筑工地巡查任务";
//		} else if (sceneId.equals("e351339cefdd86e8767994554fd2e83e")) {
//			exp += "(演示)甲岸路旁建筑工地巡查任务";
//		}
//		try {
//			String json = String.format(format, taskId, taskCode, sceneId, status, exp,
//					CommonUtils.getDateyyyyMMddHHmmss());
//			HttpUtils.sendPost("http://" + host + "/api/kafka/send/task_status", json);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
//
//	class TestThread extends Thread {
//
//		private Map<String, Object> map;
//
//		public TestThread() {
//			// TODO Auto-generated constructor stub
//		}
//
//		public TestThread(Map<String, Object> map) {
//			// TODO Auto-generated constructor stub
//			this.map = map;
//		}
//
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			String xcTime = CommonUtils.getDateyyyyMMdd();
//			for (String str : map.keySet()) {
//				QueryWrapper wrapper = new QueryWrapper();
//				wrapper.eq("sname", str);
//				Scene scene = (Scene) sceneMapper.selectOne(wrapper);
//				wrapper = new QueryWrapper();
//				wrapper.eq("ref_slience", scene.getUuid());
//				wrapper.eq("xc_time", xcTime);
//				TaskXc taskXc = (TaskXc) taskXcMapper.selectOne(wrapper);
//				if (taskXc == null) {
//					// 创建今天的调度任务
//					equipmentOnlineInfoService.addTask(LocalDate.parse(xcTime), str, new String[] { "" });
//					taskXc = (TaskXc) taskXcMapper.selectOne(wrapper);
//					// 调度
//					taskXc.setTaskStatus("2");
//					taskXcMapper.updateById(taskXc);
//				} else {
//					// 调度
//					taskXc.setTaskStatus("2");
//					taskXcMapper.updateById(taskXc);
//				}
//
//				TaskEquipmentDispatch dp = new TaskEquipmentDispatch();
//				dp.setTaskId(taskXc.getUuid());
//				dp.setEquipmentId("9a45cd7302ef004a99ba1a88d1e27c86");
//				dp.setDuration(5.0f);
//				taskEquipmentDispatchMapper.insert(dp);
//
//				m.put(taskXc, (List<String[]>) map.get(str));
//			}
//			for (TaskXc taskXc : m.keySet()) {
//				test(taskXc.getUuid(), taskXc.getTaskCode(), taskXc.getRefSlience(), m.get(taskXc));
//			}
//		}
//	}
//}
