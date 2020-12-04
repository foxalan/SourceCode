package com.alan.threefive.task;

import android.content.Context;

import com.alan.tfive_function.global.Latte;
import com.alan.tfive_function.launchstarter.task.Task;

/**
 * @author alan
 * function: 全局常量配置
 */
public class ConfigurationTask extends Task {

    private Context context;
    private static final String API_HOST = "";

    public ConfigurationTask(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        Latte.init(context)
                .withWebHost(API_HOST)
                .configure();
    }

    @Override
    public boolean needWait() {
        return true;
    }
}
