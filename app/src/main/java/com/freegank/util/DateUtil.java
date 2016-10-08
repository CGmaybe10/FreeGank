package com.freegank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by moubiao on 2016/10/8.
 * 和日期相关的工具类
 */

public class DateUtil {

    /**
     * String转为Date
     */
    public static Date string2Date(String text, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            return dateFormat.parse(text);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Date转为String
     */
    public static String date2String(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(date);
    }
}
