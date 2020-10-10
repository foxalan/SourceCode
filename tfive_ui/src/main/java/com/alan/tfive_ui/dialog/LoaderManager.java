package com.alan.tfive_ui.dialog;

import android.app.Activity;
import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.alan.tfive_ui.dialog.base.GravityDialogFrag;
import com.alan.tfive_ui.dialog.custom.CustomDialogFragment;
import com.alan.tfive_ui.dialog.custom.CustomInfo;

/**
 * @author alan
 * function:
 */
public class LoaderManager {

    public static void showCustomDialog(FragmentManager fragmentManager, String title, String content, GravityDialogFrag.OnClickListener listener) {
        CustomInfo customInfo = new CustomInfo();
        customInfo.setTitle(title);
        customInfo.setContent(content);
        CustomDialogFragment customDialogFragment = CustomDialogFragment.newInstance(customInfo, listener);
        customDialogFragment.show(fragmentManager, "title");
    }


}
