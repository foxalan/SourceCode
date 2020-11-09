package com.alan.threefive.function.record;

import android.content.Context;

import com.alan.tfive_function.database.table.DailyRecord;
import com.alan.tfive_ui.recycler.adapter.CommonRecycleAdapter;
import com.alan.tfive_ui.recycler.adapter.CommonViewHolder;

import java.util.List;

/**
 * @author alan
 * function:
 */
public class RecordAdapter extends CommonRecycleAdapter<DailyRecord> {


    public RecordAdapter(Context context, List<DailyRecord> mData, int layoutId) {
        super(context, mData, layoutId);
    }

    @Override
    protected void convert(CommonViewHolder holder, DailyRecord dailyRecord, int position) {

    }
}
