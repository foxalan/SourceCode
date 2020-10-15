package com.alan.tfive_function.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.alan.tfive_function.database.operate.SqlOperate;
import com.alan.tfive_function.database.sqlite.MySQLiteOpenHelper;

/**
 * @author alan
 * function: 数据库
 * 1.数据库 版本 姓名
 * 2.table name 主键
 * 3. table 的增 删 改 查
 *
 *
 * 1.初始化所有的数据库和所有的表
 */
public class DatabaseManager {

    private static DatabaseManager databaseManager = new DatabaseManager();
    private SQLiteDatabase sqLiteDatabase;
    private static SqlOperate sqlOperate;

    public void init(Context context){
        initDataBase(context);
    }

    private void initDataBase(Context context) {
        sqLiteDatabase = new MySQLiteOpenHelper(context).getWritableDatabase();
        sqlOperate = new SqlOperate(sqLiteDatabase);
    }

    public static SqlOperate with(){
        return sqlOperate;
    }


    public static DatabaseManager getInstance(){
        return databaseManager;
    }

}
