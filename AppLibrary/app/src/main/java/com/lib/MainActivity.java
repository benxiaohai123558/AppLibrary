package com.lib;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.lib.logger.Logger;
import com.lib.logger.inter.IUploadLogService;
import com.lib.logger.log.AndroidLogger;
import com.lib.logger.log.JavaLogger;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IUploadLogService {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.init().showLogger().setLogger(new AndroidLogger()).setPrintFile(true).setDate(2);
        Logger.clearLogFile();

        List<String> lists = new ArrayList<>();
        lists.add("a");
        lists.add("b");
        Logger.t("MainActivity").i("Hello Logger");
        Logger.t("Logger").d("Hello Logger");
        Logger.t("a").w("a", "Hello Logger");
        Logger.wtf("Hello Logger");
        Logger.v("Hello Logger");

        Logger.i(lists);
        Logger.i();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int i = 10 / 0;
                } catch (Exception e) {
                    Logger.e(e);
                }
            }
        }).start();
    }

    @Override
    public boolean uploadLog(String tag, String logValue) {
        Toast.makeText(this, "upload Log", Toast.LENGTH_LONG).show();
        return false;
    }
}
