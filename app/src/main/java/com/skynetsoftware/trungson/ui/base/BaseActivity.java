package com.skynetsoftware.trungson.ui.base;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.androidadvance.topsnackbar.TSnackbar;
import com.blankj.utilcode.util.NetworkUtils;
import com.jaeger.library.StatusBarUtil;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.interfaces.SnackBarCallBack;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.network.socket.SocketClient;
import com.skynetsoftware.trungson.network.socket.SocketConstants;
import com.skynetsoftware.trungson.ui.splash.SplashActivity;
import com.skynetsoftware.trungson.ui.views.AlertDialogCustom;
import com.skynetsoftware.trungson.ui.views.DialogOneButtonUtil;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.utils.AppConstant;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created by thaopt on 12/1/17.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract int initLayout();

    protected abstract void initVariables();

    protected abstract void initViews();

    protected abstract int initViewSBAnchor();

    protected SocketClient mSocket;
    private boolean mBounded;
    protected SnackBarCallBack snackBarCallBack;

    public SocketClient getmSocket() {
        return mSocket;
    }

    public void setmSocket(SocketClient mSocket) {
        this.mSocket = mSocket;
    }

    private MaterialDialog dialogError;
    AlertDialog dialogNetwork;
    BroadcastReceiver receiverConnectionNetwork = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (!NetworkUtils.isConnected()) {
                if (dialogNetwork == null)
                    dialogNetwork = AlertDialogCustom.dialogMessage(BaseActivity.this);
                dialogNetwork.show();
            } else {
                if (dialogNetwork != null) dialogNetwork.dismiss();
                if (getmSocket() != null) getmSocket().initSocket();
            }
        }
    };
    ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            BaseActivity.this.mBounded = true;
            SocketClient.LocalBinder mLocalBinder = (SocketClient.LocalBinder) service;
            BaseActivity.this.mSocket = mLocalBinder.getServerInstance();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            BaseActivity.this.mBounded = false;
            BaseActivity.this.mSocket = null;
        }
    };

    DialogOneButtonUtil dialogOneButtonUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayout());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        } else {

            StatusBarUtil.setLightMode(this);

        }
        ButterKnife.bind(this);

        initViews();
        initVariables();
        if (findViewById(R.id.scrollview) != null) {
            findViewById(R.id.scrollview).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event != null && event.getAction() == MotionEvent.ACTION_MOVE) {
                        InputMethodManager imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
                        boolean isKeyboardUp = imm.isAcceptingText();
                        if (isKeyboardUp) {
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                        }
                    }
                    return false;
                }
            });
        }
        dialogError = new MaterialDialog.Builder(this).title(R.string.error)
                .content(getString(R.string.unknow_error))
                .positiveText(R.string.dismis)
                .positiveColor(Color.GRAY)
                .build();
//        IntentFilter intentFilter = new IntentFilter(GPSService.ACTION_LOCATION_UPDATE);
//        registerReceiver(receiverGPS, intentFilter);

        Intent mIntent = new Intent(this, SocketClient.class);
        startService(mIntent);
        bindService(mIntent, this.mConnection, BIND_AUTO_CREATE);
    }

    public Profile getProfile() {
        Profile profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            showDialogExpired();
        }
        return profile;
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(this.receiverConnectionNetwork);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter i = new IntentFilter();
        i.addAction(SocketConstants.EVENT_CONNECTION);
        IntentFilter iConnection = new IntentFilter();
        iConnection.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(this.receiverConnectionNetwork, iConnection);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialogError != null && dialogError.isShowing()) dialogError.dismiss();
        if (this.mBounded) {
            unbindService(this.mConnection);
            this.mBounded = false;
        }
    }

    public void showDialogExpired() {
        if (dialogOneButtonUtil == null) {
            dialogOneButtonUtil = new DialogOneButtonUtil(this);
            dialogOneButtonUtil.setText(getString(R.string.notify_title), "Your token has expired. Please login again!");
            dialogOneButtonUtil.setType(2);
            dialogOneButtonUtil.setDialogOneButtonClick(new DialogOneButtonUtil.DialogOneButtonClickListener() {
                @Override
                public void okClick() {
                    Intent intent = new Intent(BaseActivity.this, SplashActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    AppController.getInstance().setmProfileUser(null);
                    AppController.getInstance().getmSetting().remove(AppConstant.KEY_PROFILE);
                    AppController.getInstance().getmSetting().remove(AppConstant.KEY_TOKEN);
                    AppController.getInstance().setmProfileUser(null);
                    startActivity(intent);
                    finishAffinity();
                }
            });
        }
        if (!dialogOneButtonUtil.isShowing())
            dialogOneButtonUtil.show();
    }

    public void showToast(String message, int type) {
        TSnackbar snackbar = TSnackbar.make(findViewById(initViewSBAnchor() == 0 ? android.R.id.content : initViewSBAnchor()), message, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor(type == AppConstant.POSITIVE ? "#395838" : "#fe3824"));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
        snackbar.setCallback(new TSnackbar.Callback() {
            @Override
            public void onDismissed(TSnackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if(snackBarCallBack!=null) snackBarCallBack.onClosedSnackBar();
            }
        });
    }
    public void showToast(String message, int type, final SnackBarCallBack snackBarCallBack) {
        TSnackbar snackbar = TSnackbar.make(findViewById(initViewSBAnchor() == 0 ? android.R.id.content : initViewSBAnchor()), message, TSnackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.WHITE);
        View snackbarView = snackbar.getView();
        snackbarView.setBackgroundColor(Color.parseColor(type == AppConstant.POSITIVE ? "#395838" : "#fe3824"));
        TextView textView = (TextView) snackbarView.findViewById(com.androidadvance.topsnackbar.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
        snackbar.setCallback(new TSnackbar.Callback() {
            @Override
            public void onDismissed(TSnackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                if(snackBarCallBack!=null) snackBarCallBack.onClosedSnackBar();
            }
        });
    }

    public void setSnackBarCallBack(SnackBarCallBack snackBarCallBack) {
        this.snackBarCallBack = snackBarCallBack;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


}
