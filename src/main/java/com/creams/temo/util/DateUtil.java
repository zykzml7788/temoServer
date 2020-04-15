package com.creams.temo.util;

import com.google.common.collect.Sets;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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



    /**
     * 获取当前时间往前7天日期
     * 年-月-日
     * @return
     */
    public static  List<String> getSevenDays(){
        //List<Map<String, String>> list = new ArrayList<>();
        List<String> list = new ArrayList<>();
        // 其日历字段已由当前日期和时间初始化：
        Calendar rightNow = Calendar.getInstance(); // 子类对象
        int resDate = rightNow.get(Calendar.DATE);
        int resYear = rightNow.get(Calendar.YEAR);
        int resMonth = rightNow.get(Calendar.MONTH);
        System.out.println();
        for (int i =0; i<7; i++){
            StringBuffer stringBuffer = new StringBuffer();
            String days = String.valueOf(stringBuffer.append(resYear).append("-0").append(resMonth+1).append("-").append(resDate-i));
            list.add(days);
        }
        return list;
    }

    /**
     * 获取日期差集，计算7天没有数据的日期
     * @param SevenDayList 7天的日期统计
     * @return
     */
    public static List  dateDifference(List<String> SevenDayList){
        List<String> list = DateUtil.getSevenDays();
        List<String> list1 = new ArrayList<>();
        //获取系统7天统计日期
        for (int i =0; i<SevenDayList.size(); i++){
            String a = SevenDayList.get(i);
            list1.add(a);
        }

        list.removeAll(list1);
        return list;

    }




    public static Timestamp getCurrentTimestamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    public static void main(String[] args) {
        List< String> list = new ArrayList<>();
        list.add("2020-2-23");
        list.add("2020-2-16");
        list.add("2020-2-219");

        System.out.println(DateUtil.dateDifference(list));
        //System.out.println(DateUtil.getCurrentTimestamp());
    }
}
