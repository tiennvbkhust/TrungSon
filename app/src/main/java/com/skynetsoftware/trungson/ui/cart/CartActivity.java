package com.skynetsoftware.trungson.ui.cart;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.cart.tabAdress.AddressInforFragment;
import com.skynetsoftware.trungson.ui.cart.tabcart.CartFragment;
import com.skynetsoftware.trungson.ui.cart.tabpayment.PaymentInforFragment;
import com.skynetsoftware.trungson.ui.home.BottomTabAdapter;

import java.util.List;

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
        bottomTabAdapter.addTab(AddressInforFragment.newInstance());
        bottomTabAdapter.addTab(PaymentInforFragment.newInstance());
        viewpager.setAdapter(bottomTabAdapter);
        tabs.setupWithViewPager(viewpager);
        tabs.getTabAt(0).setText("GIỎ HÀNG");
        tabs.getTabAt(1).setText("THÔNG TIN NHẬN HÀNG");
        tabs.getTabAt(2).setText("THANH TOÁN");
        viewpager.setCurrentItem(0);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2){
                    layoutBottom.setVisibility(View.GONE);
                }else{
                    layoutBottom.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        countProducts();

    }

    public void countProducts() {
        List<Product> list = AppController.getInstance().getListProducts();
        int total = 0;
        double price = 0;
        for (Product product : list) {
            total += product.getNumberOfProduct();
            price += (product.getNumberOfProduct() * product.getPrice());
        }
        tvNumberProduct.setText(String.format("%d sản phẩm",total));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvTotal.setText(Html.fromHtml(String.format(getString(R.string.total_format),price),Html.FROM_HTML_MODE_COMPACT));

        }else{
            tvTotal.setText(Html.fromHtml(String.format(getString(R.string.total_format),price)));
        }
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
