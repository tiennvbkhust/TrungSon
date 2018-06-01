package com.skynetsoftware.trungson.ui.verifyaccount;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.skynetsoftware.trungson.MainActivity;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.utils.AppConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VerifyAccountActivity extends BaseActivity {
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

    @Override
    protected int initLayout() {
        return R.layout.acitivity_verify;
    }

    @Override
    protected void initVariables() {
        showToast("This message is fake to test system", AppConstant.NEGATIVE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvTitle.setText(Html.fromHtml(String.format(getString(R.string.verify_title), "0962672440"), Html.FROM_HTML_MODE_LEGACY));
        } else {
            tvTitle.setText(Html.fromHtml(String.format(getString(R.string.verify_title), "0962672440")));
        }
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
                break;
            case R.id.btnsubmit:
                startActivity(new Intent(VerifyAccountActivity.this, MainActivity.class));
                break;
        }
    }
}
