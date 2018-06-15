package com.skynetsoftware.trungson.ui.history;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.models.News;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity {
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvTitle_toolbar)
    TextView tvTitleToolbar;

    @Override
    protected int initLayout() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void initVariables() {
        if (getIntent() != null && getIntent().getBundleExtra(AppConstant.BUNDLE) != null) {
            News product = getIntent().getBundleExtra(AppConstant.BUNDLE).getParcelable(AppConstant.MSG);
            tvTime.setText(product.getDate());
            name.setText(product.getTitle());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                content.setText(
                        Html.fromHtml(
                                product.getContent(),
                                Html.FROM_HTML_MODE_COMPACT));

            } else {
                content.setText(
                        Html.fromHtml(
                                product.getContent()));
            }
            if (product.getImg() != null && !product.getImg().isEmpty()) {
                Picasso.with(this).load(product.getImg()).fit().centerCrop().into(img);
            }

        }
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        tvTitleToolbar.setText("Nội dung tin tức");
    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }



    @OnClick(R.id.imgBtn_back_toolbar)
    public void onViewClicked() {
        onBackPressed();
    }
}
