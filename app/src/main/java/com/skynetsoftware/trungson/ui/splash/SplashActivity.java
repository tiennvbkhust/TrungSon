package com.skynetsoftware.trungson.ui.splash;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.LogUtils;
import com.skynetsoftware.trungson.MainActivity;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.ui.login.LoginActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;


import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity implements SlideContract.View{



    private ProgressDialogCustom dialogCustom;
    private SlideContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        dialogCustom  = new ProgressDialogCustom(this);
        presenter = new SlidePresenter(this);
       // presenter.getInfor();
//        getDialogProgress().hideDialog();
//        MainApplication.getInstance().setDay(day);
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
       /// startActivity(intent);
      //  finish();
    }

    @Override
    public void onSuccessGetInfor() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public void showProgress() {
        dialogCustom.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialogCustom.hideDialog();
    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
      //  startActivity(new Intent(SplashActivity.this, AccountActivity.class));
        finish();
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);
      //  startActivity(new Intent(SplashActivity.this, AccountActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroyView();
        super.onDestroy();
    }

    @Override
    public void onErrorAuthorization() {
      //  startActivity(new Intent(SplashActivity.this, AccountActivity.class));
        finish();
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
