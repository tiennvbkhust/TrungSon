package com.skynetsoftware.trungson.ui.chat.chatting;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.Gson;
import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.models.Message;
import com.skynetsoftware.trungson.models.Shop;
import com.skynetsoftware.trungson.network.socket.SocketConstants;
import com.skynetsoftware.trungson.network.socket.SocketResponse;
import com.skynetsoftware.trungson.ui.base.BaseActivity;
import com.skynetsoftware.trungson.ui.views.ChatParentLayout;
import com.skynetsoftware.trungson.utils.AppConstant;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity implements ChattingContract.View {
    @BindView(R.id.rcv_chat)
    RecyclerView mRcv;
    @BindView(R.id.tvTitle_toolbar)
    TextView textToolbar;

    @BindView(R.id.message_txt)
    EditText mMessage;
    @BindView(R.id.row_chat_ll)
    ChatParentLayout mChatLL;
    private ChattingContract.Presenter presenter;
    private String idShop;
    private Shop shop;
    private AdapterChat mAdapterChat;
    private List<Message> mList = new ArrayList<>();
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.e("OnReceive from UI ");
            if (intent != null) {
                SocketResponse data = new Gson().fromJson(intent.getExtras().getString(AppConstant.MSG), SocketResponse.class);
                if (data != null) {
//                    Message message = new Message();
//                    message.setContent(data.getContentMessage());
//                    message.setShId(data.getShop().getId());
//                    message.setUId(data.getUser().getId());
//                    message.setType(2);
                    mList.add(data.getMessage());
                    mAdapterChat.notifyItemInserted(mAdapterChat.getItemCount() -1);
//                    mRcv.setAdapter(mAdapterChat);
                    if (mAdapterChat.getItemCount() > 0)
                        mRcv.smoothScrollToPosition(mAdapterChat.getItemCount() );
//                    }
                }
            }
        }
    };

    @Override
    protected int initLayout() {
        return R.layout.activity_chat;
    }


    @Override
    protected void initVariables() {
        mRcv.setLayoutManager(new LinearLayoutManager(this));
        presenter = new ChattingPresenter(this);
        if (getIntent() != null) {
            shop = getIntent().getBundleExtra(AppConstant.BUNDLE).getParcelable(AppConstant.INTENT);
            idShop = shop.getId();
            if (shop.getName() != null)
                textToolbar.setText(shop.getName());
        }
        mAdapterChat = new AdapterChat(mList, this);

        AppConstant.ID_CHAT = idShop;
        LogUtils.e(AppConstant.ID_CHAT);
        getMessage();


        mChatLL.setOnKeyBoardChangeListener(new ChatParentLayout.OnKeyBoardChangeListener() {
            @Override
            public void onShow(int keyboardHeight) {
                try {
                    if (mAdapterChat.getItemCount() > 0)
                        mRcv.scrollToPosition(mAdapterChat.getItemCount() - 1);
                } catch (Exception e) {

                }
            }

            @Override
            public void onHide(int keyboardHeight) {
                try {
                    if (mAdapterChat.getItemCount() > 0)
                        mRcv.scrollToPosition(mAdapterChat.getItemCount() - 1);
                } catch (Exception e) {
                }

            }
        });

        mMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    if (mAdapterChat.getItemCount() > 0)
                        mRcv.scrollToPosition(mAdapterChat.getItemCount() - 1);
                } catch (Exception e) {
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void initViews() {
        ButterKnife.bind(this);

    }

    @Override
    protected int initViewSBAnchor() {
        return 0;
    }


    @OnClick({R.id.imgBtn_back_toolbar, R.id.send_imv})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBtn_back_toolbar:
                onBackPressed();
                break;
            case R.id.send_imv:
                final String content = mMessage.getText().toString().trim();
                if (content.isEmpty()) {
                    Toast.makeText(this, "Bạn phải nhập tin nhắn", Toast.LENGTH_SHORT).show();
                    return;
                }

                Calendar c = Calendar.getInstance();
                String year = String.valueOf(c.get(Calendar.YEAR));
                String month = String.valueOf(c.get(Calendar.MONTH) + 1);
                String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
                String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
                String minute = String.valueOf(c.get(Calendar.MINUTE));
                String second = String.valueOf(c.get(Calendar.SECOND));
                final String time = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
//                if (!isFinishing())
//                    getDialogProgress().showDialog();
//                Call<ApiResponse> message1 = getmThaiHuongApi().message(MainApplication.getInstance().getmUser().getId(), id, time
//                        , content, MainApplication.getInstance().getmUser().getId());
//                message1.enqueue(new CallBackCustom<ApiResponse>(this, getDialogProgress(), new OnResponse<ApiResponse>() {
//                    @Override
//                    public void onResponse(ApiResponse object) {
//                        if (object.getErrorId() == Constants.CODE_SUCCESS) {
//
//                        }
//                    }
//                }));
                presenter.sendMessage(idShop, content, getmSocket());
                break;
        }
    }

    private void getMessage() {
//        if (!isFinishing())
//            getDialogProgress().showDialog();
//        Call<ApiResponse<List<Message>>> getMessage = getmThaiHuongApi().getMessage(MainApplication.getInstance().getmUser().getId(), id);
//        getMessage.enqueue(new CallBackCustom<ApiResponse<List<Message>>>(this, getDialogProgress(), new OnResponse<ApiResponse<List<Message>>>() {
//            @Override
//            public void onResponse(ApiResponse<List<Message>> object) {
//                if (object.getErrorId() == Constants.CODE_SUCCESS) {
//
//                }
//            }
//        }));
        presenter.getMessages(idShop);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroyView();
        super.onDestroy();
        AppConstant.ID_CHAT = "0";
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppConstant.ID_CHAT = "0";

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(receiver);
        LogUtils.e("unregister receiver");

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new  IntentFilter();
        intentFilter.addAction(SocketConstants.SOCKET_CHAT);
        registerReceiver(receiver,intentFilter );
        LogUtils.e("register receiver");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConstant.ID_CHAT = idShop;

    }

    @Override
    public void onSuccessGetMessages(List<Message> list) {
        mList.addAll(list);
        mAdapterChat = new AdapterChat(mList, ChatActivity.this);
        mRcv.setAdapter(mAdapterChat);
        setResult(RESULT_OK);
    }

    @Override
    public void onSuccessSendMessage(Message message) {
        mMessage.setText("");
        mList.add(message);
        mAdapterChat.notifyDataSetChanged();
        mRcv.setAdapter(mAdapterChat);
        if (mAdapterChat.getItemCount() > 0)
            mRcv.scrollToPosition(mAdapterChat.getItemCount() - 1);
        setResult(RESULT_OK);

    }

    @Override
    public Context getMyContext() {
        return this;
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hiddenProgress() {

    }

    @Override
    public void onErrorApi(String message) {
        LogUtils.e(message);
    }

    @Override
    public void onError(String message) {
        LogUtils.e(message);

    }

    @Override
    public void onErrorAuthorization() {
        showDialogExpired();
    }
}
