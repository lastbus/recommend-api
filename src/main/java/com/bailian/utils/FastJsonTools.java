package com.bailian.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * @Description: fastjson工具类
 * @user zhangwenming
 * @date 2016年1月9日 下午4:27:33
 */
public class FastJsonTools {
	
	private static final Logger logger = LoggerFactory.getLogger(FastJsonTools.class);

	/**
	 * 用fastjson 将json字符串解析为一个 JavaBean
	 * @param jsonString
	 * @param cls
	 * @return  
	 * @author zhangwenming
	 */
	public static <T> T getBean(String jsonString, Class<T> clazz) {
		T t = null;
		try {
			t = JSON.parseObject(jsonString, clazz);
		} catch (Exception e) {
			logger.error("json字符串解析JavaBean失败", e);
		}
		return t;
	}
	
	/**
	 * 用fastjson 将json解析为一个 JavaBean
	 * @param json
	 * @param clazz
	 * @return  
	 * @author zhangwenming
	 */
	public static <T> T getBean(JSONObject json, Class<T> clazz) {
		T t = null;
		try {
			t = JSON.toJavaObject(json, clazz);
		} catch (Exception e) {
			logger.error("json字符串解析JavaBean失败", e);
		}
		return t;
	}

	/**
	 * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
	 * @param jsonString
	 * @param cls
	 * @return  
	 * @author zhangwenming
	 */
	public static <T> List<T> getBeans(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			list = JSON.parseArray(jsonString, cls);
		} catch (Exception e) {
			logger.error("json字符串解析List<JavaBean>或 List<String>失败", e);
		}
		return list;
	}

	/**
	 * 用fastjson 将jsonString 解析成 List<Map<String,Object>>
	 * @param jsonString
	 * @return  
	 * @author zhangwenming
	 */
	public static List<Map<String, Object>> getListMap(String jsonString) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			list = JSON.parseObject(jsonString, 
					new TypeReference<List<Map<String, Object>>>(){});
		} catch (Exception e) {
			logger.error("json字符串解析解析成 List<Map<String,Object>>失败", e);
		}
		return list;
	}
}
