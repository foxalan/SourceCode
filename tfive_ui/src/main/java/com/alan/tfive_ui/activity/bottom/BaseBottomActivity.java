package com.alan.tfive_ui.activity.bottom;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.alan.tfive_ui.R;
import com.alan.tfive_ui.activity.TFBaseActivity;
import com.alan.tfive_ui.fragment.BottomItemFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author alan
 * function:
 * todo: 如何设置超出部分
 */
public abstract class BaseBottomActivity extends TFBaseActivity implements View.OnClickListener {

    private static final String TAG = "BottomActivity";

    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final ArrayList<BottomItemFragment> ITEM_DELEGATES = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BottomItemFragment> ITEMS = new LinkedHashMap<>();
    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
    private int mClickedColor = Color.RED;

    private LinearLayoutCompat mBottomBar = null;

    public abstract LinkedHashMap<BottomTabBean, BottomItemFragment> setItems(ItemBuilder builder);

    public ArrayList<BottomItemFragment> getItemDelegates() {
        return ITEM_DELEGATES;
    }

    @Override
    public int getContentLayout() {
        return R.layout.delegate_bottom;
    }

    public abstract int setIndexDelegate();

    public abstract int setClickedColor();

    private BottomItemFragment lastFragment;

    @Override
    public boolean isStatusBarTransparent() {
        return false;
    }

    @Override
    public void initView() {
        setStatusBarColor(Color.RED, 0);

        mIndexDelegate = setIndexDelegate();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }

        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean, BottomItemFragment> items = setItems(builder);
        Log.e(TAG,"SIZE"+items.toString());
        ITEMS.putAll(items);
        for (Map.Entry<BottomTabBean, BottomItemFragment> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BottomItemFragment value = item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }

        onBindView();
        triggerFragment(ITEM_DELEGATES.get(setIndexDelegate()));
    }

    public void onBindView() {
        mBottomBar = findViewById(R.id.bottom_bar);
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {
            LayoutInflater.from(this).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            //设置每个item的点击事件
            item.setTag(i);
            item.setOnClickListener(this);
            final ImageView itemIcon = (ImageView) item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean = TAB_BEANS.get(i);
            //初始化数据
            itemIcon.setImageResource(bean.getRES_UNSEL_ID());
            itemTitle.setText(bean.getTitle());
            if (i == mIndexDelegate) {
                itemIcon.setImageResource(bean.getRES_SEL_ID());
                itemTitle.setTextColor(mClickedColor);
            }
            if (TAB_BEANS.get(i).isCenter()){
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) itemTitle.getLayoutParams();
                layoutParams.height = 280;
                itemIcon.setLayoutParams(layoutParams);
                itemIcon.setImageResource(bean.getCENTER_RES_ID());
            }

        }
    }

    /**
     * 切换fragment
     * @param currentFragment
     */
    private void triggerFragment(BottomItemFragment currentFragment) {
        if (currentFragment == lastFragment) {
            return;
        }
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!fragmentManager.getFragments().contains(currentFragment)) {
            if (lastFragment == null) {
                fragmentTransaction.add(R.id.bottom_bar_delegate_container, currentFragment).
                        commit();
            } else {
                fragmentTransaction.add(R.id.bottom_bar_delegate_container, currentFragment)
                        .hide(lastFragment).commit();
            }
        } else {
            fragmentTransaction.show(currentFragment)
                    .hide(lastFragment)
                    .commit();
        }
        lastFragment = currentFragment;
    }


    private void resetColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final ImageView itemIcon = (ImageView) item.getChildAt(0);
            itemIcon.setImageResource(TAB_BEANS.get(i).getRES_UNSEL_ID());
            final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    public void changeColor(int tabIndex) {
        resetColor();
        final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(tabIndex);
        final ImageView itemIcon = (ImageView) item.getChildAt(0);
        itemIcon.setImageResource(TAB_BEANS.get(tabIndex).getRES_SEL_ID());
        final AppCompatTextView itemTitle = (AppCompatTextView) item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);
    }

    @Override
    public void onClick(View v) {
        final int tabIndex = (int) v.getTag();
        changeColor(tabIndex);
        //注意先后顺序
        mCurrentDelegate = tabIndex;
        triggerFragment(ITEM_DELEGATES.get(tabIndex));
    }
}
