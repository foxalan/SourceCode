package com.alan.tfive_ui.recycler.adapter;

/**
 * Created by zhiwenyan on 5/25/17.
 * 多布局的支持
 */

public interface MultiTypeSupport<T> {
    int getLayoutId(T item);
}
