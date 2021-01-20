package com.alan.threefive;

import android.Manifest;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.alan.tfive_function.des.Des;
import com.alan.tfive_ui.activity.TFBaseActivity;
import com.alan.threefive.activity.record.DailyRecordActivity;
import com.alan.threefive.aop.RejectManager;
import com.alan.threefive.aop.doubleclick.SingleClick;
import com.alan.threefive.aop.permission.RequestPermission;

import org.aspectj.lang.annotation.Aspect;

/**
 * 1.数据库                               ----
 * 2.RecyclerView                   --
 * 3.网络                                   -
 * 4.图片加载
 * 5.LOG 辅助类
 * 6.视频
 */
@Aspect
public class MainActivity extends TFBaseActivity {


    @Override
    public int getContentLayout() {
//        RejectManager.rejectPermission(this);
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
        Des.setDES();
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
    @SingleClick
    public void onAopSingleClick(){

        Log.e("AOP","AOP singleClick");


        // MainActivity 调 onAopSingleClick
        // 获取 自定义的 Aop拦截


        //MainActivity 应该是动态获取  ，并获取切面方法



    }
}
