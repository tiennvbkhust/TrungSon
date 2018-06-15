package com.skynetsoftware.trungson.ui.chat.chatlist;


import com.skynetsoftware.trungson.models.ChatItem;

import java.util.List;

public class ChatListPresenter implements ChatListContract.Presenter {
    public ChatListPresenter(ChatListContract.View view) {
        this.view = view;
        interactor = new ChatListRemoteImpl(this);
    }

    private ChatListContract.View view;
    private ChatListContract.Interactor interactor;


    @Override
    public void getChatList() {
        view.showProgress();
        interactor.doGetChatList();
    }

    @Override
    public void onDestroyView() {
        view = null;
    }

    @Override
    public void onErrorApi(String message) {
        if (view == null) return;
        view.hiddenProgress();
        view.onErrorApi(message);
    }

    @Override
    public void onError(String message) {
        if (view == null) return;
        view.hiddenProgress();
        view.onError(message);
    }

    @Override
    public void onErrorAuthorization() {
        if (view == null) return;
        view.hiddenProgress();
        view.onErrorAuthorization();
    }

    @Override
    public void onSuccessGetChatList(List<ChatItem> chatListList) {
        if (view == null) {
            return;
        }
        view.hiddenProgress();
        if (chatListList != null)
            view.onSuccessGetChatList(chatListList);
    }
}
