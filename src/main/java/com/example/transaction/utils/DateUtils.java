package com.example.transaction.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期时间工具类
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

    // 日期时间格式
    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间字符串
     * @return 格式化的时间字符串
     */
    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 获取指定格式的当前时间字符串
     * @param format 日期格式
     * @return 格式化的时间字符串
     */
    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format, new Date());
    }

    /**
     * 将Date对象转换为指定格式的字符串
     * @param format 日期格式
     * @param date Date对象
     * @return 格式化的时间字符串
     */
    public static final String parseDateToStr(final String format, final Date date) {
        return new SimpleDateFormat(format).format(date);
    }
}
