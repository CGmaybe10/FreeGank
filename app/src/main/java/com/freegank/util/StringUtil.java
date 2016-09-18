package com.freegank.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by moubiao on 2016/9/18.
 */
public class StringUtil {

    /**
     * 转换字符串格式
     *
     * @param text   要转换的字符串
     * @param format 要转换的格式
     * @return 转换后的字符串
     */
    public static String transformFormat(String text, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            Date date;
            date = dateFormat.parse(text);
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
