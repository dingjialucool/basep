package com.chinobot.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class WxBotUtils {

    public final static String WEBHOOK_URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=943a3688-06ec-4067-88b6-bb80c3765e2f";

    public static void main(String[] args) throws IOException {
        try {
            int i = 1/0;
        }catch (Exception e) {
            pushTextMsg(ExceptionUtils.getStackTrace(e));
        }

    }

    /**
     * @param content  文本内容，最长不超过2048个字节，必须是utf8编码
     */
    public static void pushTextMsg(String content) throws IOException {
        if(content.length() > 2048){
            content = content.substring(0,2048);
        }
        Map<String,String> msgTextMap = new HashMap();
        msgTextMap.put("content", content);

        Map msgMap = new HashMap();
        msgMap.put("msgtype", "text");
        msgMap.put("text", msgTextMap);

        ObjectMapper mapper = new ObjectMapper();
        String reqBody = mapper.writeValueAsString(msgMap);
        System.out.println(reqBody);

        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)// 设置连接超时时间
                .readTimeout(20, TimeUnit.SECONDS)// 设置读取超时时间
                .build();
        MediaType contentType = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(contentType, reqBody);
        Request request = new Request.Builder().url(WxBotUtils.WEBHOOK_URL).post(body).addHeader("cache-control", "no-cache").build();
        Response response = client.newCall(request).execute();
        byte[] datas = response.body().bytes();
        String respMsg = new String(datas);
        System.out.println(respMsg);
    }
}
