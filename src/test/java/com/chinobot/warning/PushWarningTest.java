package com.chinobot.warning;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.alibaba.fastjson.JSONObject;
import com.chinobot.common.utils.Base64Utils;
import com.chinobot.common.utils.HttpPostUrl;
import com.chinobot.warning.entity.LngLat;

public class PushWarningTest {
	
	//定义好任务和算法
	static String[][] sfAndTask = new String[][]{{"05acf7a25f8611eab5bf0242ac110002","ee47ccf515e2478ac6e0a283961a82c9"},
		{"2b8556b75f8611eab5bf0242ac110002","4f3d45fa3eff2c192751f4f6e4d472f9"},
		{"2e362f985f8611eab5bf0242ac110002","5bb9d7d2a74f870d509ec36c5837a75c"},
		{"319ee1615f8611eab5bf0242ac110002","1632b5a3fe78d59af9a13fcbff7584ab"}};
	//定义对象
	static String [] objs = new String[] {
			"e3edea766363b4fd11966b1a4042aa71","0b8f9419e7d0db850f8df2b94a30fd91",
			"40e81e60390d2c2c5da2f848427b0afd","6ba592ecb9e007e5eda8dee4d20f132e",
			"58f86e2a47ca874009c25ecff1235d8d","d27a43bb2a798d9edfcf8e9c00469c8c"
	};
	//定义预警经纬度范围
	static double[][] lnglatRange = new double[][] {
			{114.972166, 114.987068, 22.787589, 22.805573},
			{114.941755,114.949518, 22.826386, 22.832472}
		};
	//预警框位置
	static float positionMin = 5;
	static float positionMax = 30;
	//预警框大小
	static float sizeMin = 10;
	static float sizeMax = 30;
	//准确率
	static float accuracyMin = 60;
	static float accuracyMax = 99;
	//飞行高度
	static float heightMin = 60;
	static float heightMax = 150;
	//飞行速度
	static float speedMin = 2;
	static float speedMax = 15;
	//航班作业主键
	static String work = "e3edea766363b4fd11966b1a4042aa71";
	//预警图片路径
	static String img = "D:\\download\\617d269b6316e062a1971d1f038eb999.jpg";
	//推送地址
	static String url = "http://129.204.84.2:8005/aiuas/api/kafka/send/early_warning";

	public static void main(String[] args) {
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
		obj.put("warningImg", Base64Utils.ImageToBase64ByLocal(img));//预警图片
		try {
			HttpPostUrl.postJson(url, obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//获取一个随机的坐标
	static LngLat getRandomLnglat(){
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
	//对经纬度小数点格式花
	static double fomatLnglat(double d) {
        BigDecimal bd = new BigDecimal(d);
        BigDecimal bd2 = bd.setScale(6, BigDecimal.ROUND_HALF_UP);
        return bd2.doubleValue();
	}
	
	//获取数组随机下标
	static int randomIndex(int start, int end) {
		return (int) Math.round(Math.random()*(end-start)+start);
	}
	
	//获取随机浮点数
	static float randomFLoat(float min, float max) {
		double d = Math.random()*(max-min)+min;
		BigDecimal bd = new BigDecimal(d);
        BigDecimal bd2 = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
		return bd2.floatValue();
	}
}
