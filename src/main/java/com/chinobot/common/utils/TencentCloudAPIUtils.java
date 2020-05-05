package com.chinobot.common.utils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
public class TencentCloudAPIUtils {
	private final static String CHARSET = "UTF-8";

    public static String sign(String s, String key, String method) throws Exception {
        Mac mac = Mac.getInstance(method);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(CHARSET), mac.getAlgorithm());
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(s.getBytes(CHARSET));
        return DatatypeConverter.printBase64Binary(hash);
    }

    public static String getStringToSign(TreeMap<String, Object> params) {
        StringBuilder s2s = new StringBuilder("GETtrtc.tencentcloudapi.com/?");
        // 签名时要求对参数进行字典排序，此处用TreeMap保证顺序
        for (String k : params.keySet()) {
            s2s.append(k).append("=").append(params.get(k).toString()).append("&");
        }
        return s2s.toString().substring(0, s2s.length() - 1);
    }

    public static String getUrl(TreeMap<String, Object> params) throws UnsupportedEncodingException {
        StringBuilder url = new StringBuilder("https://trtc.tencentcloudapi.com/?");
        // 实际请求的url中对参数顺序没有要求
        for (String k : params.keySet()) {
            // 需要对请求串进行urlencode，由于key都是英文字母，故此处仅对其value进行urlencode
            url.append(k).append("=").append(URLEncoder.encode(params.get(k).toString(), CHARSET)).append("&");
        }
        return url.toString().substring(0, url.length() - 1);
    }
    
    public static String getKickOutUserUrl(String room, String userId, String sdkAppId, String secretId, String secretKey)throws Exception {
    	TreeMap<String, Object> params = new TreeMap<String, Object>(); // TreeMap可以自动排序
        params.put("Nonce", new Random().nextInt(java.lang.Integer.MAX_VALUE)); // 公共参数
        params.put("Timestamp", System.currentTimeMillis() / 1000); // 公共参数
        params.put("SecretId", secretId); // 公共参数
        params.put("Action", "KickOutUser"); // 公共参数
        params.put("Version", "2019-07-22"); // 公共参数
        params.put("Region", "ap-guangzhou"); // 公共参数
        params.put("SdkAppId", sdkAppId); // 公共参数
        params.put("RoomId", room); // 业务参数
        params.put("UserIds.0", userId); // 业务参数
        params.put("Signature", sign(getStringToSign(params), secretKey, "HmacSHA1")); // 公共参数
        String url = getUrl(params);
        System.out.println(url);
    	return url;
    }

    public static void main(String[] args) throws Exception {
        TreeMap<String, Object> params = new TreeMap<String, Object>(); // TreeMap可以自动排序
        params.put("Nonce", new Random().nextInt(java.lang.Integer.MAX_VALUE));
        params.put("Timestamp", System.currentTimeMillis() / 1000);
        params.put("SecretId", "AKIDz8krbsJ5yKBZQpn74WFkmLPx3EXAMPLE"); // 公共参数
        params.put("Action", "DescribeInstances"); // 公共参数
        params.put("Version", "2017-03-12"); // 公共参数
        params.put("Region", "ap-guangzhou"); // 公共参数
        params.put("Limit", 20); // 业务参数
        params.put("Offset", 0); // 业务参数
        params.put("InstanceIds.0", "ins-09dx96dg"); // 业务参数
        params.put("Signature", sign(getStringToSign(params), "Gu5t9xGARNpq86cd98joQYCN3EXAMPLE", "HmacSHA1")); // 公共参数
        System.out.println(getUrl(params));
    }
}
