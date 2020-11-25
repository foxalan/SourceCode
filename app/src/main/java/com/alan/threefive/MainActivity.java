package com.alan.threefive;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.alan.tfive_ui.activity.TFBaseActivity;
import com.alan.threefive.function.record.DailyRecordActivity;

/**
 * 1.数据库                               ----
 * 2.RecyclerView                   --
 * 3.网络                                   -
 * 4.图片加载
 * 5.LOG 辅助类
 * 6.视频
 */
public class MainActivity extends TFBaseActivity {



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
