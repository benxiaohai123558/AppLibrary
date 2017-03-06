package com.lib.logger;

import com.lib.logger.constants.LoggerConstants;
import com.lib.logger.config.LoggerConfig;
import com.lib.logger.inter.IPrinter;

/**
 * author: Guazi.
 * time  : 2017/2/20.
 * desc  : 日志输出类
 */


public class Logger {

    private static IPrinter printer = new LoggerPrinter();

    private Logger() {
    }

    public static LoggerConfig init() {
        return printer.init(LoggerConstants.DEFAULT_TAG);
    }

    public static LoggerConfig init(String tag) {
        if (printer == null) {
            printer = new LoggerPrinter();
        }
        return printer.init(tag);
    }

    public static IPrinter t(String tag) {
        return printer.t(tag);
    }

    public static void i( Object... args) {
        printer.i(args);
    }

    public static void v(Object... args) {
        printer.v(args);
    }

    public static void w(Object... args) {
        printer.w(args);
    }

    public static void wtf(Object... args) {
        printer.wtf(args);
    }

    public static void d(Object... object) {
        printer.d(object);
    }

    public static void e(Object... args) {
        printer.e(args);
    }

    public static void e(Throwable throwable, Object... args) {
        printer.e(throwable, args);
    }

    public static void json(String json) {
        printer.json(json);
    }

    public static void xml(String xml) {
        printer.xml(xml);
    }

    public static void clearLogFile(){
        printer.clearLogFile();
    }
}
