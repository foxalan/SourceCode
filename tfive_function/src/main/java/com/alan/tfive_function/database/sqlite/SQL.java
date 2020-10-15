package com.alan.tfive_function.database.sqlite;

import java.util.LinkedList;

/**
 * @author alan
 * function:
 */
public class SQL {

    private String sql;
    private LinkedList<Object> bindArgs;

    public SQL() {
    }

    public SQL(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void addBindArg(Object arg) {
        if (bindArgs == null) {
            bindArgs = new LinkedList<>();
        }
        bindArgs.add(arg);
    }

    public Object[] getBindArgsAsArray() {
        if (bindArgs != null) {
            return bindArgs.toArray();
        }

        return null;
    }
}
