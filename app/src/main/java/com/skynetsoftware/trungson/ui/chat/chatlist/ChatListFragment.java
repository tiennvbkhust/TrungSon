package com.skynetsoftware.trungson.ui.chat.chatlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.interfaces.ICallback;
import com.skynetsoftware.trungson.models.ChatItem;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.base.BaseFragment;
import com.skynetsoftware.trungson.ui.chat.chatting.ChatActivity;
import com.skynetsoftware.trungson.ui.home.HomeFragment;
import com.skynetsoftware.trungson.ui.views.ProgressDialogCustom;
import com.skynetsoftware.trungson.utils.AppConstant;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatListFragment extends BaseFragment implements ChatListContract.View, ICallback {

    @BindView(R.id.tvTitle_toolbar)
    TextView tvTitleToolbar;
    @BindView(R.id.empty)
    TextView empty;
    @BindView(R.id.rcvChat)
    XRecyclerView rcvChat;
    private ChatListContract.Presenter presenter;
    private ProgressDialogCustom dialogLoading;
    private List<ChatItem> listChat;
    private ChatListAdapter chatListAdapter;
    HomeFragment.CallBackHomeFragment callBackHomeFragment;

    public static ChatListFragment newInstance() {

        Bundle args = new Bundle();

        ChatListFragment fragment = new ChatListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int initLayout() {
        return R.layout.activity_chat_list;
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


    @Override
    protected void initViews(View view) {
        ButterKnife.bind(this,view);
        tvTitleToolbar.setText(R.string.chatlist_title);
        rcvChat.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvChat.setPullRefreshEnabled(true);
        rcvChat.setLoadingMoreEnabled(false);
        rcvChat.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                presenter.getChatList();

            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    protected void initVariables() {
        dialogLoading = new ProgressDialogCustom(getContext());
        presenter = new ChatListPresenter(this);
        listChat = new ArrayList<>();
        chatListAdapter = new ChatListAdapter(listChat, getContext(), this);
        rcvChat.setAdapter(chatListAdapter);
        presenter.getChatList();

    }





    @OnClick({R.id.imgHome})
    public void onViewClicked(View v) {
       callBackHomeFragment.onClick(v);
    }

    @Override
    public void onSuccessGetChatList(List<ChatItem> chatListList) {
        listChat.clear();
        listChat.addAll(chatListList);
        rcvChat.refreshComplete();
        chatListAdapter.notifyDataSetChanged();
        if(listChat.size() >0){
            empty.setVisibility(View.GONE);
        }else{
            empty.setVisibility(View.VISIBLE);

        }
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
        dialogLoading.showDialog();
    }

    @Override
    public void hiddenProgress() {
        dialogLoading.hideDialog();
        rcvChat.refreshComplete();
    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpiredToken();
    }


    @Override
    public void onCallBack(int pos) {
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        Bundle b = new Bundle();
        b.putParcelable(AppConstant.INTENT, listChat.get(pos).getUse());
        intent.putExtra(AppConstant.BUNDLE, b);
        startActivityForResult(intent, 1000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            presenter.getChatList();
        }
    }
}
