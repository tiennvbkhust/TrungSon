package com.skynetsoftware.trungson.ui.home;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.tabhome.ListHomeTabFragment;

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
//        bottomTabAdapter.addTab(ListProductFragment.newInstance());
//        bottomTabAdapter.addTab(ListProductFragment.newInstance());
//        bottomTabAdapter.addTab(ListProductFragment.newInstance());
        viewpager.setAdapter(bottomTabAdapter);
        bottomtab.setupWithViewPager(viewpager);
        bottomtab.getTabAt(0).setIcon(R.drawable.ic_tab_categories);
//        bottomtab.getTabAt(1).setIcon(R.drawable.ic_tab_shop);
//        bottomtab.getTabAt(2).setIcon(R.drawable.ic_tab_map);
//        bottomtab.getTabAt(3).setIcon(R.drawable.ic_tab_me);

    }


    public void tranToCart() {
        viewpager.setCurrentItem(1);
    }

    public interface CallBackHomeFragment {
        void onClick(View v);
    }

}
