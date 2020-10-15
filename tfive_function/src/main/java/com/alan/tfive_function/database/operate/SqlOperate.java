package com.alan.tfive_function.database.operate;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.alan.tfive_function.database.conditionbuilder.ConditionBuilder;
import com.alan.tfive_function.database.helper.ReflectTools;
import com.alan.tfive_function.database.sqlite.SQL;
import com.alan.tfive_function.database.sqlite.SQLBuilder;
import com.alan.tfive_function.database.table.Entity;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 * @author alan
 * function: 数据库操作
 */
public class SqlOperate {

    private static final String TAG = "SQL OPERATE";
    /**
     * 数据库
     */
    private SQLiteDatabase sqLiteDatabase;

    public SqlOperate(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }


    /**
     * 保存数据
     *
     * @param table
     * @param <T>
     * @return
     */
    public <T extends Entity> long save(T table) {
        String tableName = ReflectTools.getTableName(table.getClass());
        try {
            return sqLiteDatabase.insert(tableName, null, table.toContentValues());
        } catch (SQLiteException e){
            Log.e(TAG, "save(T) error: " + getTraceInfo(e));
            return 0;
        }
    }

    /**
     * 保存所有的数据
     * @param tables
     * @param <T>
     * @return
     */
    public <T extends Entity> int saveAll(List<T> tables) {

        try {
            sqLiteDatabase.beginTransaction();
            for (T table : tables) {
                SQL sql = SQLBuilder.buildInsertSQL(table);
                if (sql != null) {
                    sqLiteDatabase.execSQL(sql.getSql(), sql.getBindArgsAsArray());
                }
            }
            sqLiteDatabase.setTransactionSuccessful();
            return tables.size();
        } catch (SQLiteException e) {
            Log.e(TAG, "saveAll() error: " + getTraceInfo(e));
            return 0;
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    /**
     * 查询对应
     * @param tableClass
     * @param <T>
     * @return
     */
    public <T extends Entity> ConditionBuilder<T> withTable(Class<T> tableClass) {
        return new ConditionBuilder<T>(sqLiteDatabase).withTable(tableClass);
    }






    /**
     * 打印错误信息
     * @param e
     * @return
     */
    public static String getTraceInfo(Throwable e) {
        PrintWriter printWriter = null;
        Writer info = new StringWriter();
        try {
            printWriter = new PrintWriter(info);
            e.printStackTrace(printWriter);
            Throwable cause = e.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            return info.toString();
        } finally {
            if (printWriter != null) {
                printWriter.close();
            }
        }
    }
}
