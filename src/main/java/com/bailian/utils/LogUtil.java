package com.bailian.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Description: 日志工具类
 * @user zhangwenming
 * @date 2016年1月7日 上午10:42:31
 */
public class LogUtil {

	private static LogUtil util;
	private Log logger;
	
	private LogUtil() {
		
	}

	private LogUtil(String name) {
		logger = LogFactory.getLog(name);
	}

	private LogUtil(Class<?> clazz) {
		logger = LogFactory.getLog(clazz);
	}

	/**
	 * 获取util
	 * @param name
	 * @return  
	 * @author zhangwenming
	 */
	public static LogUtil getInstance(String name) {
		util = new LogUtil(name);
		return util;
	}

	/**
	 * 获取util
	 * @param clazz
	 * @return  
	 * @author zhangwenming
	 */
	public static LogUtil getInstance(Class<?> clazz) {
		util = new LogUtil(clazz);
		return util;
	}

	/**
	 * 记录日志信息
	 * @param message
	 * @param clazz  
	 * @author zhangwenming
	 */
	public void info(String message, Class<?> clazz) {
		logger.info(clazz + "===>" + message);
	}

	/**
	 * 记录日志错误信息
	 * @param message
	 * @param t  
	 * @author zhangwenming
	 */
	public void error(String message, Exception t) {
		logger.error(message, t);
	}

	/**
	 * 记录日志错误信息
	 * @param message
	 * @param t  
	 * @author zhangwenming
	 */
	public void error(String message, Throwable t) {
		logger.error(message, t);
	}

}
