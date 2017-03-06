package com.lib.utils.android.app;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.view.Surface;

/**
 * author: Guazi.
 * time  : 2017/3/6.
 * desc  :
 */


public class WindowUtils {

    public WindowUtils() {
        throw new UnsupportedOperationException("WindowUtils can't instantiate");
    }

    /**
     * 获取当前窗口的旋转角度
     *
     * @param activity
     * @return
     */
    public static int getDisplayRotation(Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
            default:
                return 0;
        }
    }

    /**
     * 当前是否是横屏
     *
     * @param context
     * @return
     */
    public static final boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 当前是否是竖屏
     *
     * @param context
     * @return
     */
    public static final boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
