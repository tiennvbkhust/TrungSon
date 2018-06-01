package com.skynetsoftware.trungson.application;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.Utils;
import com.google.gson.Gson;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by DuongKK on 11/30/2017.
 */

public class AppController extends MultiDexApplication {
    private static AppController instance;
    private Profile mProfileUser;
    private SPUtils mSetting;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public synchronized static AppController getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(true)
//                .build();
//        Fabric.with(fabric);
        instance = this;
        Utils.init(this);

        mSetting = new SPUtils(AppConstant.KEY_SETTING);
        setmProfileUser(new Gson().fromJson(mSetting.getString(AppConstant.KEY_PROFILE), Profile.class));
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        Permission[] permissions = new Permission[] {
                Permission.EMAIL,
                Permission.PUBLISH_ACTION
        };
        SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
                .setAppId(getString(R.string.facebook_app_id))
                .setNamespace("nailsmap")
                .setPermissions(permissions)
                .build();
        SimpleFacebook.setConfiguration(configuration);
    }

    public SPUtils getmSetting() {
        return mSetting;
    }


    public Profile getmProfileUser() {
        return mProfileUser;
    }

    public void setmProfileUser(Profile mProfileUser) {
        this.mProfileUser = mProfileUser;
        if (mProfileUser != null) {
            mSetting.put(AppConstant.KEY_PROFILE, new Gson().toJson(mProfileUser));
        }else {
            mSetting.put(AppConstant.KEY_PROFILE, "");

        }

    }
}
