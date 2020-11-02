package com.alan.tfive_function.network;

import okhttp3.Callback;
import okhttp3.OkHttpClient;

/**
 * @author alan
 * function:
 */
public class TFClient {

    //请求地址
    private String url;

    public TFClient(String url){

        this.url = url;
        OkHttpClient okHttpClient = new OkHttpClient();

    }

    public void request(Callback callback){

    }

}
