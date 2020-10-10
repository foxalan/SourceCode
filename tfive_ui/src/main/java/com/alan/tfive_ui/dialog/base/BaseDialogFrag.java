package com.alan.tfive_ui.dialog.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;


/**
 * 基础的对话框碎片
 * <p>
 * 仿照Activity，增加了findViewById，setContentView，setDialogView
 * <p>
 * 另外通过onHiddenChanged监听来实现start-resume-pause-stop的生命周期
 * <p>
 * 另外增加OnDismissListener来监听对话框关闭
 * <p>
 * 增加saveStateFlag标记，当为true时，在视图被销毁时提前与父组件进行剥离，在下一次循环使用，实现复用
 */
public abstract class BaseDialogFrag extends DialogFragment {

    public Activity mActivity;
    public BaseDialogFrag mFragment;
    public View mContentView;
    private OnDismissListener onDismissListener;
    private boolean saveStateFlag = true;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mFragment = this;
        mActivity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mContentView == null || !isSaveStateFlag()) {
            onCreateView(inflater, container, savedInstanceState, isSaveStateFlag());
        }
        return mContentView;
    }

    public abstract void onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState, boolean isSaveStateFlag);


    public View getContentView() {
        return mContentView;
    }

    public void setContentView(int contentRes) {
        mContentView = LayoutInflater.from(getContext()).inflate(contentRes, null);
    }


    /**
     * 重写函数
     * 根据隐藏显示状态手动调用来模拟Activity启动、播放，暂停，停止的生命周期
     * 根据测试，dialogFragment的该函数不会被触发
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onStart();
            onResume();
        } else {
            onPause();
            onStop();
        }
    }

    @Override
    public void onDestroyView() {
        if (mContentView != null && mContentView.getParent() != null && isSaveStateFlag()) {
            ((ViewGroup) mContentView.getParent()).removeView(mContentView);
        }
        super.onDestroyView();
    }

    /**
     * 设置状态保存标记
     * 若改标记为true，则该碎片所表示的视图在onDestoryView函数中不会被销毁，在下一次onCreateView中可以复用
     * 默认为true
     *
     * @return
     */
    public boolean isSaveStateFlag() {
        return saveStateFlag;
    }

    /**
     * 设置状态保存标记
     * 若改标记为true，则该碎片所表示的视图在onDestoryView函数中不会被销毁，在下一次onCreateView中可以复用
     * 默认为true
     *
     * @param saveStateFlag
     */
    public void setSaveStateFlag(boolean saveStateFlag) {
        this.saveStateFlag = saveStateFlag;
    }

    /**
     * 在contentView视图中，根据ID获取组件
     *
     * @param id
     * @return
     */
    public <T extends View> T findViewById(int id) {
        if (mContentView != null) {
            return mContentView.findViewById(id);
        }
        return null;
    }

    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(this);
        }
    }

    public interface OnDismissListener {
        void onDismiss(DialogFragment dialogFragment);
    }
}