package com.alan.threefive.function.record;

import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alan.tfive_function.database.TFDatabaseCreator;
import com.alan.tfive_function.database.table.DailyRecord;
import com.alan.tfive_ui.activity.TFBaseActivity;
import com.alan.threefive.R;

/**
 * @author alan
 * function: 数据写入界面
 */
public class DailyRecordWriteActivity  extends TFBaseActivity {


    private EditText mEtSport;
    private EditText mEtEye;
    private EditText mEtStudy;
    private EditText mEtJet;
    private EditText mEtPhone;


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
        dailyRecord.date = "0";
        dailyRecord.pointSport = sport;
        dailyRecord.pointEyes = eye;
        dailyRecord.pointStudy = study;
        dailyRecord.pointJet = jet;
        dailyRecord.pointPhone = phone;
        dailyRecord.totalPoint = 100;

        TFDatabaseCreator.with().save(dailyRecord);
    }
}
