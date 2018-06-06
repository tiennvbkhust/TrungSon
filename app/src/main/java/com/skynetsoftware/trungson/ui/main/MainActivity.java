package com.skynetsoftware.trungson.ui.main;

import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.home.HomeFragment;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.skynetsoftware.trungson.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements HomeFragment.CallBackHomeFragment {
    ProgressDialogCustom dialogCustom;
    @BindView(R.id.nav_staff)
    LinearLayout navStaff;
    @BindView(R.id.nav_customer)
    LinearLayout navCustomer;
    @BindView(R.id.imgAvatarProfile)
    CircleImageView imgAvatarProfile;
    @BindView(R.id.tvNameProfile)
    TextView tvNameProfile;
    @BindView(R.id.nav_c_home)
    LinearLayout navCHome;
    @BindView(R.id.nav_c_shop)
    LinearLayout navCShop;
    @BindView(R.id.nav_c_nearby)
    LinearLayout navCNearby;
    @BindView(R.id.nav_c_cart)
    LinearLayout navCCart;
    @BindView(R.id.nav_c_notification)
    LinearLayout navCNotification;
    @BindView(R.id.nav_c_history)
    LinearLayout navCHistory;
    @BindView(R.id.nav_c_news)
    LinearLayout navCNews;
    @BindView(R.id.tv_nav_c_badget_favourite)
    TextView tvNavCBadgetFavourite;
    @BindView(R.id.nav_c_favourite)
    LinearLayout navCFavourite;
    @BindView(R.id.nav_c_setting)
    LinearLayout navCSetting;
    @BindView(R.id.tv_nav_s_badget_ondemand)
    TextView tvNavSBadgetOndemand;
    @BindView(R.id.nav_s_ondemand)
    LinearLayout navSOndemand;
    @BindView(R.id.nav_s_checkin)
    LinearLayout navSCheckin;
    @BindView(R.id.tv_nav_s_badget_noti)
    TextView tvNavSBadgetNoti;
    @BindView(R.id.nav_s_notification)
    LinearLayout navSNotification;
    @BindView(R.id.nav_s_news)
    LinearLayout navSNews;
    @BindView(R.id.nav_s_setting)
    LinearLayout navSSetting;
    private Profile profile;
    DrawerLayout drawer;
    LinearLayout currentMenu;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables() {
        //    showToast("abc ss", AppConstant.NEGATIVE);
        profile = AppController.getInstance().getmProfileUser();
        if (profile == null) showDialogExpired();
        if (profile.getType() == AppConstant.TYPE_USER) {
            navStaff.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().add(R.id.root, HomeFragment.newInstance(), "home").commit();
            currentMenu = navCHome;
            currentMenu.getChildAt(0).setVisibility(View.VISIBLE);
        } else {
            navCustomer.setVisibility(View.GONE);
        }

        bindUIProfile();
    }

    private void bindUIProfile() {
        tvNameProfile.setText(String.format("%s\n%s", profile.getName(), (profile.getType() == AppConstant.TYPE_USER ? profile.getAddress() : "Nhân viên")));
        PicassoUtils.loadImage(this, profile.getAvatar(), imgAvatarProfile);
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    protected int initViewSBAnchor() {
        return R.id.root;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @OnClick({R.id.nav_c_home, R.id.nav_c_shop, R.id.nav_c_nearby, R.id.nav_c_cart, R.id.nav_c_notification, R.id.nav_c_history, R.id.nav_c_news, R.id.nav_c_favourite, R.id.nav_c_setting, R.id.nav_s_ondemand, R.id.nav_s_checkin, R.id.nav_s_notification, R.id.nav_s_news, R.id.nav_s_setting})
    public void onViewClicked(View view) {
        if (currentMenu != null) currentMenu.getChildAt(0).setVisibility(View.INVISIBLE);
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home");

        switch (view.getId()) {
            case R.id.nav_c_home:
                homeFragment.tranToTab(0);

                break;
            case R.id.nav_c_shop:
                homeFragment.tranToTab(1);

                break;
            case R.id.nav_c_nearby:
                homeFragment.tranToTab(2);

                break;
            case R.id.nav_c_cart:
                break;
            case R.id.nav_c_notification:
                break;
            case R.id.nav_c_history:
                break;
            case R.id.nav_c_news:
                break;
            case R.id.nav_c_favourite:
                break;
            case R.id.nav_c_setting:
                homeFragment.tranToTab(3);
                break;
            case R.id.nav_s_ondemand:
                break;
            case R.id.nav_s_checkin:
                break;
            case R.id.nav_s_notification:
                break;
            case R.id.nav_s_news:
                break;
            case R.id.nav_s_setting:

                break;
        }
        currentMenu = (LinearLayout) view;
        currentMenu.getChildAt(0).setVisibility(View.VISIBLE);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void changeNavUI(int idViewChoose){
        if (currentMenu != null) currentMenu.getChildAt(0).setVisibility(View.INVISIBLE);
        currentMenu = (LinearLayout) findViewById(idViewChoose);
        currentMenu.getChildAt(0).setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(View v) {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
        if (v.getId() == R.id.imgHome) {
            drawer.openDrawer(Gravity.LEFT);
        } else if (v.getId() == R.id.imgCart) {
            homeFragment.tranToTab(1);

        }
    }
}