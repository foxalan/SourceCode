package com.example.code.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.code.R;
import com.example.sourcecode.entity.my.MyItem;

import java.util.List;


/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/8/3
 * owspace
 */
public class ArtRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int CONTENT_TYPE = 1002;
    private List<MyItem> artList ;
    private Context context;


    public ArtRecycleViewAdapter(Context context, List<MyItem> itemList) {
        this.context = context;
        this.artList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my, parent, false);
        return new ArtHolder(view);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return artList.size();
    }


    static class ArtHolder extends RecyclerView.ViewHolder {

        TextView titleTv;
        TextView authorTv;

        public ArtHolder(View itemView) {
            super(itemView);
        }
    }
}
