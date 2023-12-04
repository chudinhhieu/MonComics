package com.example.doraemoncomics.Models;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class MyDate {
    @SuppressLint("SimpleDateFormat")
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
    static SimpleDateFormat vn = new SimpleDateFormat("dd/MM/yyyy");

    public static String toString(Date date) {
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(date);
    }

    public static String toStringVn(Date date) {
        vn.setTimeZone(TimeZone.getDefault());
        return vn.format(date);
    }

    public static Date toDate(String strDate) throws ParseException {
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.parse(strDate);
    }

    public static long getTimeDifference(Date date) {
        Date currentDate = new Date();
        // Chuyển đổi thời gian của date từ GMT+0 sang múi giờ của máy tính
        long dateInMillis = date.getTime() + TimeZone.getDefault().getOffset(date.getTime());
        long currentInMillis = currentDate.getTime();
        long differenceInMillis = currentInMillis - dateInMillis;
        return differenceInMillis;
    }


    public static String getReadableTimeDifference(Date date) {
        long differenceInMillis = getTimeDifference(date);
        long seconds = differenceInMillis / 1000;

        if (seconds < 60) {
            return "1 phút trước";
        } else if (seconds < 3600) {
            long minutes = seconds / 60;
            return minutes + " phút trước";
        } else if (seconds < 86400) {
            long hours = seconds / 3600;
            return hours + " giờ trước";
        } else {
            long days = seconds / 86400;
            return days + " ngày trước";
        }
    }
}
