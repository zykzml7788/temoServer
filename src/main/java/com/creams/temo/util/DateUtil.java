package com.creams.temo.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 日期处理工具
 */
public class DateUtil {

    public static String dealDateFormat(String oldDate) {

        Date date1 = null;
        DateFormat df2 = null;
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date = df.parse(oldDate);
            SimpleDateFormat df1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } catch (ParseException e) {

            e.printStackTrace();
        }
        return df2.format(date1);
    }

    /**
     * 获取当前日期时间
     * @return 当前日期
     */
    public static String getCurrentTime(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(d);
    }

    /**
     * 秒转成时分秒
     * @param date
     * @return
     */
    public static Map<String,Integer> getDate(Integer date ) {
        Map<String, Integer> map = new HashMap<>();
        if (date < 60) {
            map.put("seconds", date);
            return map;
        } else if (date >= 60 && date < 3600) {
            int m = date / 60;
            int s = date % 60;
            map.put("minute", m);
            map.put("seconds", s);
            return map;
        } else if (date >= 3600 && date < 216000){
            int h = date / 3600;
            int m = (date % 3600) / 60;
            int s = (date % 3600) % 60;
            map.put("minute", m);
            map.put("seconds", s);
            map.put("hour", h);
            return map;
        } else {
            int d = date / 86400;
            int h = (date / 3600) % 24;
            int m = (date % 3600) / 60;
            int s = (date % 3600) % 60;
            map.put("minute", m);
            map.put("seconds", s);
            map.put("hour", h);
            map.put("day", d);
            return map;
        }

    }


    public static Timestamp getCurrentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getCurrentTimestamp());
    }
}
