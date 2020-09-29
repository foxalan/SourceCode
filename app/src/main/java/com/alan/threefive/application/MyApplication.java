package com.alan.threefive.application;

import android.app.Application;

import com.alan.tfive_function.launchstarter.TaskDispatcher;
import com.alan.threefive.task.ConfigurationTask;


/**
 * @author alan
 * function:
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

    }
}
