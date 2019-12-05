package com.example.code;

import android.graphics.Color;
import android.os.Bundle;


import com.example.code.fragment.home.HomeFragment;
import com.example.code.fragment.my.MyFragment;
import com.example.sourcecode.activity.BaseMainActivity;
import com.example.sourcecode.entity.PageItemBean;


import java.util.ArrayList;



public class MainActivity extends BaseMainActivity {

    public static final String RETROFIT = "retrofit";

    private final String selectTextColor = "#8295FE";// 导航栏默认选择字体颜色


    @Override
    public int defaultSelected() {
        return 0;
    }

    @Override
    public ArrayList<PageItemBean> pageItemBeans() {
        ArrayList<PageItemBean> pageItemBeans = new ArrayList<>();
        pageItemBeans.add(new PageItemBean(R.mipmap.icon_home_selected,R.mipmap.icon_home_normal,"首页",new HomeFragment()));
        pageItemBeans.add(new PageItemBean(R.mipmap.icon_me_selected,R.mipmap.icon_me_nromal,"我的",new MyFragment()));
        return pageItemBeans;
    }

    @Override
    public int selectedColor() {
        return Color.parseColor(selectTextColor);
    }
}
