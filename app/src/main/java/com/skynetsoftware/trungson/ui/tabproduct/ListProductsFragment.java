package com.skynetsoftware.trungson.ui.tabproduct;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.home.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ListProductsFragment extends BaseFragment {

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
    private HomeFragment.CallBackHomeFragment callBackHomeFragment;
    private String idAgency = null;
    private BottomSheetBehavior.BottomSheetCallback bottomSheetCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            switch (newState) {
                case BottomSheetBehavior.STATE_HIDDEN:
                    getView().findViewById(R.id.bg).setVisibility(View.GONE);
                    break;
                case BottomSheetBehavior.STATE_COLLAPSED: {
                    getView().findViewById(R.id.bg).setVisibility(View.VISIBLE);
                }
                break;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            getView().findViewById(R.id.bg).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.bg).setAlpha(slideOffset);
        }
    };

    public static ListProductsFragment newInstance(String idAgency) {
        Bundle args = new Bundle();
        args.putString("idAgency",idAgency);
        ListProductsFragment fragment = new ListProductsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_tab_product;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
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
            }
        });
        toolbarCbSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sheetBehaviorSort.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });
    }

    @Override
    protected void initVariables() {
       idAgency =  getArguments().getString("idAgency");
       if(idAgency == null || idAgency.isEmpty()){
           btnMsg.setVisibility(View.GONE);
           btnRate.setVisibility(View.GONE);
       }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
    @OnClick(R.id.bg)
    public void onViewBClicked() {
        sheetBehaviorSort.setState(BottomSheetBehavior.STATE_HIDDEN);
        sheetBehaviorFitler.setState(BottomSheetBehavior.STATE_HIDDEN);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            callBackHomeFragment = (HomeFragment.CallBackHomeFragment) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callBackHomeFragment = null;
    }


    @OnClick({R.id.imgHome, R.id.btn_msg, R.id.btn_rate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgHome:
                callBackHomeFragment.onClick(view);
                break;
            case R.id.btn_msg:
                break;
            case R.id.btn_rate:
                break;
        }
    }
}
