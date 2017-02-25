package com.lib.utils.java.lang;

import android.os.Build;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * author: Guazi.
 * time  : 2017/2/25.
 * desc  : 判空相关工具类
 */


public class EmptyUtils {

    private EmptyUtils() {
        throw new UnsupportedOperationException("EmptyUtils can't instantiate");
    }

    /**
     * 判断对象是否为空或者空值
     *
     * @param obj 对象
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String && ((String) obj).isEmpty()) {
            return true;
        } else if (obj.getClass().isArray() && Array.getLength(obj) == 0) {
            return true;
        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;
        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;
        } else if (obj instanceof SparseArray && ((SparseArray) obj).size() == 0) {
            return true;
        } else if (obj instanceof SparseBooleanArray && ((SparseBooleanArray) obj).size() == 0) {
            return true;
        } else if (obj instanceof SparseIntArray && ((SparseIntArray) obj).size() == 0) {
            return true;
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            if (obj instanceof SparseLongArray && ((SparseLongArray) obj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否不为空或者空值
     *
     * @param obj 对象
     * @return
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }
}
