package com.skynetsoftware.trungson.ui.writereviewshop;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatRatingBar;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WriteReviewShopActivity extends BaseActivity implements WriteReviewShopContract.View {

    String shopID;
    WriteReviewShopContract.Presenter presenter;
    ProgressDialogCustom dialogLoading;
    @BindView(R.id.rate)
    AppCompatRatingBar rate;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.scroll)
    NestedScrollView scroll;


    @Override
    protected int initLayout() {
        return R.layout.activity_write_review;
    }

    @Override
    protected void initVariables() {
        dialogLoading = new ProgressDialogCustom(this);
        presenter = new WriteReviewShopPresenter(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            shopID = getIntent().getExtras().getString(AppConstant.MSG);
            if (shopID != null) {

            }

        }
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

        scroll.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                if (event != null && event.getAction() == MotionEvent.ACTION_MOVE)
                {
                    InputMethodManager imm = ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE));
                    boolean isKeyboardUp = imm.isAcceptingText();

                    if (isKeyboardUp)
                    {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
                return false;
            }
        });
    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.layoutRoot;
    }


    @OnClick({R.id.img_back, R.id.btnSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.btnSubmit:
                if(shopID!=null)
                presenter.writeReview(shopID,rate.getRating());
                break;
        }
    }

    @Override
    public void onSuccessReviews() {
        setResult(RESULT_OK);
        Toast.makeText(this, "Gửi đánh giá thành công.", Toast.LENGTH_SHORT).show();
        finish();
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
        LogUtils.e(message);
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);

    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpired();
    }


}
