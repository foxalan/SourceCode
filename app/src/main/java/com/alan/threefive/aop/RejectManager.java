package com.alan.threefive.aop;

import android.view.View;

import com.alan.threefive.aop.permission.RequestPermission;
import com.alan.threefive.aop.permission.ResId;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class RejectManager {

    /**
     * 权限请求
     *
     * @param object
     */
    public static void rejectPermission(Object object) {
        Class<?> targetClass = object.getClass();
        RequestPermission requestPermission = targetClass.getAnnotation(RequestPermission.class);
        //加载布局
        if (requestPermission != null){
            int layoutId = requestPermission.value();
            Method method = null;
            try {
                method = targetClass.getMethod("setContentView",int.class);
                method.invoke(object,layoutId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        //加载变量
        Field[] fields = targetClass.getFields();
        for (Field field:fields){
            ResId resId = field.getAnnotation(ResId.class);
            if (resId!=null){
                int id  = resId.value();
                try {
                    Method method = targetClass.getMethod("findViewById",int.class);
                    View view = (View) method.invoke(object,id);
                    field.setAccessible(true);
                    field.set(object,view);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
