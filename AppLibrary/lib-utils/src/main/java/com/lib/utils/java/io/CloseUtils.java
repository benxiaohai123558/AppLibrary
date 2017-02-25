package com.lib.utils.java.io;

import java.io.Closeable;
import java.io.IOException;

/**
 * author: Guazi.
 * time  : 2017/2/25.
 * desc  : 关闭相关工具类
 */
public class CloseUtils {

    private CloseUtils() {
        throw new UnsupportedOperationException("CloseUtils can't instantiate");
    }

    /**
     * 关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 安静关闭IO
     *
     * @param closeables closeable
     */
    public static void closeIOQuietly(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException ignored) {
                }
            }
        }
    }
}
