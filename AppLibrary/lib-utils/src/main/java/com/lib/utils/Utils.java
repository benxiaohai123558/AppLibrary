package com.lib.utils;

import android.content.Context;

import com.lib.utils.android.json.JsonUtils;

/**
 * Author : Guazi
 * Time: 17/2/24
 * Desc : Utils初始化
 */

public class Utils {

    private static Context context;

    private static String shareName;

    private Utils() {
        throw new UnsupportedOperationException("Utils can't instantiate");
    }

    /**
     * 初始化工具类
     *
     * @param context   上下文
     * @param shareName SharedPreferences保存的名字
     */
    public static void init(Context context, String shareName) {
        Utils.context = context;
        Utils.shareName = shareName;
        new JsonUtils();
    }

    /**
     * 获取上下文对象
     *
     * @return 上下文
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("Utils not init ");
    }

    /**
     * 获取sp保存的名字
     *
     * @return
     */
    public static String getShareName() {
        return shareName;
    }
}
