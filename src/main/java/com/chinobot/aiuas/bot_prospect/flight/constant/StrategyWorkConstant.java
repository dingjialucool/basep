package com.chinobot.aiuas.bot_prospect.flight.constant;

/**
 * 策略生成任务常量类
 * @author huangw
 *
 */
public interface StrategyWorkConstant {

	/**
	 * 提前天数
	 */
	int ADVANCE_DAY = 7;
	/**
	 * 周期类型：每天
	 */
	String DAY_CIRCLE_TYPE = "day";
	
	/**
	 * 周期类型：每周
	 */
	String WEEK_CIRCLE_TYPE = "week";
	
	/**
	 * 周期类型：每月
	 */
	String MONTH_CIRCLE_TYPE = "month";
	
	/**
	 * 策略类型：周期
	 */
	String CIRCLE_STRATEGY_TYPE = "1";
	
	/**
	 * 策略类型：气候
	 */
	String WEATHER_STRATEGY_TYPE = "2";
	
	/**
	 * 策略类型：时点
	 */
	String SEASON_STRATEGY_TYPE = "3";
	/**
	 * 高德天气-城市编码: 深汕编码
	 */
	String SS_WEATHER_CITY_CODE = "441521";
	/**
	 * 高德天气-城市编码: 深汕
	 */
	String SS_WEATHER_CITY_NAME = "海丰县（深汕）";
	
	/**
	 * 气候：暴雨
	 */
	String BY = "by";
	
	/**
	 * 气候：大暴雨
	 */
	String DBY = "dby";
	
	/**
	 * 气候：特大暴雨
	 */
	String TDBY = "tdby";
	
	/**
	 * 气候：大风（8级）
	 */
	String DF = "df";
	
	/**
	 * 气候：狂风（10级）
	 */
	String KF = "kf";
	
	/**
	 * 气候：台风（12级）
	 */
	String TF = "tf";
	
	/**
	 * 气候：黄色预警高温
	 */
	String HSYJGW = "hsyjgw";
	
	/**
	 * 气候：橙色预警高温
	 */
	String CSYJGW = "csyjgw";
	
	/**
	 * 气候：红色预警高温
	 */
	String REDYJGW = "redyjgw";
	/**
	 * 气候值：暴雨
	 */
	String BY_VAL = "暴雨";
	
	/**
	 * 气候值：大暴雨
	 */
	String DBY_VAL = "大暴雨";
	
	/**
	 * 气候值：特大暴雨
	 */
	String TDBY_VAL = "特大暴雨";
	
	/**
	 * 气候值：大风（8级）
	 */
	int DF_VAL = 8;
	
	/**
	 * 气候值：狂风（10级）
	 */
	int KF_VAL = 10;
	
	/**
	 * 气候值：台风（12级）
	 */
	int TF_VAL = 12;
	
	/**
	 * 气候值：黄色预警高温
	 */
	int HSYJGW_VAL = 35;
	
	/**
	 * 气候值：橙色预警高温
	 */
	int CSYJGW_VAL = 37;
	
	/**
	 * 气候值：红色预警高温
	 */
	int REDYJGW_VAL = 40;	
	/**
	 * 气候种类：雨
	 */
	String RAIN = "rain";
	/**
	 * 气候种类：风
	 */
	String WIND = "wind";
	/**
	 * 气候种类：温度
	 */
	String TEMP = "temp";

	/**
	 * 航班作业状态 - 待安排
	 */
	String WORK_STATUS_STANDBY = "1";
	/**
	 * 航班作业状态 - 待执行
	 */
	String WORK_STATUS_EXECUTED = "2";
	/**
	 * 航班作业状态 - 待完成
	 */
	String WORK_STATUS_COMPLETED = "3";
	/**
	 * 航班作业状态 - 已完成
	 */
	String WORK_STATUS_DONE = "4";
	/**
	 * 航班作业状态 - 执行中
	 */
	String WORK_STATUS_DOING = "5";
	/**
	 * 航班作业状态 - 已取消
	 */
	String WORK_STATUS_CANCEL = "0";
}
