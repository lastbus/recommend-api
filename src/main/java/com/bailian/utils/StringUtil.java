package com.bailian.utils;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 字符串工具类
 * @user zhangwenming
 * @date 2016年1月7日 上午10:48:03
 */
public class StringUtil {

	private StringUtil() {

	}

	/**
	 * 判断是否为空串
	 * 
	 * @param s
	 * @return
	 * @author zhangwenming
	 */
	public static boolean isEmpty(String s) {
		if (s == null) {
			return true;
		}
		if (s.equals("")) {
			return true;
		}
		if (s.trim().length()==0) {
			return true;
		}
		if(s.trim().equalsIgnoreCase("null"))
		{
			return true;
		}
		return false;
	}

	/**
	 * 判断名称是否匹配
	 * 
	 * @param array
	 * @param name
	 * @return
	 * @author zhangwenming
	 */
	public static boolean isNamePattern(String[] array, String name) {

		boolean result = true;
		for (String s : array) {
			if (name.indexOf(s) < 0) {
				return false;
			}
		}
		return result;
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 *            null、“ ”、“null”都返回true
	 * @return
	 * @author zhangwenming
	 */
	public static boolean isNullString(String str) {
		return (null == str || StringUtils.isBlank(str.trim()) || "null".equals(str.trim().toLowerCase())) ? true
				: false;
	}

	/**
	 * 格式化字符串 如果为空，返回“”
	 * 
	 * @param str
	 * @return
	 * @author zhangwenming
	 */
	public static String formatString(String str) {
		if (isNullString(str)) {
			return "";
		} else {
			return str;
		}
	}

	/**
	 * 将字符串转换成数字
	 * @param input
	 * @return  
	 * @author zhangwenming
	 */
	public static String string2num(String input) {
		String reg = "[a-zA-Z]";
		StringBuffer strBuf = new StringBuffer();
		input = input.toLowerCase();
		if (null != input && !"".equals(input)) {
			for (char c : input.toCharArray()) {
				if (String.valueOf(c).matches(reg)) {
					strBuf.append(c - 96);
				} else {
					strBuf.append(c);
				}
			}
			return strBuf.toString();
		} else {
			return input;
		}
	}

	/**
	 * 将字母转换成数字
	 * @param input  
	 * @author zhangwenming
	 */
	public static int char2num(char input) {
		return input - 96;
	}

	/**
	 * 将数字转换成字母
	 * @param input
	 * @param isLower  是否是小写字母
	 * @return  
	 * @author zhangwenming
	 */
	public static char num2char(int input, boolean isLower) {
		if (isLower) {
			return num2lowerChar(input);
		} else {
			return num2upperChar(input);
		}
	}
	
	/**
	 * 将数字转换成大写字母
	 * @param input  
	 * @author zhangwenming
	 */
	public static char num2upperChar(int input) {
		return (char) (input + 64);
	}
	
	/**
	 * 将数字转换成小写字母
	 * @param input
	 * @return  
	 * @author zhangwenming
	 */
	public static char num2lowerChar(int input) {
		return (char) (input + 96);
	}

	/**
	 * 以固定字符分割字符串，存储到List集合中
	 * @param originalStr
	 * @return
     */
	public static List<String> splitNotBlankStr(String originalStr, String decollator) {
		List<String> result = null;
		if (StringUtils.isNotBlank(originalStr)) {
			String[] arrays = originalStr.split(decollator);

			result = new ArrayList<String>(arrays.length);
			for (int i = 0; i < arrays.length; i++) {
				String value = StringUtils.trim(arrays[i]);
				if (StringUtils.isNotBlank(value)) {
					result.add(value);
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	public static List<String> split(String input)
	{
		if(isEmpty(input))
		{
			return null;
		}
		String[] strArray = input.split("#");
//		List<String> list = Arrays.asList(strArray);
//		List<String> arrayList = new ArrayList<String>(list);
		//1031 oom
		List<String> list = new ArrayList<String>();
		for(String s:strArray)
		{
			list.add(new String(s));
		}
		strArray = null;
		return list; 
	}
	/**
	 * 
	 * @param input
	 * @return
	 */
	public static List<String> splitItemsWithWight(String input)
	{
		List<String> itemList = new ArrayList<String>();
		String[] strArray = input.split("#");
		for(String s:strArray)
		{
			String [] kv = s.split(":");
			//1031 oom
			itemList.add(new String(kv[0].trim()));
			kv = null;
					
		}
		strArray = null;
		return itemList; 
	}

}
