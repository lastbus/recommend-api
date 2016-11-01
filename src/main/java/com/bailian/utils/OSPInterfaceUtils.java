package com.bailian.utils;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: 连接中台OSP的接口
 * @user zhangwenming
 * @date 2016年1月9日 下午3:00:57
 */
public class OSPInterfaceUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(OSPInterfaceUtils.class);
	
	private OSPInterfaceUtils() {
		
	}

	/** OSP URL */
	public static String OSP_URL = null;
	/** 用户登录接口 */
	public static String SYS_USER_LOGIN = null;
	/** 查询用户详情接口 */
	public static String SYS_USER_DETAIL = null;
	
	
	private static Properties props = new Properties();
	static{
		try {
			props.load(OSPInterfaceUtils.class.getClassLoader().getResourceAsStream("ospPrivilege.properties"));
			
			OSP_URL = "http://" + props.getProperty("osp.url");
			SYS_USER_LOGIN = OSP_URL + "/" + props.getProperty("osp.sys.user.login");
			SYS_USER_DETAIL = OSP_URL + "/" + props.getProperty("osp.sys.user.detail");
			
		} catch (Exception e) {
			logger.error("获取ospPrivilege.properties信息失败", e);
		}
	}

	public static String readStringValue(String key){
		return props.getProperty(key);
	}
	
	public static int readIntValue(String key){
		return Integer.parseInt(props.getProperty(key));
	}
	
	public static boolean readBooleanValue(String key){
		return Boolean.parseBoolean(props.getProperty(key));
	}
	
	public static String[] readStringArray(String key, String regex){
		String val = props.getProperty(key);
		String[] result = val.split(regex);
		return result;
	}
	
	/**
	 * @Description: 通用的状态码
	 * @user zhangwenming
	 * @date 2016年1月9日 下午4:13:13
	 */
	public abstract class CommonCode {
		public static final String SUCCESS_CODE = "00100000"; // 获取数据成功状态码
		public static final String FAILED_CODE = "00100001"; // 获取数据失败状态码
	}
	
}
