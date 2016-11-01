package com.bailian.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * NumberUtils 数字工具类
 * @author wanggh
 * @date 2016年5月5日
 */
public class EnglishNumberUtils {

	public static String setNullValuesInStrack(BigDecimal value){
			return value==null?ConstantUtils.CURRENT_BLANK:value.toString();
    }
	/**
	 * 3位加逗号 整数
	 * @author wanggh
	 * @date 2016年5月5日
	 * @param value
	 * @return
	 */
	public static String getIntValue(BigDecimal value){
    	String reValue = ConstantUtils.CURRENT_BLANK;
    	if(value !=null){
    		DecimalFormat df = new DecimalFormat("#,###");
    		reValue = df.format(value.setScale(0, BigDecimal.ROUND_HALF_UP));
    	}
    	
		return reValue;
    }
	/**
	 * 3位加逗号 小数
	 * @author wanggh
	 * @date 2016年5月5日
	 * @param value
	 * @return
	 */
	public static String getFloatValues(BigDecimal value,int roundIndex){
    	String reValue = ConstantUtils.CURRENT_BLANK;
    	if(value !=null){
    		StringBuffer formatStr = new StringBuffer("#,###.");
    		for(int i=0;i<roundIndex;i++){
    			formatStr.append("0");
    		}
    		DecimalFormat df = new DecimalFormat(formatStr.toString());
    		reValue = df.format(value);
    	}
    	
		return reValue;
    }
	/**
	 * 百分比
	 * @author wanggh
	 * @date 2016年5月5日
	 * @param value
	 * @return
	 */
	public static String getPercentValue(BigDecimal value,int roundIndex){
    	String reValue = ConstantUtils.CURRENT_BLANK;
    	if(value !=null){
    		StringBuffer formatStr = new StringBuffer("#.");
			for(int i=0;i<roundIndex;i++){
				formatStr.append("0");
			}
    		DecimalFormat df = new DecimalFormat(formatStr.toString());
    		reValue = df.format(value.multiply(new BigDecimal("100"))) +"%";
    	}
    	
		return reValue;
    }
	
}
