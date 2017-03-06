package com.lib_base.controls.inter;

/**
 * author: Guazi.
 * time  : 2017/3/6.
 * desc  : 倒计时监听器
 */


public interface ICountDownListener {
    /**
     * 当倒计时开始
     */
    void onStart();

    /**
     * 当倒计时结束
     */
    void onFinish();

    /**
     * 更新
     *
     * @param currentRemainingSeconds 剩余时间
     */
    void onUpdate(int currentRemainingSeconds);
}
