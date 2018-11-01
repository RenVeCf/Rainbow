package com.ipd.taxiu.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    //返回当前年月日
    public static String getNowDate() {
        Date date = new Date();
        String nowDate = new SimpleDateFormat("yyyy年MM月dd日").format(date);
        return nowDate;
    }

    //返回当前年份
    public static int getYear() {
        Date date = new Date();
        String year = new SimpleDateFormat("yyyy").format(date);
        return Integer.parseInt(year);
    }

    //返回当前月份
    public static int getMonth() {
        Date date = new Date();
        String month = new SimpleDateFormat("MM").format(date);
        return Integer.parseInt(month);
    }

    public static String getYear(long time) {
        Date date = new Date(time);
        String year = new SimpleDateFormat("yyyy").format(date);
        return year;
    }

    public static String getMonth(long time) {
        Date date = new Date(time);
        String month = new SimpleDateFormat("MM").format(date);
        return month;
    }

    public static String getDay(long time) {
        Date date = new Date(time);
        String day = new SimpleDateFormat("dd").format(date);
        return day;
    }

    //判断闰年
    public static boolean isLeap(int year) {
        if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0))
            return true;
        else
            return false;
    }

    //返回当月天数
    public static int getDays(int year, int month) {
        int days;
        int FebDay = 28;
        if (isLeap(year))
            FebDay = 29;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                days = 30;
                break;
            case 2:
                days = FebDay;
                break;
            default:
                days = 0;
                break;
        }
        return days;
    }

}
