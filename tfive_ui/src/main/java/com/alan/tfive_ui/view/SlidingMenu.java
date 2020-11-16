package com.alan.tfive_ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.core.view.ViewCompat;

import com.alan.tfive_ui.R;
import com.alan.tfive_ui.util.ScreenUtils;

import static com.alan.tfive_ui.util.ScreenUtils.getScreenWidth;

/**
 * @author alan
 * function:
 * 解压密码：VIPC6.COM_hJt474864U
 */
public class SlidingMenu extends HorizontalScrollView {

    private View mMenuView;
    private View mContentView;

    private int mMenuWidth;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 初始化自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);

        float rightMargin = array.getDimension(
                R.styleable.SlidingMenu_menuRightMargin, ScreenUtils.dip2px(context, 150));
        // 菜单页的宽度是 = 屏幕的宽度 - 右边的一小部分距离（自定义属性）
        mMenuWidth = (int) (getScreenWidth(context) - rightMargin);
        array.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        /**
         * 1.设置侧滑菜单的宽度
         */
        // 指定宽高 1.内容页的宽度是屏幕的宽度
        // 获取LinearLayout
        ViewGroup container = (ViewGroup) getChildAt(0);

        int childCount = container.getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("只能放置两个子View!");
        }

        mMenuView = container.getChildAt(0);
        // 设置只能通过 LayoutParams ，
        ViewGroup.LayoutParams menuParams = mMenuView.getLayoutParams();
        menuParams.width = mMenuWidth;
        // 7.0 以下的手机必须采用下面的方式
        mMenuView.setLayoutParams(menuParams);

        // 2.菜单页的宽度是 屏幕的宽度 - 右边的一小部分距离（自定义属性）
        mContentView = container.getChildAt(1);
        ViewGroup.LayoutParams contentParams = mContentView.getLayoutParams();
        contentParams.width = getScreenWidth(getContext());
        mContentView.setLayoutParams(contentParams);
        // 2. 初始化进来是关闭 发现没用

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        /**
         * 2.滑动出左边菜单的位置
         */
        scrollTo(mMenuWidth, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        /**
         * 3.手指抬起时，打开或关闭Menu
         */
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            int moveWidth = getScrollX();

            if (moveWidth > mMenuWidth / 2) {
                closeMenu();
            } else {
                openMenu();
            }
            return true;
        }

        return super.onTouchEvent(ev);
    }

    private void openMenu() {
        // smoothScrollTo 有动画
        smoothScrollTo(0, 0);
    }

    private void closeMenu() {
        smoothScrollTo(mMenuWidth, 0);
    }

    /**
     * 4.缩放
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        float rate = 1.0f*l / mMenuWidth;
        float scale = (float) (0.7 + 0.3 * rate);
        Log.e("scroll", l + "==="+scale+"==="+rate+"==="+mMenuWidth);
        mContentView.setScaleX(scale);
        mContentView.setScaleY(scale);

        // 菜单的缩放和透明度
        // 透明度是 半透明到完全透明  0.5f - 1.0f
        float leftAlpha = 0.5f + (1-rate)*0.5f;
        ViewCompat.setAlpha(mMenuView,leftAlpha);
        // 缩放 0.7f - 1.0f
        float leftScale = 0.7f + (1-rate)*0.3f;
        ViewCompat.setScaleX(mMenuView,leftScale);
        ViewCompat.setScaleY(mMenuView, leftScale);

        // 最后一个效果 退出这个按钮刚开始是在右边，安装我们目前的方式永远都是在左边
        // 设置平移，先看一个抽屉效果
        // ViewCompat.setTranslationX(mMenuView,l);
        // 平移 l*0.7f
        ViewCompat.setTranslationX(mMenuView, 0.25f*l);
    }
}
