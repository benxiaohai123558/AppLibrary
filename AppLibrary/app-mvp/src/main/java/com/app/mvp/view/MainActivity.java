package com.app.mvp.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.mvp.R;
import com.app.mvp.model.login.LoginModel;
import com.app.mvp.presenter.LoginPresenter;

public class MainActivity extends AppCompatActivity implements ILoginView {

    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.loginPresenter = new LoginPresenter(this);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginPresenter.login("a","a");
            }
        });
    }

    @Override
    public void login() {
        Toast.makeText(this, "login success", Toast.LENGTH_LONG).show();
    }
}
