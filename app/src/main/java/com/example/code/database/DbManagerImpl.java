package com.example.code.database;

/**
 * @author alan
 * function:
 */
public class DbManagerImpl {


    private static DbManagerImpl dbManager;

    public static DbManagerImpl getInstance() {
        if (dbManager == null) {
            dbManager = new DbManagerImpl();
        }
        return dbManager;
    }
}
