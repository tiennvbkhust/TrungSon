package com.skynetsoftware.trungson.ui.updateInfor;

import android.content.Context;
import android.text.InputType;
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

public class UpdateInforActivity extends BaseActivity implements UpdateInforContract.View {
    @BindView(R.id.imgBtn_back_toolbar)
    ImageView imgBtnBackToolbar;
    @BindView(R.id.tvTitle_toolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.edt)
    EditText edt;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    String type, text;
    private ProgressDialogCustom dialogCustom;
    private UpdateInforContract.Presenter presenter;

    @Override
    protected int initLayout() {
        return R.layout.activity_update_infor;
    }

    @Override
    protected void initVariables() {
        if (getIntent() != null && getIntent().getExtras() != null) {
            type = getIntent().getExtras().getString("type");
            text = getIntent().getExtras().getString("text");
            if (type.equals("phone")) {
                tvTitleToolbar.setText("Cập nhật số điện thoại");
                edt.setHint("Điền số điện thoại");
                edt.setInputType(InputType.TYPE_CLASS_NUMBER);
                edt.setMaxEms(13);
                edt.setMinEms(10);
            } else if (type.equals("email")) {
                tvTitleToolbar.setText("Cập nhật email");
                edt.setHint("Điền  email");
            } else {
                tvTitleToolbar.setText("Cập nhật họ tên");
                edt.setHint("Điền  họ tên");

            }
            edt.setText(text);
        }
        presenter = new UpdateInforPresenter(this);
        dialogCustom = new ProgressDialogCustom(this);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }


    @OnClick({R.id.imgBtn_back_toolbar, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBtn_back_toolbar:
                onBackPressed();
                break;
            case R.id.btn_submit:
                presenter.update(type, edt.getText().toString());
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
