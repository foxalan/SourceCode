package com.alan.tfive_function.database.sqlite;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.alan.tfive_function.database.helper.SugarCursorFactory;
import com.alan.tfive_function.database.table.Entity;

import java.util.ArrayList;
import java.util.List;

import static com.alan.tfive_function.database.helper.ManifestHelper.getDatabaseVersion;
import static com.alan.tfive_function.database.helper.ManifestHelper.getDbName;

/**
 * @author alan
 * function:
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private SQLiteExecute sqLiteExecute;



    public MySQLiteOpenHelper(Context context) {
        super(context, getDbName(context), new SugarCursorFactory(), getDatabaseVersion(context));
        sqLiteExecute =  new SQLiteExecute();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        sqLiteExecute.createDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        sqLiteExecute.onUpgrade(db,oldVersion,newVersion);
    }
}
