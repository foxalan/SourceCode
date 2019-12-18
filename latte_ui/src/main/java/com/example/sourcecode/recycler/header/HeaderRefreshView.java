package com.example.sourcecode.recycler.header;

/**
 * @author alan
 * function:
 */
public interface HeaderRefreshView {

    int STATE_NORMAL = 0;
    int STATE_CAN_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_FINISH = 3;

    void changeState(int state, int move);

    void setState(int state);
}
