package com.app.mvp.model.login;

import android.text.TextUtils;

import com.app.mvp.model.ILoginModel;

/**
 * author: Guazi.
 * time  : 2017/2/24.
 * desc  :
 */


public class LoginModel implements ILoginModel {

    @Override
    public boolean login(String userName, String pwd) {
        if (TextUtils.equals(userName, pwd)) {
            return true;
        }
        return false;
    }
}
