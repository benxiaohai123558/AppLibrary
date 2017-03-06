package com.lib.logger.inter;

import com.lib.logger.config.LoggerConfig;

/**
 * author: Guazi.
 * time  : 2017/2/20.
 * desc  :
 */


public interface IPrinter {

    LoggerConfig init(String tag);

    IPrinter t(String tag);

    void i(Object... args);

    void v(Object... args);

    void w(Object... args);

    void wtf(Object... args);

    void d(Object... args);

    void e(Object... args);

    void e(Throwable throwable, Object... args);

    void json(String json);

    void xml(String xml);

    boolean clearLogFile();
}
