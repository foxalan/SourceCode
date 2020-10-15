package com.alan.threefive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



/**
 * 1.数据库
 * 2.RecyclerView
 * 3.网络
 * 4.图片加载
 * 5.LOG 辅助类
 */
public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.text);

        if (BuildConfig.SKIP_MSG_CHECK_ENT) {
            Log.e("ALAN","DEBUG");
            textView.setText("DEBUG");
        } else { Log.e("ALAN","RE");
            textView.setText("DERE");
        }


    }
}
