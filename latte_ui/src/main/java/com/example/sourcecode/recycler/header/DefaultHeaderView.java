package com.example.sourcecode.recycler.header;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author alan
 * function:
 */
public class DefaultHeaderView extends View implements HeaderRefreshView {


    public DefaultHeaderView(Context context) {
        super(context);
    }

    public DefaultHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DefaultHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void changeState(int state, int move) {

    }

    @Override
    public void setState(int state) {

    }
}
