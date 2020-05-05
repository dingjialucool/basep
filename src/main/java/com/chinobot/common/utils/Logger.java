package com.chinobot.common.utils;


public class Logger {

	private org.slf4j.Logger logger;

	
	/**
	 * 构造方法
	 */
	private Logger(org.slf4j.Logger log4jLogger) {
		logger = log4jLogger;
	}

	/**
	 * 获取构造器，根据类初始化Logger对象
	 * 
	 * @param Class
	 *            Class对象
	 * @return Logger对象
	 */
	public static Logger getLogger(Class<?> classObject) {
		return new Logger(org.slf4j.LoggerFactory.getLogger(classObject));
	}

	/**
	 * 获取构造器，根据类名初始化Logger对象
	 * 
	 * @param String
	 *            类名字符串
	 * @return Logger对象
	 */
	public static Logger getLogger(String loggerName) {
		return new Logger(org.slf4j.LoggerFactory.getLogger(loggerName));
	}

	public void debug(String arg0) {
		if(logger.isDebugEnabled())
			logger.debug(arg0);
	}
	public void debug(String arg0, Throwable arg1) {
		if(logger.isDebugEnabled())
			logger.debug(arg0, arg1);;
	}
	public void info(String arg0) {
		if(logger.isInfoEnabled())
			logger.info(arg0);
	}
	public void info(String arg0, Throwable arg1) {
		if(logger.isInfoEnabled())
			logger.info(arg0, arg1);;
	}
	public void warn(String arg0) {
		if(logger.isWarnEnabled())
			logger.warn(arg0);
	}
	public void warn(String arg0, Throwable arg1) {
		if(logger.isWarnEnabled())
			logger.warn(arg0, arg1);;
	}
	public void error(String arg0) {
		if(logger.isErrorEnabled())
			logger.error(arg0);
	}
	public void error(String arg0, Throwable arg1) {
		if(logger.isErrorEnabled())
			logger.error(arg0, arg1);;
	}
	public String getName() {
		return logger.getName();
	}

	public org.slf4j.Logger getLogger() {
		return logger;
	}

	public boolean equals(Logger newLogger) {
		return logger.equals(newLogger.getLogger());
	}
}