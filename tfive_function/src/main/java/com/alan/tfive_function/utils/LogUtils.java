package com.alan.tfive_function.utils;


import java.util.concurrent.ExecutorService;

public class LogUtils {

    private static ExecutorService sExecutorService;

    public static void setExecutor(ExecutorService executorService){
        sExecutorService = executorService;
    }

    public static final String TAG = "performance";

    public static void i(String msg){
//        if(Utils.isMainProcess(PerformanceApp.getApplication())){
//            Log.i(TAG,msg);
//        }
//        // 异步
//        if(sExecutorService != null){
////            sExecutorService.execute();
//        }
    }

}
