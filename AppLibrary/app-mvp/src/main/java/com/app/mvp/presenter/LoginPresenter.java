package com.app.mvp.presenter;

import com.app.mvp.model.ILoginModel;
import com.app.mvp.model.login.LoginModel;
import com.app.mvp.view.ILoginView;
import com.app.mvp.view.MainActivity;

/**
 * author: Guazi.
 * time  : 2017/2/24.
 * desc  :
 */


public class LoginPresenter {

    private MainActivity activity;

    private ILoginView loginView;

    private ILoginModel loginModel;

    public LoginPresenter(ILoginView loginView, MainActivity activity) {
        this.loginView = loginView;
        this.activity = activity;
        loginModel = new LoginModel();
    }

    public void login(final String userName, final String pwd) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean result = loginModel.login(userName, pwd);
                if (result) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            loginView.login();
                        }
                    });
                }
            }
        }).start();
    }
}
