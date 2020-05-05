package com.chinobot.common.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.Person;

public class TokenUtil {
	private static final String APP_KEY = "user_key"; // 进行数字签名的私钥

	public static String createToken(Person person, Dept dept, List<Map> roleList, int minute) throws Exception {
		Date iatDate = new Date();
		Calendar nowTime = Calendar.getInstance();
		nowTime.add(Calendar.MINUTE, minute);
		Date expiresDate = nowTime.getTime();

		Map<String, Object> map = new HashMap<>();
		map.put("alg", "HS256");
		map.put("typ", "JWT");

		String token = JWT.create().withHeader(map) // header
				.withClaim("person", JSON.toJSONString(person)) // payload
				.withClaim("dept", JSON.toJSONString(dept))
				.withClaim("roleList", JSON.toJSONString(roleList))
				.withIssuedAt(iatDate) // sign time
				.withExpiresAt(expiresDate) // expire time
				.sign(Algorithm.HMAC256(APP_KEY)); // signature
		return token;
	}

	// 私钥解密token信息
	public static Map<String, Claim> getClaims(String token) {
		DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(APP_KEY)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
        }
        return jwt.getClaims();
	}

	public static void main(String[] args) {
//    	//当前用户ID
//        String userId = "dba59e2eb0d349a9651c679b5a920752";
//        //该JWT的签发者，是否使用是可选的
//        String issuer = "小袁";
//        //该JWT所面向的用户，是否使用是可选的
//        String subject = "wwwwwww";
//        //什么时候过期，这里是一个Unix时间戳，是否使用是可选的
//        long ttlMillis = 1000 * 60 * 60 * 24;
//        //接收该JWT的一方，是否使用是可选的
//        String audience = "33333333";
//        String token = TokenUtil.createJWT(userId,issuer,subject,0,audience);
//        //打印出token信息
//        System.out.println(token);
// 
// 
////        解密token信息
//        Claims claims = TokenUtil.getClaims(token);
//        System.out.println("---------------------------解密的token信息----------------------------------");
//        System.out.println("ID: " + claims.getId());
//        System.out.println("Subject: " + claims.getSubject());
//        System.out.println("Issuer: " + claims.getIssuer());
//        System.out.println("audience: " + claims.getAudience());
//        System.out.println("Expiration: " + claims.getExpiration());

	}

}
