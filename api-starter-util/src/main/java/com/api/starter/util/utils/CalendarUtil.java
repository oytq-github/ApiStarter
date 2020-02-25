package com.api.starter.util.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class CalendarUtil {

    public static String date2Str(Date date) {
        if (null == date) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(date);
        } catch (Exception e) {
            log.error("str2date-日期转换出错-[{}]-错误信息:[{}]", date, e);
        }
        return null;
    }

    public static String date2Str(Date date, String format) {
        if (null == date || StringUtils.isEmpty(format)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            log.error("str2date-日期转换出错-[{}]-[{}]-错误信息:[{}]", date, format, e);
        }
        return null;
    }

    public static Date str2date(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(format)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("str2date-日期转换出错-[{}]-[{}]-错误信息:[{}]", dateStr, format, e);
        }
        return null;
    }

    public static Date str2date(String dateStr) {

        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            log.error("str2date-日期转换出错-[{}]-[{}]", dateStr, e);
        }
        return null;
    }
}
