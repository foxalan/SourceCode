package com.alan.tfive_function.database.conditionbuilder;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.alan.tfive_function.database.annotation.CrossJoin;
import com.alan.tfive_function.database.annotation.ID;
import com.alan.tfive_function.database.annotation.InnerJoin;
import com.alan.tfive_function.database.annotation.LeftJoin;
import com.alan.tfive_function.database.annotation.NaturalJoin;
import com.alan.tfive_function.database.helper.ReflectTools;
import com.alan.tfive_function.database.info.ColumnInfo;
import com.alan.tfive_function.database.table.Entity;
import com.alan.tfive_function.database.table.Query;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.alan.tfive_function.database.operate.SqlOperate.getTraceInfo;

/**
 * Smilier with {@link ConditionBuilder} but expose api for
 * multi-table query only.
 *
 * @author zhangfei
 */
public class MultiTableConditionBuilder<T extends Query> implements BuilderSupport<T>{
    private static final String TAG = "";
    private final SQLiteDatabase database;

    private Class<T> clazz;
    private String[] columns;
    private String whereClause;
    private String[] whereArgs;
    private String groupBy;
    private String having;
    private String orderBy;
    private Integer limitOffset;
    private Integer limitSize;
    private boolean distinct;

    /**
     * used in database query
     */
    private String[] aliasColumns;


    public MultiTableConditionBuilder(SQLiteDatabase database) {
        this.database = database;
    }

    public final MultiTableConditionBuilder<T> withColumns(String... columns) {
        this.columns = columns;
        return this;
    }

    @Override
    public MultiTableConditionBuilder<T> withWhere(String whereClause, Object... whereArgs) {
        this.whereClause = whereClause;
        this.whereArgs = new String[whereArgs.length];

        for (int i = 0; i < whereArgs.length; i++) {
            Object arg = whereArgs[i];

            if (arg instanceof String
                    || arg instanceof Integer
                    || arg instanceof Long
                    || arg instanceof Float
                    || arg instanceof Double) {
                this.whereArgs[i] = arg.toString();
            } else if (arg instanceof Boolean) {
                this.whereArgs[i] = Boolean.valueOf(arg.toString()) ? "1" : "0";
            } else {
                throw new SQLException(arg.toString() + " is not supported as where argument in SQLite");
            }
        }

        return this;
    }

    @Override
    public MultiTableConditionBuilder<T> withGroupBy(String groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    @Override
    public MultiTableConditionBuilder<T> withHaving(String having) {
        this.having = having;
        return this;
    }

    @Override
    public MultiTableConditionBuilder<T> withOrderBy(String orderBy) {
        this.orderBy = orderBy;
        return this;
    }

    @Override
    public MultiTableConditionBuilder<T> withLimit(int limitOffset, int limitSize) {
        this.limitOffset = limitOffset;
        this.limitSize = limitSize;
        return this;
    }

    @Override
    public MultiTableConditionBuilder<T> withDistinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    @Override
    public T applySearchById(long id) {
        this.whereClause = Entity._ID + "=?";
        this.whereArgs = new String[]{String.valueOf(id)};

        return applySearchFirst();
    }

    @Override
    public int applyCount() {
        this.columns = Entity.COUNT_COLUMNS;

        Cursor c = applySearch();
        if (c == null) {
            throw new SQLiteException("Cannot create cursor object, database or columns may have error...");
        }

        try {
            if (c.moveToFirst()) {
                return c.getInt(0);
            } else {
                return 0;
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "applyCount() error: " + getTraceInfo(e));
            return 0;
        } finally {
            c.close();
        }
    }

    /**
     * Apply search and return cursor as result
     *
     * @return query cursor
     */
    @Override
    public Cursor applySearch() {
        String limit = null;
        if (limitOffset != null && limitSize != null) {
            limit = limitOffset + "," + limitSize;
        }

        InnerJoin innerJoin = clazz.getAnnotation(InnerJoin.class);
        if (innerJoin != null){
            String tables = JoinClauseBuilder.buildInnerJoinClause(innerJoin);
            String query = SQLiteQueryBuilder.buildQueryString(
                    distinct, tables, aliasColumns, whereClause,
                    groupBy, having, orderBy, limit);
            return database.rawQuery(query, whereArgs);
        }

        LeftJoin leftJoin = clazz.getAnnotation(LeftJoin.class);
        if (leftJoin != null){
            String tables = JoinClauseBuilder.buildLeftJoinClause(leftJoin);
            String query = SQLiteQueryBuilder.buildQueryString(
                    distinct, tables, aliasColumns, whereClause,
                    groupBy, having, orderBy, limit);
            return database.rawQuery(query, whereArgs);
        }

        CrossJoin crossJoin = clazz.getAnnotation(CrossJoin.class);
        if (crossJoin != null){
            String tables = JoinClauseBuilder.buildCrossJoinClause(crossJoin);
            String query = SQLiteQueryBuilder.buildQueryString(
                    distinct, tables, aliasColumns, whereClause,
                    groupBy, having, orderBy, limit);
            return database.rawQuery(query, whereArgs);
        }

        NaturalJoin naturalJoin = clazz.getAnnotation(NaturalJoin.class);
        if (naturalJoin != null){
            String tables = JoinClauseBuilder.buildNaturalJoinClause(naturalJoin);
            String query = SQLiteQueryBuilder.buildQueryString(
                    distinct, tables, aliasColumns, whereClause,
                    groupBy, having, orderBy, limit);
            return database.rawQuery(query, whereArgs);
        }

        throw new SQLiteException("no join annotation found over Query class");
    }

    /**
     * Apply search with condition and return list as result
     *
     * @return list of table class object as result
     */
    @Override
    public List<T> applySearchAsList() {
        Cursor c = applySearch();
        List<T> entities = new ArrayList<>();

        try {
            while (c.moveToNext()) {
                T table = getContent(c, clazz);
                if (table != null) {
                    entities.add(table);
                }
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "applySearchAsList() error: " + getTraceInfo(e));
            return entities;
        } finally {
            c.close();
        }
        return entities;
    }

    /**
     * Apply search first row with condition
     *
     * @return first item of result
     */
    @Override
    public T applySearchFirst() {
        Cursor c = applySearch();

        if (c == null) {
            throw new SQLiteException("Cannot create cursor object, database or columns may have error...");
        }

        try {
            if (c.moveToFirst()) {
                return getContent(c, clazz);
            } else {
                return null;
            }
        } catch (SQLiteException e) {
            Log.e(TAG, "applySearchFirst() error: " + getTraceInfo(e));
            return null;
        } finally {
            c.close();
        }
    }

    public MultiTableConditionBuilder<T> withQuery(Class<T> clazz) {
        this.clazz = clazz;

        // read columns from class
        Field[] fields = ReflectTools.getClassFields(clazz);

        List<String> columns = new ArrayList<>();
        List<String> aliasColumns = new ArrayList<>();
        for (Field field : fields) {
            // ignore _id field
            if (field.isAnnotationPresent(ID.class)) {
                continue;
            }

            ColumnInfo columnInfo = ReflectTools.getColumnInfo(field);
            columns.add(columnInfo.getName());
            aliasColumns.add(columnInfo.getAliasName());
        }

        this.columns = columns.toArray(new String[columns.size()]);
        this.aliasColumns = aliasColumns.toArray(new String[aliasColumns.size()]);
        return this;
    }

    private T getContent(Cursor cursor, Class<T> tableClass) {
        try {
            T content = tableClass.newInstance();
            content.restore(cursor, columns);
            return content;
        } catch (IllegalAccessException e) {
            Log.e(TAG, "getContent() error: " + getTraceInfo(e));
        } catch (InstantiationException e) {
            Log.e(TAG, "getContent() error: " + getTraceInfo(e));
        }
        return null;
    }

}
