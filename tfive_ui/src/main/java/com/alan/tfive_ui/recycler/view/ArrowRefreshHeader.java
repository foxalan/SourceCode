package com.alan.tfive_ui.recycler.view;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * @author alan
 * function:
 */
public class ArrowRefreshHeader extends LinearLayout implements BaseRefreshHeader {


    public ArrowRefreshHeader(Context context) {
        super(context);
    }

    @Override
    public void onMove(float delta) {

    }

    @Override
    public boolean releaseAction() {
        return false;
    }

    @Override
    public void refreshComplete() {

    }
}
