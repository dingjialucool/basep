//package com.chinobot.common.utils;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonIOException;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.google.gson.JsonSyntaxException;
//
//public class JsonRead {
//	 public static void main(String args[]){
//	        JsonParser parse =new JsonParser();  //创建son解析器
//	        try {
//	            JsonObject json=(JsonObject) parse.parse(new FileReader("F://chaoyang_building.json"));  //创建jsonObject对象
//	            System.out.println("resultcode:"+json.get("resultcode").getAsInt());  //将json数据转为为int型的数据
//	            System.out.println("reason:"+json.get("reason").getAsString());     //将json数据转为为String型的数据
//	             
//	            JsonObject result=json.get("result").getAsJsonObject();
//	           
//	            JsonObject today=result.get("today").getAsJsonObject();
//	            System.out.println("temperature:"+today.get("temperature").getAsString());
//	            System.out.println("weather:"+today.get("weather").getAsString());
//	            
//	            JsonArray future=result.get("future").getAsJsonArray();
//	            for(int i=0;i<future.size();i++){
//	                System.out.println("---------------");
//	                JsonObject subObject=future.get(i).getAsJsonObject();
//	                System.out.println("温度 : "+subObject.get("temperature").getAsString());
//	                System.out.println("天气 : "+subObject.get("weather").getAsString());
//	                System.out.println("风向 : "+subObject.get("wind").getAsString());
//	                System.out.println("星期 : "+subObject.get("week").getAsString());
//	                System.out.println("日期 : "+subObject.get("date").getAsString());
//	            }
//	            
//	             
//	        } catch (JsonIOException e) {
//	            e.printStackTrace();
//	        } catch (JsonSyntaxException e) {
//	            e.printStackTrace();
//	        } catch (FileNotFoundException e) {
//	            e.printStackTrace();
//	        }
//	    }
//}
