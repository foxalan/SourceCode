package com.alan.threefive.aop.test.demo;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.alan.threefive.aop.test.BeanUtil;

public class TestMyProxy {


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) throws IllegalAccessException, InstantiationException, InterruptedException {
        BeanUtil beanUtil = new BeanUtil();
        beanUtil.initBean();
        TestService testService = beanUtil.getBean("testService");
        System.out.println(testService.print("wqewqeqw"));
        testService.sendUser();
    }
}
