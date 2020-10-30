package com.alan.tfive_ui.recycler.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alan.tfive_ui.recycler.view.HeaderView;

/**
 * @author alan
 * function:
 */
public class HeaderAndFooterAdapter extends RecyclerView.Adapter {

    private RecyclerView.Adapter adapter;

    private HeaderView headerView;
    private static final int HEAD_TYPE = 10001;

    public void setHeaderView(HeaderView headerView) {
        this.headerView = headerView;
    }
    public HeaderAndFooterAdapter(RecyclerView.Adapter adapter) {
        this.adapter = adapter;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == HEAD_TYPE){
            return new SimpleViewHolder(headerView);
        } else {
            return   adapter.onCreateViewHolder(parent,viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == 0){

        } else {
            adapter.onBindViewHolder(holder,position-1);
        }
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    private int getCount() {
        int count = 0;
        if (headerView != null) {
            count++;
        }
        count = count + adapter.getItemCount();
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (headerView != null) {
            if (position == 0) {
                return HEAD_TYPE;
            } else {
                adapter.getItemViewType(position - 1);
            }
        }
        return super.getItemViewType(position);
    }

    class  SimpleViewHolder extends RecyclerView.ViewHolder{

        public SimpleViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
