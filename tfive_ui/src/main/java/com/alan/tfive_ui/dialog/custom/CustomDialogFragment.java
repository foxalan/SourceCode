package com.alan.tfive_ui.dialog.custom;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alan.tfive_ui.R;
import com.alan.tfive_ui.dialog.base.GravityDialogFrag;

/**
 * @author alan
 * function:
 */
public class CustomDialogFragment extends GravityDialogFrag {

    /** 存放按钮文本列表的key */
    public static final String DATA = "btnList";
    /** 监听器，用于回调事件 */
    public static final String LISTENER = "listener";

    private CustomInfo customInfo;
    private OnClickListener listener;

    private TextView mTvDialogTitle;
    private TextView mTvDialogContent;



    public static CustomDialogFragment newInstance(CustomInfo customInfo,OnClickListener listener){
        Bundle args = new Bundle();
        args.putSerializable(DATA, customInfo);
        args.putSerializable(LISTENER, listener);
        CustomDialogFragment fragment = new CustomDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public int getGravity() {
        return Gravity.CENTER;
    }

    @Override
    public void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, boolean isSaveStateFlag) {
        setContentView(R.layout.custom_dialog);
        setCancelable(false);
        initData();
        findViewById(R.id.btnDialogCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener !=null){
                    listener.onCancel();
                }
                dismiss();
            }
        });
    }

    /**
     * 获取数据
     */
    private void initData() {
        if (getArguments() == null){
            throw new IllegalStateException("can not pass no data");
        }
        customInfo = (CustomInfo) getArguments().getSerializable(DATA);
        listener = (OnClickListener) getArguments().getSerializable(LISTENER);

        mTvDialogContent = findViewById(R.id.tvDialogContent);
        mTvDialogContent.setText(customInfo.getContent());
    }

}
