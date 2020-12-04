package com.alan.threefive.task;

import android.content.Context;

import com.alan.tfive_function.database.TFDatabaseCreator;
import com.alan.tfive_function.launchstarter.task.Task;

/**
 * @author alan
 * function:
 */
public class DatabaseTask extends Task {

    private Context context;

    public DatabaseTask(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        TFDatabaseCreator.getInstance().init(context);
    }
}
