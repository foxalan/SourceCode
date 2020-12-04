package com.alan.threefive.activity.record;

import android.view.View;
import android.widget.EditText;


import com.alan.tfive_function.database.TFDatabaseCreator;
import com.alan.tfive_function.database.table.DailyRecord;
import com.alan.tfive_ui.activity.TFBaseActivity;
import com.alan.threefive.R;

import java.util.Calendar;

/**
 * @author alan
 * function: 数据写入界面
 * todo:
 * 1.UI优化
 */
public class DailyRecordWriteActivity  extends TFBaseActivity {


    private EditText mEtSport;
    private EditText mEtEye;
    private EditText mEtStudy;
    private EditText mEtJet;
    private EditText mEtPhone;

    private static final int POINT_SPORT = 1;
    private static final int POINT_JET = -20;
    private static final int POINT_PHONE = -2;

    @Override
    public int getContentLayout() {
        return R.layout.activty_daily_record_write;
    }

    @Override
    public void initView() {
        mEtEye = findViewById(R.id.et_eye_number);
        mEtSport = findViewById(R.id.et_sport_number);
        mEtStudy = findViewById(R.id.et_study_number);
        mEtJet = findViewById(R.id.et_jet_number);
        mEtPhone = findViewById(R.id.et_phone_number);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initEvent() {

    }

    public void onSaveRecord(View view){

        int sport = Integer.parseInt(mEtSport.getText().toString());
        int eye = Integer.parseInt(mEtEye.getText().toString());
        int study = Integer.parseInt(mEtStudy.getText().toString());
        int jet = Integer.parseInt(mEtJet.getText().toString());
        int phone = Integer.parseInt(mEtPhone.getText().toString());

        DailyRecord dailyRecord = new DailyRecord();
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH)+1;
        int day = c.get(Calendar.DAY_OF_MONTH);
        dailyRecord.date = year+"-"+month+"-"+day;
        dailyRecord.pointSport = sport;
        dailyRecord.pointEyes = eye;
        dailyRecord.pointStudy = study;
        dailyRecord.pointJet = jet;
        dailyRecord.pointPhone = phone;
        dailyRecord.totalPoint = (sport+eye+study)*POINT_SPORT+jet*POINT_JET+phone*POINT_PHONE;

        TFDatabaseCreator.with().save(dailyRecord);
    }
}
