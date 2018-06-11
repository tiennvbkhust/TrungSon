package com.skynetsoftware.trungson.ui.detailproduct;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.cart.CartActivity;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailProductActivity extends BaseActivity implements DetailProductContract.View {
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.cartImg)
    ImageView cartImg;
    @BindView(R.id.numberCart)
    TextView numberCart;
    @BindView(R.id.imgCart)
    RelativeLayout imgCart;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.cbFav)
    CheckBox cbFav;
    @BindView(R.id.tilteDescription)
    TextView tilteDescription;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.div)
    ImageView div;
    @BindView(R.id.numberOfProducts)
    TextView numberOfProducts;
    @BindView(R.id.plus)
    ImageView plus;
    @BindView(R.id.tvStateProduct)
    TextView tvStateProduct;
    @BindView(R.id.stateOfProduct)
    LinearLayout stateOfProduct;
    @BindView(R.id.addTocart)
    LinearLayout addTocart;

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tvContent)
    TextView tvContent;

    private DetailProductContract.Presenter presenter;
    private ProgressDialogCustom dialogCustom;
    private Product product;

    @Override
    protected int initLayout() {
        return R.layout.fragment_detail_product;
    }

    @Override
    protected void initVariables() {
        dialogCustom = new ProgressDialogCustom(this);
        presenter = new DetailProductPresenter(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            presenter.getDetail(getIntent().getExtras().getString(AppConstant.MSG));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        countNumberInCart();

    }

    private void countNumberInCart() {
        if (AppController.getInstance().getListProducts().size() > 0) {
            int total = 0;
            for (Product product : AppController.getInstance().getListProducts()) {
                total += product.getNumberOfProduct();
            }
            numberCart.setText(total + "");
            numberCart.setVisibility(View.VISIBLE);
        }else{
            numberCart.setVisibility(View.INVISIBLE);

        }
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (product == null) return;
                if (tab.getText().equals("Mô tả")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tvContent.setText(Html.fromHtml(product.getFeature(), Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        tvContent.setText(Html.fromHtml(product.getFeature()));
                    }
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        tvContent.setText(Html.fromHtml(product.getUsed(), Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        tvContent.setText(Html.fromHtml(product.getUsed()));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                    //  Collapsed
                    imgHome.setImageResource(R.drawable.ic_arrow_back_black_24dp);
                    cartImg.setImageResource(R.drawable.ic_cart);
                } else {
                    //Expanded
                    cartImg.setImageResource(R.drawable.ic_cart_white);
                    imgHome.setImageResource(R.drawable.md_nav_back);
                }
            }
        });
    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.toolbar;
    }


    @OnClick({R.id.imgHome, R.id.imgCart, R.id.div, R.id.plus, R.id.addTocart})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
                onBackPressed();
                break;
            case R.id.imgCart:
                startActivity(new Intent(DetailProductActivity.this, CartActivity.class));
                break;
            case R.id.div: {
                int number = Integer.valueOf(numberOfProducts.getText().toString());
                number--;
                if (number < 1) number = 1;
                numberOfProducts.setText(number + "");
                break;
            }
            case R.id.plus: {
                int number = Integer.valueOf(numberOfProducts.getText().toString());
                numberOfProducts.setText(++number + "");
                break;
            }
            case R.id.addTocart:
                int number = Integer.valueOf(numberOfProducts.getText().toString());
                boolean flagContain = false;
                int i=0;
                product.setNumberOfProduct(number);
                for (Product item : AppController.getInstance().getListProducts()) {
                    if (item.getId().equals(product.getId())) {
                        flagContain = true;
                        item.setNumberOfProduct(product.getNumberOfProduct()+item.getNumberOfProduct());
                        AppController.getInstance().getListProducts().set(i,item);
                        break;
                    }
                    i++;
                }
                if (!flagContain) {
                    try {
                        AppController.getInstance().add(product);
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                        showToast("Không thể thêm vào giỏ.",AppConstant.NEGATIVE);
                    }
                }
                number = 1;
                numberOfProducts.setText(number + "");
                product.setNumberOfProduct(number);
                countNumberInCart();
                break;
        }
    }

    @Override
    public void onSuccessGetDetail(final Product product) {
        this.product = product;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            tvContent.setText(Html.fromHtml(product.getFeature(), Html.FROM_HTML_MODE_COMPACT));
            tilteDescription.setText(Html.fromHtml(product.getFeature(), Html.FROM_HTML_MODE_COMPACT));

        } else {
            tvContent.setText(Html.fromHtml(product.getFeature()));
            tilteDescription.setText(Html.fromHtml(product.getFeature()));

        }
        name.setText(product.getName());

        tilteDescription.setText(product.getFeature());
        price.setText(String.format("%,.0f vnđ", product.getPrice()));
        numberOfProducts.setText(1 + "");
        if (product.getStatus() == 0) {
            stateOfProduct.setBackgroundColor(ContextCompat.getColor(this, R.color.red));
            tvStateProduct.setText("Tạm hết hàng");
            addTocart.setVisibility(View.GONE);
        }
        cbFav.setChecked(product.getIs_favourite() == 1);
        cbFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.toggleFav(product.getId(), isChecked ? 1 : 2);
            }
        });
        if (product.getImg() != null && !product.getImg().isEmpty()) {
            Picasso.with(this).load(product.getImg()).fit().centerCrop().into(image);
        }
    }

    @Override
    public Context getMyContext() {
        return null;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hiddenProgress() {

    }

    @Override
    public void onErrorApi(String message) {

    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onErrorAuthorization() {

    }
}
