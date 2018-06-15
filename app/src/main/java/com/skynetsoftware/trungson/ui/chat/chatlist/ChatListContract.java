package com.skynetsoftware.trungson.ui.chat.chatlist;



import com.skynetsoftware.trungson.models.ChatItem;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

import java.util.List;

public interface ChatListContract {
    interface View extends BaseView {
        void onSuccessGetChatList(List<ChatItem> chatListList);
    }

    interface Presenter extends BasePresenter,OnChatListListener{
        void getChatList();
    }

    interface Interactor {
        void doGetChatList();
    }
    interface OnChatListListener extends OnFinishListener {
        void onSuccessGetChatList(List<ChatItem> chatListList);
    }
}
