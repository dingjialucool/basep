package com.chinobot.aiuas.bot_prospect.warning2info.service;

public interface IWarning2InfoService {

	/**
	 * 接收kafka推送过来的预警
	 * @param msg
	 */
	void receiveWarning(String msg);
	
	/**
	 * 预警过滤
	 * @param warningId
	 * @return true代表过滤了，false代表未触发过滤
	 */
	boolean warningFilter(String warningId);
	
	/**
	 * 事件分拨
	 * @param warningId
	 */
	void eventAllocation(String warningId);
}
