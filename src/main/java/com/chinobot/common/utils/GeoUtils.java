package com.chinobot.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class GeoUtils {

	public static JSONObject String2Geojson(List<Map<String, Object>> list) {
		JSONObject featureCollection = new JSONObject();
		featureCollection.put("type", "FeatureCollection");
		JSONArray features = new JSONArray();
		for(Map<String, Object> map : list) {
			JSONObject feature = new JSONObject();
			String id = map.get("id").toString();
			String lnglats = (String) map.get("lnglats");
			feature.put("id", id);
			feature.put("type", "Feature");
			JSONObject geometry = new JSONObject();//   MultiPolygon
			geometry.put("type", "MultiPolygon");
			//开始解析边界
			List<List<List<String[]>>> coordinates = new ArrayList<List<List<String[]>>>();
			for(String polygonCoorStr : lnglats.split("#")) {
				String[] pointCoorStr = polygonCoorStr.split(",");
				List<List<String[]>> linesCoor= new  ArrayList<List<String[]>>();
				List<String[]> lineCoor = new ArrayList<String[]>();
				for(int i=0; i<pointCoorStr.length; i+=2) {
					String[] point = {pointCoorStr[i], pointCoorStr[i+1]};
					lineCoor.add(point);
				}
				linesCoor.add(lineCoor);
				coordinates.add(linesCoor);
			}
			//解析边界结束
			geometry.put("coordinates", coordinates);
			feature.put("geometry", geometry);
			JSONObject properties = new JSONObject();
			properties.put("name", map.get("name"));
			properties.put("cp", ((String)map.get("center")).split(","));
			//自定义属性
			if(map.get("properties") != null) {
				properties.putAll((Map<String, Object>) map.get("properties"));
			}
			feature.put("properties", properties);
			features.add(feature);
		}
		featureCollection.put("features", features);
		return featureCollection;
	}
}
