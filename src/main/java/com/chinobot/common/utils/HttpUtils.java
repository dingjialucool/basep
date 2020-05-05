package com.chinobot.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author Post Method
 */
public class HttpUtils {

	private static final String MAP_KEY = "7710d5fc2e12506f3710a2c347937a08";
    /**
     * 向指定URL发送POST请求
     * @param url
     * @param json
     * @return 响应结果
     */
    public static String sendPost(String url, String json) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());

            // 设置请求属性
//            String param = "";
//            if (paramMap != null && paramMap.size() > 0) {
//                Iterator<String> ite = paramMap.keySet().iterator();
//                while (ite.hasNext()) {
//                    String key = ite.next();// key
//                    String value = paramMap.get(key);
//                    //param += key + "=" + value + "&";
//                    param += key + "=" + URLEncoder.encode(String.valueOf(value), "utf-8") + "&";
//                }
//                param = param.substring(0, param.length() - 1);
//            }
//            System.out.println(json);
            // 发送请求参数
            out.print(json);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += new String(line.getBytes("UTF-8"),"UTF-8");
            }
        } catch (Exception e) {
            System.err.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 数据流post请求
     * @param urlStr
     * @param xmlInfo
     */
    public static String doPost(String urlStr, String xmlInfo) {
        String reStr="";
        try {
            URL url = new URL(urlStr);
            URLConnection con = url.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Pragma:", "no-cache");
            con.setRequestProperty("Cache-Control", "no-cache");
            con.setRequestProperty("Content-Type", "text/xml");
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(new String(xmlInfo.getBytes("utf-8")));
            out.flush();
            out.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            String line = "";
            for (line = br.readLine(); line != null; line = br.readLine()) {
                reStr += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return reStr;
    }

    /**
     *  下载文件至本地
     * @param urlString
     * @param filename
     * @param savePath
     * @throws Exception
     */
    public static void download(String urlString, String filename,String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5*1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf=new File(savePath);
        if(!sf.exists()){
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath()+"\\"+filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }


    /**
     * 测试主方法
     * @param args
     */
    public static void main(String[] args) throws Exception{
//        Map<String, String> mapParam = new HashMap<String, String>();
//        mapParam.put("name", "张三");
//        mapParam.put("validation","test");
        String json = "{"+
                "\"uuid\": \"hhhsjhdfbj223423898890\"," +
        "\"ecode\": \"uav-002\"," +
        "\"runStatus\": 1," +
        "\"operateExplain\": \"巡查某某建筑工地任务开机\"," +
        "\"operateBy\": \"505df3f8f6bc9d9e8bc971c560e01209\"," +
        "\"operateTime\": \"2019-04-13 12:10:15\"" +
"}";
		/*
		 * String path = HttpUtils.class.getResource("/uav2run.json").getFile(); path =
		 * java.net.URLDecoder.decode(path,"utf-8"); path = path.replace("\\", "/"); if
		 * (path.contains(":")) { path = path.replace("file:/","");// 2 }
		 */
        ///String input = FileUtils.readFileToString(new File(path), "UTF-8");
        //String pathUrl = "http://localhost/api/kafka/send/robot_status";
        //String result = sendPost(pathUrl, input);
        //System.out.println(result);
//        System.out.println(lnglatToAddress("114.037234","22.614929"));
        System.out.println(amapWeather("all", "441521").toString());
        //System.out.println(sendGet("https://restapi.amap.com/v3/geocode/regeo","extensions=base&key=7710d5fc2e12506f3710a2c347937a08&location=114.037234,22.614929"));

    }
    
    /**
     * 调用高德api通过经纬度查对应的地址信息
     * @param lng
     * @param lat
     * @return
     */
    public static String lnglatToAddress(String lng,String lat) {
    	String url = "https://restapi.amap.com/v3/geocode/regeo";
    	String param = "extensions=base&key="+MAP_KEY+"&location=" + lng + "," + lat; 
    	String result = sendGet(url, param);
    	JSONObject jsonObject = JSON.parseObject(result);
    	JSONObject regeocode = jsonObject.getJSONObject("regeocode");
    	return regeocode.getString("formatted_address");
    }
    
    /**
     * 
     * @param extensions 可选值：base/all   base:返回实况天气  all:返回预报天气
     * @param city 编码
     * @return
     */
    public static JSONObject amapWeather(String extensions,String city) {
    	String url = "https://restapi.amap.com/v3/weather/weatherInfo";
    	String param = "output=JSON&extensions="+extensions+"&key="+MAP_KEY+"&city=" + city; 
    	String result = sendGet(url, param);
    	JSONObject jsonObject = JSON.parseObject(result);
    	if("10000".equals(jsonObject.getString("infocode"))) {
    		return jsonObject.getJSONArray("forecasts").getJSONObject(0);
    	}
    	return null;
    }
    
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            System.out.println("urlNameString: " + urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }


}
