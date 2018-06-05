package com.skynetsoftware.trungson.ui.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class BottomTabAdapter extends FragmentPagerAdapter {
    List<Fragment> listFragment = new ArrayList<>();
    List<String> lstTitle = new ArrayList<>();

    public BottomTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return listFragment.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return lstTitle.get(position);
    }

    public void addTab(Fragment fragment) {
        listFragment.add(fragment);
        lstTitle.add(fragment.getTag());
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }
}
