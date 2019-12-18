package com.example.sourcecode.entity.my;

import com.example.sourcecode.entity.BaseItem;

/**
 * @author alan
 * function:
 */
public class MyItem extends BaseItem {

    private String title;
    private String context;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
