package com.chinobot.aiuas.bot_prospect.flight.service;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.chinobot.aiuas.bot_collect.result.entity.Result;
import com.chinobot.aiuas.bot_collect.result.service.IResultService;
import com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant;
import com.chinobot.aiuas.bot_prospect.flight.entity.Flight;
import com.chinobot.aiuas.bot_prospect.flight.entity.FlightWork;
import com.chinobot.aiuas.bot_prospect.flight.entity.LngLat;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.UavObjectVo;
import com.chinobot.aiuas.bot_prospect.flight.mapper.FlightWorkMapper;
import com.chinobot.aiuas.bot_prospect.flight.service.TestWarningService.TestWarn;
import com.chinobot.common.utils.Base64Utils;
import com.chinobot.common.utils.FileUtil;
import com.chinobot.common.utils.GPSUtil;
import com.chinobot.common.utils.HttpPostUrl;
import com.chinobot.common.utils.ZipUtil;
import com.chinobot.framework.web.constant.KafkaConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestWarningService {
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	@Autowired
	private IFlightWorkService flightWorkService;
	
	@Autowired
	private FlightWorkMapper flightWorkMapper;
	@Autowired
	private IFlightService flightService;
	@Autowired
	private IResultService resultService;
	@Value("${config.zipPath}")
	private String zipTemp;
	//定义好任务和算法
	private String[][] sfAndTask = new String[][]{{"05acf7a25f8611eab5bf0242ac110002","ee47ccf515e2478ac6e0a283961a82c9"},
		{"2b8556b75f8611eab5bf0242ac110002","4f3d45fa3eff2c192751f4f6e4d472f9"},
		{"2e362f985f8611eab5bf0242ac110002","5bb9d7d2a74f870d509ec36c5837a75c"},
		{"319ee1615f8611eab5bf0242ac110002","1632b5a3fe78d59af9a13fcbff7584ab"}};
	//定义对象
	private String [] objs = new String[] {
			"e3edea766363b4fd11966b1a4042aa71","0b8f9419e7d0db850f8df2b94a30fd91",
			"40e81e60390d2c2c5da2f848427b0afd","6ba592ecb9e007e5eda8dee4d20f132e",
			"58f86e2a47ca874009c25ecff1235d8d","d27a43bb2a798d9edfcf8e9c00469c8c"
	};
	//定义预警经纬度范围
	private double[][] lnglatRange = new double[][] {
			{114.972166, 114.987068, 22.787589, 22.805573},
			{114.941755,114.949518, 22.826386, 22.832472}
		};
	private double[] lnglatRangePro = new double[] { 114.967456, 115.088002, 22.827778, 22.914689 };
	//预警框位置
	private float positionMin = 5;
	private float positionMax = 30;
	//预警框大小
	private float sizeMin = 10;
	private float sizeMax = 30;
	//准确率
	private float accuracyMin = 60;
	private float accuracyMax = 99;
	//飞行高度
	private float heightMin = 60;
	private float heightMax = 150;
	//飞行速度
	private float speedMin = 2;
	private float speedMax = 15;
	//车辆数
	private int carNumMin = 60;
	private int carNumMax = 360;
	//人数
	private int personMin = 20;
	private int personMax = 150;
	//航班作业主键
//	private String work = "5a348efb1c5d42ed1216cb1c17bdd188";
	//预警图片路径
//	private String img = "D:\\download\\617d269b6316e062a1971d1f038eb999.jpg";
	//推送地址
//	private String url = "http://129.204.84.2:8005/aiuas/api/kafka/send/early_warning";
	public String postCollectData(String workIds, String type, String stage) {
		if(StringUtils.isBlank(workIds) || StringUtils.isBlank(type)) {
			return "参数不能为空！";
		}
		String workIdWrong = "";
		for(String workId : workIds.split(",")) {
			FlightWork flightWork = flightWorkService.getById(workId);
			LocalDate flightDate = flightWork.getFlightDate();
			DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String format = pattern.format(flightDate);
			List<UavObjectVo> list = flightWorkMapper.getObjAndTaskByFight(flightWork.getFlightUuid());
			if(list.size()==0) {
				workIdWrong += workId + ",";
			}
			for(UavObjectVo vo : list) {
				if("1".equals(type)) {
					insertResult(workId, vo.getObjectId(), "startTime", format + " 09:00:00", "time", "开始时间", null);
					insertResult(workId, vo.getObjectId(), "endTime", format + " 10:40:00", "time", "结束时间", null);
					insertResult(workId, vo.getObjectId(), "speedMinute", "100", "float", "持续耗时", null);
					insertResult(workId, vo.getObjectId(), "totalCarNum", randomInt(carNumMin, carNumMax) + "", "int", "车次总数", null);
				}
				if("2".equals(type)) {
					insertResult(workId, vo.getObjectId(), "startTime", format + " 09:00:00", "time", "开始时间", null);
					insertResult(workId, vo.getObjectId(), "endTime", format + " 10:40:00", "time", "结束时间", null);
					insertResult(workId, vo.getObjectId(), "monitorDate", format, "date", "监测日期", null);
					insertResult(workId, vo.getObjectId(), "stage", stage, "string", "阶段", "bot_project_stage");
					insertResult(workId, vo.getObjectId(), "totalPersonNum", randomInt(personMin, personMax) + "", "int", "总人数", null);
				}
			}
		}
		if(!"".equals(workIdWrong)) {
			workIdWrong = workIdWrong.substring(0, workIdWrong.length() - 1);
			return "作业" + workIdWrong + "关系不全,着重检查任务与算法，航班与对象的关系！";
		}

		return "插入模拟结果成功！";
	}
	private void insertResult(String workId, String collectId, String key, String val, String dateType, String remark, String dataCode) {
		Result rs = new Result();
		rs.setWorkId(workId);
		rs.setCollectId(collectId);
		rs.setRsKey(key);
		rs.setRsValue(val);
		rs.setRsType(dateType);
		rs.setRemark(remark);
		rs.setBaseDataCode(dataCode);
		resultService.save(rs);
	}
	public String postWarningsPro(String work, String imgsPath) {
		if(StringUtils.isBlank(work) || StringUtils.isBlank(imgsPath)) {
			return "参数不能为空！";
		}
		File file = new File(imgsPath);
		if(!file.exists()) {
			return "路径不存在！";
		}
		if(!file.isDirectory()) {
			return "路径不是文件夹！";
		}
		File[] files = file.listFiles();
		int num = 0;
		if(files != null) {
			for(File img : files) {
				if(img.isFile() && (img.getName().toUpperCase().contains(".JPG") || img.getName().toUpperCase().contains(".PNG"))) {
					num ++;
					postWarningPro(work, img);
				}
			}
		}
		if(num == 0) {
			return "未发现图片文件！";
		}
		return "成功推送" + num + "条预警！";
	}
	
	public String postWarningsProPro(String work, String imgsPath) {
		if(StringUtils.isBlank(work) || StringUtils.isBlank(imgsPath)) {
			return "参数不能为空！";
		}
		File file = new File(imgsPath);
		if(!file.exists()) {
			return "路径不存在！";
		}
		if(!file.isDirectory()) {
			return "路径不是文件夹！";
		}
		File[] files = file.listFiles();
		int num = 0;
		File[] checkFiles = null;
		if(files != null) {
			for(File zip : files) {
				if(zip.isFile() && zip.getName().toUpperCase().contains(".ZIP") ) {
					Map<String, TestWarn> warnings = new HashMap<String, TestWarn>();
					ZipUtil.deleteDir(zipTemp);//删除临时文件夹下的所有文件
					FileUtil.createDir(zipTemp);
					boolean bo = ZipUtil.decompressZipFile(zip, zipTemp+"/");
					if(bo) {
						try {
							File tmpFile = new File(zipTemp);
							File[] listFiles = tmpFile.listFiles()[0].listFiles();
							for(File dir : listFiles) {
								if("p2".equals(dir.getName())) {//预警图
									checkFiles = dir.listFiles();
									break;
								}
							}
							for(File dir : listFiles) {
								if("res".equals(dir.getName())) {//基准图
									for(File baseImg : dir.listFiles()) {
										for(File warnImg : checkFiles){
											String baseImgName = baseImg.getName();
											String warnImgName = warnImg.getName().substring(0,warnImg.getName().lastIndexOf("."));
											if(baseImgName.contains(warnImgName+"_")) {
												TestWarn testWarn = warnings.get(warnImg.getName());
												if(testWarn == null) {
													testWarn = new TestWarn();
													testWarn.setWarnImg(warnImg);
													testWarn.setBaseImgs(new ArrayList<File>());
													warnings.put(warnImg.getName(), testWarn);
												}
												testWarn.getBaseImgs().add(baseImg);
											}
										}
									}
									break;
								}
							}
						} catch (Exception e) {
							return "压缩包解析错误：" + ExceptionUtils.getStackTrace(e);
						}
					}
					for(String key : warnings.keySet()) {
						TestWarn testWarn = warnings.get(key);
						num += testWarn.getBaseImgs().size();
						postWarningPro(work, testWarn);
					}
				}
			}
		}

		if(num == 0) {
			return "未发现压缩包文件或者压缩包文件内容不正确！";
		}
		ZipUtil.deleteDir(imgsPath);
		FileUtil.createDir(imgsPath);
		return "成功推送" + num + "条预警！";
	}
	
	public String postWarnings(String work, String imgsPath) {
		if(StringUtils.isBlank(work) || StringUtils.isBlank(imgsPath)) {
			return "参数不能为空！";
		}
		File file = new File(imgsPath);
		if(!file.exists()) {
			return "路径不存在！";
		}
		if(!file.isDirectory()) {
			return "路径不是文件夹！";
		}
		File[] files = file.listFiles();
		int num = 0;
		if(files != null) {
			for(File img : files) {
				if(img.isFile() && (img.getName().toUpperCase().contains(".JPG") || img.getName().toUpperCase().contains(".PNG"))) {
					num ++;
					postWarning(work, img);
				}
			}
		}
		if(num == 0) {
			return "未发现图片文件！";
		}
		return "成功推送" + num + "条预警！";
	}
	
	public void postWarning(String work, File img) {
		try {
			JSONObject obj = new JSONObject();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			obj.put("warningTimeStr", dtf.format(LocalDateTime.now()));//预警时间
			LngLat lnglat = getRandomLnglat();
			obj.put("longitudeOrigin", lnglat.getLng());//预警经度
			obj.put("latitudeOrigin", lnglat.getLat());//预警纬度
			int sfIndex = randomIndex(0, sfAndTask.length-1);
			obj.put("algorithmInfoUuid", sfAndTask[sfIndex][0]);//算法主键
			obj.put("taskUuid", sfAndTask[sfIndex][1]);//采查任务主键
			int objIndex = randomIndex(0, objs.length-1);
			obj.put("collectObjectUuid", objs[objIndex]);//采查对象主键
			obj.put("coordinateX", randomFLoat(positionMin, positionMax));//预警框横坐标
			obj.put("coordinateY", randomFLoat(positionMin, positionMax));//预警框纵坐标
			obj.put("indiciaW", randomFLoat(sizeMin, sizeMax));//预警框宽
			obj.put("indiciaH",  randomFLoat(sizeMin, sizeMax));//预警框高
			obj.put("accuracy",  randomFLoat(accuracyMin, accuracyMax));//准确率
			LngLat speedLnglat = getRandomLnglat();
			obj.put("flyLongitude", speedLnglat.getLng());//飞行经度
			obj.put("flyLatitude", speedLnglat.getLat());//飞行纬度
			obj.put("flyHeight", randomFLoat(heightMin, heightMax));//飞行高度
			obj.put("flySpeed", randomFLoat(speedMin, speedMax));//飞行速度
			obj.put("flightTaskUuid", work);//航班作业主键
			obj.put("warningImg", Base64Utils.ImageToBase64ByFile(img));//预警图片
//			HttpPostUrl.postJson(url, obj);
			kafkaTemplate.sendDefault(KafkaConstant.PIPE_PUSH_WARNING, obj.toString());
		} catch (Exception e) {
			log.error("模拟推送预警报错了：{}", e);
		}
	}
	
	public void postWarningPro(String work, File img) {
		try {
			JSONObject obj = new JSONObject();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			FlightWork flightWork = flightWorkService.getById(work);
			LocalDate flightDate = flightWork.getFlightDate();
			LocalTime flightTime = flightWork.getFlightTime();
			LocalDateTime atTime = flightDate.atTime(flightTime);
			obj.put("warningTimeStr", dtf.format(atTime));//预警时间
			List<UavObjectVo> list = flightWorkMapper.getObjAndTaskByFight(flightWork.getFlightUuid());
			if(list.size() > 0) {
				int objIndex = randomIndex(0, list.size()-1);
				obj.put("collectObjectUuid", list.get(objIndex).getObjectId());//采查对象主键
				if(list.get(objIndex).getTasks().size() > 0) {
					int sfIndex = randomIndex(0, list.get(objIndex).getTasks().size()-1);
					obj.put("algorithmInfoUuid", list.get(objIndex).getTasks().get(sfIndex).getAlgorithmId());//算法主键
					obj.put("taskUuid", list.get(objIndex).getTasks().get(sfIndex).getTaskId());//采查任务主键
				}
				LngLat lnglat = getRandomLnglatPro(list.get(objIndex).getLnglats());
				obj.put("longitudeOrigin", lnglat.getLng());//预警经度
				obj.put("latitudeOrigin", lnglat.getLat());//预警纬度
				LngLat speedLnglat = getRandomLnglatPro(list.get(objIndex).getLnglats());
				obj.put("flyLongitude", speedLnglat.getLng());//飞行经度
				obj.put("flyLatitude", speedLnglat.getLat());//飞行纬度
			}
			
			obj.put("coordinateX", randomFLoat(positionMin, positionMax));//预警框横坐标
			obj.put("coordinateY", randomFLoat(positionMin, positionMax));//预警框纵坐标
			obj.put("indiciaW", randomFLoat(sizeMin, sizeMax));//预警框宽
			obj.put("indiciaH",  randomFLoat(sizeMin, sizeMax));//预警框高
			obj.put("accuracy",  randomFLoat(accuracyMin, accuracyMax));//准确率
			obj.put("flyHeight", randomFLoat(heightMin, heightMax));//飞行高度
			obj.put("flySpeed", randomFLoat(speedMin, speedMax));//飞行速度
			obj.put("flightTaskUuid", work);//航班作业主键
			String imgString = Base64Utils.ImageToBase64ByFile(img);
			obj.put("warningImg", imgString);//预警图片
//			Flight flight = flightService.getById(flightWork.getFlightUuid());
//			if(GlobalConstant.PHOTO_MODE.equals(flight.getMode())) {
//				obj.put("baseImg", imgString);//基准图片
//			}
//			System.out.println(obj.toString());
			kafkaTemplate.sendDefault(KafkaConstant.PIPE_PUSH_WARNING, obj.toString());
		} catch (Exception e) {
			log.error("模拟推送预警报错了：{}", e);
		}
	}
	
	public void postWarningPro(String work, TestWarn testWarn) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			FlightWork flightWork = flightWorkService.getById(work);
			LocalDate flightDate = flightWork.getFlightDate();
			LocalTime flightTime = flightWork.getFlightTime();
			LocalDateTime atTime = flightDate.atTime(flightTime);
			String format = dtf.format(atTime);//预警时间
			List<UavObjectVo> list = flightWorkMapper.getObjAndTaskByFight(flightWork.getFlightUuid());
			String collectObjectUuid = null, algorithmInfoUuid= null, taskUuid= null;
			if(list.size() > 0) {
				int objIndex = 0;
			    collectObjectUuid = list.get(objIndex).getObjectId();//采查对象主键
				if(list.get(objIndex).getTasks().size() > 0) {
					int sfIndex = randomIndex(0, list.get(objIndex).getTasks().size()-1);
					algorithmInfoUuid = list.get(objIndex).getTasks().get(sfIndex).getAlgorithmId();//算法主键
					taskUuid = list.get(objIndex).getTasks().get(sfIndex).getTaskId();//采查任务主键
				}
			}
			//读经纬度
			List<Object> gps = GPSUtil.getImgGps(testWarn.getWarnImg());
			for(File baseImg : testWarn.getBaseImgs()) {
				JSONObject obj = new JSONObject();
				obj.put("warningTimeStr",format);
				obj.put("collectObjectUuid",collectObjectUuid);
				obj.put("algorithmInfoUuid",algorithmInfoUuid);
				obj.put("taskUuid",taskUuid);
				obj.put("longitudeOrigin", gps.get(0));//预警经度
				obj.put("latitudeOrigin", gps.get(1));//预警纬度
				obj.put("flyLongitude", gps.get(0));//飞行经度
				obj.put("flyLatitude", gps.get(1));//飞行纬度
				obj.put("flyHeight", gps.get(2));//飞行高度
				obj.put("coordinateX", randomFLoat(positionMin, positionMax));//预警框横坐标
				obj.put("coordinateY", randomFLoat(positionMin, positionMax));//预警框纵坐标
				obj.put("indiciaW", randomFLoat(sizeMin, sizeMax));//预警框宽
				obj.put("indiciaH",  randomFLoat(sizeMin, sizeMax));//预警框高
				obj.put("accuracy",  randomFLoat(accuracyMin, accuracyMax));//准确率
				obj.put("flySpeed", randomFLoat(speedMin, speedMax));//飞行速度
				obj.put("flightTaskUuid", work);//航班作业主键
				String imgString = Base64Utils.ImageToBase64ByFile(testWarn.getWarnImg());
				obj.put("warningImg", imgString);//预警图片
				obj.put("baseImg", Base64Utils.ImageToBase64ByFile(baseImg));//基准图片
				kafkaTemplate.sendDefault(KafkaConstant.PIPE_PUSH_WARNING, obj.toString());
			}
			
		} catch (Exception e) {
			log.error("模拟推送预警报错了：{}", e);
		}
	}
	
	//获取一个随机的坐标
	private LngLat getRandomLnglat(){
		LngLat lngLat = new LngLat();
		int max=lnglatRange.length-1,min=0;
		int index = (int) Math.round(Math.random()*(max-min)+min);
		double[] ds = lnglatRange[index];
		double lng = Math.random()*(ds[1]-ds[0])+ds[0];
		double lat = Math.random()*(ds[3]-ds[2])+ds[2];
		lngLat.setLng(fomatLnglat(lng));
		lngLat.setLat(fomatLnglat(lat));
		return lngLat;
	}
	private LngLat getRandomLnglatPro(String lnglats){
		LngLat lngLat = new LngLat();
		double[] ds = lnglatsMinAndMax(lnglats);;
		double lng = Math.random()*(ds[1]-ds[0])+ds[0];
		double lat = Math.random()*(ds[3]-ds[2])+ds[2];
		double[] gcj02_To_Gps84 = GPSUtil.gcj02_To_Gps84(lat, lng);
		lngLat.setLng(fomatLnglat(gcj02_To_Gps84[1]));
		lngLat.setLat(fomatLnglat(gcj02_To_Gps84[0]));//模拟是WGS84坐标系
		return lngLat;
	}
	
	private double[] lnglatsMinAndMax(String lnglats) {
		double lngMin = 9999999, lngMax = 0, latMin = 99999, latMax = 0;
		if(StringUtils.isNotBlank(lnglats)) {
			String[] split = lnglats.split(";");
			for(String str : split) {
				String[] lnglatStr = str.split(",");
				double lng = Double.parseDouble(lnglatStr[0]);
				double lat = Double.parseDouble(lnglatStr[1]);
				if(lng > lngMax) {
					lngMax = lng;
				}
				if(lng < lngMin) {
					lngMin = lng;
				}
				if(lat > latMax) {
					latMax = lat;
				}
				if(lat < latMin) {
					latMin = lat;
				}
			}
		}
		return new double[] {lngMin, lngMax, latMin, latMax};
	}
	//对经纬度小数点格式花
	private double fomatLnglat(double d) {
        BigDecimal bd = new BigDecimal(d);
        BigDecimal bd2 = bd.setScale(6, BigDecimal.ROUND_HALF_UP);
        return bd2.doubleValue();
	}
	
	//获取数组随机下标
	private int randomIndex(int start, int end) {
		return (int) Math.round(Math.random()*(end-start)+start);
	}
	
	//获取随机浮点数
	private float randomFLoat(float min, float max) {
		double d = Math.random()*(max-min)+min;
		BigDecimal bd = new BigDecimal(d);
        BigDecimal bd2 = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
		return bd2.floatValue();
	}
	
	private int randomInt(int min, int max) {
		double d = Math.random()*(max-min)+min;
		long round = Math.round(d);
		return (int) round;
	}
	
	class TestWarn {
		private File warnImg;
		private List<File> baseImgs;
		public File getWarnImg() {
			return warnImg;
		}
		public void setWarnImg(File warnImg) {
			this.warnImg = warnImg;
		}
		public List<File> getBaseImgs() {
			return baseImgs;
		}
		public void setBaseImgs(List<File> baseImgs) {
			this.baseImgs = baseImgs;
		}
		
	}
}
