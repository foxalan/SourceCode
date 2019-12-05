package com.example.sourcecode.entity;

import com.example.sourcecode.fragment.BaseBottomFragment;

/**
 * @author alan
 * function:
 */
public class PageItemBean {

    private int imgSelectedId;
    private int imgUnSelectedId;
    private String title;
    private BaseBottomFragment bottomFragment;

    public PageItemBean(int imgSelectedId, int imgUnSelectedId, String title, BaseBottomFragment bottomFragment) {
        this.imgSelectedId = imgSelectedId;
        this.imgUnSelectedId = imgUnSelectedId;
        this.title = title;
        this.bottomFragment = bottomFragment;
    }

    public int getImgSelectedId() {
        return imgSelectedId;
    }

    public void setImgSelectedId(int imgSelectedId) {
        this.imgSelectedId = imgSelectedId;
    }

    public int getImgUnSelectedId() {
        return imgUnSelectedId;
    }

    public void setImgUnSelectedId(int imgUnSelectedId) {
        this.imgUnSelectedId = imgUnSelectedId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BaseBottomFragment getBottomFragment() {
        return bottomFragment;
    }

    public void setBottomFragment(BaseBottomFragment bottomFragment) {
        this.bottomFragment = bottomFragment;
    }
}
