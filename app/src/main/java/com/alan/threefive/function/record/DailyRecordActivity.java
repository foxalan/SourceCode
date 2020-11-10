package com.alan.threefive.function.record;

import android.content.Intent;
import android.view.View;
import com.alan.tfive_ui.activity.TFBaseActivity;
import com.alan.tfive_ui.recycler.group.XRecyclerView;
import com.alan.threefive.R;


/**
 * @author alan
 * function:
 * todo:
 * 1.添加EmptyView
 * 2.上拉刷新下拉加载
 */
public class DailyRecordActivity extends TFBaseActivity {

    private static final String TAG = "dailyRecord";
    private XRecyclerView mRycDailyRecord;


    @Override
    public int getContentLayout() {
        return R.layout.activity_daily_record;
    }

    @Override
    public void initView() {
        mRycDailyRecord = findViewById(R.id.ryc_record);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {
        DailyRefreshHandler.create(mRycDailyRecord,this);
    }

    public void addDailyRecord(View view){
        startActivity(new Intent(DailyRecordActivity.this,DailyRecordWriteActivity.class));
    }
}
