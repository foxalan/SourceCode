package com.alan.threefive.aop.test.demo;

import com.alan.threefive.aop.test.MyAopAnnotation;

public class TestServiceImpl implements TestService {

    @MyAopAnnotation
    @Override
    public String print(String msg) throws InterruptedException {
        System.out.println("我执行了，参数是："+msg);
        Thread.sleep(5000);
        return msg;
    }

    @Override
    public void sendUser() {
        System.out.println("----发送信息---");
    }
}