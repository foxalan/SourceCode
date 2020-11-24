package com.alan.tfive_ui.view.dispatch;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author alan
 * function: View的事件分发
 */
public class ViewDispatch extends View {

    public static final String TAG = "dispatch";

    public ViewDispatch(Context context) {
        super(context);
    }

    public ViewDispatch(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewDispatch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        Log.e(TAG,"view dispatch "+event.getAction());
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"view onTouch "+event.getAction());
        return super.onTouchEvent(event);
    }
}
