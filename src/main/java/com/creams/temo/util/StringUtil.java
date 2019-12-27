package com.creams.temo.util;

import com.alibaba.fastjson.JSONPath;

import java.util.UUID;

/**
 * 字符串工具类
 */
public class StringUtil {


    /**
     * 生成64位uuid
     * @return
     */
    public static String uuid(){
        return UUID.randomUUID().toString();
    }

    public static boolean isEmptyOrNull(String str){
        return "".equals(str) || str == null;
    }

    public static String log(String logLevel,String logs){
        return String.format("【 %s 】【 %s 】-- %s\n",DateUtil.getCurrentTime(),logLevel,logs);
    }

    public static void main(String[] args) {
        System.out.println(StringUtil.log("INFO","hahaha")+StringUtil.log("INFO","hahaha"));
    }

}
