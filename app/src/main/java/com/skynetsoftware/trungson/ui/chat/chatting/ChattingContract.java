package com.skynetsoftware.trungson.ui.chat.chatting;



import com.skynetsoftware.trungson.models.Message;
import com.skynetsoftware.trungson.network.socket.SocketClient;
import com.skynetsoftware.trungson.ui.base.BasePresenter;
import com.skynetsoftware.trungson.ui.base.BaseView;
import com.skynetsoftware.trungson.ui.base.OnFinishListener;

import java.util.List;

public interface ChattingContract {
    interface View extends BaseView {
        void onSuccessGetMessages(List<Message> list);
        void onSuccessSendMessage(Message mess);
    }

    interface Presenter extends BasePresenter,ChattingListener{
        void getMessages(String udId);
        void sendMessage(String idUser, String content, SocketClient socketClient);
    }

    interface Interactor {
        void getMessages(String udId, String idShop);
        void sendMessage(String idUser, String idShop, String content, String time);
    }

    interface ChattingListener extends OnFinishListener {
        void onSuccessGetMessages(List<Message> list);
        void onSuccessSendMessage(Message message);
    }
}
