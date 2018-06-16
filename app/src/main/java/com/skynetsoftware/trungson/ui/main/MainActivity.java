package com.skynetsoftware.trungson.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.network.socket.SocketClient;
import com.skynetsoftware.trungson.network.socket.SocketResponse;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.cart.CartActivity;
import com.skynetsoftware.trungson.ui.chat.chatlist.ChatListFragment;
import com.skynetsoftware.trungson.ui.chat.chatting.ChatActivity;
import com.skynetsoftware.trungson.ui.checkin.SearchMapAdressActivity;
import com.skynetsoftware.trungson.ui.checkout.CheckoutActivity;
import com.skynetsoftware.trungson.ui.detailbooking.DetailBookingActivity;
import com.skynetsoftware.trungson.ui.favourite.FavouriteActivity;
import com.skynetsoftware.trungson.ui.history.HistoryActivity;
import com.skynetsoftware.trungson.ui.home.HomeFragment;
import com.skynetsoftware.trungson.ui.news.NewsActivity;
import com.skynetsoftware.trungson.ui.notification.NotificationActivity;
import com.skynetsoftware.trungson.ui.tabproduct.ListProductsFragment;
import com.skynetsoftware.trungson.ui.tabprofile.ProfileFragment;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.skynetsoftware.trungson.utils.PicassoUtils;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements HomeFragment.CallBackHomeFragment, MainContract.View {
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
    @BindView(R.id.tv_nav_c_badget_history)
    TextView tv_nav_c_badget_history;
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
    private MainContract.Presenter presenter;

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables() {

        if (getIntent() != null && getIntent().getExtras() != null) {
            String dataFromNoti = getIntent().getExtras().getString(AppConstant.NOTIFICATION_SOCKET);
            if (dataFromNoti != null) {
                SocketResponse data = new Gson().fromJson(dataFromNoti, SocketResponse.class);
                if (data != null) {
                    Intent i;
                    if (data.getTypeData() == SocketClient.TYPE_BOOKING) {
                        i = new Intent(MainActivity.this, DetailBookingActivity.class);
                        i.putExtra(AppConstant.MSG, data.getIdOrder());
                    } else if (data.getTypeData() == SocketClient.TYPE_MESSAGE) {
                        i = new Intent(MainActivity.this, ChatActivity.class);
                        Bundle b = new Bundle();
                        Profile p = new Profile();
                        if(AppController.getInstance().getmProfileUser().getType() == AppConstant.TYPE_USER){
                            p.setId(data.getShop().getId());
                        }else{
                            p.setId(data.getUser().getId());
                        }
                        b.putParcelable(AppConstant.INTENT,  p);
                        i.putExtra(AppConstant.BUNDLE, b);
                    } else {
                        i = new Intent(MainActivity.this, NotificationActivity.class);
                    }
                    startActivity(i);
                }
            }

        }
        //    showToast("abc ss", AppConstant.NEGATIVE);
        presenter = new MainPresenter(this);
        profile = AppController.getInstance().getmProfileUser();
        if (profile == null) {
            showDialogExpired();
            return;
        }
        if (profile.getType() == AppConstant.TYPE_USER) {
            navStaff.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().add(R.id.root, HomeFragment.newInstance(), "home").commit();
            currentMenu = navCHome;
            currentMenu.getChildAt(0).setVisibility(View.VISIBLE);
        } else {
            navCustomer.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().add(R.id.root, ChatListFragment.newInstance(), "chatlist").commit();

        }

        bindUIProfile();
    }

    private void bindUIProfile() {
        tvNameProfile.setText(String.format("%s\n%s", profile.getName(), (profile.getType() == AppConstant.TYPE_USER ? profile.getAddress() : "Nhân viên")));
        PicassoUtils.loadImage(this, profile.getAvatar(), imgAvatarProfile);
        if (profile.getBooking() > 0) {
            tv_nav_c_badget_history.setText(profile.getBooking() + "");
            tv_nav_c_badget_history.setVisibility(View.VISIBLE);
        }
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


    @OnClick({R.id.nav_c_home, R.id.nav_c_shop, R.id.nav_s_checkout, R.id.nav_c_nearby, R.id.nav_c_cart, R.id.nav_c_notification, R.id.nav_c_history, R.id.nav_c_news, R.id.nav_c_favourite, R.id.nav_c_setting, R.id.nav_s_ondemand, R.id.nav_s_checkin, R.id.nav_s_notification, R.id.nav_s_news, R.id.nav_s_setting})
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
                startActivity(new Intent(MainActivity.this, CartActivity.class));
                break;
            case R.id.nav_c_notification:
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
                break;
            case R.id.nav_c_history:
                startActivity(new Intent(MainActivity.this, HistoryActivity.class));

                break;
            case R.id.nav_c_news:
                startActivity(new Intent(MainActivity.this, NewsActivity.class));

                break;
            case R.id.nav_c_favourite:
                startActivity(new Intent(MainActivity.this, FavouriteActivity.class));

                break;
            case R.id.nav_c_setting:
                homeFragment.tranToTab(3);
                break;
            case R.id.nav_s_ondemand:
                tranToFragment(ChatListFragment.newInstance());
                break;
            case R.id.nav_s_checkin:
                startActivity(new Intent(MainActivity.this, SearchMapAdressActivity.class));

                break;
                case R.id.nav_s_checkout:
                startActivity(new Intent(MainActivity.this, CheckoutActivity.class));

                break;
            case R.id.nav_s_notification:
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));

                break;
            case R.id.nav_s_news:
                startActivity(new Intent(MainActivity.this, NewsActivity.class));
                break;
            case R.id.nav_s_setting:
                tranToFragment(ProfileFragment.newInstance());
                break;
        }
        currentMenu = (LinearLayout) view;
        currentMenu.getChildAt(0).setVisibility(View.VISIBLE);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//            if (fragment != null) fragment.onResume();
//
//        }

        presenter.getInfor();
    }

    public void changeNavUI(int idViewChoose) {
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

        }
    }

    public void tranToFragment(Fragment listProductsFragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.root, listProductsFragment, listProductsFragment.getTag()).commit();
    }

    @Override
    public void getInforSuccess() {
        profile = AppController.getInstance().getmProfileUser();
        bindUIProfile();
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
        showDialogExpired();
    }

}
