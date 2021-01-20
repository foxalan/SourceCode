package com.alan.threefive.aop.test.demo;

public interface TestService {
    /**
     * 打印一句话  拦截方法
     * @param msg 返回信息
     */
    String print(String msg) throws InterruptedException;
    /**
     * 普通方法 不拦截
     **/
    void sendUser();
}
