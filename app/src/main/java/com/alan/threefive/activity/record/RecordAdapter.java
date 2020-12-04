package com.alan.threefive.activity.record;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.TextView;

import com.alan.tfive_function.database.table.DailyRecord;
import com.alan.tfive_ui.recycler.adapter.CommonRecycleAdapter;
import com.alan.tfive_ui.recycler.adapter.CommonViewHolder;
import com.alan.threefive.R;

import java.util.List;

/**
 * @author alan
 * function:
 */
public class RecordAdapter extends CommonRecycleAdapter<DailyRecord> {

    private static final String SPORT_TAG = "SPORT:";
    private static final String EYE_TAG = "EYE:";
    private static final String STUDY_TAG = "STUDY:";
    private static final String JET_TAG = "JET:";
    private static final String PHONE_TAG = "PHONE:";
    private static final String TOTAL_TAG = "TOTAL:";

    public RecordAdapter(Context context, List<DailyRecord> mData, int layoutId) {
        super(context, mData, layoutId);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(CommonViewHolder holder, DailyRecord dailyRecord, int position) {
        TextView tvSport = holder.getView(R.id.tv_daily_sport);
        tvSport.setText(SPORT_TAG + dailyRecord.pointSport);

        TextView tvEye = holder.getView(R.id.tv_daily_eye);
        tvEye.setText(EYE_TAG + dailyRecord.pointEyes);

        TextView tvStudy = holder.getView(R.id.tv_daily_study);
        tvStudy.setText(STUDY_TAG + dailyRecord.pointStudy);

        TextView tvJet = holder.getView(R.id.tv_daily_jet);
        tvJet.setText(JET_TAG + dailyRecord.pointJet);

        TextView tvPhone = holder.getView(R.id.tv_daily_phone);
        tvPhone.setText(PHONE_TAG + dailyRecord.pointPhone);

        TextView tvTotal = holder.getView(R.id.tv_daily_total);
        tvTotal.setText(TOTAL_TAG + dailyRecord.totalPoint);

        TextView tvDate = holder.getView(R.id.tv_daily_date);
        tvDate.setText(dailyRecord.date);
    }
}
