package com.example.sourcecode.activity;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sourcecode.R;
import com.example.sourcecode.entity.PageItemBean;
import com.example.sourcecode.fragment.BaseBottomFragment;

import java.util.ArrayList;

/**
 * @author alan
 * function:
 */
public abstract class BaseMainActivity extends BaseActivity {

    private LinearLayout bottomContainer;

    private ArrayList<PageItemBean> pageItemBeans;
    private ArrayList<ImageView> bottomImageViewList = new ArrayList<>();
    private ArrayList<TextView> bottomTextViewList = new ArrayList<>();

    private BaseBottomFragment lastFragment;
    private BaseBottomFragment curFragment;

    private final String textRbColor = "#FFA6A9BA"; //默认颜色


    @Override
    public int getLayoutId() {
        return R.layout.delegate_bottom;
    }

    @Override
    public void initViews() {
        bottomContainer = findViewById(R.id.bottom_bar);
    }

    @Override
    public void initData() {
        pageItemBeans = pageItemBeans();
        if (pageItemBeans == null) {
            throw new IllegalStateException("page item can not be null");
        }
        initBottomBar();
        onPageSelected(defaultSelected());
        transFragment(defaultSelected());
    }

    private void initBottomBar() {
        bottomImageViewList.clear();
        bottomTextViewList.clear();
        for (int i = 0; i < pageItemBeans.size(); i++) {
            final int position = i;
            PageItemBean pageItemBean = pageItemBeans.get(i);
            View bottomItemView = LayoutInflater.from(this).inflate(R.layout.bottom_item_icon_text_layout, bottomContainer, false);
            TextView tvTile = bottomItemView.findViewById(R.id.tv_bottom_item);
            ImageView ivIcon = bottomItemView.findViewById(R.id.icon_bottom_item);
            tvTile.setText(pageItemBean.getTitle());
            ivIcon.setImageResource(pageItemBean.getImgUnSelectedId());
            bottomItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onPageSelected(position);
                    transFragment(position);
                }
            });
            bottomContainer.addView(bottomItemView);
            bottomImageViewList.add(ivIcon);
            bottomTextViewList.add(tvTile);
        }
    }

    private void onPageSelected(int position) {
        resetImageAndText();
        bottomTextViewList.get(position).setTextColor(selectedColor());
        bottomImageViewList.get(position).setImageResource(pageItemBeans.get(position).getImgSelectedId());
    }

    /**
     * 重置
     */
    private void resetImageAndText() {
        for (TextView tvTitle : bottomTextViewList) {
            tvTitle.setTextColor(Color.parseColor(textRbColor));
        }

        for (int i = 0; i < bottomImageViewList.size(); i++) {
            ImageView ivIcon = bottomImageViewList.get(i);
            ivIcon.setImageResource(pageItemBeans.get(i).getImgUnSelectedId());
        }
    }

    private void transFragment(int position) {
        curFragment = pageItemBeans.get(position).getBottomFragment();
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (!fragmentManager.getFragments().contains(curFragment)) {
            if (lastFragment == null) {
                fragmentTransaction.add(R.id.bottom_bar_delegate_container, curFragment).
                        commit();
            } else {
                fragmentTransaction.add(R.id.bottom_bar_delegate_container, curFragment)
                        .hide(lastFragment).commit();
            }
        } else {
            fragmentTransaction.show(curFragment)
                    .hide(lastFragment)
                    .commit();
        }
        lastFragment = curFragment;
    }


    /**
     * 默认被选中的fragment
     *
     * @return
     */
    public abstract int defaultSelected();

    /**
     * 获取首页的数据
     *
     * @return
     */
    public abstract ArrayList<PageItemBean> pageItemBeans();

    /**
     * 选中之后的颜色
     *
     * @return
     */
    public abstract int selectedColor();
}
