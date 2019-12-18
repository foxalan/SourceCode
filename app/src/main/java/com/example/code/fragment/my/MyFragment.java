package com.example.code.fragment.my;

import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.code.R;
import com.example.code.adapter.ArtRecycleViewAdapter;
import com.example.sourcecode.entity.my.MyItem;
import com.example.sourcecode.fragment.BaseBottomFragment;
import com.example.sourcecode.recycler.RefreshRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alan
 * function:
 */
public class MyFragment extends BaseBottomFragment {

    private RefreshRecyclerView refreshRecyclerView;
    private ArtRecycleViewAdapter artRecycleViewAdapter;
    private List<MyItem> myItemList;

    @Override
    public void onUserVisible() {

    }

    @Override
    public void onUserInVisible() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initViews(View view) {
        refreshRecyclerView = view.findViewById(R.id.refreshRecyclerView);

        initData();
    }

    private void initData() {
        myItemList = new ArrayList<>();
        myItemList.add(new MyItem());
        myItemList.add(new MyItem());
        artRecycleViewAdapter = new ArtRecycleViewAdapter(activity,myItemList);
        refreshRecyclerView.setLayoutManager(new LinearLayoutManager(activity));
        refreshRecyclerView.setAdapter(artRecycleViewAdapter);
    }
}
