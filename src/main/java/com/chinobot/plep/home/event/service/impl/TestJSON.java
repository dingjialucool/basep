package com.chinobot.plep.home.event.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class TestJSON {

	public static void main(String[] args) {

		String json = "{\"JSON_ID\": \"V0.1_01_02_001:_0_1\","
				+ "\"N_Obj\": \"illegal\"}";
		
		String str = "";
		JSONObject jsonObject = JSON.parseObject(json);
		str = jsonObject.getString("JSON_ID");
		str = jsonObject.getString("json_id");
		System.out.println(str);
	}
}
