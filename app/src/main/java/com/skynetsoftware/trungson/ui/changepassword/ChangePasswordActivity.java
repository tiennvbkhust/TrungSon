package com.skynetsoftware.trungson.ui.changepassword;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChangePasswordActivity extends BaseActivity implements ChangePasswordContract.View {
    @BindView(R.id.imgBtn_back_toolbar)
    ImageView imgBtnBackToolbar;
    @BindView(R.id.tvTitle_toolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.edt)
    EditText edt;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.edt_new_pass)
    EditText edtNewPass;
    @BindView(R.id.edt_renew_pass)
    EditText edtRenewPass;
    private ProgressDialogCustom dialogCustom;
    private ChangePasswordContract.Presenter presenter;

    @Override
    protected int initLayout() {
        return R.layout.activity_change_pass;
    }

    @Override
    protected void initVariables() {
        presenter = new ChangePasswordPresenter(this);
        dialogCustom = new ProgressDialogCustom(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        tvTitleToolbar.setText(R.string.changpass_title);

    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.layout;
    }


    @OnClick({R.id.imgBtn_back_toolbar, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBtn_back_toolbar:
                onBackPressed();
                break;
            case R.id.btn_submit:
                if(!edtNewPass.getText().toString().equals(edtRenewPass.getText().toString())) {
                    onError("Mật khẩu không trùng nhau");
                    return;
                }
                presenter.update(edtNewPass.getText().toString(), edt.getText().toString());
                break;
        }
    }

    @Override
    public void onSuccessUpdate() {
        Toast.makeText(this, R.string.update_infor_success_txt, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
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
        dialogCustom.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialogCustom.hideDialog();

    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpired();
    }

}
