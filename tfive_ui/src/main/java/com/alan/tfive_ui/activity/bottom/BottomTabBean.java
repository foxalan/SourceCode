package com.alan.tfive_ui.activity.bottom;


public final class BottomTabBean {

    private CharSequence ICON;
    private CharSequence TITLE;
    /**
     * 是否是中心图标
     */
    private boolean isCenter = false;
    private int CENTER_RES_ID;
    /**
     * 本地图片
     */
    private int RES_SEL_ID;
    private int RES_UNSEL_ID;

    public CharSequence getICON() {
        return ICON;
    }

    public void setICON(CharSequence ICON) {
        this.ICON = ICON;
    }

    public CharSequence getTITLE() {
        return TITLE;
    }

    public void setTITLE(CharSequence TITLE) {
        this.TITLE = TITLE;
    }

    public int getCENTER_RES_ID() {
        return CENTER_RES_ID;
    }

    public void setCENTER_RES_ID(int CENTER_RES_ID) {
        this.CENTER_RES_ID = CENTER_RES_ID;
    }

    public int getRES_SEL_ID() {
        return RES_SEL_ID;
    }

    public void setRES_SEL_ID(int RES_SEL_ID) {
        this.RES_SEL_ID = RES_SEL_ID;
    }

    public int getRES_UNSEL_ID() {
        return RES_UNSEL_ID;
    }

    public void setRES_UNSEL_ID(int RES_UNSEL_ID) {
        this.RES_UNSEL_ID = RES_UNSEL_ID;
    }

    public boolean isCenter() {
        return isCenter;
    }

    public void setCenter(boolean center) {
        isCenter = center;
    }

    public BottomTabBean(CharSequence TITLE, boolean isCenter, int CENTER_RES_ID) {
        this.TITLE = TITLE;
        this.isCenter = isCenter;
        this.CENTER_RES_ID = CENTER_RES_ID;
    }

    public BottomTabBean(CharSequence TITLE, int RES_SEL_ID, int RES_UNSEL_ID) {
        this.TITLE = TITLE;
        this.RES_SEL_ID = RES_SEL_ID;
        this.RES_UNSEL_ID = RES_UNSEL_ID;
    }

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public BottomTabBean(CharSequence icon, CharSequence title, boolean isCenter) {
        this.ICON = icon;
        this.TITLE = title;
        this.isCenter = isCenter;
    }



    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}
