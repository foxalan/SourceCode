package com.example.code.database;


import org.litepal.crud.DataSupport;

/**
 * @author alan
 * function:
 */
public class DbManager {

    private static DbManagerImpl dbManager;

    public static DbManagerImpl init(){
        return  DbManagerImpl.getInstance();
    }


    public void initPlatform(){

    }

}
