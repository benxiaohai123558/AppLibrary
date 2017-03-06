package com.lib.logger.config;

import com.lib.logger.inter.IUploadLogService;
import com.lib.logger.log.AndroidLogger;
import com.lib.logger.inter.ILogger;

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

    /**
     * 上传log日志接口
     */
    private IUploadLogService uploadLogService;

    /**
     * 是否将log日志输入到文件
     */
    private boolean isPrintFile;

    /**
     * 输出日志文件路径
     */
    private String logFilePath;

    /**
     * 删除几天前的日志
     */
    private int date = 1;


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

    public IUploadLogService getUploadLogService() {
        return uploadLogService;
    }

    public void setUploadLogService(IUploadLogService uploadLogService) {
        this.uploadLogService = uploadLogService;
    }

    public boolean isPrintFile() {
        return isPrintFile;
    }

    public LoggerConfig setPrintFile(boolean printFile) {
        isPrintFile = printFile;
        return this;
    }

    public String getLogFilePath() {
        return logFilePath;
    }

    public LoggerConfig setLogFilePath(String logFilePath) {
        this.logFilePath = logFilePath;
        return this;
    }

    public int getDate() {
        return date;
    }

    public LoggerConfig setDate(int date) {
        this.date = date;
        return this;
    }

    public void reset() {
        this.isLogger = false;
        this.methodCount = 1;
        this.showThreadInfo = true;
        this.isPrintFile = false;
        this.date = 1;
        this.uploadLogService = null;
    }
}
