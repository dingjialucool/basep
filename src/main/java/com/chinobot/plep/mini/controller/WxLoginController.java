package com.chinobot.plep.mini.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.mini.entity.WxUser;
import com.chinobot.plep.mini.service.ILoginService;

@RestController
@RequestMapping("/wpi")
public class WxLoginController extends BaseController {

    @Autowired
    private ILoginService loginService;
	
    /**
     * 登陆
     * @param code
     * @return
     * @author shizt  
     * @date 2019年3月1日
     * @company chinobot
     */
    @RequestMapping("login")
	public Result login(@RequestParam String code, @RequestParam(required = false) String userId) {
		if(CommonUtils.isEmpty(code)) return ResultFactory.error(-2,"参数不完整",null);
		Result<String> result = loginService.genSessionCode(code, userId);
		
		if(result.getCode() != 200) {
			return result;
		}
		
		WxUser wxUser = getWxUser(result.getData());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userKey", result.getData());
		map.put("userType", wxUser.getUserType());
		map.put("nickName", wxUser.getNickName());
		
		return ResultFactory.success(map);
	}
}
