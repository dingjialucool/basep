package com.chinobot.plep.mini.util;

import java.util.Calendar;
import java.util.Date;

import com.chinobot.plep.mini.entity.WxUser;

/**
 * <p>
 * 功能描述：验证登录用户是否多次
 * </p>
 * @author wenls
 * @version V1.0
 * @date 2018年9月11日 下午17:09:08
 * @company chinobot
 */
public class ValidateCode {
	private Long identity;

	/** 验证用户 */
	private WxUser user;
	/** 过期时间 */
	private  Date expireTime;
	/** 创建时间 */
	private  Date createTime;
	//过期时长 12小时
	private static final Integer EXPORT_TIME = 60*12;

	public WxUser getUser() {
		return user;
	}

	public void setUser(WxUser user) {
		this.user = user;
	}

	public void setIdentity(long identity) {
		this.identity = identity;
		// 设置默认过期时间
		Calendar expire = Calendar.getInstance();
		expire.set(Calendar.MINUTE, expire.get(Calendar.MINUTE) + EXPORT_TIME);
		this.expireTime = expire.getTime();
	}


	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public ValidateCode(long identity, WxUser user) {
		this.identity = identity;
		this.user = user;
		this.createTime = new Date();

		// 设置默认过期时间
		Calendar expire = Calendar.getInstance();
		expire.set(Calendar.MINUTE, expire.get(Calendar.MINUTE) +EXPORT_TIME);
		this.expireTime = expire.getTime();
	}


	/**
	 * 方法描述: 判断验证用户是否已过期
	 * @return 已过期返回true，否则返回false
	 * @author wenls
	 */
	public boolean isExpired() {
		if (expireTime == null) return false;
		return expireTime.before(new Date());
	}

	public long getIdentity() {
		return identity;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setExpire(Date time) {
		this.expireTime = time;
	}

}
