package com.skynetsoftware.trungson.ui.tabhome.listfood;

import android.os.Bundle;
import android.view.View;

import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.R;

public class ListFoodFragment extends BaseFragment {

    public static ListFoodFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ListFoodFragment fragment = new ListFoodFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Override
    protected int initLayout() {
        return R.layout.fragment_list_food
                ;
    }

    @Override
    protected void initViews(View view) {

    }

    @Override
    protected void initVariables() {

    }
}
