package com.example.code.database;

import com.example.code.entity.Platform;

/**
 * @author alan
 * function:
 */
public class DbConfig {


    public static void initPlatform(){

        Platform platform = new Platform();
        platform.setId(0);
        platform.setName("趣闲赚");
        platform.setDes("S");
        platform.save();

        Platform platform2 = new Platform();
        platform2.setId(1);
        platform2.setName("牛帮");
        platform2.setDes("A");
        platform2.save();

        Platform platform3 = new Platform();
        platform3.setId(2);
        platform3.setName("余赚网");
        platform3.setDes("B");
        platform3.save();

    }
}
