package com.chinobot;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class GeoJsonTest {

	public static void main(String[] args) throws Exception {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		ClassPathResource resource1 = new ClassPathResource("龙岗区.txt");
		InputStream inputStream1 = resource1.getInputStream();
		List<String> readLines1 = IOUtils.readLines(inputStream1);
		Map<String, Object>  map1 = new HashMap<String, Object>();
		map1.put("id","40030011");
		map1.put("lnglats",readLines1.get(0));
		map1.put("name","龙岗区");
		map1.put("center","114.246899,22.720968");
		list.add(map1);
		ClassPathResource resource2 = new ClassPathResource("龙华区.txt");
		InputStream inputStream2 = resource2.getInputStream();
		List<String> readLines2 = IOUtils.readLines(inputStream2);
		Map<String, Object>  map2 = new HashMap<String, Object>();
		map2.put("id","40030012");
		map2.put("lnglats",readLines2.get(0));
		map2.put("name","龙华区");
		map2.put("center","114.026779,22.652548");
		list.add(map2);
		JSONObject jsonObject = String2Geojson(list);
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("D:\\workProject\\city\\workDoc\\testGeo.json"));
		out.write(jsonObject.toJSONString().getBytes());
		//清楚缓存
        out.flush();
        out.close();
	}

	private static JSONObject String2Geojson(List<Map<String, Object>> list) {
		JSONObject featureCollection = new JSONObject();
		featureCollection.put("type", "FeatureCollection");
		JSONArray features = new JSONArray();
		for(Map<String, Object> map : list) {
			JSONObject feature = new JSONObject();
			String id = (String) map.get("id");
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
