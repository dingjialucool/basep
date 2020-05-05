package com.chinobot.framework.web.controller;

import com.chinobot.plep.mini.entity.WxUser;
import com.chinobot.plep.mini.util.MemoryStore;
import com.chinobot.plep.mini.util.ValidateCode;

/**
 * <p>
 *  顶级前端控制器
 * </p>
 *
 * @author laihb
 * @since 2019-02-05
 */
public class BaseController {
	
	/**
	 * 
	 * 不通过登入入口时userKey = userId, 
	 * 通过登陆入口时使用 String userId = getWxUser(param.get("userKey")).getUuid(); 获取userId
	 */
	public static final boolean IS_LOGIN = false;
	
	/**
	 * 根据userKey获取WxUser
	 * @param userKey
	 * @return
	 * @author shizt  
	 * @date 2019年2月27日
	 * @company chinobot
	 */
	public WxUser getWxUser(String userKey) {
		ValidateCode code = MemoryStore.getValidateCode(userKey);
		return code.getUser();
	}
}
