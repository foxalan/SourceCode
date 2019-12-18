package com.example.sourcecode.recycler;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcecode.recycler.adapter.RefreshAdapter;
import com.example.sourcecode.recycler.header.HeaderRefreshView;

/**
 * @author alan
 * function:
 */
public class RefreshRecyclerView extends RecyclerView {

    private RefreshAdapter refreshAdapter;
    private Context context;

    private HeaderRefreshView headerRefreshView;

    public RefreshRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initView();
    }

    private void initView() {

    }

    @Override
    public void setAdapter(@Nullable Adapter adapter) {
        if (refreshAdapter == null) {
            refreshAdapter = new RefreshAdapter(adapter);
            refreshAdapter.setHeaderView(new View(context));
            refreshAdapter.setFooterView(new View(context));
        }
        super.setAdapter(refreshAdapter);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(e);
    }
}
