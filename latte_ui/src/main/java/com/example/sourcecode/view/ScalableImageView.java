package com.example.sourcecode.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author alan
 * function: 可缩放的图片
 */
public class ScalableImageView extends View {

    //图片宽度
    private static final float IMAGE_WIDTH = Utils.dpToPixel(300);

    private static final float OVER_SCALE_FACTOR = 1.5f;

    private Bitmap targetBitmap;


    public ScalableImageView(Context context) {
        super(context);
    }

    public ScalableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScalableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
