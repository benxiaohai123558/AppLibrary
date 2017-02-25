package com.lib.utils.android.json;

import com.google.gson.Gson;
import com.lib.utils.java.lang.StringUtils;

import java.lang.reflect.Type;

/**
 * author: Guazi.
 * time  : 2017/2/25.
 * desc  : Json解析工具类
 */


public class JsonUtils {

    private static Gson gson;

    public JsonUtils() {
        this.gson = new Gson();
    }

    /**
     * object类型转Json
     *
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * json解析获取对象
     *
     * @param json
     * @param clas
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> clas) {
        if (StringUtils.isNotEmpty(json)) {
            return gson.fromJson(json, clas);
        }
        return null;
    }

    /**
     * json解析获取对象
     *
     * @param json
     * @param typeOfT
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type typeOfT) {
        if (StringUtils.isNotEmpty(json)) {
            return gson.fromJson(json, typeOfT);
        }
        return null;
    }
}
