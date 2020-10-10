package com.alan.tfive_ui.dialog.base;

import android.os.Bundle;


import androidx.fragment.app.DialogFragment;

import com.alan.tfive_ui.R;
import com.alan.tfive_ui.dialog.base.BaseDialogFrag;
import com.alan.tfive_ui.util.ScreenUtils;

import java.io.Serializable;


/**
 * 无样式，置于底部的对话框
 *
 * @author ICO
 */
public abstract class GravityDialogFrag extends BaseDialogFrag {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_Dialog_None);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().setLayout(ScreenUtils.getScreenWidth(getActivity()), getDialog().getWindow().getAttributes().height);
        getDialog().getWindow().setGravity(getGravity());
    }

    /** 返回对话框位置的对齐方式 */
    public abstract int getGravity();

    public interface  OnClickListener extends Serializable {

        void onConfirm();

        void onCancel();
    }
}
