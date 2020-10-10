package com.alan.tfive_ui.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author alan
 * function:
 */
public class ScreenUtils {

    private ScreenUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static int getScreenWidth(Context var0) {
        WindowManager var1 = (WindowManager)var0.getSystemService("window");
        DisplayMetrics var2 = new DisplayMetrics();
        var1.getDefaultDisplay().getMetrics(var2);
        return var2.widthPixels;
    }

    public static int getScreenHeight(Context var0) {
        WindowManager var1 = (WindowManager)var0.getSystemService("window");
        DisplayMetrics var2 = new DisplayMetrics();
        var1.getDefaultDisplay().getMetrics(var2);
        return var2.heightPixels;
    }

}
