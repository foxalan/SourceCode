package com.alan.tfive_ui.dialog.custom;

import java.io.Serializable;

/**
 * @author alan
 * function:
 */
public class CustomInfo implements Serializable {

    //标题
    private String title;
    //内容
    private String content;
    //确定
    private String confirm;
    //取消
    private String cancel;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getCancel() {
        return cancel;
    }

    public void setCancel(String cancel) {
        this.cancel = cancel;
    }
}
