package com.alan.tfive_function.database.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.alan.tfive_function.database.annotation.Column;
import com.alan.tfive_function.database.annotation.Table;
import com.alan.tfive_function.database.helper.ReflectTools;
import com.alan.tfive_function.database.table.DailyRecord;
import com.alan.tfive_function.database.table.Entity;
import com.alan.tfive_function.database.table.UserInfo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * @author alan
 * function: 数据创建执行者
 */

public class SQLiteExecute {

    private final List<Class<? extends Entity>> mTableClasses = new ArrayList<>();

    public SQLiteExecute(){
        //添加用户表
        mTableClasses.add(UserInfo.class);
        //日常信息表
        mTableClasses.add(DailyRecord.class);
    }


    /**
     * 创建数据库
     * @param db
     */
    public void createDatabase(SQLiteDatabase db){
        for (Class<? extends Entity> clazz : mTableClasses) {
            Table tableView = clazz.getAnnotation(Table.class);
            if (tableView == null) {
                continue;
            }
            // 创建table
            db.execSQL(SQLBuilder.buildCreateSQL(clazz).getSql());
        }
    }

    /**
     * 更新数据库
     * 更新思路：
     * 1.得知sqlite数据库里默认会生成两个表分别是：sqlite_sequence和sqlite_master，
     *      今天看的是sqlite_master，里面存放了每张表结构
     * 2.检索此表可以知道新建的表是否在其中存在
     * 3.不存表在则创建新表； 存在表再检查所有字段是否存在，不存在则加字段（数据库升级的原则就是只增不减）；
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        autoMigrate(db,mTableClasses);
    }

    /**
     *
     * @param db
     * @param tableClasses
     * 1.查询sqlite_master
     * 2.不存在则更新表
     * 3.存在则更新变量
     */
    private void autoMigrate(SQLiteDatabase db, List<Class<? extends Entity>> tableClasses) {
        for (Class<? extends Entity> clazz : tableClasses) {
            String tableName = ReflectTools.getTableName(clazz);
            boolean exist = ReflectTools.isTableExist(db, tableName);
            if (exist) {
                Field[] fields = ReflectTools.getClassFields(clazz);
                for (Field field : fields) {
                    Column column = field.getAnnotation(Column.class);
                    if (column == null) {
                        continue;
                    }

                    String columnName = !TextUtils.isEmpty(column.name()) ? column.name() : field.getName();
                    String dataType = ReflectTools.getDataTypeByField(field);
                    boolean columnExist = ReflectTools.isColumnExist(db, tableName, columnName);
                    if (!columnExist) {
                        db.execSQL("ALTER TABLE " + tableName + " ADD " + columnName + " " + dataType);
                    }
                }
            } else {
                db.execSQL(SQLBuilder.buildCreateSQL(clazz).getSql());
            }
        }
    }

}
