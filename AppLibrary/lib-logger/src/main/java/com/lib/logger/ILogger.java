package com.lib.logger;

/**
 * author: Guazi.
 * time  : 2017/2/20.
 * desc  :
 */


public interface ILogger {

    void d(String tag, String message);

    void e(String tag, String message);

    void w(String tag, String message);

    void i(String tag, String message);

    void v(String tag, String message);

    void wtf(String tag, String message);

}
