package com.example.sourcecode.fragment;

/**
 * @author alan
 * function:
 */
public abstract class BaseBottomFragment extends BaseFragment {

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            onUserInVisible();
        }else {
            onUserVisible();
        }
    }

    /**
     * 可见
     */
   public abstract void onUserVisible();


    /**
     * 不可见
     */
    public abstract void onUserInVisible();
}
