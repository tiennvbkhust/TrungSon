package com.skynetsoftware.trungson.ui.base;


import com.skynetsoftware.trungson.network.api.TrungSonAPI;

/**
 * Created by thaopt on 12/1/17.
 */

public abstract class Interactor {
    private TrungSonAPI mService;
    public Interactor(){
        mService = createService();

    }
    public abstract TrungSonAPI createService();
    public TrungSonAPI getmService() {
        return mService;
    }
}
