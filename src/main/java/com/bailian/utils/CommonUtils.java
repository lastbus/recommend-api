package com.bailian.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

/**
 * @ClassName: CommonUtils 
 * @Description: 常用工具类
 * @author zhangwenming
 * @date 2015年11月16日 下午2:15:03
 */
public class CommonUtils {

	/**
	 * @Description: bean 属性拷贝
	 * @param orig
	 * @param dest
	 * @author zhangwenming
	 * @date 2015年11月25日 上午11:42:08
	 * @throws
	 */
	public static void beanCopyProperties(Object dest, Object orig) {
		try {
			BeanUtils.copyProperties(dest, orig);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获得一个UUID
	 * @author zhangwenming
	 * @return String UUID
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}


	/**
	 * 获得指定数目的UUID
	 * @param number
	 *            int 需要获得的UUID数量
	 * @author zhangwenming
	 * @return String[] UUID数组
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}

	public static final String[] TIME_PERIOD = {"00:30:00",
			"01:00:00", "01:30:00",
			"02:00:00", "02:30:00",
			"03:00:00", "03:30:00",
			"04:00:00", "04:30:00",
			"05:00:00", "05:30:00",
			"06:00:00", "06:30:00",
			"07:00:00", "07:30:00",
			"08:00:00", "08:30:00",
			"09:00:00", "09:30:00",
			"10:00:00", "10:30:00",
			"11:00:00", "11:30:00",
			"12:00:00", "12:30:00",
			"13:00:00", "13:30:00",
			"14:00:00", "14:30:00",
			"15:00:00", "15:30:00",
			"16:00:00", "16:30:00",
			"17:00:00", "17:30:00",
			"18:00:00", "18:30:00",
			"19:00:00", "19:30:00",
			"20:00:00", "20:30:00",
			"21:00:00", "21:30:00",
			"22:00:00", "22:30:00",
			"23:00:00", "23:30:00",
			"24:00:00"
	};
	
}
