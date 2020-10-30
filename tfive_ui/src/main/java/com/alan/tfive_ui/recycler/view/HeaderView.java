package com.alan.tfive_ui.recycler.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.alan.tfive_ui.R;

/**
 * @author alan
 * function:
 */
public abstract class HeaderView extends RelativeLayout {

    private View  rootView;

    public HeaderView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        rootView = LayoutInflater.from(context).inflate(R.layout.recycler_defualt_header_view,this,false);
        addView(rootView,0);
    }

    public void onMove(int length){

    }
}
