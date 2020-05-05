package com.chinobot.plep.mini.entity;

/**
 * 调用微信JS接口的临时票据
 * @author shizt
 * @date 2019年2月26日
 */
public class JsapiTicket {

	private String ticket;
	private int expires_in;
	private int errcode;
	private String errmsg;
	private long post_time;
	
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public long getPost_time() {
		return post_time;
	}
	public void setPost_time(long post_time) {
		this.post_time = post_time;
	}
	
}
