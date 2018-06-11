package com.skynetsoftware.trungson.ui.home;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.google.android.gms.maps.MapsInitializer;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.main.MainActivity;
import com.skynetsoftware.trungson.ui.tabhome.ListHomeTabFragment;
import com.skynetsoftware.trungson.ui.tabhome.listfood.ListFoodFragment;
import com.skynetsoftware.trungson.ui.tabmap.MapFragment;
import com.skynetsoftware.trungson.ui.tabproduct.ListProductsFragment;
import com.skynetsoftware.trungson.ui.tabprofile.ProfileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.bottomtab)
    TabLayout bottomtab;
    private BottomTabAdapter bottomTabAdapter;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int initLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initVariables() {
        bottomTabAdapter = new BottomTabAdapter(getFragmentManager());
        bottomTabAdapter.addTab(ListHomeTabFragment.newInstance());
        bottomTabAdapter.addTab(ListProductsFragment.newInstance());
        bottomTabAdapter.addTab(MapFragment.newInstance());
        bottomTabAdapter.addTab(ProfileFragment.newInstance());
        viewpager.setAdapter(bottomTabAdapter);
        bottomtab.setupWithViewPager(viewpager);
        bottomtab.getTabAt(0).setIcon(R.drawable.ic_tab_categories);
        bottomtab.getTabAt(1).setIcon(R.drawable.ic_tab_shop);
        bottomtab.getTabAt(2).setIcon(R.drawable.ic_tab_map);
        bottomtab.getTabAt(3).setIcon(R.drawable.ic_tab_me);
//        this.viewpager.setOffscreenPageLimit(0);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0: {
                        ((MainActivity) getActivity()).changeNavUI(R.id.nav_c_home);
                        break;
                    }
                    case 1: {
                        ((MainActivity) getActivity()).changeNavUI(R.id.nav_c_shop);

                        break;
                    }
                    case 2: {
                        ((MainActivity) getActivity()).changeNavUI(R.id.nav_c_nearby);

                        break;
                    }
                    case 3: {
                        ((MainActivity) getActivity()).changeNavUI(R.id.nav_s_setting);

                        break;
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    public void tranToTab(int tab) {
        if (tab < 4)
            viewpager.setCurrentItem(tab);
    }
    public void tranToTab(Fragment tab) {
          getFragmentManager().beginTransaction().replace(R.id.layoutRoot,tab).commit();
    }



    public interface CallBackHomeFragment {
        void onClick(View v);
    }

}
