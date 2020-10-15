package com.alan.tfive_function.database.helper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;


import com.alan.tfive_function.database.annotation.Column;
import com.alan.tfive_function.database.annotation.OrderBy;
import com.alan.tfive_function.database.annotation.Table;
import com.alan.tfive_function.database.info.ColumnInfo;
import com.alan.tfive_function.database.table.Entity;
import com.alan.tfive_function.database.table.Query;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author alan
 * function 找到注解的
 */
public class ReflectTools {

    private static Map<Class<? extends Query>, String> classTableNameCache = new HashMap<>();
    private static Map<Class<? extends Query>, Field[]> classFieldsCache = new HashMap<>();

    public static <T extends Entity> String getTableName(Class<T> tableClass) {
        String tableName = classTableNameCache.get(tableClass);
        if (TextUtils.isEmpty(tableName)) {
            Table table = tableClass.getAnnotation(Table.class);
            if (table == null) {
                throw new SQLiteException(
                        "Table annotation is not defined on ["
                                + tableClass.getSimpleName() + "]");
            }

            tableName = table.value();
            classTableNameCache.put(tableClass, tableName);
        }
        return tableName;
    }

    public static <T extends Entity> String getDefaultOrderBy(Class<T> tableClass){
        OrderBy orderBy = tableClass.getAnnotation(OrderBy.class);
        if (orderBy != null) {
            return orderBy.value();
        }

        return null;
    }

    public static ColumnInfo getColumnInfo(Field field) {
        Column column = field.getAnnotation(Column.class);
        if (column == null) {
            throw new SQLiteException("@Column was not defined for field ["
                    + field.getName() + "]");
        }

        ColumnInfo info = new ColumnInfo();
        String aliasName = column.aliasName();
        if (!TextUtils.isEmpty(aliasName)) {
            info.setAliasName(aliasName);
        }

        String columnName = column.name();
        if (!TextUtils.isEmpty(columnName)) {
            info.setName(columnName);
        } else {
            info.setName(field.getName());
        }

        info.setNotNull(column.notnull());
        info.setUnique(column.unique());
        info.setDefVal(column.defVal());

        return info;
    }

    public static <T extends Entity> Object getFieldValue(T table, Field field) {
        try {
            field.setAccessible(true);
            return field.get(table);
        } catch (Throwable e) {
            throw new SQLiteException("Field '[" + field.getName() + "]' is not accessible.");
        }
    }

    /**
     * 获取变量
     * @param clazz
     * @return
     * todo
     * totalFields.addAll(0, Arrays.asList(superClassFields));
     */
    public static Field[] getClassFields(Class<? extends Query> clazz) {
        Field[] fields = classFieldsCache.get(clazz);
        if (fields == null) {
            Field[] tableClassFields = clazz.getDeclaredFields();
            List<Field> totalFields = new ArrayList<>(Arrays.asList(tableClassFields));

            // cache fields and return
            String objectClassStr = Object.class.toString();
            // 当前运行类的继承的父类的名字为
            Class<?> superClass = clazz.getSuperclass();
            while (superClass != null && !superClass.toString().equals(objectClassStr)) {
                Field[] superClassFields = superClass.getDeclaredFields();
                totalFields.addAll(0, Arrays.asList(superClassFields));
                superClass = superClass.getSuperclass();
            }

            // filter out static fields which are not table field
            List<Field> fieldsToRemove = new ArrayList<>();
            for (Field field : totalFields) {
                if (Modifier.isStatic(field.getModifiers())) {
                    fieldsToRemove.add(field);
                }
            }
            totalFields.removeAll(fieldsToRemove);

            fields = totalFields.toArray(new Field[totalFields.size()]);
            classFieldsCache.put(clazz, fields);
        }
        return fields;
    }

    interface DataType {

        String INTEGER = "INTEGER";

        String BLOB = "BLOB";

        String TEXT = "TEXT";

        String REAL = "REAL";
    }

   public   static String getDataTypeByField(Field field) {
        Class<?> dataTypeClass = field.getType();

        // all number type will be treat as INTEGER in SQLite
        if ((dataTypeClass == Integer.class || dataTypeClass == int.class)) {
            return DataType.INTEGER;
        } else if (dataTypeClass == Long.class || dataTypeClass == long.class) {
            return DataType.INTEGER;
        } else if (dataTypeClass == String.class) {
            return DataType.TEXT;
        } else if (dataTypeClass == Short.class || dataTypeClass == short.class) {
            return DataType.INTEGER;
        } else if (dataTypeClass == Double.class || dataTypeClass == double.class) {
            return DataType.REAL;
        } else if (dataTypeClass == Float.class || dataTypeClass == float.class) {
            return DataType.REAL;
        } else if (dataTypeClass == Boolean.class || dataTypeClass == boolean.class) {
            return DataType.INTEGER;
        } else if (dataTypeClass == Byte[].class || dataTypeClass == byte[].class){
            return DataType.BLOB;
        } else {
            throw new SQLiteException("field [" + field.getName() + "] is a not supported data type.");
        }
    }

   public static boolean isTableExist(SQLiteDatabase db, String tableName) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
            boolean hasNext = cursor.moveToNext();
            return hasNext && cursor.getInt(0) > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

   public  static boolean isColumnExist(SQLiteDatabase db, String tableName, String columnName) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT count(*) FROM sqlite_master WHERE tbl_name = ? AND (sql LIKE ? OR sql LIKE ?);",
                    new String[]{tableName, "%(" + columnName + "%", "%, " + columnName + " %"});
            boolean hasNext = cursor.moveToNext();
            return hasNext && cursor.getInt(0) > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
