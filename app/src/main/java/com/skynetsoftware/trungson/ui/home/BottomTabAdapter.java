package com.skynetsoftware.trungson.ui.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.skynetsoftware.trungson.ui.tabproduct.product.ProductFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BottomTabAdapter extends FragmentPagerAdapter {
    List<Fragment> listFragment = new ArrayList<>();
    List<String> lstTitle = new ArrayList<>();
    private Map<Integer, String> mFragmentTags;
    FragmentManager fragmentManager;
    public BottomTabAdapter(FragmentManager fm) {
        super(fm);
        this.fragmentManager = fm;
        mFragmentTags = new HashMap<Integer, String>();
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
    public int getItemPosition(@NonNull Object object) {
        ProductFragment f = (ProductFragment ) object;
        if (f != null) {
            f.update();
        }
        return super.getItemPosition(object);
    }

    @Override
    public int getCount() {
        return listFragment.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object object = super.instantiateItem(container, position);
        if (object instanceof Fragment) {
            Fragment fragment = (Fragment) object;
            String tag = fragment.getTag();
            mFragmentTags.put(position, tag);
        }
        return object;
    }

    public Fragment getFragment(int position) {
        Fragment fragment = null;
        String tag = mFragmentTags.get(position);
        if (tag != null) {
            fragment = fragmentManager.findFragmentByTag(tag);
        }
        return fragment;
    }
}
