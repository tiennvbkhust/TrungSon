package com.skynetsoftware.trungson.ui.views;

/**
 * Created by hoaph on 10/19/2017.
 */

public interface BaseCustomRefreshHeader {
    int STATE_NORMAL = 0;
    int STATE_RELEASE_TO_REFRESH = 1;
    int STATE_REFRESHING = 2;
    int STATE_DONE = 3;

    void onMove(float delta);

    boolean releaseAction();

    void refreshComplete();

}
