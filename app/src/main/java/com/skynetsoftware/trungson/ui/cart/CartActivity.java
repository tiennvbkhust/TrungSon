package com.skynetsoftware.trungson.ui.cart;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.cart.tabcart.CartFragment;
import com.skynetsoftware.trungson.ui.home.BottomTabAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends BaseActivity {
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tvNumberProduct)
    TextView tvNumberProduct;
    @BindView(R.id.tvTotal)
    TextView tvTotal;
    @BindView(R.id.layoutBottom)
    LinearLayout layoutBottom;

    BottomTabAdapter bottomTabAdapter;

    @Override
    protected int initLayout() {
        return R.layout.activity_cart;
    }

    @Override
    protected void initVariables() {
        bottomTabAdapter = new BottomTabAdapter(getSupportFragmentManager());
        bottomTabAdapter.addTab(CartFragment.newInstance());
        bottomTabAdapter.addTab(CartFragment.newInstance());
        bottomTabAdapter.addTab(CartFragment.newInstance());
        viewpager.setAdapter(bottomTabAdapter);
        tabs.setupWithViewPager(viewpager);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.layoutRoot;
    }


    @OnClick({R.id.img_back, R.id.btnNext})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.btnNext:
                if (viewpager.getCurrentItem() < 2)
                    viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                break;
        }
    }
}
