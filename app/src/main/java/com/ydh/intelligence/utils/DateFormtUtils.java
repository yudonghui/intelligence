package com.ydh.intelligence.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/27 0027.
 */
public class DateFormtUtils {
    public static String YMD = "yyyy-MM-dd";

    public static String YMD_HMS = "yyyy-MM-dd HH:mm:ss";
    public static String YMD_HM = "yyyy-MM-dd HH:mm";
    public static String MD_HMS = "MM-dd HH:mm:ss";
    public static String MD_HM = "M-dd HH:mm";
    public static String HM = "HH:mm";
    public static String MD = "MM月dd日";

    public static int[] obtainTime(long time) {
        int[] times = new int[4];
        int i1 = (int) (time / 1000);
        int i2 = i1 / 60;
        int i3 = i2 / 60;
        int i4 = i3 / 24;
        int second = i1 % 60;
        int minute = i2 % 60;
        int hour = i3 % 24;
        int day = i4;
        times[0] = day;
        times[1] = hour;
        times[2] = minute;
        times[3] = second;
        return times;
    }

    public static String getCurrentDate() {
        return getCurrentDate(YMD);
    }

    public static String getCurrentDate(String format) {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        String sim = dateFormat.format(date);
        return sim;
    }

    public static String longToDate(long date) {
        if (date == 0) return "--";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
        return simpleDateFormat.format(date);
    }

    public static String strByDate(Date date) {
        return strByDate(date, YMD);
    }

    public static String strByDate(Date date, String fomat) {
        if (date == null) return "--";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(fomat);
        return simpleDateFormat.format(date);
    }

    public static String dateByDate(String year, String month, String day) {
        String yearStr = year.substring(0, year.length() - 1);
        String monthStr = month.substring(0, month.length() - 1);
        String dayStr = day.substring(0, day.length() - 1);

        if (Integer.parseInt(monthStr) < 10) {
            monthStr = "0" + monthStr;
        }
        if (Integer.parseInt(dayStr) < 10) {
            dayStr = "0" + dayStr;
        }
        return yearStr + "-" + monthStr + "-" + dayStr;
    }

    /**
     * 获取多少天 月之后
     *
     * @param type Calendar.MONTH Calendar.DATE
     */
    public static String getDateAfter(int type, int num) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(type, num);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }

    /**
     * 获取多少分钟之后
     */
    public static String getMinAfter(Date date, int minute) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HM);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }


    /**
     * 获取本周一的时间点。
     */
    public static String getWeekStart() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取本周日的时间点。
     */
    public static String getWeekEnd() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        long timeInMillis = calendar.getTimeInMillis() + 518400000;
        calendar.setTimeInMillis(timeInMillis);
        return simpleDateFormat.format(calendar.getTime());
    }

    /**
     * 获取当前月份的第一天
     */
    public static String getBeginDayofMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_MONTH, 1);
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);
        Date firstDate = startDate.getTime();
        return simpleDateFormat.format(firstDate);

    }

    /**
     * 获取当前月份的最后一天
     */
    public static String getEndDayofMonth() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar startDate = Calendar.getInstance();
        startDate.set(Calendar.DAY_OF_MONTH, startDate.getActualMaximum(Calendar.DAY_OF_MONTH));
        startDate.set(Calendar.HOUR_OF_DAY, 23);
        startDate.set(Calendar.MINUTE, 59);
        startDate.set(Calendar.SECOND, 59);
        startDate.set(Calendar.MILLISECOND, 999);
        Date firstDate = startDate.getTime();
        return simpleDateFormat.format(firstDate);
    }

    /**
     * 获取一个月前的日期
     *
     * @param date 传入的日期
     * @return
     */
    public static String getMonthAgo(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }

    public static String getDayAfter(Date date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        String monthAgo = simpleDateFormat.format(calendar.getTime());
        return monthAgo;
    }

    static String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};

    public static String forecastDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        int i = cal.get(Calendar.DAY_OF_WEEK);
        return weekDays[i - 1];
    }

    // 将年月日转换成星期
    public static String getWeek(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date parse = format.parse(time);
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse);
            int i1 = cal.get(Calendar.DAY_OF_WEEK);
            return weekDays[i1 - 1];
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String dateByLong(long time) {
        if (time == 0) return "";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return simpleDateFormat.format(cal.getTime());
    }
    public static String dateByLong(long time,String format) {
        if (time == 0) return "";
        if (TextUtils.isEmpty(format)) format = YMD_HMS;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time);
        return simpleDateFormat.format(cal.getTime());
    }
    public static long getLongByDate(String string, String format) {
        if (TextUtils.isEmpty(string)) return 0;
        if (TextUtils.isEmpty(format)) format = YMD_HMS;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = new Date();
        try {
            date = simpleDateFormat.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static String showformatDate(Calendar selectCalendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String selectDate = sdf.format(selectCalendar.getTime());
        String todayDate = sdf.format(Calendar.getInstance().getTime());
        if (selectDate.equals(todayDate)) {
            selectDate = selectDate + " (今天)";
        }
        return selectDate;
    }

    public static String getStringByCalendar(Calendar selectCalendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(selectCalendar.getTime());
    }

    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }


}
