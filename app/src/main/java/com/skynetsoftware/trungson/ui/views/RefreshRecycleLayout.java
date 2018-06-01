package com.skynetsoftware.trungson.ui.views;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;


/**
 * Created by hoaph on 8/3/2017.
 */

public class RefreshRecycleLayout extends SwipeRefreshLayout {
    private RecyclerView mRecyclerView;
    private boolean mLoading;
    private RefreshListener mRefreshListener;
    int pastVisibleItems, visibleItemCount, totalItemCount;

    public RefreshRecycleLayout(Context context) {
        super(context);
    }

    public RefreshRecycleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setRefreshListener(RefreshListener refreshListener) {
        mRefreshListener = refreshListener;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.mRecyclerView = recyclerView;
        final LinearLayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
//        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//        divider.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.my_custom_divider));
//        recyclerView.addItemDecoration(divider);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) //check for scroll down
                {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (!mLoading && (visibleItemCount + pastVisibleItems) >= totalItemCount && !isRefreshing()) {
                        if (mRefreshListener != null) {
                            mRefreshListener.onLoadMore();
                            setEnabled(false);
                        }
                        mLoading = true;
                        Log.v("...", "Last Item Wow !");
                        //Do pagination.. i.e. fetch new data
                    }
                }
            }
        });

        this.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (mRefreshListener != null) {
                    mRefreshListener.onRefresh();
                    mLoading = true;
                }
            }
        });
    }

    public interface RefreshListener {
        void onRefresh();

        void onLoadMore();
    }

    public void setLoadMoreComplete() {
        mLoading = false;
        setRefreshing(false);
        setEnabled(true);
    }

    public void setRefreshFinish() {
        mLoading = false;
        setRefreshing(false);
        setEnabled(true);
    }
}
