package com.chinobot.framework.web.config;


import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.auth0.jwt.interfaces.Claim;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.service.IRoleService;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.common.utils.TokenUtil;
@Component
public class TokenInterceptor implements HandlerInterceptor{
	@Autowired
	private IRoleService roleService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	 	//获取token
		String tokenEnmu= request.getHeader("authorization");

		if(!CommonUtils.isEmpty(tokenEnmu)) {
			try {
				Map<String, Claim> claims = TokenUtil.getClaims(tokenEnmu);
				Person person = JSON.parseObject(claims.get("person").asString(), new TypeReference<Person>() {});

				Dept dept = JSON.parseObject(claims.get("dept").asString(), new TypeReference<Dept>() {});
				if(!CommonUtils.isObjEmpty(claims.get("exp"))) {
					person.setDept(dept);
					List<Map> roleList = roleService.getRoleByPersonId(person.getUuid());
					person.setRoleList(roleList);
					ThreadLocalUtil.setResources(person);
					//更新时间戳，用来生成token
					int ttlMillis = 120;
					String newtoken = TokenUtil.createToken(person,dept,roleList,ttlMillis);
					//将token设置到浏览器
					response.setHeader("authorization", newtoken);
				}else {
					response.setStatus(401);
					return false;
				}
			} catch (Exception e) {
				response.setStatus(401);
				return false;
			}
		}else {
			response.setStatus(401);
			return false;
		}
		//返回 false 则请求中断
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//	        log.info("postHandle:请求后调用");
//	    	获取塞入的用户id
//	    	Person personInfo =  ThreadLocalUtil.getResources();
//	    	System.out.println("用户id："+personInfo.getUuid()+"-----------");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//	        log.info("afterCompletion:请求调用完成后回调方法，即在视图渲染完成后回调");

	}
	    
}
