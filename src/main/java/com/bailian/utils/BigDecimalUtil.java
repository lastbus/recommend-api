package com.bailian.utils;

import java.math.BigDecimal;

/**
 * author: zhangwenming
 * Date: 2016/5/23
 * Time: 13:41
 * version: 1.0
 */
public class BigDecimalUtil {


    /**
     * BigDecimal 是null
     * @param value
     * @return
     */
    public static boolean isNull(BigDecimal value) {
        if (value == null) {
            return true;
        }
        return false;
    }

    /**
     * BigDecimal 不为null
     * @param value
     * @return
     */
    public static boolean isNotNull(BigDecimal value) {
        return !isNull(value);
    }

    /**
     *  BigDecimal 转化成 double类型
     * @param value
     * @return
     */
    public static double toDouble(BigDecimal value) {
        if(isNotNull(value)) {
            return value.doubleValue();
        }
        return 0.0;
    }

    /**
     *  BigDecimal 转化成 long类型
     * @param value
     * @return
     */
    public static long toLong(BigDecimal value) {
        if(isNotNull(value)) {
            return value.longValue();
        }
        return 0L;
    }

    /**
     *  BigDecimal 转化成 int类型
     * @param value
     * @return
     */
    public static int toInt(BigDecimal value) {
        if(isNotNull(value)) {
            return value.intValue();
        }
        return 0;
    }
    

}
