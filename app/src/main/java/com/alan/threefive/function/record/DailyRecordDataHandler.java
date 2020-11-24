package com.alan.threefive.function.record;

import android.util.Log;

import com.alan.tfive_function.database.TFDatabaseCreator;
import com.alan.tfive_function.database.table.DailyRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alan
 * function: 日常数据获取类
 * 1.拿到所有年份的信息
 * 2.拿到
 */
public class DailyRecordDataHandler {


    public static final int TYPE_DAY = 0;
    public static final int TYPT_MONTH = 1;
    public static final int TYPE_YEAR = 2;


    /**
     * 获取所有的日志
     *
     * @return
     */
    public static List<DailyRecord> getAllDailyList() {
        List<DailyRecord> dailyRecords = new ArrayList<>();
        dailyRecords.addAll(TFDatabaseCreator.with().withTable(DailyRecord.class).applySearchAsList());
        return dailyRecords;
    }

    public static List<DailyRecord> getAllDailyListYear() {

        List<DailyRecord> dailyRecords = getAllDailyList();

        for (DailyRecord dailyRecord : dailyRecords) {
            String date = dailyRecord.date;
            String[] str = date.split("-");
            int year = Integer.parseInt(str[0]);
            int month = Integer.parseInt(str[1]);
            int day = Integer.parseInt(str[2]);
        }

        return dailyRecords;
    }

    /**
     * 得到 年 月 的日志
     *
     * @param targetYear
     * @param targetMonth
     * @return
     */
    public static List<DailyRecord> getMonthDailyList(int targetYear, int targetMonth) {
        List<DailyRecord> dailyRecords = getAllDailyList();

        List<DailyRecord> monthRecords = new ArrayList<>();
        for (DailyRecord dailyRecord : dailyRecords) {
            String date = dailyRecord.date;
            String[] str = date.split("-");
            int year = Integer.parseInt(str[0]);
            int month = Integer.parseInt(str[1]);
            int day = Integer.parseInt(str[2]);
            if (year == targetYear && month == targetMonth) {
                monthRecords.add(dailyRecord);
            }
            Log.e("DailyRecord",dailyRecord.toString()+"===="+targetYear+"==="+targetMonth);
        }
        return monthRecords;
    }

    /**
     * 得到 年 月 的日志
     * 以月做为统计的日志
     *
     * @param targetYear
     * @return
     */
    public static List<DailyRecord> getMonthDaily(int targetYear) {
        List<DailyRecord> dailyRecords = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            List<DailyRecord> monthRecord = getMonthDailyList(targetYear,i);
            if (monthRecord.size() == 0){
                continue;
            }
            DailyRecord month = new DailyRecord();
            month.pointPhone = 0;
            month.pointStudy = 0;
            month.pointEyes = 0;
            month.pointJet = 0;
            month.pointSport = 0;
            month.totalPoint = 0;
            month.date = targetYear+"-"+i;
            for (DailyRecord dailyRecord :monthRecord){
                if (dailyRecord.pointPhone == null){
                    continue;
                }
                month.pointPhone = month.pointPhone+dailyRecord.pointPhone;
                month.pointStudy = month.pointStudy+dailyRecord.pointStudy;
                month.pointEyes = month.pointEyes+dailyRecord.pointEyes;
                month.pointJet = month.pointJet+dailyRecord.pointJet;
                month.pointSport = month.pointSport+dailyRecord.pointSport;
                month.totalPoint = month.totalPoint +dailyRecord.totalPoint;
                Log.e("DailyRecord",month.toString()+"===="+i);
            }
            dailyRecords.add(month);
        }

        return dailyRecords;
    }

}
