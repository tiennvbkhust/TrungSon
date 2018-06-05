package com.skynetsoftware.trungson.ui.forgotpassword;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.SnackBarCallBack;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgotPasswordActivity extends BaseActivity implements SnackBarCallBack, ForgotPwContract.View {

    @BindView(R.id.edtEmail)
    EditText edtCode;

    @BindView(R.id.scrollview)
    ScrollView scrollview;
    private ProgressDialogCustom dialogLoading;
    private ForgotPwContract.Presenter presenter;

    @Override
    protected int initLayout() {
        return R.layout.acitivity_forgotpassword;
    }

    @Override
    protected void initVariables() {
        setSnackBarCallBack(this);
        dialogLoading = new ProgressDialogCustom(this);
        presenter = new ForgotPwPresenter(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.scrollview;
    }


    @OnClick({R.id.btnsubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btnsubmit:
                presenter.signUp(edtCode.getText().toString());
                break;
        }
    }

    @Override
    public void onClosedSnackBar() {
//        finish();
    }

    @Override
    public void signUpSuccess() {
        showToast("Mật khẩu đã được gửi tới Email. Vui lòng kiểm tra Email của bạn.", AppConstant.POSITIVE, new SnackBarCallBack() {
            @Override
            public void onClosedSnackBar() {
                finish();
            }
        });
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
        showDialogExpired();
    }
}
