package com.app.mvp.presenter;

import com.app.mvp.model.ILoginModel;
import com.app.mvp.model.login.LoginModel;
import com.app.mvp.view.ILoginView;

/**
 * author: Guazi.
 * time  : 2017/2/24.
 * desc  :
 */


public class LoginPresenter {

    private ILoginView loginView;

    private ILoginModel loginModel;

    public LoginPresenter(ILoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModel();
    }

    public void login(String userName, String pwd) {
        boolean result = loginModel.login(userName, pwd);
        if (result) {
            loginView.login();
        }
    }
}
