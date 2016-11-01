package com.bailian.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 项目名称：blcharts   
 * 类名称：DateUtil   
 * 类描述： 日期工具类.  
 * 创建人：zhangwenming   
 * 创建时间：2015年11月09日 下午3:18:44   
 * 修改备注：   
 * @version
 */
public class DateUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
	

	private static final ThreadLocal<Map<String, SimpleDateFormat>> threadLocal = new ThreadLocal<Map<String,SimpleDateFormat>>();
	
	public static SimpleDateFormat getFormat(String pattern) {
		Map<String, SimpleDateFormat> formats = threadLocal.get();
		if (formats == null) {
			formats = new HashMap<String, SimpleDateFormat>();
		}
		SimpleDateFormat sdf = formats.get(pattern);
		if (sdf == null) {
			sdf = new SimpleDateFormat(pattern);
			formats.put(pattern, sdf);
			threadLocal.set(formats);
		}
		return sdf;
	}
	
	/**
	 * 字符串格式日期转换成新的字符串日期
	 * @param strDate  
	 * @param originFormat 字符串日期的原始格式  yyyy-MM-dd
	 * @param targetFormat 转换后显示日期格式  MM月dd日
	 * @return String
	 * @throws
	 */
	public static String strDate2targetDate(String strDate, String originFormat, String targetFormat) {
		return dateToString(stringToDate(strDate, originFormat), targetFormat);
	}
	
	/**
	 * 字符串转化成日期
	 * @param strDate 日期字符串
	 * @param formatPattern  转化格式
	 * @return  
	 * @author zhangwenming
	 */
    public static Date stringToDate(String strDate, String formatPattern) {
        if (strDate == null || "".equals(strDate.trim())) {
            return null;
        }
		SimpleDateFormat format = getFormat(formatPattern);
		Date date = formatParse2Date(format, strDate);
		return date;
    }

    /**
     * 字符串转化成日期
     * @param date   日期字符串
     * @param formatPattern  转化格式
     * @return  
     * @author zhangwenming
     */
    public static String dateToString(Date date, String formatPattern) {
    	if (date == null) {
    		return null;
    	}
        return getFormat(formatPattern).format(date);
    }
	
    public static Date formatParse2Date(SimpleDateFormat format, String strDate) {
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (Exception e) {
        	logger.error("日期转换错误", e);
        }
        return date;
    }
	
	public static Date getBeginOfMinute() {
		return getEndOfDay(new Date());
	}
	
	public static Date getBeginOfMinute(Date date) {
		if (date == null) {
			date = new Date();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}
	
    public static Date getEndOfDay() {
        return getEndOfDay(new Date());
    }

    public static Date getEndOfDay(Date date) {
        if (date == null) {
        	date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }
  
    public static Date getBeginOfHour(Date date) {
        if (date == null) {
        	date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);
        return calendar.getTime();
    }
    
    public static Date getBeginOfDay(Date date) {
        if (date == null) {
        	date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);
        return calendar.getTime();
    }
    
    public static Date getBeginOfDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);
        return calendar.getTime();
    }

    public static Date getBeginOfYear() {
    	return getBeginOfYear(null);
    }
    
    public static Date getBeginOfYear(Date date) {
    	Calendar calendar = Calendar.getInstance();
    	if (date != null) {
    		calendar.setTime(date);
    	}
    	calendar.set(Calendar.MONTH, 1);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 000);
    	return calendar.getTime();
    }
    
    /**
     * 本年的日期  eg:2015-01-01
     * @return
     */
    public static Date getCurrentOfYear() {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.MONTH, 0);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 000);
    	return calendar.getTime();
	}
    
    public static Date getBeginOfYear(int year) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.YEAR, year);
    	calendar.set(Calendar.MONTH, 1);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	calendar.set(Calendar.HOUR_OF_DAY, 0);
    	calendar.set(Calendar.MINUTE, 0);
    	calendar.set(Calendar.SECOND, 0);
    	calendar.set(Calendar.MILLISECOND, 000);
    	return calendar.getTime();
    }

    public static Date getEndOfYear() {
    	return getEndOfYear(null);
    }
    
    public static Date getEndOfYear(Date date) {
    	Calendar calendar = Calendar.getInstance();
    	if (date != null) {
    		calendar.setTime(date);
    	}
    	calendar.set(Calendar.MONTH, 12);
    	calendar.set(Calendar.DAY_OF_MONTH, 31);
    	calendar.set(Calendar.HOUR_OF_DAY, 23);
    	calendar.set(Calendar.MINUTE, 59);
    	calendar.set(Calendar.SECOND, 59);
    	calendar.set(Calendar.MILLISECOND, 999);
    	return calendar.getTime();
    }

    public static Date getEndOfYear(int year) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.YEAR, year);
    	calendar.set(Calendar.MONTH, 12);
    	calendar.set(Calendar.DAY_OF_MONTH, 31);
    	calendar.set(Calendar.HOUR_OF_DAY, 23);
    	calendar.set(Calendar.MINUTE, 59);
    	calendar.set(Calendar.SECOND, 59);
    	calendar.set(Calendar.MILLISECOND, 999);
    	return calendar.getTime();
    }

    /**
     * 获取当前的日期
     * @return  
     * @author zhangwenming
     */
    public static Date getNowDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);
        return calendar.getTime();
    }
    
    
    /**
     * 获取当前的年份和月份   eg:2015-06 \  2015.06
     * @param date
     * @return
     */
    public static String getCurrentYearAndMonth(Date date, char separator) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date); 
		int year = cal.get(Calendar.YEAR);// 获取年份
		int month = cal.get(Calendar.MONTH) + 1;// 获取月份
		return year + "" + separator + "0" + month;
    }

    /**
     * n天后的结束时间
     * @param n
     * @return  
     * @author zhangwenming
     */
    public static Date getEndExpiredDate(int n) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, +n);
        Date date = DateUtil.getEndOfDay(cal.getTime());
        return date;
    }

    /**
     * 获取昨天的日期
     * @return  
     * @author zhangwenming
     */
    public static Date getYesterday() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 得到本月的第一天
     *
     * @return
     */
    public static Date firstDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);
        return calendar.getTime();
    }
    
    /**
     * 得到指定日期的第一天
     *
     * @return
     */
    public static Date firstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar
                .getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 000);
        return calendar.getTime();
    }

    /**
     * 获得指定日期的最后一天
     *
     * @param date
     * @return
     */
    public static Date lastDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int value = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, value);
        return cal.getTime();
    }

    /**
     * 获得某一年某一季度的最后一天日期
     * 
     * @param year 年份
     * @param quarter 季度（1/2/3/4）
     * @return
     */
    public static Date lastDayOfQuarter(Integer year, int quarter) {
    	if (quarter < 1 || quarter > 4) {
    		return null;
    	}
    	Calendar cal = Calendar.getInstance();
    	if (year != null && year > 0) {
    		cal.set(Calendar.YEAR, year);
    	}
    	if (quarter == 1) {
    		cal.set(Calendar.MONTH, 2);
    	} else if (quarter == 2) {
    		cal.set(Calendar.MONTH, 5);
    	} else if (quarter == 3) {
    		cal.set(Calendar.MONTH, 8);
    	} else {
    		cal.set(Calendar.MONTH, 11);
    	} 
    	cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    	cal.set(Calendar.HOUR, 0);
    	cal.set(Calendar.MINUTE, 0);
    	cal.set(Calendar.SECOND, 0);
    	return cal.getTime();
    }
    
    /**
     * 判断是否还有7天到期
     *
     * @param date 截止日期
     * @return true false
     * @throws java.text.ParseException
     */

    public static boolean getDiffDate(Date date) {
        long dateRange = diffDay(date);
        if (dateRange == 7) {
            return true;
        }
        return false;
    }

    /**
     * 当前时间
     * @return  xx月xx日xx时xx分
     * @author zhangwenming
     */
    public static String getNowTime() {
        Calendar c = Calendar.getInstance();
        String nowTime = c.get(Calendar.MONTH) + 1 + "月" + c.get(Calendar.DATE) + "日" + c.get(Calendar.HOUR_OF_DAY) + "时" + c
                .get(Calendar.MINUTE) + "分";
        return nowTime;
    }

    public static Long diffDay(Date date) {
        long dateRange = 0L;
        SimpleDateFormat format = getFormat("yyyy-MM-dd");
        String strDate = format.format(new Date());
        Date sysDate = formatParse2Date(format, strDate);
        dateRange = date.getTime() - sysDate.getTime();
        long time = 1000 * 3600 * 24; //A day in milliseconds
        return dateRange / time;
    }


    /**
     * 日期转化成字符串
     *
     * @param date
     * @return
     */
    public static String dateToString(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return getFormat("yyyy-MM-dd").format(cal.getTime());
    }

    /**
     * 日期转数字
     * @param date
     * @return
     */
    public static Integer dateToInteger(Date date){
    	String result = dateToString(date,"yyyyMMdd");
    	if(result != null && StringUtils.isNotBlank(result)){
    		return Integer.parseInt(result);
    	}
    	return null;
    }
    
    /**
     * yyyyMMdd格式数字转日期
     * @param date
     * @return
     */
    public static Date integerToDate(Integer date){
    	if(date == null){
    		return null;
    	}
    	return stringToDate(date.toString(), "yyyyMMdd");
    }
    /**
     * 根据给定时间段和间隔天数，返回指定时间字符串列表
     * @param beginAt
     * @param endAt
     * @param dateFormat
     * @return
     */
    public static List<String> getDateList(Date beginAt, Date endAt, int intervalDays, String dateFormat) {
        if (beginAt == null && endAt == null) {
            return new ArrayList<String>();
        }
        Date date = beginAt;
        SimpleDateFormat df = getFormat(dateFormat);
        List<String> dateList = new ArrayList<String>();
        long oneDay = 1000L * 60 * 60 * 24;  // 一天的时间
        do {
            dateList.add(df.format(date));
            date = new Date(date.getTime() + oneDay * intervalDays);
        } while (DateUtil.getBeginOfDay(date).compareTo(DateUtil.getBeginOfDay(endAt)) < 0);
        if (DateUtil.getBeginOfDay(date).compareTo(DateUtil.getBeginOfDay(endAt)) >= 0) {
            dateList.add(df.format(endAt));
        }
        return dateList;
    }

    /**
     * 根据给定时间段和间隔天数，返回指定时间字符串列表
     * @param beginAt
     * @return
     */
    public static Date getDateAfterTimes(Date beginAt, int minute) {
        Date date = new Date();
        if (beginAt != null) {
            date = beginAt;
        }
        long timesDay = 1000L * 60 * minute;  // 额外增加的时间   1000L * 60 秒 * 当前输入分钟数
        date = new Date(date.getTime() + timesDay);
        return date;
    }
    
    /**
     * 根据给定时间段和间隔天数，返回指定时间字符串列表
     * @param beginAt
     * @return
     */
    public static Date getDateCutTimes(Date beginAt, int minute) {
    	Date date = new Date();
    	if (beginAt != null) {
    		date = beginAt;
    	}
    	long timesDay = 1000L * 60 * minute;  // 额外减少的时间   1000L * 60 秒 * 当前输入分钟数
    	date = new Date(date.getTime() - timesDay);
    	return date;
    }

    /**
     * 获取指定日期之前n天的日期.
     * @param day
     * @param intervalDays
     * @return
     */
    public static Date getBeforeDate(Date day, int intervalDays) {
        Calendar beforeDay = Calendar.getInstance();
        beforeDay.setTime(day);
        beforeDay.add(Calendar.DATE, 0 - intervalDays);
        return beforeDay.getTime();
    }

    /**
     * @return Date    返回类型
     * @Title: getAfterDate
     * @Description: 某一天后，指定的几天
     */
    public static Date getAfterDate(Date day, int afterDays) {
        Calendar afterDay = Calendar.getInstance();
        afterDay.setTime(day);
        afterDay.add(Calendar.DAY_OF_YEAR, afterDays);
        return afterDay.getTime();
    }
    
    /**
     * 该天中，指定的几小时后
     * @param date
     * @param afterDays
     * @return
     */
    public static Date getDateAfterHours(Date date, int afterDays) {
        Calendar afterDay = Calendar.getInstance();
        afterDay.setTime(date);
        afterDay.add(Calendar.HOUR_OF_DAY, afterDays);
        return afterDay.getTime();
	}
    
    /**
     * 指定几天后
     * @param date
     * @param afterDays
     * @return
     */
    public static Date getDateAfterDays(Date date, int afterDays) {
    	Calendar afterDay = Calendar.getInstance();
    	afterDay.setTime(date);
    	afterDay.add(Calendar.DAY_OF_YEAR, afterDays);
    	return afterDay.getTime();
    }
    
	public static Date longToDate(long timeStamp) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.parse(format.format(timeStamp));
	}
    
    
	/**
	 * 指定的几月之后之前
	 * @param date
	 * @param months  负数：之前的，正数：之后
	 * @return  
	 * @author zhangwenming
	 */
    public static Date getDateAfterMonths(Date date, int months) {
    	Calendar afterDay = Calendar.getInstance();
    	if (date != null) {
    		afterDay.setTime(date);
    	}
    	afterDay.add(Calendar.MONTH, months);
    	return afterDay.getTime();
    }

    /**
     * 获取前n年的日期
     * @param currentDate
     * @param years 年数
     * @return
     */
    public static Date beforeYears(Date currentDate, int years) {
    	Calendar cal = Calendar.getInstance();
    	if (currentDate != null) {
    		cal.setTime(currentDate);
    	}
    	cal.add(Calendar.YEAR, -years);
    	return cal.getTime();
    }
    
    /**
     * 获取后n年的日期
     * @param currentDate
     * @param years 年数
     * @return
     */
    public static Date afterYears(Date currentDate, int years) {
    	Calendar cal = Calendar.getInstance();
    	if (currentDate != null) {
    		cal.setTime(currentDate);
    	}
    	cal.add(Calendar.YEAR, years);
    	return cal.getTime();
    }
    
    //获取下一年的日期
    public static Date nextYear(Date currentDate) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(currentDate);
        cal.add(GregorianCalendar.YEAR, 1);//在年上加1
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    //获取前月的第一天/
    public static Date lastMonthOfFirstDay() {
        Calendar cal = Calendar.getInstance();//获取当前日期
        cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    //获取前月的最后一天
    public static Date lastMonthOfEndDay() {
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);
        cale.set(Calendar.HOUR_OF_DAY, 23);
        cale.set(Calendar.MINUTE, 59);
        cale.set(Calendar.SECOND, 59);
        return cale.getTime();

    }

    /**
     * 获取明天的0点的时间.
     * @return
     */
    public static Date getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取n个小时前的时间
     * @param i 小时数
     * @return
     */
    public static Date getBeforeHour(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 0 - i);
        return calendar.getTime();
    }

    /**
     * 获取n个小时前的时间
     * @param i 小时数
     * @return
     */
    public static Date getBeforeMinutes(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, 0 - i);
        return calendar.getTime();
    }

    /**
     * 得到指定日期的星期数，周一为1，周日为7
     * @param date
     * @return
     */
    public static Integer getIntegerWeek(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int week = cd.get(Calendar.DAY_OF_WEEK);
        if (week == 1) {
            week = 7;
        } else {
            week = week - 1;
        }
        return week;
    }

    /**
     * 根据传入日期查询是周几 已经转换成中国地区的周
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        Calendar cd = Calendar.getInstance();
        cd.setTime(date);
        int week = cd.get(Calendar.DAY_OF_WEEK);
        if (week == 1)
            return "周日";
        else if (week == 2) {
            return "周一";
        } else if (week == 3)
            return "周二";
        else if (week == 4)
            return "周三";
        else if (week == 5)
            return "周四";
        else if (week == 6)
            return "周五";
        else
            return "周六";
    }

    /**
     * 获取指定时间与现在时间的对比，显示几秒前/几分钟前/几天前/几周前
     * @return
     */
    public static String getDateDiffCompareNow(Date date) {
        if (date == null) {
            return "";
        }
        Long diff = diffDay(date);
        if (diff < 60 * 3) {      //小于三分钟显示刚刚
            return "刚刚";
        }
        if (diff >= 60 * 3 && diff < 60 * 30) {    //大于三分钟小于半小时显示 分钟数
            return diff / 60 + "分钟前";
        }
        if (diff >= 60 * 30 && diff < 60 * 60) {
            return "半小时前";
        }
        if (diff >= 60 * 60 && diff < 24 * 60 * 60) {
            return diff / 60 / 60 + "小时前";
        }
        if (diff > 24 * 60 * 60 && diff < 24 * 60 * 60 * 7) {
            return diff / 60 / 60 / 24 + "天前";
        }
        return "一周前";
    }

    public static java.sql.Date strToSqldate(String dateStr, String dateFormat){
		Date date = stringToDate(dateStr,dateFormat);
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}
    
	public static java.sql.Timestamp strToSqltimestamp(String dateStr, String dateFormat){
		Date date = stringToDate(dateStr,dateFormat);
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		return timestamp;
	}
    
	public static int compare(Date date1, Date date2) {
		if (date1 == null) {
			return -1;
		}
		if (date2 == null) {
			return 1;
		}
		if (date1.getTime() == date2.getTime()) {
			return 0;
		} else if (date1.getTime() > date2.getTime()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public static Date parseUTC(String utc) {
		SimpleDateFormat sdf = getFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
			Date date = sdf.parse(utc);
			return date;
		} catch (ParseException e) {
		}
        return null;
	}
	
	/**
	 * 返回两个日期之间的天数
	 * @Title: getDaysMinusDate 
	 * @Description: TODO
	 */
	public static Long getDaysMinusDate(Date date1, Date date2){
		if(date1 == null || date2 == null){
			return null;
		}
		return (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);	
	}
	
	
	/**
	 * 返回两个日期之间的天数
	 *
	 * @param date1
	 * @param date2
	 */
	public static Long getDaysMinusDate(String date1, String date2, String format){
		if(format == null){
			format = "yyyy-MM-dd";
		}
		Date d1 = stringToDate(date1,format);
		Date d2 = stringToDate(date2,format);
		return getDaysMinusDate(d1,d2);
	}
	
	/**
	 * 判断是否在同一天
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean inOneDay(Date date1, Date date2){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否是同一月
	 * @param date1
	 * @param date2
	 * @return  
	 * @author zhangwenming
	 */
	public static boolean inOneMonth(Date date1, Date date2){
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 获得当前日期与本周日相差的天数
	 * @return
	 */
	private static int getMondayPlus() {
		Calendar cd = Calendar.getInstance();
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 1) {
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}
	
	/**
	 * 获得本周一的日期
	 * @return
	 */
	public static String getMondayOFWeek(){
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus);
		Date monday = currentDate.getTime();
		DateFormat df = getFormat("yyyy-MM-dd");
		String preMonday = df.format(monday);
		return preMonday;
	}
	
	/**
	 * 获得本周星期日的日期
	 * @return
	 */
	public static String getCurrentWeekday() {
		int mondayPlus = getMondayPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
		Date monday = currentDate.getTime();
		DateFormat df = getFormat("yyyy-MM-dd");
		String preMonday = df.format(monday);
		return preMonday;
	}
	
	/**
	 * @Description: 获取指定月第一天(字符串)
	 * @param formatPattern
	 * @return String
	 * @throws
	 */
	public static String getFirstDayOfAssignMonth(Date today, String formatPattern) {
		return dateToString(getFirstDayOfAssignMonth(today), formatPattern);
	}
	
	/**
	 * @Description: 获取指定月第一天(日期)
	 * @param formatPattern
	 * @return Date
	 * @throws
	 */
	public static Date getFirstDayOfAssignMonth(Date today) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(today);
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1 号
		return lastDate.getTime();
	}
	
	/**
	 * 获取当月第一天(字符串)
	 * @return
	 */
	public static String getFirstDayOfMonth(String formatPattern) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1 号
		return dateToString(lastDate.getTime(), formatPattern);
	}
	
	/**
	 * 获取当月第一天(日期)
	 * @return
	 */
	public static Date getFirstDayDateOfMonth() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1 号
		return lastDate.getTime();
	}
	
	/**
	 * 计算当月最后一天,返回字符串
	 * @return
	 */
	public static String getDefaultDay() {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1 号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1 号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		str = sdf.format(lastDate.getTime());
		return str;
	}
	
	/**
	 * 获取本季度第一天到最后一天
	 * @param month 月份
	 * @param separator 分隔符
	 * @return
	 */
	public static String getThisSeasonTime(int month,String separator) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		int end_month = array[season - 1][2];
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		int start_days = 1;
		// years+"-"+String.valueOf(start_month)+"-";
		// getLastDayOfMonth(years_value,start_month);
		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + start_month + "-" + start_days + separator + years_value + "-" + end_month + "-" + end_days;
		return seasonDate;
	}
	
	/**
	 * 当前季度的最后一天
	 * @param month 月份
	 * @param separator  分隔符
	 * @return  
	 * @author zhangwenming
	 */
	public static String getThisSeasonLastDay(int month, String separator) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int end_month = array[season - 1][2];
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		int end_days = getLastDayOfMonth(years_value, end_month);
		return years_value + separator + end_month + separator + end_days;
	}
	
	
	/**
	 * 获取某年某月的最后一天
	 * @param year  年
	 * @param month 月
	 * @return 最后一天
	 */
	public static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}
	
	/**
	 * 是否闰年
	 * @param year 年
	 * @return
	 */
	public static boolean isLeapYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
	}
	
	/**
	 * 获取当前月份 
	 * @return
	 */
	public static int getCurrentMonth(){
		return Calendar.getInstance().get(Calendar.MONTH) + 1;
	}
	
	/**
	 * 获得本年第一天的日期
	 * @return
	 */
	public static String getCurrentYearFirst() {
		int yearPlus = getYearPlus();
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.add(GregorianCalendar.DATE, yearPlus);
		Date yearDay = currentDate.getTime();
		DateFormat df = getFormat("yyyy-MM-dd");
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}
	
	public static int getYearPlus() {
		Calendar cd = Calendar.getInstance();
		int yearOfNumber = cd.get(Calendar.DAY_OF_YEAR);// 获得当天是一年中的第几天
		cd.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		cd.roll(Calendar.DAY_OF_YEAR, -1);// 把日期回滚一天。
		int MaxYear = cd.get(Calendar.DAY_OF_YEAR);
		if (yearOfNumber == 1) {
			return -MaxYear;
		} else {
			return 1 - yearOfNumber;
		}
	}
	
	/**
	 * 获得本年最后一天的日期
	 * @return
	 */
	public static String getCurrentYearEnd() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}
	
	/**
	 * 获取指定的周几（返回字符串日期）
	 * @param weekNum  为推迟的周数，-1：上周 ， 0：本周，2下周，依次类推
	 * @param several 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
	 * @return
	 */
	public static String getAssignWeekDate(Date date, int weekNum, int several, String formart) {
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 cal.add(Calendar.DATE, weekNum * 7);
		 cal.set(Calendar.DAY_OF_WEEK, several);
		 
		 if (StringUtils.isBlank(formart)) {
			 formart = "yyyy-MM-dd";
		 }
		 return dateToString(cal.getTime(), formart);
	}
	
	/**
	 * 获取指定的周几（返回Date日期）
	 * @param weekNum  为推迟的周数，-1：上周 ， 0：本周，2下周，依次类推
	 * @param several 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
	 * @return
	 */
	public static Date getAssignWeekDate(Date date, int weekNum, int several) {
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(date);
		 cal.add(Calendar.DATE, weekNum * 7);
		 cal.set(Calendar.DAY_OF_WEEK, several);
		 return cal.getTime();
	}
	
	
	public static String getNextDay(String dateString)  {		
    	SimpleDateFormat dateformat = getFormat("yyyy-MM-dd");
		Date date = formatParse2Date(dateformat, dateString);
    	Calendar afterTime = Calendar.getInstance();  
		afterTime.setTime(date);
		afterTime.add(Calendar.DATE, 1);   
		Date afterDate = (Date) afterTime.getTime();   	
		String afterDateString=dateformat.format(afterDate);		
	    return afterDateString;
	}
	
	public static int getIntervalNumBetweenDays(String startDate, String endDate) {
		SimpleDateFormat dateformat = getFormat("yyyy-MM-dd");
		// 间隔天数
		long days = (formatParse2Date(dateformat, endDate).getTime() - formatParse2Date(dateformat, startDate).getTime()) / (24 * 60 * 60 * 1000);
	    return new Long(days).intValue() ;		
	}

    public static String getbeforeMonth(String date, String formatPattern, int interval) {		
		Date stringToDate = stringToDate(date, formatPattern);
		stringToDate = stringToDate == null ? new Date() : stringToDate;
    	Calendar afterTime = Calendar.getInstance();  
		afterTime.setTime(stringToDate);
		//当前月
		afterTime.add(Calendar.MONTH, interval);   
		Date afterDate = afterTime.getTime();   	
	    return dateToString(afterDate, formatPattern);
	}

    public static String getbeforeYear(String date, String formatPattern, int interval) {		
		Date stringToDate = stringToDate(date, formatPattern);
		stringToDate = stringToDate == null ? new Date() : stringToDate;
    	Calendar afterTime = Calendar.getInstance();  
		afterTime.setTime(stringToDate);
		afterTime.add(Calendar.YEAR, interval);   
		Date afterDate = afterTime.getTime();   	
	    return dateToString(afterDate, formatPattern);
	}
    
    /**
     * 判断给定日期是否为月末的一天
     * @param date
     * @return  true:是|false:不是 
     * @author zhangwenming
     */
	public static boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, (calendar.get(Calendar.DATE) + 1));
		if (calendar.get(Calendar.DAY_OF_MONTH) == 1) {
			return true;
		}
		return false;
	}
}
