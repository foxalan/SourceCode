package com.alan.threefive.fragment.home;

import android.view.View;

import com.alan.tfive_ui.fragment.BottomItemFragment;
import com.alan.tfive_ui.recycler.group.XRecyclerView;
import com.alan.threefive.R;

/**
 * @author alan
 * function: 首页
 */
public class HomeFragment extends BottomItemFragment {

    private XRecyclerView mRycHome;

    @Override
    public void initAction() {

    }

    @Override
    public void initViews(View view) {
        mRycHome = view.findViewById(R.id.ryc_home);
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_home;
    }
}
