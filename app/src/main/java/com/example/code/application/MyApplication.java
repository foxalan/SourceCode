package com.example.code.application;

import android.app.Application;

/**
 * @author alan
 * function:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initConfig();
    }

    /**
     * 初始化常量
     */
    private void initConfig() {

    }
}
