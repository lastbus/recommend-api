package com.bailian.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class PercentNumber {
	/**
	 * 增长的百分百
	 * x/y  == >  30/50  ==> (50-30)/30 = 66.6%
	 * @param x
	 * @param y
	 * @return
	 */
	public static String getPrecent(String x, String y,int keepAfterDot){
		if(x==null||y==null||x.equals("null")||y.equals("null")){
			return "-";
		}
		String result="-";
		BigDecimal xx = BigDecimal.valueOf(Double.parseDouble(x));
		BigDecimal yy = BigDecimal.valueOf(Double.parseDouble(y));
		if(!(xx.doubleValue()==0)){
			NumberFormat nf =NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(keepAfterDot);
			result=nf.format((yy.doubleValue()-xx.doubleValue())*1.0/xx.doubleValue());
		}
//		String result=x+"  "+y;
		return result; 
	}
	
	
	/**
	 * 百分占比
	 * x/y  == >  30/50  ==> 60.0%
	 * @param x
	 * @param y
	 * @return
	 */
	public static String getPrePercent(String x, String y,int keepAfterDot){
		if(x==null||y==null||x.equals("null")||y.equals("null")){
			return "-";
		}
		String result="-";
		BigDecimal xx = BigDecimal.valueOf(Double.parseDouble(x));
		BigDecimal yy = BigDecimal.valueOf(Double.parseDouble(y));
		if(!(xx.doubleValue()==0)){
			NumberFormat nf =NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(keepAfterDot);
			result=nf.format(xx.doubleValue()*1.0/yy.doubleValue());
		}
//		String result=x+"  "+y;
		return result; 
	}

	/**
	 * 增长率
	 * (x-y)/y  == >  30/50  ==> 60.0%
	 * @param x
	 * @param y
	 * @return
	 */
	public static String getPrePercent(BigDecimal x, BigDecimal y,int keepAfterDot){
		if(x==null||y==null){
			return "-";
		}
		String result="-";
		if(!(y.doubleValue()==0)){
			NumberFormat nf =NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(keepAfterDot);
			result=nf.format(x.subtract(y).doubleValue()*1.0/y.doubleValue());
		}
		return result; 
	}
	
	/**
	 * x ==> 0.8000  ==>80.0
	 * @param x
	 * @param keepAfterDot
     * @return
     */
	public static String getPercent(String x,int keepAfterDot){
		if(x==null||x.equals("null")){
			return "-";
		}
		String result="-";
		BigDecimal xx = BigDecimal.valueOf(Double.parseDouble(x));
		if(!(xx.doubleValue()==0)){
			NumberFormat nf =NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(keepAfterDot);
			result=nf.format((xx.doubleValue()));
		}
		result.replace("%", "");
		return result; 
	}
	
	

	public static String getPercent(BigDecimal x, BigDecimal total) {
		String result = "0.00%";// 接受百分比的值
		if (total != null && !(total.doubleValue() == 0)) {
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(2);
			result = nf.format(x.doubleValue() * 1.0 / total.doubleValue());
		}
		return result;
	}
	
	public static String getPercent(double x, double total) {
		String result = "0.00%";// 接受百分比的值
		if (!(total == 0)) {
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(2);
			result = nf.format(x * 1.0 / total);
		}
		return result;
	}

	public static String getPercent(double x) {
		String result = "0.00%";// 接受百分比的值
		if (!(x == 0)) {
			NumberFormat nf = NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(2);
			result = nf.format(x);
		}
		return result;
	}

	public static String parseDoubleToStr(double x) {
		return String.format("%.2f", x);
	}
	
	/**
	 * 保留2位小数
	 * @param x 被除数
	 * @param total 除数
	 * @return  
	 * @author zhangwenming
	 */
	public static String divisor(double x, double total) {
		return total == 0 ? "0.00" : parseDoubleToStr(x * 100.0 / total);
	}
	
/*	public static String divisor(BigDecimal x, BigDecimal total) {
		String rate = "0.00";
		if (x != null && total != null && total.doubleValue() != 0) {
			rate = parseDoubleToStr(x.doubleValue() * 100.0 / total.doubleValue());
		}
		return rate; 
	}*/
	
	public static String divisor(BigDecimal x, BigDecimal total, double coefficient) {
		String rate = "0.00";
		if (x != null && total != null && total.doubleValue() != 0) {
			rate = parseDoubleToStr(x.doubleValue() * coefficient / total.doubleValue());
		}
		return rate; 
	}

	public static String divisor(double x, double total, double coefficient) {
		double rate = 0.0;
		if (total != 0) {
			rate = x * coefficient / total;
		}
		return parseDoubleToStr(rate);
	}

	public static double divisor(BigDecimal x, BigDecimal total) {
		double rate = 0.0;
		if (total != null && total.doubleValue() != 0) {
			rate = new BigDecimal((x.doubleValue() - total.doubleValue()) / total.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return rate;
	}

	public static double getDoublePercent(String x) {
		return Double.parseDouble(x);
	}

	/**
	 * 0.8850 ----> 88.5
	 * @param decimal
	 * @return
	 */
	public static String getPercentFromDecimal(String decimal){
		if(decimal==null||decimal.equals("null")){
			return "-";
		}else{
			double d = Double.parseDouble(decimal);
			//d=d*100;
			String percent = getPercent(d);
			//percent+="%";
			return percent;
		}
	}

	public static double opDivisor(BigDecimal x, BigDecimal total) {
		return opDivisor(x, total, 1.0);
	}

	public static double opDivisor(BigDecimal x, BigDecimal total, double coefficient) {
		if (x == null || total == null) {
			return 0.0;
		}
		return opDivisor(x.doubleValue(), total.doubleValue(), coefficient);
	}

	public static double opDivisor(double x, double total) {
		return opDivisor(x, total, 1.0);
	}

	public static double opDivisor(double x, double total, double coefficient) {
		double value = 0.0;
		double dividend = x * coefficient;
		if (total != 0.0) {
			value = new BigDecimal(dividend / total).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return value;
	}
	/**
	 * 转化率
	 * x/y  == >  30/50  ==> 60.0%
	 * @param x
	 * @param y
	 * @return
	 */
	public static String getPercent(BigDecimal x, BigDecimal y,int keepAfterDot){
		if(x==null||y==null){
			return "-";
		}
		String result="-";
		if(!(y.doubleValue()==0)){
			NumberFormat nf =NumberFormat.getPercentInstance();
			nf.setMinimumFractionDigits(keepAfterDot);
			result=nf.format(x.doubleValue()*1.0/y.doubleValue());
		}
		return result; 
	}/**
	 * 上月环比
	 * x/y-1  == >  30/50  ==> 60.0%
	 * @param x
	 * @param y
	 * @return
	 */
	public static String getLastMonthPercent(BigDecimal x, BigDecimal y){
		if(x==null||y==null ){
			return "-";
		}
		String result="-";
		if(!(y.doubleValue()==0)){
			result=parseDoubleToStr(x.doubleValue()/y.doubleValue()-1);
		}
		return result; 
	}

	public static void main (String args[]){
		
		//System.out.println(PercentNumber.divisor(30.121212, 0));
		//System.out.println(PercentNumber.getPercent("0.885",1));
		//System.out.println("--> " + parseDoubleToStr(0));
	}

	
}
