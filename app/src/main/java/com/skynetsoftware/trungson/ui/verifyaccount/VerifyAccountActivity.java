package com.skynetsoftware.trungson.ui.verifyaccount;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.skynetsoftware.trungson.ui.main.MainActivity;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyAccountActivity extends BaseActivity implements VerifyAccountContract.View {
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.edtCode)
    EditText edtCode;
    @BindView(R.id.tvResend)
    TextView tvResend;
    @BindView(R.id.tvCountdown)
    TextView tvCountdown;
    @BindView(R.id.scrollview)
    ScrollView scrollview;

    ProgressDialogCustom dialogLoading;
    VerifyAccountContract.Presenter presenter;
    private String name, phone, email, code;
    private CountDownTimer countDownTimer;
    private boolean flagResend;

    @Override
    protected int initLayout() {
        return R.layout.acitivity_verify;
    }

    @Override
    protected void initVariables() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvTitle.setText(Html.fromHtml(String.format(getString(R.string.verify_title), "0962672440"), Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvTitle.setText(Html.fromHtml(String.format(getString(R.string.verify_title), "0962672440")));
        }

        dialogLoading = new ProgressDialogCustom(this);
        presenter = new VerifyPresenter(this);
        name = getIntent().getExtras().getString("name");
        phone = getIntent().getExtras().getString("phone");
        email = getIntent().getExtras().getString("email");
        code = getIntent().getExtras().getString("code");
        LogUtils.e(code);
        countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountdown.setText(" sau " + millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                flagResend = true;
                tvResend.setText("Gửi lại");
                tvCountdown.setVisibility(View.INVISIBLE);
            }
        };
        countDownTimer.start();
        edtCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (edtCode.getText().toString().equals(code)) {
                    presenter.signUp(name, email, phone, code);
                }
            }
        });
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.scrollview;
    }


    @OnClick({R.id.tvResend, R.id.btnsubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvResend:
                if (flagResend)
                    presenter.sendCode(phone);
                break;
            case R.id.btnsubmit:
                if (edtCode.getText().toString().equals(code)) {
                    presenter.signUp(name, email, phone, code);
                } else {
                    showToast("Mã xác thực không chính xác", AppConstant.NEGATIVE);
                }
                break;
        }
    }

    @Override
    public void onSuccessSignUp() {
        Intent intent = new Intent(VerifyAccountActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void onSuccessSendCode(String code) {
        this.code = code;
        countDownTimer.start();
        tvCountdown.setVisibility(View.VISIBLE);
        flagResend = false;

    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroyView();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        dialogLoading.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialogLoading.hideDialog();
    }

    @Override
    public void onErrorApi(String message) {
        showToast(message, AppConstant.NEGATIVE);
    }

    @Override
    public void onError(String message) {
        showToast(message, AppConstant.NEGATIVE);

    }

    @Override
    public void onErrorAuthorization() {

    }
}
