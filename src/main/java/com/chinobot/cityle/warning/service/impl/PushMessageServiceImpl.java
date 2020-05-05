//package com.chinobot.cityle.warning.service.impl;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.chinobot.cityle.base.entity.Person;
//import com.chinobot.cityle.base.entity.Scene;
//import com.chinobot.cityle.base.entity.SceneTask;
//import com.chinobot.cityle.base.service.IPersonService;
//import com.chinobot.cityle.base.service.ISceneService;
//import com.chinobot.cityle.base.service.ISceneTaskService;
//import com.chinobot.cityle.warning.entity.PushMessage;
//import com.chinobot.cityle.warning.mapper.PushMessageMapper;
//import com.chinobot.cityle.warning.service.IPushMessageService;
//import com.chinobot.common.email.OhMyEmail;
//import com.chinobot.common.email.SendMailException;
//import com.chinobot.framework.web.service.impl.BaseService;
//
//import static io.github.biezhi.ome.OhMyEmail.SMTP_QQ;
//
//import java.io.IOException;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import java.io.UnsupportedEncodingException;
//
//import org.apache.commons.httpclient.Header;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.HttpException;
//import org.apache.commons.httpclient.NameValuePair;
//import org.apache.commons.httpclient.methods.PostMethod;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.stereotype.Service;
//
///**
// * <p>
// * 推送信息表 服务实现类
// * </p>
// *
// * @author dingjl
// * @since 2019-03-16
// */
//@PropertySource(value = "classpath:conf/email.properties", ignoreResourceNotFound = true)
//@Service
//public class PushMessageServiceImpl extends BaseService<PushMessageMapper, PushMessage> implements IPushMessageService {
//
//	@Autowired
//	private PushMessageMapper pushMessageMapper;
//	@Value("${email.account}")
//	private String emailAccount;
//	@Value("${email.password}")
//	private String emailPassword;
//	@Value("${email.from_address}")
//	private String fromAddress;
//	@Value("${phone.uid}")
//	private String phoneUid;
//	@Value("${phone.key}")
//	private String phoneKey;
//	
//	@Autowired
//	private ISceneTaskService sceneTaskService;
//	@Autowired
//	private IPersonService personService;
//	@Autowired 
//	private ISceneService sceneService;
//	
//	@Override
//	public IPage<Map> getYesPushMessage(Page page,Map param) {
//		// TODO Auto-generated method stub
//		IPage<Map> yesPushMessage = pushMessageMapper.getYesPushMessage(page,param);
//		return yesPushMessage;
//		
//	}
//	@Override
//	public IPage<Map> getNoPushMessage(Page page, Map<String, Object> param) {
//		if(param.get("push_way")!=null && param.get("push_way")!="" ) {
//        	String str = (String) param.get("push_way");
//        	if(str.indexOf(',')>0) {
//        		String[] split = str.split(",");
//        		param.put("push_way", split);
//        	}else {
//				String[] split = new String[1];
//				split[0] = str;
//				param.put("push_way", split);
//			}
//        }
//		IPage<Map> noPushMessage = pushMessageMapper.getNoPushMessage(page,param);
//		return noPushMessage;
//	}
//	@Override
//	public IPage<Map> getRealTimeMessage(Page page, Map<String, Object> param) {
//		IPage<Map> realTimeMessage = pushMessageMapper.getRealTimeMessge(page,param);
//		return realTimeMessage;
//	}
//	@Override
//	public IPage<Map> getHistoryTimeMessage(Page page, Map<String, Object> param) {
//		IPage<Map> historyTimeMessge = pushMessageMapper.getHistoryTimeMessge(page,param);
//		return historyTimeMessge;
//	}
//	@Override
//	public List<LinkedHashMap<String, String>> getFileId(Map<String, Object> param) {
//		
//		return pushMessageMapper.getFileId(param);
//	}
//	
//	/**
//	 * 	再次推送预警信息
//	 */
//	@Override
//	public boolean pushMessageAgain(Map<String, Object> param) {
//		String  push_way = (String) param.get("push_way");
//		System.out.println("push_way"+push_way);
//		String email = null;
//		String phone = null;
//		String  sceneTaskId = (String) param.get("scene_task_id");
//		String  sceneId = (String) param.get("scene_id");
//		String warnContent = (String) param.get("content");
//		String warnTime = (String) param.get("warning_time");
//		List<Person> deptPersons = getDeptPersons(sceneTaskId);
//		Scene scene = getScene(sceneId);
//		if(push_way.equals("1")) {//推送方式为邮件
//			boolean bo =false;
//			for (Person person : deptPersons) {
//				email = person.getEmail();
//				if (email != null) {
//					bo = emailPush(email,scene,warnContent,warnTime);
//				}
//			}
//			return bo;
//		}
//		if(push_way.equals("0")) {//推送方式为短信
//			boolean bo =false;
//			for (Person person : deptPersons) {
//				phone = person.getPhone();
//				if (phone != null) {
//					bo = phonePush(phone,scene,warnContent,warnTime);
//				}
//			}
//			return bo;
//		}
//		if(push_way == null || "".equals(push_way)) {
//			return false;
//		}
//		return false;
//	}
//	
//	/**
// 	 * 根据场景主键查出场景名称与地址
// 	 * @return
// 	 */
// 	private Scene getScene(String sceneUuid) {
// 		QueryWrapper<Scene> queryWrapper = new QueryWrapper();
// 		queryWrapper.select("sname","address");
// 		queryWrapper.eq("uuid", sceneUuid);
// 		Scene scene = sceneService.getOne(queryWrapper);
// 		return scene;
// 	}
//	
//	/**
// 	 * 	根据巡查内容主键查找出预判处置部门id ,再根据部门id查出部门的人
// 	 * @param sceneTaskUuid
// 	 * @return
// 	 */
// 	private List<Person> getDeptPersons(String sceneTaskUuid) {
// 		QueryWrapper<SceneTask> queryWrapper = new QueryWrapper();
// 		queryWrapper.select("dept_id");
// 		queryWrapper.eq("uuid", sceneTaskUuid);
// 		SceneTask sceneTask = sceneTaskService.getOne(queryWrapper);
// 		//根据部门id查询部门所有人
// 		QueryWrapper<Person> queryWrapperPerson = new QueryWrapper();
// 		queryWrapperPerson.eq("dept_id", sceneTask.getDeptId());
// 		List<Person> list = personService.list();
// 		
//		return list;
// 		
// 	}
//	
//	/**
//	 * 	短信发送
//	 * @param phone
//	 * @return
//	 */
//	private boolean phonePush(String phone,Scene scene ,String warnContent,String warnTime) {
//		String phoneSmsText = null;
//		phoneSmsText = "预警信息：在" + scene.getAddress() +"," + warnContent + ",请及时处理!预警时间："   + warnTime  ;
//		HttpClient client = new HttpClient();
//		PostMethod post = new PostMethod("http://sms.webchinese.cn/web_api/");
//		post.addRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
//		NameValuePair[] data = {
//		new NameValuePair("Uid", phoneUid), // 注册的用户名
//		new NameValuePair("Key", phoneKey), // 注册成功后,登录网站使用的密钥
//		new NameValuePair("smsMob", phone), // 手机号码
//		new NameValuePair("smsText", phoneSmsText) 
//			};//设置短信内容
//		post.setRequestBody(data);
//		try {
//			client.executeMethod(post);
//		} catch (HttpException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		Header[] headers = post.getResponseHeaders();
//		int statusCode = post.getStatusCode();
//		System.out.println("statusCode:" + statusCode);	//statusCode=200表示请示成功！
//		for (Header h : headers) {
//		System.out.println(h.toString());
//		}
//		String result = null;
//		try {
//			result = new String(post.getResponseBodyAsString().getBytes("gbk"));
//			if(result.equals("200")) {
//				return true;
//			}
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} //设置编码格式
//		System.out.println(result);
//		post.releaseConnection();
//		return false;
//		}
//	
//	/**
//	 * 	邮件推送
//	 * @param email
//	 */
//	private boolean emailPush(String email,Scene scene,String warnContent,String warnTime) {
//		String emailText = null;
//		emailText = "预警信息：在" + scene.getAddress() +"," + warnContent + ",请及时处理!预警时间："   + warnTime  ;
//		// 配置，一次即可
//        OhMyEmail.config(SMTP_QQ(true), emailAccount, emailPassword);
//        try {
//			OhMyEmail.subject(scene.getAddress() +"-"+warnContent)
//			.from(fromAddress)
//			.to(email)
//			.text(emailText)
//			.send();
//		} catch (SendMailException e) {
//			e.printStackTrace();
//			return false;
//		}
//		return true;
//		
//	}
//
//}
