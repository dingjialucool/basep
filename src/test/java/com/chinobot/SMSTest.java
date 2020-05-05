package com.chinobot;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.junit.Test;

public class SMSTest {

	@Test
	public void test() throws UnsupportedEncodingException, IOException {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://sms.webchinese.cn/web_api/");
		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
		NameValuePair[] data = {
		new NameValuePair("Uid", "dingjl"), // 注册的用户名
		new NameValuePair("Key", "d41d8cd98f00b204e980"), // 注册成功后,登录网站使用的密钥
		new NameValuePair("smsMob", "15079480476"), // 手机号码
		new NameValuePair("smsText", "深圳中科保泰欢迎您！") };//设置短信内容
		post.setRequestBody(data);
		try {
			client.executeMethod(post);
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Header[] headers = post.getResponseHeaders();
		int statusCode = post.getStatusCode();
		System.out.println("statusCode:" + statusCode);	//statusCode=200表示请示成功！
		for (Header h : headers) {
		System.out.println(h.toString());
		}
		String result = new String(post.getResponseBodyAsString().getBytes("gbk")); //设置编码格式
		System.out.println(result);
		post.releaseConnection();

	}
}
