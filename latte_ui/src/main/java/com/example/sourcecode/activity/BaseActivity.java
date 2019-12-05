package com.example.sourcecode.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author alan
 * function:
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initHeader();
        setContentView(getLayoutId());
        initViews();
        initData();
    }

    protected void initHeader() {

    }

    /**
     * 初始化布局ID
     *
     * @return
     */
    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void initData();

}
