package com.alan.threefive.application;

import android.app.Application;

import com.alan.tfive_function.launchstarter.TaskDispatcher;
import com.alan.threefive.task.ConfigurationTask;
import com.alan.threefive.task.DatabaseTask;


/**
 * @author alan
 * function:
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TaskDispatcher.init(this);
        TaskDispatcher.createInstance()
                //全局常量
                .addTask(new ConfigurationTask(getApplicationContext()))
                //数据库
                .addTask(new DatabaseTask(getApplicationContext()))
                .start();

        TaskDispatcher.createInstance().await();
    }
}
