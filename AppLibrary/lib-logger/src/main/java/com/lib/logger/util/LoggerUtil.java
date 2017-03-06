package com.lib.logger.util;

import android.os.Environment;

import com.lib.logger.constants.LoggerConstants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * author: Guazi.
 * time  : 2017/2/20.
 * desc  : 工具类
 */


public class LoggerUtil {

    /**
     * 简单的日期格式化.
     */
    public static final String PATTERN_DATE_ONLY = "yyyy-MM-dd";

    private LoggerUtil() {
    }

    /**
     * 字符串判断是为空
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * 字符串判断是否相等
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        if (a != null && b != null) {
            int length = a.length();
            if (length == b.length()) {
                if (a instanceof String && b instanceof String) {
                    return a.equals(b);
                } else {
                    for (int i = 0; i < length; i++) {
                        if (a.charAt(i) != b.charAt(i)) return false;
                    }
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Copied from "android.util.Log.getStackTraceString()" in order to avoid usage of Android stack
     * in unit tests.
     *
     * @return Stack trace in form of String
     */
    public static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        // This is to reduce the amount of log spew that apps do in the non-error
        // condition of the network being unavailable.
        Throwable t = tr;
        while (t != null) {
            if (t instanceof UnknownHostException) {
                return "";
            }
            t = t.getCause();
        }

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        tr.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static File createLogFile(String path) {
        File file;
        if (!isEmpty(path)) {
            file = new File(Environment.getExternalStorageDirectory(), path);
        } else {
            file = new File(Environment.getExternalStorageDirectory(), LoggerConstants.DEFAULT_PATH);
        }
        if (file != null && !file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    public static File createLogFile(File fileRoot, String name) throws IOException {
        File file = new File(fileRoot, name);
        if (file != null && !file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    /**
     * 得到当天时间偏移指定天数的日期
     *
     * @param offset 偏移的天数
     * @return Date 改变后的时间
     */
    public static Date getDateByDayOffset(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();// 此时打印它获取的是系统当前时间
        calendar.setTime(date);
        calendar.add(Calendar.DATE, offset); // 正数为未来某天，负数则为过去某天
        return calendar.getTime();
    }

    /**
     * 把日期格式字符串解析成日期.
     *
     * @param dateStr 日期字符串.
     * @return Date 日期.
     */
    public static Date parse(String dateStr) {
        if (LoggerUtil.isEmpty(dateStr) || LoggerUtil.isEmpty(PATTERN_DATE_ONLY)) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(PATTERN_DATE_ONLY);
        try {
            return df.parse(dateStr);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Illegal data string:" + dateStr, e);
        }
    }

    private static final ThreadLocal<DateFormat> DATE_FORMAT_LOCAL = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        }
    };

    /**
     * 把日期格式化到天。
     * <p>
     * 格式：yyyy-MM-dd
     * <p>
     *
     * @param date 要格式化的日期.
     * @return String 格式化结果.
     */
    public static String formatAsYearToDay(Date date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMAT_LOCAL.get().format(date);
    }


    /**
     * 获取目录下所有文件包括子目录
     *
     * @param dir 目录
     * @return 文件链表
     */
    public static List<File> listFilesInDir(File dir) {
        if (!isDir(dir)) return null;
        List<File> list = new ArrayList<>();
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                list.add(file);
                if (file.isDirectory()) {
                    list.addAll(listFilesInDir(file));
                }
            }
        }
        return list;
    }

    /**
     * 判断是否是目录
     *
     * @param file 文件
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDir(File file) {
        return isFileExists(file) && file.isDirectory();
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(File file) {
        return file != null && file.exists();
    }

    /**
     * 将字符串写入文件
     *
     * @param file    文件
     * @param content 写入内容
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     * @throws IOException
     */
    public static boolean writeFile(File file, String content) throws IOException {
        if (file == null || content == null) return false;
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(content);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }
}
