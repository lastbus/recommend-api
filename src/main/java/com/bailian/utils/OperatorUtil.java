package com.bailian.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * author: zhangwenming
 * Date: 2016/5/12
 * Time: 11:00
 * version: 1.0
 */
public class OperatorUtil {

    private static String[] operators = {"+", "-", "*", "/", "%"};
    private static Map<String, Integer> operatorMap = new HashMap<String, Integer>(operators.length);

    public static Map<String, Integer> getOperatorMap() {
        for (int i = 0; i < operators.length; i++) {
            operatorMap.put(operators[i], i);
        }
        return operatorMap;
    }



}
