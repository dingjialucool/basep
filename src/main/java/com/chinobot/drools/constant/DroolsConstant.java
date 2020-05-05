package com.chinobot.drools.constant;

import java.util.HashMap;
import java.util.Map;

public interface DroolsConstant {

	String DATE_FOMAT = "yyyy-MM-dd HH:mm:ss";
	
	Map map = new HashMap();
	static Map getWhiteParam() {
		map.put("lnglat", 0.000005);//经纬度误差范围
		map.put("laberXy", 5);//标记框位置误差范围
		map.put("labelWh", 5);//标记框大小误差范围
		return map;
	}
}
