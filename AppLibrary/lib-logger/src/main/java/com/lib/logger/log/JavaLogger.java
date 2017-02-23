package com.lib.logger.log;

import com.lib.logger.ILogger;

/**
 * author: Guazi.
 * time  : 2017/2/23.
 * desc  : Java输入日志
 */


public class JavaLogger implements ILogger {
    @Override
    public void d(String tag, String message) {
        System.out.println(message);
    }

    @Override
    public void e(String tag, String message) {
        d(tag,message);
    }

    @Override
    public void w(String tag, String message) {
        d(tag,message);
    }

    @Override
    public void i(String tag, String message) {
        d(tag,message);
    }

    @Override
    public void v(String tag, String message) {
        d(tag,message);
    }

    @Override
    public void wtf(String tag, String message) {
        System.out.println(String.format("%s%s", tag, message));
    }
}
