package com.alan.threefive.activity.record;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alan.tfive_function.database.table.DailyRecord;
import com.alan.tfive_ui.activity.TFBaseActivity;
import com.alan.tfive_ui.recycler.group.XRecyclerView;
import com.alan.threefive.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.alan.threefive.activity.record.DailyRecordView.RECORD_TYPE_DAY;


/**
 * @author alan
 * function:
 * todo:
 * 1.添加EmptyView     --
 * 2.上拉刷新下拉加载 ----
 * 3.点击事件未添加
 * 4.UI优化
 */
public class DailyRecordActivity extends TFBaseActivity {


    private XRecyclerView mRycDailyRecord;
    private DailyRecordView dailyRecordView;
    private List<DailyRecord> dailyRecordList = new ArrayList<>();

    private TextView mTvSport;
    private TextView mTvEye;
    private TextView mTvStudy;
    private TextView mTvJet;
    private TextView mTvPhone;
    private TextView mTvTotal;

    boolean isMonth = true;

    @Override
    public int getContentLayout() {
        return R.layout.activity_daily_record;
    }

    @Override
    public void initView() {
        mRycDailyRecord = findViewById(R.id.ryc_record);
        dailyRecordView = findViewById(R.id.dailyRecordView);

        mTvSport = findViewById(R.id.tv_pay_money);
        mTvEye = findViewById(R.id.tv_pay_receiving);
        mTvStudy = findViewById(R.id.tv_pay_use_found);
        mTvJet = findViewById(R.id.tv_pay_use_serial);
        mTvPhone = findViewById(R.id.tv_pay_use_account);

        mTvTotal = findViewById(R.id.tvResult);

    }

    @Override
    public void initData() {

        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final  int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);

        dailyRecordList.addAll(DailyRecordDataHandler.getMonthDailyList(year,month));
        dailyRecordView.setDailyList(dailyRecordList,RECORD_TYPE_DAY);
        dailyRecordView.setOnChooseCallBack(new DailyRecordView.onChooseCallBack() {
            @Override
            public void onDateCallBack(DailyRecord dailyRecord) {

                Log.e("DailyRecord","onCallBack"+dailyRecord.toString());

                mTvPhone.setText(dailyRecord.pointPhone+"分");
                mTvEye.setText(dailyRecord.pointEyes+"分");
                mTvJet.setText(dailyRecord.pointJet+"分");
                mTvSport.setText(dailyRecord.pointSport+"分");
                mTvTotal.setText(dailyRecord.totalPoint+"分");
                mTvStudy.setText(dailyRecord.pointStudy+"分");
            }
        });



        findViewById(R.id.tv_month).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyRecordList.clear();
                if (isMonth){
                    dailyRecordList.addAll(DailyRecordDataHandler.getMonthDaily(year));
                    dailyRecordView.setDailyList(dailyRecordList,RECORD_TYPE_DAY);
                    isMonth = false;
                }else {
                    dailyRecordList.addAll(DailyRecordDataHandler.getMonthDailyList(year,month));
                    dailyRecordView.setDailyList(dailyRecordList,RECORD_TYPE_DAY);
                    isMonth = true;
                }
            }
        });
    }

    @Override
    public void initEvent() {
        DailyRefreshHandler.create(mRycDailyRecord,this);
    }

    public void addDailyRecord(View view){
        startActivity(new Intent(DailyRecordActivity.this,DailyRecordWriteActivity.class));
    }
}
