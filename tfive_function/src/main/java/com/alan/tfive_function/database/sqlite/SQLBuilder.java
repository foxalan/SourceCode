package com.alan.tfive_function.database.sqlite;

import android.database.sqlite.SQLiteException;
import android.text.TextUtils;

import com.alan.tfive_function.database.annotation.Column;
import com.alan.tfive_function.database.annotation.Foreign;
import com.alan.tfive_function.database.annotation.ID;
import com.alan.tfive_function.database.annotation.Table;
import com.alan.tfive_function.database.helper.ReflectTools;
import com.alan.tfive_function.database.table.Entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author alan
 * function: 数据生成
 */
public class SQLBuilder {

    /**
     * 创建SQL对象，拿到SQL语句
     * @param tableClass
     * @return
     */
    public static SQL buildCreateSQL(Class<? extends Entity> tableClass) {

        final StringBuilder buffer = new StringBuilder();
        //拿到表名
        String tableName = ReflectTools.getTableName(tableClass);
        buffer.append("CREATE TABLE IF NOT EXISTS ").append(tableName);
        buffer.append(" (");
        Field[] fields = ReflectTools.getClassFields(tableClass);

        int index = 0;
        for (Field field : fields) {
            index++;
            Column column = field.getAnnotation(Column.class);
            if (column == null) {
                continue;
            }
            //拿到列名
            String columnName = ReflectTools.getColumnInfo(field).getName();
            //验证数据是否合法
            validateFieldType(field, tableName);
            String columnType = ReflectTools.getDataTypeByField(field);
            buffer.append(columnName).append(" ").append(columnType);
            ID id = field.getAnnotation(ID.class);
            if (id != null) {
                buffer.append(" PRIMARY KEY AUTOINCREMENT");
            }
            //添加数据的默认值
            appendDefaultValue(column, field, buffer);
            // 是否唯一
            boolean unique = column.unique();
            if (unique) {
                buffer.append(" UNIQUE");
            }
            //是否不为空
            if (column.notnull()) {
                buffer.append(" NOT NULL");
            }
            //外键
            Foreign foreign = field.getAnnotation(Foreign.class);
            if (foreign != null) {
                Class<? extends Entity> refTableClass = foreign.value();
                Table refTable = refTableClass.getAnnotation(Table.class);
                String refTableName = refTable.value();
                String refColumnName = Entity._ID;
                buffer.append(" REFERENCES ").append(refTableName).append("(").append(refColumnName).append(")");
            }

            if (index != fields.length) {
                buffer.append(", ");
            }
        }
        buffer.append(");");
        return new SQL(buffer.toString());

    }

    /**
     * 添加数据的默认值
     * @param column
     * @param field
     * @param buffer
     */
    private static void appendDefaultValue(Column column, Field field, StringBuilder buffer) {
        String defVal = column.defVal();
        if (!TextUtils.isEmpty(defVal)) {
            Class<?> typeClass = field.getType();
            if (typeClass == Byte[].class || typeClass == byte[].class) {
                throw new SQLiteException("SQLITE does not support 'BLOB' data type for " + field.getName());
            }

            buffer.append(" DEFAULT '").append(defVal).append("'");
        }
    }

    /**
     * 生成插入数据
     * @param table
     * @param <T>
     * @return
     */
    public static <T extends Entity> SQL buildInsertSQL(T table) {
        List<KeyValue<Object>> keyValueList = table2KeyValueList(table);
        if (keyValueList.size() == 0) {
            return null;
        }

        StringBuilder buffer = new StringBuilder();
        SQL sql = new SQL();
        buffer.append("INSERT INTO ");
        buffer.append(ReflectTools.getTableName(table.getClass()));
        buffer.append(" (");
        for (KeyValue<Object> kv : keyValueList) {
            if (Entity._ID.equals(kv.key)) {
                continue;
            }
            buffer.append(kv.key).append(",");
            sql.addBindArg(kv.value);
        }
        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append(") VALUES (");

        for (KeyValue<Object> kv : keyValueList) {
            if (Entity._ID.equals(kv.key)) {
                continue;
            }
            buffer.append("?,");
        }

        buffer.deleteCharAt(buffer.length() - 1);
        buffer.append(")");
        sql.setSql(buffer.toString());
        return sql;
    }


    private static <T extends Entity> ArrayList<KeyValue<Object>> table2KeyValueList(T table) {
        ArrayList<KeyValue<Object>> keyValueList = new ArrayList<>();
        Field[] fields = ReflectTools.getClassFields(table.getClass());

        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                KeyValue<Object> kv = column2KeyValue(table, field, column);
                if (kv != null) {
                    keyValueList.add(kv);
                }
            }
        }
        return keyValueList;
    }

    public static <T extends Entity> KeyValue<Object> column2KeyValue(T table, Field field, Column column) {
        KeyValue<Object> keyValue = null;
        String key = column.name();
        if (!TextUtils.isEmpty(key)) {
            Object value = ReflectTools.getFieldValue(table, field);
            if (value == null) {
                value = getDefaultValueOfField(field.getType());
            }
            keyValue = new KeyValue<>(key, value);
        }
        return keyValue;
    }


    private static Object getDefaultValueOfField(Class<?> typeClass) {
        if (typeClass == Integer.class || typeClass == int.class) {
            return 0;
        } else if (typeClass == Short.class || typeClass == short.class) {
            return 0;
        } else if (typeClass == Double.class || typeClass == double.class) {
            return 0;
        } else if (typeClass == Float.class || typeClass == float.class) {
            return 0f;
        } else if (typeClass == Long.class || typeClass == long.class) {
            return 0L;
        } else if (typeClass == Boolean.class || typeClass == boolean.class) {
            return false;
        } else if (typeClass == String.class) {
            return "";
        } else if (typeClass == byte[].class || typeClass == Byte[].class) {
            return new byte[0];
        } else {
            throw new SQLiteException("type [" + typeClass.toString() + "] is not supported in SQLITE");
        }
    }




    /**
     * 验证数据类型是否合法
     * @param field
     * @param tableName
     */
    private static void validateFieldType(Field field, String tableName) {
        Class<?> typeClass = field.getType();
        if (typeClass != Integer.class
                && typeClass != int.class
                && typeClass != Short.class
                && typeClass != short.class
                && typeClass != Double.class
                && typeClass != double.class
                && typeClass != Float.class
                && typeClass != float.class
                && typeClass != Long.class
                && typeClass != long.class
                && typeClass != Boolean.class
                && typeClass != boolean.class
                && typeClass != String.class
                && typeClass != byte[].class
                && typeClass != Byte[].class) {
            throw new SQLiteException(field.getName() + " in " + tableName
                    + " is not in supported data type in SQLITE");
        }
    }

    private static class KeyValue<T> implements Serializable {
        final String key;
        final T value;

        KeyValue(String key, T value) {
            this.key = key;
            this.value = value;
        }
    }
}
