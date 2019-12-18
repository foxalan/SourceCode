package com.example.sourcecode.recycler.adapter;

;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sourcecode.R;
import com.example.sourcecode.recycler.header.HeaderViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alan
 * function:
 */
public class RefreshAdapter extends RecyclerView.Adapter {

    private List<View> headerViews = new ArrayList<>();
    private List<View> footerViews = new ArrayList<>();

    private boolean isHeaderView;
    private boolean isFooterView;

    private RecyclerView.Adapter adapter;

    private static final int HEADER_TYPE = 10001;
    private static final int FOOTER_TYPE = 10002;

    public RefreshAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }

    public void setHeaderView(View view) {
        headerViews.add(view);
    }

    public void setFooterView(View footer) {
        footerViews.add(footer);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //todo
        if (viewType == HEADER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.default_header_item,parent,false);
            return new HeaderViewHolder(view);
        } else if (viewType == FOOTER_TYPE) {
            View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.default_footer_item, parent,false);
            return new HeaderViewHolder(view);
        } else {
            return adapter.onCreateViewHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isHeaderType(position)) {

        } else if (isFooterViewType(position)) {

        } else {
            adapter.onBindViewHolder(holder, position - headerViews.size());
        }
    }

    @Override
    public int getItemCount() {
        return headerViews.size() + adapter.getItemCount() + footerViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderType(position)) {
            return HEADER_TYPE;
        }
        if (isFooterViewType(position)) {
            return FOOTER_TYPE;
        }
        return super.getItemViewType(position);
    }

    private boolean isHeaderType(int position) {
        int size = headerViews.size();
        if (position < size) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isFooterViewType(int position) {
        int headerViewSize = headerViews.size();
        int size = adapter.getItemCount();
        if (position >= (headerViewSize + size)) {
            return true;
        } else {
            return false;
        }
    }
}
