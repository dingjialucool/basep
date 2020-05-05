package com.chinobot.plep.mini.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import com.alibaba.fastjson.JSON;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.HttpPostUrl;
import com.chinobot.plep.mini.entity.JsapiTicket;
import com.chinobot.plep.mini.entity.Jscode2Session;
import com.chinobot.plep.mini.entity.WxQrCode;
import com.chinobot.plep.mini.entity.WxToken;
import com.chinobot.plep.mini.entity.WxUser;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by yaoxf on 2019-1-22.
 */
public class WxUtils {
	private static final String WX_SERVER_URL = "https://www.xyforever.cn/wx/sns/jscode2session?grant_type=authorization_code";
	private static final String WX_TOKEN_URL = "https://www.xyforever.cn/wx/cgi-bin/token?grant_type=client_credential";
	private static final String WX_WXQRCODE_URL = "https://www.xyforever.cn/wx/wxa/getwxacodeunlimit";
//    private static  final String WX_IMAGESERVER_URL = "https://www.laihbyun.com/wx/cgi-bin/media/get";
	private static final String WX_IMAGESERVER_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get";
	// private static final String WX_IMAGESERVER_URL =
	// "https://webapi.amap.com/theme/v1.3/markers/n/mark_bs.png";
	public static final String WX_APPID = "wx17997f0cf6aa7f9c";
	public static final String WX_TEST_APPID = "wx340167893afcee37";
//    public static final String WX_TEST_APPID = "wx7cfa40c8e391de89"; // laihb
	private static final String WX_SECRET = "7651ed0000132c479d90d5a9c81473a0";
	private static final String WX_TEST_SECRET = "128127e5a377edaac57784a8c78e8719";
//    private static final String WX_TEST_SECRET ="a67f28e199bc246a41a36da80545803d"; // laihb
	private static final String QRCODE_SAVE_PATH = "C:\\TEMP\\QR_CODE";
	static final String JSAPI_TICKET_URL = "https://www.xyforever.cn/wx/cgi-bin/ticket/getticket";
	// 访问token
	public static WxToken token = null;
	// 访问token
	public static WxToken JS_token = null;
	// 访问ticket
	public static JsapiTicket ticket = null;
	// session_code参数名
	private static final String PARAM_KEY_CODE = "js_code";
	// appid参数名
	private static final String PARAM_KEY_APPID = "appid";
	// 密匙参数名
	private static final String PARAM_KEY_SECRET = "secret";
	// 二维码参数名
	private static final String PARAM_KEY_SENCE = "scene";
	// 微信访问token值
	private static final String PARAM_KEY_ACCESS_TOKEN = "access_token";

	/**
	 * <p>
	 * sessionCode换取用户登录的session_key
	 * </p>
	 * 
	 * @param code
	 * @return
	 */
	public static Jscode2Session jscode2session(String code) {
		Map<String, String> param = new HashMap<String, String>();
		param.put(PARAM_KEY_APPID, WX_APPID);
		param.put(PARAM_KEY_SECRET, WX_SECRET);
		param.put(PARAM_KEY_CODE, code);
		Gson gson = new Gson();
		String a = HttpPostUrl.sendPost(WX_SERVER_URL, param);
		return gson.fromJson(a, Jscode2Session.class);
	}

	/**
	 * <p>
	 * 服务器请求获取微信访问token
	 * </p>
	 * 
	 * @return
	 */
	public static synchronized WxToken getToken() {
		if (token == null) {
			token = getServerToken();
		}
		if (token.getAccess_token() == null) {
			token = getServerToken();
		}
		// 提前5分钟获取新的token
		long expireTime = token.getPost_timne() + (token.getExpires_in() - 300) * 1000;
		if (expireTime < System.currentTimeMillis()) {
			token = getServerToken();
		}
		return token;
	}

	/**
	 * 获取服务端的token值
	 * 
	 * @return
	 */
	public static WxToken getServerToken() {
		Gson gson = new Gson();
		Map<String, String> params = new HashMap<String, String>();
		params.put(PARAM_KEY_APPID, WX_APPID);
		params.put(PARAM_KEY_SECRET, WX_SECRET);
		String a = HttpPostUrl.sendPost(WX_TOKEN_URL, params);
		WxToken token = gson.fromJson(a, WxToken.class);
		token.setPost_timne(System.currentTimeMillis());
		return token;
	}

	/**
	 * <p>
	 * 测试服务器请求获取微信访问token
	 * </p>
	 * 
	 * @return
	 */
	public static synchronized WxToken getTestToken() {
		if (JS_token == null) {
			JS_token = getTestServerToken();
		}
		if (JS_token.getAccess_token() == null) {
			JS_token = getTestServerToken();
		}
		if (JS_token.getErrcode() != 0) {
			return JS_token;
		}
		// 提前5分钟获取新的token
		System.out.println(JS_token.getExpires_in());
		long expireTime = JS_token.getPost_timne() + (JS_token.getExpires_in() - 300) * 1000;
		System.out.println(System.currentTimeMillis());
		if (expireTime < System.currentTimeMillis()) {
			JS_token = getTestServerToken();
		}
		return JS_token;
	}

	/**
	 * 测试获取服务端的token值
	 * 
	 * @return
	 */
	public static WxToken getTestServerToken() {
		Gson gson = new Gson();
		Map<String, String> params = new HashMap<String, String>();
		params.put(PARAM_KEY_APPID, WX_TEST_APPID);
		params.put(PARAM_KEY_SECRET, WX_TEST_SECRET);
		String a = HttpPostUrl.sendPost(WX_TOKEN_URL, params);
		WxToken token = gson.fromJson(a, WxToken.class);
		token.setPost_timne(System.currentTimeMillis());
		return token;
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
//        url ="https://xyforever.cn/ss.html";
		if (ticket == null) {
			ticket = getJsapiTicket();
		}
		if (ticket.getTicket() == null) {
			ticket = getJsapiTicket();
		}
		if (ticket.getErrcode() == 0) {
			// 提前5分钟获取新的token
			long expireTime = ticket.getPost_time() + (ticket.getExpires_in() - 300) * 1000;
			if (expireTime < System.currentTimeMillis()) {
				ticket = getJsapiTicket();
			}
		}
		long timestamp = System.currentTimeMillis() / 1000;
		String noncestr = "Wm3WZYTPz0wzccnW";

		String str = "jsapi_ticket=" + ticket.getTicket() + "&noncestr=" + noncestr + "&timestamp=" + timestamp
				+ "&url=" + url;

		System.out.println("str: " + str);

		String signature = getSha1(str);

		Map resultSign = new HashMap();
		resultSign.put("appId", WxUtils.WX_TEST_APPID);
		resultSign.put("noncestr", noncestr);
		resultSign.put("timestamp", timestamp);
		resultSign.put("signature", signature);

		return JSON.toJSONString(resultSign);
	}

	/**
	 * 获取jsapi_ticket
	 * 
	 * @return
	 */
	public static JsapiTicket getJsapiTicket() {
		Gson gson = new Gson();
		String token = WxUtils.getTestToken().getAccess_token();
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", token);
		params.put("type", "jsapi");
		String a = HttpPostUrl.sendPost(JSAPI_TICKET_URL, params);
		JsapiTicket ticket = gson.fromJson(a, JsapiTicket.class);
		ticket.setPost_time(System.currentTimeMillis());
		return ticket;
	}

	/**
	 * 下载小程序二维码至本地
	 * 
	 * @param filename
	 * @param params
	 * @throws Exception
	 */
	public static WxQrCode getWxQrcode(String filename, Map<String, String> params) throws Exception {
		WxToken token1 = getToken();
		return getImage(token1, filename, params, WX_WXQRCODE_URL);
	}

	/**
	 * 下载微信公众号上传图片至本地
	 * 
	 * @param filename
	 * @param picId
	 * @throws Exception
	 */
	public static WxQrCode getWxImage(String filename, String picId) throws Exception {
		WxToken token1 = getTestToken();
		Map<String, String> params = new HashMap<String, String>();
		params.put("media_id", picId);
		if (CommonUtils.isObjEmpty(filename) && !filename.contains("."))
			filename = filename + ".png";
		WxQrCode code = getImage(token1, filename, params, WX_IMAGESERVER_URL);
		if (code.getErrcode() == 40001) {
			JS_token = getTestServerToken();
			token1 = JS_token;
			code = getImage(token1, filename, params, WX_IMAGESERVER_URL);
		}
		return code;
	}

	private static WxQrCode getImage(WxToken token, String filename, Map<String, String> params, String imageServer)
			throws Exception {
		Gson gson = new Gson();
		// 默认跳转登录页面
		params.put("page", "pages/secutiry/login");
		String json = gson.toJson(params);
		// 构造URL
		URL url = new URL(
				imageServer + "?access_token=" + token.getAccess_token() + "&media_id=" + params.get("media_id"));
		// 打开连接
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		// 设置通用的请求属性
		con.setRequestMethod("GET");
		con.setRequestProperty("accept", "*/*");
		con.setRequestProperty("connection", "Keep-Alive");
		// con.setRequestProperty("Content-Length", String.valueOf(json.length()));
		con.setInstanceFollowRedirects(true);
		con.setRequestProperty("Content-Type", "application/json");
		// 设置请求超时为2s
		con.setConnectTimeout(2 * 1000);
		con.connect();

		if (con.getResponseCode() == 200) {
			// 读取响应
			InputStream in = con.getInputStream();
			String type = CommonUtils.hanldNull(con.getHeaderField("Content-Type"));
			System.out.print("##########" + type);
			if (type.contains("json") || type.contains("text")) {
				String result = "";
				BufferedReader in1 = new BufferedReader(new InputStreamReader(in));
				String line;
				while ((line = in1.readLine()) != null) {
					result += new String(line.getBytes("GBK"), "UTF-8");
				}
				System.out.print(result);
				in.close();
				con.disconnect();
				return gson.fromJson(result, WxQrCode.class);
			}
			BufferedImage bufferedImage = ImageIO.read(in);
			File file = new File(QRCODE_SAVE_PATH + File.separator + filename);
			if (!file.exists()) {
				if (!file.getParentFile().exists())
					file.getParentFile().mkdirs();
				file.createNewFile();
			}
			in.close();
			con.disconnect();
//            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
//        ImageIO.setCacheDirectory(file);
//            ImageIO.write(bufferedImage, "png", outputStream);
			ImageIO.write(bufferedImage, "png", fileOutputStream);
			// 对字节数组Base64编码       
			Base64 encoder = new Base64();
			// 返回Base64编码过的字节数组字符串   

			WxQrCode code = new WxQrCode();
			code.setErrcode(200);
			code.setErrmsg("请求成功。");
			code.setPath(file.getCanonicalPath());
//            code.setImage(encoder.encode(outputStream.toByteArray()));
			return code;
		}
		return null;
	}

	/*
	 * public static WxUser key2Session(String sessionCode){ ValidateCode
	 * validateCode = MemoryStore.getValidateCode(sessionCode); if
	 * (CommonUtils.objNotEmpty(validateCode)){ if (!validateCode.isExpired()){
	 * return validateCode.getUser(); }
	 * 
	 * } return null; }
	 */
	public static String session2Key(WxUser user) {
		if (CommonUtils.isObjEmpty(user)) {
			return null;
		}
//        String UUID = CommonUtils.getUUID().toUpperCase();
		// 用户存入缓存中
		ValidateCode code = new ValidateCode(System.currentTimeMillis(), user);
//        MemoryStore.setStore(UUID,code);
//        return UUID;
		MemoryStore.setStore(user.getUuid(), code);
		return user.getUuid();
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

	private static WxQrCode getImage2(WxToken token, String filename, Map<String, String> params, String imageServer)
			throws Exception {
		Gson gson = new Gson();
		// 默认跳转登录页面
		params.put("page", "pages/secutiry/login");
		String json = gson.toJson(params);
		// 构造URL
		URL url = new URL(
				imageServer + "?access_token=" + token.getAccess_token() + "&media_id=" + params.get("media_id"));
		// 打开连接
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		// 设置通用的请求属性
		con.setRequestMethod("GET");
		con.setRequestProperty("accept", "*/*");
		con.setRequestProperty("connection", "Keep-Alive");
		// con.setRequestProperty("Content-Length", String.valueOf(json.length()));
		con.setInstanceFollowRedirects(true);
		con.setRequestProperty("Content-Type", "application/json");
		// 设置请求超时为2s
		con.setConnectTimeout(2 * 1000);
		con.connect();

		if (con.getResponseCode() == 200) {
			// 读取响应
			InputStream in = con.getInputStream();
			String type = CommonUtils.hanldNull(con.getHeaderField("Content-Type"));
			if (type.contains("json")) {
				String result = "";
				BufferedReader in1 = new BufferedReader(new InputStreamReader(in));
				String line;
				while ((line = in1.readLine()) != null) {
					result += new String(line.getBytes("GBK"), "UTF-8");
				}
				System.out.print(result);
				return gson.fromJson(result, WxQrCode.class);
			}
			BufferedImage bufferedImage = ImageIO.read(in);
			File file = new File(QRCODE_SAVE_PATH + File.separator + filename);
			if (!file.exists()) {
				if (!file.getParentFile().exists())
					file.getParentFile().mkdirs();
				file.createNewFile();
			}
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			FileOutputStream fileOutputStream = new FileOutputStream(file);
//        ImageIO.setCacheDirectory(file);
//            ImageIO.write(bufferedImage, "png", outputStream);
			ImageIO.write(bufferedImage, "png", fileOutputStream);
			// 对字节数组Base64编码       
			Base64 encoder = new Base64();
			// 返回Base64编码过的字节数组字符串   

			WxQrCode code = new WxQrCode();
			code.setErrcode(200);
			code.setErrmsg("请求成功。");
			code.setPath(file.getName());
			// code.setImage(encoder.encode(outputStream.toByteArray()));
			return code;
		}
		return null;
	}

	/**
	 * 测试主方法
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

//    	System.out.println(getToken().getAccess_token());
		// System.out.println(getToken().getAccess_token());
		System.out.println(getJsSdk(""));
		getWxImage("sdfsdfdfsdf.png", "sdffffffffffff");
	}

}
