package com.skynetsoftware.trungson.ui.productOfAgency;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.jaygoo.widget.OnRangeChangedListener;
import com.jaygoo.widget.RangeSeekBar;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Category;
import com.skynetsoftware.trungson.models.Filter;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.home.BottomTabAdapter;
import com.skynetsoftware.trungson.ui.home.HomeFragment;
import com.skynetsoftware.trungson.ui.main.MainActivity;
import com.skynetsoftware.trungson.ui.tabproduct.product.ProductFragment;
import com.skynetsoftware.trungson.ui.writereviewshop.WriteReviewShopActivity;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListProductsOfAgencyActivity extends BaseActivity implements ListProductsContract.View {

    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.toolbarCbSort)
    ImageView toolbarCbSort;
    @BindView(R.id.toolbarCbFilter)
    ImageView toolbarCbFilter;
    @BindView(R.id.btn_msg)
    FloatingActionButton btnMsg;
    @BindView(R.id.btn_rate)
    FloatingActionButton btnRate;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.cb)
    CheckBox cbFilterAll;
    @BindView(R.id.pagerProduct)
    ViewPager pagerProduct;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottomFilterSheet;
    BottomSheetBehavior sheetBehaviorFitler;
    @BindView(R.id.bottom_sort_sheet)
    LinearLayout bottomSortSheet;
    BottomSheetBehavior sheetBehaviorSort;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.seekbar)
    RangeSeekBar seekbar;
    @BindView(R.id.rcvFilter)
    RecyclerView rcvFilter;
    @BindView(R.id.btnCancel)
    TextView btnCancel;
    @BindView(R.id.btnApply)
    TextView btnApply;
    @BindView(R.id.radGroup)
    RadioGroup radioGroup;
    @BindView(R.id.radBestSeller)
    RadioButton radBestSeller;
    private ListProductsContract.Presenter presenter;
    private BottomTabAdapter bottomTabAdapter;
    private List<Category> lisCategories;
    private HomeFragment.CallBackHomeFragment callBackHomeFragment;
    private String idAgency = null;
    private FilterAdapter adapterFilter;
    private final static float NUMBER_PRICE_MAX = 10000000;
    private final static float NUMBER_PRICE_MIN = 0;
    private float maxPrice = NUMBER_PRICE_MAX, minPrice = NUMBER_PRICE_MIN;
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN:
                    findViewById(R.id.bg).setVisibility(View.GONE);
                    break;
                case BottomSheetBehavior.STATE_COLLAPSED: {
                    findViewById(R.id.bg).setVisibility(View.VISIBLE);
                }
                break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            findViewById(R.id.bg).setVisibility(View.VISIBLE);
           findViewById(R.id.bg).setAlpha(slideOffset);
        }
    };



    @Override
    protected int initLayout() {
        return R.layout.fragment_product_of_agency;
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        rcvFilter.setLayoutManager(new LinearLayoutManager(this));
        rcvFilter.setHasFixedSize(true);

        sheetBehaviorFitler = BottomSheetBehavior.from(bottomFilterSheet);
        sheetBehaviorSort = BottomSheetBehavior.from(bottomSortSheet);
        sheetBehaviorFitler.setBottomSheetCallback(bottomSheetCallback);
        sheetBehaviorSort.setBottomSheetCallback(bottomSheetCallback);
        sheetBehaviorSort.setState(BottomSheetBehavior.STATE_HIDDEN);
        sheetBehaviorFitler.setState(BottomSheetBehavior.STATE_HIDDEN);

        toolbarCbFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehaviorFitler.setState(BottomSheetBehavior.STATE_EXPANDED);
                sheetBehaviorSort.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });
        toolbarCbSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehaviorSort.setState(BottomSheetBehavior.STATE_EXPANDED);
                sheetBehaviorFitler.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        });

        pagerProduct.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvTitle.setText("Danh mục " + lisCategories.get(position).getName());
                Fragment fragment = bottomTabAdapter.getFragment(position);
                if (fragment != null) {
                    fragment.onResume();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) - appBarLayout.getTotalScrollRange() == 0) {
                    //  Collapsed
                    if (AppController.getInstance().getmFilter() != null) {
                        toolbarCbFilter.setImageResource(R.drawable.ic_filter);
                    } else {
                        toolbarCbFilter.setImageResource(R.drawable.selector_cb_filter_black);
                    }
                    if (AppController.getInstance().getTypeSort() == -1) {
                        toolbarCbSort.setImageResource(R.drawable.selector_cb_sort_black);
                    } else {
                        toolbarCbSort.setImageResource(R.drawable.ic_sort_icon);
                    }
                    if (idAgency == null || idAgency.isEmpty()) {

                        imgHome.setImageResource(R.drawable.ic_navbar);
                    } else {
                        imgHome.setImageResource(R.drawable.ic_arrow_back_black_24dp);

                    }
                } else {
                    //Expanded
                    if (AppController.getInstance().getmFilter() != null) {
                        toolbarCbFilter.setImageResource(R.drawable.ic_filter);
                    } else {
                        toolbarCbFilter.setImageResource(R.drawable.selector_cb_filter);
                    }
                    if (AppController.getInstance().getTypeSort() == -1) {
                        toolbarCbSort.setImageResource(R.drawable.selector_cb_sort);

                    } else {
                        toolbarCbSort.setImageResource(R.drawable.ic_sort_icon);
                    }
                    if (idAgency == null || idAgency.isEmpty()) {
                        imgHome.setImageResource(R.drawable.ic_navbar_white);
                    } else {
                        imgHome.setImageResource(R.drawable.md_nav_back);

                    }
                }
            }
        });
//        pagerProduct.setOffscreenPageLimit(0);
        seekbar.setValue(NUMBER_PRICE_MIN, NUMBER_PRICE_MAX);
        //  radBestSeller.setChecked(true);
        seekbar.setOnRangeChangedListener(new OnRangeChangedListener() {
            @Override
            public void onRangeChanged(RangeSeekBar view, float min, float max, boolean isFromUser) {
                if (isFromUser) {
                    tvPrice.setText(String.format("%,.0f vnđ - %,.0f vnđ", min, max));
                    maxPrice = max;
                    minPrice = min;
                }
            }

            @Override
            public void onStartTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }

            @Override
            public void onStopTrackingTouch(RangeSeekBar view, boolean isLeft) {

            }
        });

        tvPrice.setText(String.format("%,.0f vnđ - %,.0f vnđ", NUMBER_PRICE_MIN, NUMBER_PRICE_MAX));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                sheetBehaviorSort.setState(BottomSheetBehavior.STATE_HIDDEN);
                //   Filter filter = AppController.getInstance().getmFilter();
                //  if (filter == null) filter = new Filter();
                if (checkedId == R.id.radBestSeller) {
                    AppController.getInstance().setTypeSort(1);
                } else if (checkedId == R.id.radMaxPrice) {
                    AppController.getInstance().setTypeSort(2);
                } else {
                    AppController.getInstance().setTypeSort(3);
                }
                // filter.setTypeSort(AppController.getInstance().getTypeSort());
                // AppController.getInstance().setmFilter(filter);
                bottomTabAdapter.notifyDataSetChanged();
                pagerProduct.setAdapter(bottomTabAdapter);
                tabs.setupWithViewPager(pagerProduct);
                for (int j = 0; j < lisCategories.size(); j++) {
                    tabs.getTabAt(j).setText(lisCategories.get(j).getName());
                    if (j == 0) {
                        tvTitle.setText("Danh mục " + lisCategories.get(j).getName());
                    }
                }
                sheetBehaviorFitler.setState(BottomSheetBehavior.STATE_HIDDEN);
                toolbarCbSort.setImageResource(R.drawable.ic_sort1_black);
            }
        });
    }

    @Override
    protected void initVariables() {
        presenter = new ListProductsPresenter(this);
        presenter.getListCategories();
        bottomTabAdapter = new BottomTabAdapter(getSupportFragmentManager());
        if (getIntent().getExtras() != null) {
            idAgency = getIntent().getExtras().getString("idAgency");
            String url = getIntent().getExtras().getString("url");

            if (idAgency == null || idAgency.isEmpty()) {
                btnMsg.setVisibility(View.GONE);
                btnRate.setVisibility(View.GONE);
            }
        }
    }


    @Override
    protected int initViewSBAnchor() {
        return R.id.app_bar_layout;
    }


    @Override
    protected void onDestroy() {
        presenter.onDestroyView();

        super.onDestroy();
    }

    @OnClick(R.id.bg)
    public void onViewBClicked() {
        sheetBehaviorSort.setState(BottomSheetBehavior.STATE_HIDDEN);
        sheetBehaviorFitler.setState(BottomSheetBehavior.STATE_HIDDEN);
    }




    @OnClick({R.id.imgHome, R.id.btn_msg, R.id.btn_rate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
               onBackPressed();
                break;
            case R.id.btn_msg:
                Intent i = new Intent(ListProductsOfAgencyActivity.this, WriteReviewShopActivity.class);
                i.putExtra(AppConstant.MSG,idAgency);
                startActivity(i);
                break;
            case R.id.btn_rate:
                Intent i2 = new Intent(ListProductsOfAgencyActivity.this, WriteReviewShopActivity.class);
                i2.putExtra(AppConstant.MSG,idAgency);
                startActivity(i2);
                break;
        }
    }

    @OnClick({R.id.btnCancel, R.id.btnApply})
    public void onViewFilterClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                if (adapterFilter != null) {
                    adapterFilter.clearCheck();
                }
                seekbar.setValue(NUMBER_PRICE_MIN, NUMBER_PRICE_MAX);
                tvPrice.setText(String.format("%,.0f vnđ - %,.0f vnđ", NUMBER_PRICE_MIN, NUMBER_PRICE_MAX));
                cbFilterAll.setChecked(false);
                AppController.getInstance().setmFilter(null);
                toolbarCbFilter.setImageResource(R.drawable.ic_filter2);
                minPrice = NUMBER_PRICE_MIN;
                maxPrice = NUMBER_PRICE_MAX;
                break;
            case R.id.btnApply:
                Filter filter = AppController.getInstance().getmFilter();
                if (filter == null) filter = new Filter();
                filter.setMinPrice(minPrice);
                filter.setMaxPrice(maxPrice);
                int i = 0;
                for (Category category : lisCategories) {
                    if (category.isChecked()) {
                        filter.setIdCategory(category.getId());
                        break;
                    }
                    i++;
                }
                if (cbFilterAll.isChecked()) {
                    filter.setIdCategory("-1");
                    AppController.getInstance().setmFilter(filter);
                }
                AppController.getInstance().setmFilter(filter);
                bottomTabAdapter.notifyDataSetChanged();
                pagerProduct.setAdapter(bottomTabAdapter);
                tabs.setupWithViewPager(pagerProduct);
                if (i < lisCategories.size()) {
                    pagerProduct.setCurrentItem(i);
                }
                for (int j = 0; j < lisCategories.size(); j++) {
                    tabs.getTabAt(j).setText(lisCategories.get(j).getName());
                    if (j == 0) {
                        tvTitle.setText("Danh mục " + lisCategories.get(j).getName());
                    }
                }
                sheetBehaviorFitler.setState(BottomSheetBehavior.STATE_HIDDEN);
                toolbarCbFilter.setImageResource(R.drawable.selector_cb_filter_black);
                break;
        }
    }

    @Override
    public void onSuccessGetListCategories(List<Category> list) {
        lisCategories = list;
        for (Category ca : list) {
            bottomTabAdapter.addTab(ProductFragment.newInstance(ca));
        }
        pagerProduct.setAdapter(bottomTabAdapter);
        tabs.setupWithViewPager(pagerProduct);
        for (int j = 0; j < list.size(); j++) {
            tabs.getTabAt(j).setText(list.get(j).getName());
            if (j == 0) {
                tvTitle.setText("Danh mục " + list.get(j).getName());
            }
        }
        adapterFilter = new FilterAdapter(this.lisCategories, this);
        rcvFilter.setAdapter(adapterFilter);
    }

    @Override
    public Context getMyContext() {
        return this;

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hiddenProgress() {

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
        //   ((MainActivity)getActivity()).showToast(message, AppConstant.NEGATIVE);

    }


}
