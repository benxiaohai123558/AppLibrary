package com.lib.logger.inter;

/**
 * author: Guazi.
 * time  : 2017/3/6.
 * desc  : 上传日志接口
 */


public interface IUploadLogService {
    boolean uploadLog(String tag, String logValue);
}
