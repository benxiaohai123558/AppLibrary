package com.lib.logger.config;

import com.lib.logger.log.AndroidLogger;
import com.lib.logger.ILogger;

/**
 * author: Guazi.
 * time  : 2017/2/20.
 * desc  : 配置logger 输出属性
 */


public class LoggerConfig {
    /**
     * 是否输出日志
     */
    private boolean isLogger;

    /**
     * 输入方法行数
     */
    private int methodCount = 1;
    /**
     * 是否输出线程信息
     */
    private boolean showThreadInfo = true;

    /**
     * 自定义输入日志方式
     */
    private ILogger logger;

    public void setIsLogger(boolean isLogger) {
        this.isLogger = isLogger;
    }

    public LoggerConfig showLogger() {
        this.isLogger = true;
        return this;
    }

    public LoggerConfig setMethodCount(int methodCount) {
        this.methodCount = methodCount;
        return this;
    }

    public LoggerConfig hideThreadInfo() {
        this.showThreadInfo = false;
        return this;
    }

    public LoggerConfig setLogger(ILogger logger) {
        this.logger = logger;
        return this;
    }

    public ILogger getLogger() {
        if (logger == null) {
            logger = new AndroidLogger();
        }
        return logger;
    }

    public boolean isLogger() {
        return isLogger;
    }

    public int getMethodCount() {
        return methodCount;
    }


    public boolean isShowThreadInfo() {
        return showThreadInfo;
    }

    public void reset() {
        this.isLogger = false;
        this.methodCount = 1;
        this.showThreadInfo = true;
    }
}
