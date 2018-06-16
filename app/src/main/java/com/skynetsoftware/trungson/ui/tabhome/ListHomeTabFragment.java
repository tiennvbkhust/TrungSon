package com.skynetsoftware.trungson.ui.tabhome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.ui.allagencies.ListAgencyActivity;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.cart.CartActivity;
import com.skynetsoftware.trungson.ui.home.BottomTabAdapter;
import com.skynetsoftware.trungson.ui.home.HomeFragment;
import com.skynetsoftware.trungson.ui.main.MainActivity;
import com.skynetsoftware.trungson.ui.productOfAgency.ListProductsOfAgencyActivity;
import com.skynetsoftware.trungson.ui.tabhome.listagency.ListAgencyFragment;
import com.skynetsoftware.trungson.ui.tabhome.listfood.ListFoodFragment;
import com.skynetsoftware.trungson.ui.tabhome.listproduct.ListProductFragment;
import com.skynetsoftware.trungson.ui.views.ColorPointHintViewCustom;
import com.skynetsoftware.trungson.ui.views.SlideView;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListHomeTabFragment extends BaseFragment implements ListProductContract.View {
    HomeFragment.CallBackHomeFragment callBackHomeFragment;
    @BindView(R.id.imgHome)
    ImageView imgHome;
    @BindView(R.id.imgCart)
    RelativeLayout imgCart;
    @BindView(R.id.slidePhotos)
    SlideView slidePhotos;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.tvNUmberOfCart)
    TextView tvNUmberOfCart;

    @BindView(R.id.loading)
    ProgressBar loading;
    ListProductContract.Presenter presenter;
    @BindView(R.id.pagerShop)
    ViewPager pagerShop;
    @BindView(R.id.layoutRoot)
    LinearLayout layoutRoot;

    private SlidePhotoAdapter slidePhotoAdapter;
    private BottomTabAdapter bottomTabAdapter;
    private List<Shop> listShop;
    private ICallback callBackClickOnImageSlide = new ICallback() {
        @Override
        public void onCallBack(int pos) {
            if (listShop == null || listShop.size() == 0) return;
            Intent i = new Intent(getActivity(), ListProductsOfAgencyActivity.class);
            i.putExtra("idAgency", listShop.get(pos).getId());
            i.putExtra("url", listShop.get(pos).getAvatar());
            startActivity(i);
        }
    };

    public static ListHomeTabFragment newInstance() {
        ListHomeTabFragment fragment = new ListHomeTabFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_hometab;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);

    }

    @Override
    protected void initVariables() {
        presenter = new ListProductPresenter(this);
        presenter.getListShop();
        slidePhotoAdapter = new SlidePhotoAdapter(slidePhotos, callBackClickOnImageSlide);
        slidePhotos.setAdapter(slidePhotoAdapter);

        bottomTabAdapter = new BottomTabAdapter(getFragmentManager());
        bottomTabAdapter.addTab(ListProductFragment.newInstance());
        bottomTabAdapter.addTab(ListAgencyFragment.newInstance());
//        bottomTabAdapter.addTab(ListFoodFragment.newInstance());
        pagerShop.setAdapter(bottomTabAdapter);
        tabs.setupWithViewPager(pagerShop);
        tabs.getTabAt(0).setText("Sản phẩm mua nhiều");
        tabs.getTabAt(1).setText("Đại lý thuốc gần bạn");
//        tabs.getTabAt(2).setText("Thực phẩm chức năng");

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
//        ViewTreeObserver vto = layoutRoot.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//                    layoutRoot.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                } else {
//                    layoutRoot.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                }
//                int width  = layoutRoot.getMeasuredWidth();
//                int height = layoutRoot.getMeasuredHeight();
//        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
//        params.height = height;
//        layoutRoot.setLayoutParams(params);
//
//            }
//        });
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


    @OnClick({R.id.imgHome, R.id.imgCart, R.id.showAll})
    public void onViewClicked(View view) {
        callBackHomeFragment.onClick(view);
        switch (view.getId()) {
            case R.id.imgHome:

                break;
            case R.id.showAll:
                startActivity(new Intent(getActivity(), ListAgencyActivity.class));
                break;
            case R.id.imgCart:
                startActivity(new Intent(getActivity(), CartActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (AppController.getInstance().getListProducts().size() > 0) {
            int total = 0;
            for (Product product : AppController.getInstance().getListProducts()) {
                total += product.getNumberOfProduct();
            }
            tvNUmberOfCart.setText(total + "");
            tvNUmberOfCart.setVisibility(View.VISIBLE);
        } else {
            tvNUmberOfCart.setVisibility(View.GONE);
        }
    }

    @Override
    public void onSuccessGetList(List<Shop> listShop) {
        this.listShop = listShop;
        slidePhotoAdapter.setUrlPhotos(listShop);
        slidePhotos.setHintView(new ColorPointHintViewCustom(getContext(), Color.parseColor("#ea88c2"), Color.WHITE));
        LogUtils.e("ssss");
        slidePhotoAdapter.notifyDataSetChanged();

    }

    @Override
    public Context getMyContext() {
        return getContext();
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroyView();
        super.onDestroyView();
    }

    @Override
    public void showProgress() {
        loading.setVisibility(View.VISIBLE);
    }

    @Override
    public void hiddenProgress() {
        loading.setVisibility(View.GONE);

    }

    @Override
    public void onErrorApi(String message) {
        ((MainActivity) (getActivity())).showToast(message, AppConstant.NEGATIVE);
    }

    @Override
    public void onError(String message) {
        ((MainActivity) (getActivity())).showToast(message, AppConstant.NEGATIVE);
    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpiredToken();
    }


}
