package com.alan.threefive;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.Process;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alan.tfive_ui.activity.TFBaseActivity;
import com.alan.threefive.aop.doubleclick.SingleClick;
import com.alan.threefive.function.record.DailyRecordActivity;

import java.lang.reflect.Method;
import java.util.List;


/**
 * 1.数据库 ----
 * 2.RecyclerView 
 * 3.网络
 * 4.图片加载
 * 5.LOG 辅助类
 */
public class MainActivity extends TFBaseActivity {

    private TextView textView;
    private boolean isEnd;


    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isEnd){
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            startActivity(new Intent(MainActivity.this,MainTestActivity.class));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private int isAllowed() {
        AppOpsManager ops = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
        try {
            int op = 10021;
            Method method = ops.getClass().getMethod("checkOpNoThrow", new Class[]{int.class, int.class, String.class});
            Integer result = (Integer) method.invoke(ops, op, Process.myUid(), getPackageName());
            return result == AppOpsManager.MODE_ALLOWED ? 0 : 1;

        } catch (Exception e) {
            return -1;
        }
    }

    /** 判断程序是否在前台运行（当前运行的程序） */
    public boolean isRunForeground() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;// 程序运行在前台
            }
        }
        return false;
    }

    /**
     * 日常记录
     * @param view
     */
    public void onDailyRecord(View view){
        startActivity(new Intent(MainActivity.this,DailyRecordActivity.class));
    }

    /**
     * Aop 双击测试
     */
    public void onAopSingleClick(){
        Log.e("AOP","AOP singleClick");

    }
}
