package com.alan.threefive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
