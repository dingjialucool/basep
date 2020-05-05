package com.chinobot.plep.mini.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.plep.mini.entity.Jscode2Session;
import com.chinobot.plep.mini.entity.WxUser;
import com.chinobot.plep.mini.mapper.WxUserMapper;
import com.chinobot.plep.mini.service.ILoginService;
import com.chinobot.plep.mini.util.WxUtils;

/**
 * Created by yaoxf on 2019-1-22.
 */
@Service
public class LoginServiceImpl implements ILoginService {

	@Autowired
	private WxUserMapper wxUserMapper;

	public Jscode2Session jscode2session(String code) {
		// 访问微信api获取session_key
		Jscode2Session session = WxUtils.jscode2session(code);
		return session;
	}

	public Result<String> genSessionCode(String code, String userId) {
		Jscode2Session session = jscode2session(code);
		if (session == null) {
			return ResultFactory.error(-2, "用户信息不存在，请重新登录。", null);
		}
		if (session.getErrcode() == 40029) {
			return ResultFactory.error(-2, "用户信息过期，请重新登录。", null);
		}
		if (session.getErrcode() == -1) {
			return ResultFactory.error(-1, "服务器响应繁忙，请稍后再试。", null);
		}
		if (session.getErrcode() == 45011) {
			return ResultFactory.error(-2, "用户信息过期，请重新登录。", null);
		}
		if (session.getErrcode() == 40163) {
			return ResultFactory.error(-2, "登录码已经被使用，请重新登录。", null);
		}
		if (CommonUtils.isEmpty(session.getOpenid())) {
			return ResultFactory.error(-2, "用户信息过期，请重新登录。", null);
		}
		// 卡片分享进入
		if (CommonUtils.isNotEmpty(userId)) {
			QueryWrapper<WxUser> wrapper = new QueryWrapper();
			wrapper.eq("uuid", userId);
			wrapper.eq("data_status", "1");
			WxUser user = (WxUser) wxUserMapper.selectOne(wrapper);
			if (CommonUtils.objNotEmpty(user)) {
				Map result = new HashMap();
				result.put("userId", userId);
				result.put("openId", session.getOpenid());
				result.put("sessionKey", session.getSession_key());
				return ResultFactory.error(-4, "转刷脸登陆。", result);
			} else {
				return ResultFactory.error(-3, "无权限访问该应用。", null);
			}
		}
		// 正常登陆
		WxUser wxUser = getWxUser(session);
		if (wxUser == null || CommonUtils.isEmpty(wxUser.getOpenId())) {
			return ResultFactory.error(-3, "无权限访问该应用。", null);
		}
		if ("普通用户".equals(wxUser.getUserType())) {
			// 转刷脸登陆
			Map result = new HashMap();
			result.put("userId", wxUser.getUuid());
			result.put("openId", session.getOpenid());
			result.put("sessionKey", session.getSession_key());
			return ResultFactory.error(-4, "转刷脸登陆。", result);
		}
		wxUser.setSessionKey(session.getSession_key());
		wxUser.setLastLoginTime(CommonUtils.getLocalDateTimeValue());
		wxUserMapper.updateById(wxUser);
		return ResultFactory.success(WxUtils.session2Key(wxUser));
	}

	@Transactional
	public Result<String> saveWxuser(Jscode2Session session, WxUser user) {
		if (session == null || user == null) {
			return ResultFactory.error(-2, "用户信息不存在，请重新登录。", null);
		}
		if (session.getErrcode() == 40029) {
			return ResultFactory.error(-2, "用户信息过期，请重新登录。", null);
		}
		if (session.getErrcode() == -1) {
			return ResultFactory.error(-1, "服务器响应繁忙，请稍后再试。", null);
		}
		if (session.getErrcode() == 45011) {
			return ResultFactory.error(-2, "用户信息过期，请重新登录。", null);
		}
		if (session.getErrcode() == 40163) {
			return ResultFactory.error(-2, "登录码已经被使用，请重新登录。", null);
		}
		if (CommonUtils.isEmpty(session.getOpenid())) {
			return ResultFactory.error(-2, "用户信息过期，请重新登录。", null);
		}
		WxUser wxUser = getWxUser(session);
		if (CommonUtils.objNotEmpty(wxUser) && CommonUtils.objNotEmpty(wxUser.getOpenId())) {
			user = wxUser;
			user.setSessionKey(session.getSession_key());
			user.setLastLoginTime(LocalDateTime.now());
			wxUserMapper.updateById(user);
		} else {
			user.setOpenId(session.getOpenid());
			user.setUnionId(session.getUnionid());
			user.setSessionKey(session.getSession_key());
			user.setLastLoginTime(LocalDateTime.now());
			wxUserMapper.insert(user);
		}
		return ResultFactory.success(WxUtils.session2Key(user));
	}

	public WxUser getWxUser(Jscode2Session jscode2Session) {
		if (CommonUtils.isEmpty(jscode2Session.getOpenid()))
			return null;
		QueryWrapper<WxUser> queryWrapper = new QueryWrapper();
		queryWrapper.eq("open_id", jscode2Session.getOpenid());
		queryWrapper.eq("data_status", "1");

		List<WxUser> list = wxUserMapper.selectList(queryWrapper);
		return list.isEmpty() ? null : list.get(0);
	}

	public WxUser getWxUser(String uuid) {
		QueryWrapper<WxUser> queryWrapper = new QueryWrapper();
		queryWrapper.eq("uuid", uuid);
		queryWrapper.eq("data_status", "1");
		return (WxUser) wxUserMapper.selectOne(queryWrapper);
	}

}
