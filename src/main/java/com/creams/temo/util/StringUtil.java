package com.creams.temo.util;

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


}
