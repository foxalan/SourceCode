package com.alan.tfive_ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.alan.tfive_ui.activity.status.StatusUtil;

/**
 * @author alan
 * function:
 * 1.简化onCreateView，是否
 * 2.请求权限
 * 3.webviewActivity
 * 4.主页处理
 * 5.设置状态栏状态
 */
public abstract class TFBaseActivity extends AppCompatActivity {

    /**
     * 1.bug 使用了public的onCreate导致界面出不来
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if (isStatusBarTransparent()){
            StatusUtil.setStatusBarTransparent(this);
        }
        setContentView(getContentLayout());
        initView();
        initData();
        initEvent();
    }

    public abstract int getContentLayout();

    public abstract void initView();

    public abstract void initData();

    public abstract void initEvent();


    /**
     * 是否使用沉浸式
     * 设置透明后，整体布局会上移动
     * @return
     */
    public boolean isStatusBarTransparent(){
        return false;
    }

    /**
     * 设置statusBar颜色，整体布局不会上移动
     * @param color
     * @param statusBarAlpha
     */
    public void setStatusBarColor(int color, int statusBarAlpha){
        StatusUtil.setColor(this, color, statusBarAlpha);
    }

}
