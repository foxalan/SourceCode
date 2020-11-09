package com.alan.threefive.function.record;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.alan.tfive_function.database.TFDatabaseCreator;
import com.alan.tfive_function.database.table.DailyRecord;
import com.alan.tfive_ui.activity.TFBaseActivity;
import com.alan.threefive.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alan
 * function:
 */
public class DailyRecordActivity extends TFBaseActivity {

    private static final String TAG = "dailyRecord";
    private List<DailyRecord> dailyRecords;


    @Override
    public int getContentLayout() {
        return R.layout.activity_daily_record;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        //获取数据库中的信息
        dailyRecords = new ArrayList<>();
        dailyRecords = TFDatabaseCreator.with()
                .withTable(DailyRecord.class)
                .applySearchAsList();
        if (dailyRecords.size() == 0){
            Log.e(TAG,"current record is null");
        }else {
            for (DailyRecord dailyRecord:dailyRecords){
                Log.e(TAG,dailyRecord.toString());
            }
        }

    }

    @Override
    public void initEvent() {

    }

    public void addDailyRecord(View view){
        startActivity(new Intent(DailyRecordActivity.this,DailyRecordWriteActivity.class));
    }
}
