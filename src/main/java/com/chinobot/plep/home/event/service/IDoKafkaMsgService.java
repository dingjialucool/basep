package com.chinobot.plep.home.event.service;

public interface IDoKafkaMsgService {
	/**
	 * 处理推送过来的预警信息	
	 * @param jsonMsg
	 * @throws Exception 
	 */
	void doEarlyWarnning(String jsonMsg) throws Exception;
/**
	 * 处理推送过来的设备轨迹
	 * @param value
	 */
	void updateDataBase(String value);

	/**
	 * 处理推送过来的状态
	 * @param value
	 */
	void DoUavStatus(String value);
	/**
	 * 处理预计上传
	 * @param jsonMsg
	 * @throws Exception 
	 */
	void warningUpload(String jsonMsg) throws Exception;
	/**
	 * 处理巡查记录上传
	 * @param value
	 */
	void recordUpload(String value) throws Exception;
	
	void pushWarningStock(String value);

	void pushWarningAddition(String value);
	
	/**
	 * 人员轨迹
	 * @param value
	 */
	void doPersonTrajectory(String value);
	/**
	 * 任务应答
	 * @param value
	 * @throws Exception 
	 */
	void taskConfirm(String value) throws Exception;
	
	/**
	 * 数量监测处理
	 * @param value
	 */
	void numerPost(String value);
	
	
}
