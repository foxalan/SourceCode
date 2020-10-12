package com.alan.tfive_ui.activity.status;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;

import com.alan.tfive_ui.R;


public class StatusUtil {

    private static final int FAKE_STATUS_BAR_VIEW_ID = R.id.statusbarutil_fake_status_bar_view;

    public static final int USE_DEFAULT_COLOR = -1;

    public static final int USE_CUR_COLOR = -2;


    public static int defaultColor_21 = Color.parseColor("#33000000");


    public static void setUseStatusBarColor(Activity activity, @ColorInt int color) {
        setUseStatusBarColor(activity, color, USE_CUR_COLOR);
    }


    public static void setUseStatusBarColor(Activity activity, @ColorInt int color, int surfaceColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M || (surfaceColor == USE_CUR_COLOR)) ? color : surfaceColor == USE_DEFAULT_COLOR ? defaultColor_21 : surfaceColor);
        }
    }


    @Deprecated
    public static void setTransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // after 21(5.0)
            setUseStatusBarColor(activity, Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            // between 19(4.4) and 21(5.0)
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    public static void setSystemStatus(Activity activity, boolean isTransparent, boolean isBlack) {
        int flag = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && isTransparent) {
            // after 16(4.1)
            flag |= View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isBlack) {
            // after 23(6.0)
            flag |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        }
        activity.getWindow().getDecorView().setSystemUiVisibility(flag);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // between 19(4.4) and 21(5.0)
            if (isTransparent) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }else {
                activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
            ViewGroup contentView = (ViewGroup) activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
            View childAt = contentView.getChildAt(0);
            childAt.setFitsSystemWindows(!isTransparent);
        }
    }

    public static int getStatusBarHeight(Context context) {
        return getSizeByReflection(context, "status_bar_height");
    }


    public static void hideStatusBar(Activity activity){
        View decorView = activity.getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
    }


    public static void setStatusBarTransparent(Activity activity){


        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);}
    }



    public static int getNavigationBarHeight(Context context) {
        return getSizeByReflection(context, "navigation_bar_height");
    }

    public static int getSizeByReflection(Context context, String field) {
        int size = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField(field).get(object).toString());
            size = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
    
    public static void setNavigationBar(Activity activity, @ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setNavigationBarColor(color);
        }
    }

    public static void setColor(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int statusBarAlpha) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int statusBarColor = activity.getWindow().getStatusBarColor();
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(calculateStatusColor(color, statusBarAlpha));
            activity.getWindow().getStatusBarColor();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
            if (fakeStatusBarView != null) {
                if (fakeStatusBarView.getVisibility() == View.GONE) {
                    fakeStatusBarView.setVisibility(View.VISIBLE);
                }
                fakeStatusBarView.setBackgroundColor(calculateStatusColor(color, statusBarAlpha));
            } else {
                decorView.addView(createStatusBarView(activity, color, statusBarAlpha));
            }
            setRootView(activity);
        }
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int calculateStatusColor(@ColorInt int color, int alpha) {
        if (alpha == 0) {
            return color;
        }
        float a = 1 - alpha / 255f;
        int red = color >> 16 & 0xff;
        int green = color >> 8 & 0xff;
        int blue = color & 0xff;
        red = (int) (red * a + 0.5);
        green = (int) (green * a + 0.5);
        blue = (int) (blue * a + 0.5);
        return 0xff << 24 | red << 16 | green << 8 | blue;
    }

    /**
     * 生成一个和状态栏大小相同的半透明矩形条
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    透明值
     * @return 状态栏矩形条
     */
    private static View createStatusBarView(Activity activity, @ColorInt int color, int alpha) {
      // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(calculateStatusColor(color, alpha));
        statusBarView.setId(FAKE_STATUS_BAR_VIEW_ID);
        return statusBarView;
    }

    /**
     * 设置根布局参数
     */
    private static void setRootView(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(android.R.id.content);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }
}
