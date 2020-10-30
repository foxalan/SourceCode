package com.alan.threefive.application;

import android.app.Application;

import com.alan.tfive_function.database.TFDatabaseCreator;
import com.alan.tfive_function.launchstarter.TaskDispatcher;
import com.alan.threefive.task.ConfigurationTask;


/**
 * @author alan
 * function:
 * 1.数据库添加
 *    todo 外JOIN相关知识
 *
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TaskDispatcher.init(this);
        TaskDispatcher.createInstance().
                addTask(new ConfigurationTask())
                .start();

        TaskDispatcher.createInstance().await();
        TFDatabaseCreator.getInstance().init(getApplicationContext());
    }
}
