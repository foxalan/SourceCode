package com.alan.tfive_ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author alan
 * function:
 */
public class MyTextView extends View {

    // 直接new 的时候 调用
    public MyTextView(Context context) {
        super(context);
        //TextView textView = new TextView(context)
    }

    //在xml中使用的时候，不使用style
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //在xml中使用，使用style
    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        switch (widthMode) {
            case MeasureSpec.AT_MOST:
                //warp_content 时候调用

                break;
            case MeasureSpec.EXACTLY:
                //match_parent ，设置精确的值

                break;
            case MeasureSpec.UNSPECIFIED:
                //尽可能长，ScrollView

                break;
            default:
                break;

        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
