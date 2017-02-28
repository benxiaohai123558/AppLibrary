package com.lib.utils.android.dimen;

import android.annotation.SuppressLint;

import com.lib.utils.Utils;
import com.lib.utils.java.constants.ConstUtils;

/**
 * author: Guazi.
 * time  : 2017/2/25.
 * desc  : 转换相关工具类
 */
public class DimenUtils {

    private DimenUtils() {
        throw new UnsupportedOperationException("ConvertUtils can't instantiate");
    }


    /**
     * 以unit为单位的时间长度转毫秒时间戳
     *
     * @param timeSpan 毫秒时间戳
     * @param unit     单位类型
     *                 <ul>
     *                 <li>{@link ConstUtils.TimeUnit#MSEC}: 毫秒</li>
     *                 <li>{@link ConstUtils.TimeUnit#SEC }: 秒</li>
     *                 <li>{@link ConstUtils.TimeUnit#MIN }: 分</li>
     *                 <li>{@link ConstUtils.TimeUnit#HOUR}: 小时</li>
     *                 <li>{@link ConstUtils.TimeUnit#DAY }: 天</li>
     *                 </ul>
     * @return 毫秒时间戳
     */
    public static long timeSpan2Millis(long timeSpan, ConstUtils.TimeUnit unit) {
        switch (unit) {
            default:
            case MSEC:
                return timeSpan;
            case SEC:
                return timeSpan * ConstUtils.SEC;
            case MIN:
                return timeSpan * ConstUtils.MIN;
            case HOUR:
                return timeSpan * ConstUtils.HOUR;
            case DAY:
                return timeSpan * ConstUtils.DAY;
        }
    }

    /**
     * 毫秒时间戳转以unit为单位的时间长度
     *
     * @param millis 毫秒时间戳
     * @param unit   单位类型
     *               <ul>
     *               <li>{@link ConstUtils.TimeUnit#MSEC}: 毫秒</li>
     *               <li>{@link ConstUtils.TimeUnit#SEC }: 秒</li>
     *               <li>{@link ConstUtils.TimeUnit#MIN }: 分</li>
     *               <li>{@link ConstUtils.TimeUnit#HOUR}: 小时</li>
     *               <li>{@link ConstUtils.TimeUnit#DAY }: 天</li>
     *               </ul>
     * @return 以unit为单位的时间长度
     */
    public static long millis2TimeSpan(long millis, ConstUtils.TimeUnit unit) {
        switch (unit) {
            default:
            case MSEC:
                return millis;
            case SEC:
                return millis / ConstUtils.SEC;
            case MIN:
                return millis / ConstUtils.MIN;
            case HOUR:
                return millis / ConstUtils.HOUR;
            case DAY:
                return millis / ConstUtils.DAY;
        }
    }

    /**
     * 毫秒时间戳转合适时间长度
     *
     * @param millis    毫秒时间戳
     *                  <p>小于等于0，返回null</p>
     * @param precision 精度
     *                  <ul>
     *                  <li>precision = 0，返回null</li>
     *                  <li>precision = 1，返回天</li>
     *                  <li>precision = 2，返回天和小时</li>
     *                  <li>precision = 3，返回天、小时和分钟</li>
     *                  <li>precision = 4，返回天、小时、分钟和秒</li>
     *                  <li>precision &gt;= 5，返回天、小时、分钟、秒和毫秒</li>
     *                  </ul>
     * @return 合适时间长度
     */
    @SuppressLint("DefaultLocale")
    public static String millis2FitTimeSpan(long millis, int precision) {
        if (millis <= 0 || precision <= 0) return null;
        StringBuilder sb = new StringBuilder();
        String[] units = {"天", "小时", "分钟", "秒", "毫秒"};
        int[] unitLen = {86400000, 3600000, 60000, 1000, 1};
        precision = Math.min(precision, 5);
        for (int i = 0; i < precision; i++) {
            if (millis >= unitLen[i]) {
                long mode = millis / unitLen[i];
                millis -= mode * unitLen[i];
                sb.append(mode).append(units[i]);
            }
        }
        return sb.toString();
    }

    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(float dpValue) {
        final float scale = Utils.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dp
     *
     * @param pxValue px值
     * @return dp值
     */
    public static int px2dp(float pxValue) {
        final float scale = Utils.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * sp转px
     *
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(float spValue) {
        final float fontScale = Utils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * px转sp
     *
     * @param pxValue px值
     * @return sp值
     */
    public static int px2sp(float pxValue) {
        final float fontScale = Utils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }
}
