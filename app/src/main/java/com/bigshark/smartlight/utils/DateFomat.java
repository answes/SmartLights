package com.bigshark.smartlight.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jlbs1 on 2017/4/26.
 */

public class DateFomat {
    //剩余秒数转换

    public static String convertSecond2Day(int time) {
        int day = time/86400;
        int hour = (time - 86400*day)/3600;
        int min = (time - 86400*day - 3600*hour)/60;
        int sec = (time - 86400*day - 3600*hour - 60*min);
        StringBuilder sb = new StringBuilder();
        sb.append(day);
        sb.append("天");
        sb.append(hour);
        sb.append("时");
        sb.append(min);
        sb.append("分");
        sb.append(sec);
        sb.append("秒");
        return sb.toString();
    }

    /**
     * 精确到秒（"yyyy-MM-dd HH:mm:ss"）
     * @param time
     * @return
     */
    public static String convertSecond2DateSS(String time){
        Date date = new Date(Long.parseLong(time)*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  sdf.format(date);
    }

    public static String getNowDateSS(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  sdf.format(date);
    }


    /**
     * 精确到天（"yyyy-MM-dd"）
     * @param time
     * @return
     */
    public static String convertSecond2Date(String time){
        Date date = new Date(Long.parseLong(time)*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return  sdf.format(date);
    }
}
