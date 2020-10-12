package com.alan.threefive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.alan.tfive_ui.dialog.LoaderManager;
import com.alan.tfive_ui.dialog.base.GravityDialogFrag;

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

        LoaderManager.showCustomDialog(getSupportFragmentManager(), "TITLE", "CON",
                new GravityDialogFrag.OnClickListener() {
            @Override
            public void onConfirm() {

            }

            @Override
            public void onCancel() {

            }
        });
    }
}
