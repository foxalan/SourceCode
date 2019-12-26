package com.example.sourcecode.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author alan
 * function:
 */
public class OnTouchView extends View {

    public OnTouchView(Context context) {
        super(context);
    }

    public OnTouchView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OnTouchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }
}
