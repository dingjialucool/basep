//package com.chinobot.common.utils;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.util.ArrayList;
//import java.util.List;
//
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonIOException;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.google.gson.JsonSyntaxException;
//
////{
////    "cat":"it",
////    "language":[
////        {"id":1,"ide":"eclipse","name":Java},
////        {"id":2,"ide":"XCode","name":"Swift"},
////        {"id":3,"ide":"Visual Stdio","name":"C#"}     
////    ],
////    "pop":true
////}
//public class Read {
//	 public static void main(String args[]){
//	        try {
//	             
//	            JsonParser parser=new JsonParser();  //创建JSON解析器
////	            JsonObject object=(JsonObject) parser.parse(new FileReader("F://chaoyang_building.json"));  //创建JsonObject对象
////	            System.out.println("cat="+object.get("cat").getAsString()); //将json数据转为为String型的数据
////	            System.out.println("pop="+object.get("pop").getAsBoolean()); //将json数据转为为boolean型的数据
//	             
////	            JsonArray array=object.get("features").getAsJsonArray();    //得到为json的数组
////	            List<Object> arrList = new ArrayList<Object>();
////	            for(int i=0;i<array.size();i++){
////	                JsonObject subObject=array.get(i).getAsJsonObject();
////	                JsonObject pro = subObject.get("properties").getAsJsonObject();
////	                if(!CommonUtils.isObjEmpty(pro.get("district"))) {
////	                	if( pro.get("district").getAsString().equals("团结湖街道")) {
////	                		arrList.add(subObject);
////			                System.out.println(pro.get("district").getAsString());
////	                	}
////	                	
////	                }
////	            }
////	            System.out.println(arrList.toString());
//	            JsonObject object=(JsonObject) parser.parse(new FileReader("F://tuanjiehujiedao.json"));  //创建JsonObject对象
//	            JsonArray array=object.get("features").getAsJsonArray(); 
//	            List<Object> arrList = new ArrayList<Object>();
//	            for(int i=0;i<array.size();i++){
//	                JsonObject subObject=array.get(i).getAsJsonObject();
////	                JsonObject pro = subObject.get("properties").getAsJsonObject();
////	                		arrList.add(subObject);
//	                JsonObject bbb = subObject.get("geometry").getAsJsonObject();
//	                JsonArray coordinatesarr =  bbb.get("coordinates").getAsJsonArray();
//	                for(int y=0;y<coordinatesarr.size();y++) {
//	                	 JsonArray coordarr =  coordinatesarr.get(y).getAsJsonArray();
//	                	 for(int z=0;z<coordarr.size();z++) {
//	                		 String[] aa =  coordarr.get(z).toString().split(","); 
//	                		 double aa0 = Double.valueOf(aa[0].replace("[", ""));
//	                		 double aa1 = Double.valueOf(aa[1].replace("]", ""));
//	                		 System.out.println(aa0+"--------"+aa1);
//	                	 }
//	                }
//	                	
//	            }
//	        } catch (JsonIOException e) {
//	            e.printStackTrace();
//	        } catch (JsonSyntaxException e) {
//	            e.printStackTrace();
//	        } catch (FileNotFoundException e) {
//	            e.printStackTrace();
//	        }
//	    }
//}
