package com.example.code;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.code.retrofit.GetRequestService;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    public static final String RETROFIT = "retrofit";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fanyi.youdao.com/") //设置网络请求的Url地址
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        // 创建 网络请求接口 的实例
        GetRequestService request = retrofit.create(GetRequestService.class);
        //对 发送请求 进行封装
        Call<RequestBody> call = request.getCall();
        call.enqueue(new Callback<RequestBody>() {
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                Log.e(RETROFIT, "success");
            }

            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Log.e(RETROFIT, "fail");
            }
        });


        OkHttpClient client = new OkHttpClient();
        Request request2 = new Request.Builder()
                .url("http://www.baidu.com")
                .build();

        client.newCall(request2).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {

            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                Log.d("kwwl","获取数据成功了");
                Log.d("kwwl","response.code()=="+response.code());
                Log.d("kwwl","response.body().string()=="+response.body().string());
            }
        });


    }
}
