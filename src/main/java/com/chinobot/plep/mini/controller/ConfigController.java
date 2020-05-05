package com.chinobot.plep.mini.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.HttpPostUrl;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.plep.mini.entity.JsapiTicket;
import com.chinobot.plep.mini.util.WxUtils;
import com.google.gson.Gson;

@RestController
@RequestMapping("/api/config")
public class ConfigController {
	
	static final String JSAPI_TICKET_URL = "https://www.laihbyun.com/wx/cgi-bin/ticket/getticket";
	public static JsapiTicket ticket = null;
	public static final String TOKEN = "yfkj_xfcamp_token";

	@RequestMapping("/jsconfig")
	public String jsConfig(@RequestParam Map param) {

		System.out.println("userKey: " + param.get("userKey"));
		System.out.println("requestUrl: " + param.get("requestUrl"));
		return WxUtils.getJsSdk((String) param.get("requestUrl"));
	}

	@RequestMapping("/get")
	public void getToken(HttpServletResponse response, String signature, String timestamp, String nonce, String echostr)
			throws NoSuchAlgorithmException, IOException {
		// 将token、timestamp、nonce三个参数进行字典序排序
		System.out.println("signature:" + signature);
		System.out.println("timestamp:" + timestamp);
		System.out.println("nonce:" + nonce);
		System.out.println("echostr:" + echostr);
		System.out.println("TOKEN:" + TOKEN);
		String[] params = new String[] { TOKEN, timestamp, nonce };
		Arrays.sort(params);
		// 将三个参数字符串拼接成一个字符串进行sha1加密
		String clearText = params[0] + params[1] + params[2];
		String sign = getSha1(clearText);
		// 开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		if (signature.equals(sign)) {
			response.getWriter().print(echostr);
		}
	}


	/**
	 * 获取JS-SDK使用权限
	 * 
	 * @return
	 * @author shizt
	 * @date 2019年2月26日
	 * @company chinobot
	 */
	public static String getJsSdk(String url) {
		url = "https://xyforever.cn/index.html";

		if (ticket == null) {
			ticket = getJsapiTicket();
		}
		while (ticket.getTicket() == null) {
			ticket = getJsapiTicket();
		}
		// 提前5分钟获取新的token
		long expireTime = ticket.getPost_time() + (ticket.getExpires_in() - 300) * 1000;
		while (expireTime < System.currentTimeMillis()) {
			ticket = getJsapiTicket();
		}

		if (CommonUtils.isObjEmpty(ticket.getErrcode()) || 0 != ticket.getErrcode()) {
			ResultFactory.error();
		}

		long timestamp = System.currentTimeMillis() / 1000;
		String noncestr = "Wm3WZYTPz0wzccnW";

		String str = "jsapi_ticket=" + ticket.getTicket() + "&noncestr=" + noncestr + "&timestamp=" + timestamp
				+ "&url=" + url;

		System.out.println("str: " + str);

		String signature = getSha1(str);

		Map resultSign = new HashMap();
		resultSign.put("appId", WxUtils.WX_APPID);
		resultSign.put("noncestr", noncestr);
		resultSign.put("timestamp", timestamp);
		resultSign.put("signature", signature);

		return JSON.toJSONString(resultSign);
	}

	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取jsapi_ticket
	 * 
	 * @return
	 */
	public static JsapiTicket getJsapiTicket() {
		Gson gson = new Gson();
		String token = WxUtils.getToken().getAccess_token();
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token);
		params.put("type", "jsapi");
		String a = HttpPostUrl.sendPost(JSAPI_TICKET_URL, params);
		JsapiTicket ticket = gson.fromJson(a, JsapiTicket.class);
		ticket.setPost_time(System.currentTimeMillis());
		return ticket;
	}

	public static void main(String[] args) {
		System.out.println(getJsSdk("aaaa"));
		System.out.println(getSha1(
				"jsapi_ticket=LIKLckvwlJT9cWIhEQTwfEBGVSYcwTefFeqKWnmIsfmvS7ErAFHQs5eH-kdo6D7U7w1yLxEaniqP_ytqYRaJ7w&noncestr=Wm3WZYTPz0wzccnW&timestamp=1551178473&url=https://xyforever.cn/index.html"));

	}

}
