package com.freanja.holigo.Utils;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class DateUtil {

    @SuppressLint("SimpleDateFormat")
    public static String date2Week(String str, int range){
        String[] weekRange = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(simpleDateFormat.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
            return "Error";
        }

        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + range - 1);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0) {
            week = 7;
        }

        return weekRange[week - 1];
    }

    public static String int2Month(int index) {
        String[] month = new String[]{
                "January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December"
        };
        return month[index-1];
    }

    @NonNull
    public static String calcEndDate(String cal, int range){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            calendar.setTime(sdf.parse(cal));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + range - 1);
        int month = calendar.get(Calendar.MONTH) + 1;

        return int2Month(month) + " " + calendar.get(Calendar.DATE);
    }

    public static String calcEndDate2(String cal, int range){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            calendar.setTime(sdf.parse(cal));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + range - 1);

        String resMonth = calendar.get(Calendar.MONTH) < 9 ? "0" + (calendar.get(Calendar.MONTH)+1) : "" + (calendar.get(Calendar.MONTH)+1);
        String resDay = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : "" + calendar.get(Calendar.DAY_OF_MONTH);

        return calendar.get(Calendar.YEAR) + resMonth + resDay;
    }

    public static Boolean rangeDate(String cal, String sel){
        if (cal.equals("") || sel.equals("")){
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        Calendar calendarS = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        try {
            calendar.setTime(sdf.parse(cal));
            calendarS.setTime(sdf.parse(sel));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int gap = -1;

        if (calendar.before(calendarS))
            return false;

        if (calendar.get(Calendar.MONTH) != calendarS.get(Calendar.MONTH)){

            return false;
        }

        gap = calendar.get(Calendar.DAY_OF_MONTH) - calendarS.get(Calendar.DAY_OF_MONTH);
        return gap < 7 && gap >= 0;
    }

    public static int getDay(String cal, int range) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        try {
            calendar.setTime(sdf.parse(cal));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + range - 1);

        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getMonth(String cal, int range) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            calendar.setTime(sdf.parse(cal));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + range - 1);

        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getYear(String cal, int range) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        try {
            calendar.setTime(sdf.parse(cal));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + range - 1);

        return calendar.get(Calendar.YEAR);
    }

    public boolean beforeCur(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (calendar.before(Calendar.getInstance()))
            return true;

        return false;
    }

    public int curMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }
}
