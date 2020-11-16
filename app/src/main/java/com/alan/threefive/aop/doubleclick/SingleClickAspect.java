package com.alan.threefive.aop.doubleclick;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.Calendar;

/**
 * @author alan 切面编程
 * function:
 * (1).防重复点击
 * (2).判断是否登录
 * (3).动态权限请求
 */

public class SingleClickAspect {

    private long lastClickTime = 0;
    private long MIN_CLICK_DELAY_TIME = 2000;

    //@Pointcut来标识所要寻找的切点，就是我们定义的@ SingleClick注解
    @Pointcut("execution(@com.alan.threefive.aop.doubleclick.SingleClick * *(..))")//方法切入点
    void methodAnnotated() {

    }

    /**
     * joinPoint.proceed() 执行注解所标识的代码
     * @After 可以在方法前插入代码
     * @Before 可以在方法后插入代码
     * @Around 可以在方法前后各插入代码
     */
    @Around("methodAnnotated()")
    //@Throws这个注解不必在意，这个是kotlin的注解，标识该方法可以抛出异常
    void aroundJoinPoint(ProceedingJoinPoint joinPoint) {

//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        SingleClick checkLogin = signature.getMethod().getAnnotation(SingleClick.class);
        //获取系统当前时间
        long currentTime = Calendar.getInstance().getTimeInMillis();

        //当前时间-上次记录时间>过滤的时间 过滤掉600毫秒内的连续点击
        //表示该方法可以执行
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            //将刚进入方法的时间赋值给上次点击时间
            lastClickTime = currentTime;
            //执行原方法
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }
}
