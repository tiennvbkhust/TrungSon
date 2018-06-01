package com.skynetsoftware.trungson.network.socket;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.engineio.parser.Packet;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;

import com.skynetsoftware.trungson.R;
import com.skynetsoftware.trungson.application.AppController;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.utils.AppConstant;
import com.skynetsoftware.trungson.utils.CommomUtils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * Created by Huy on 7/3/2017.
 */

public class SocketClient extends Service {
    IBinder mBinder;
    Socket socket;
    OnListenResponse onListenRespone;
    SPUtils mSetting = new SPUtils(AppConstant.KEY_SETTING);
    Notification notification;
    CountDownTimer countDownTimer;
    public static final int STATE_NEWORDER = 1;
    public static final int STATE_RECEIVED = 2;
    public static final int STATE_INPROGRESS = 3;
    public static final int STATE_FINISHED = 4;
    public static final int STATE_CANCELED = 5;
    public static final int TYPE_NOTIFY = 0;
    public static final int TYPE_BOOKING = 1;
    public static final int TYPE_MESSAGE = 2;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.e("OnBind");
        initSocket();
        return mBinder;
    }

    public interface OnListenResponse {
        void onResponse(String str);
    }

    public class LocalBinder extends Binder {
        public SocketClient getServerInstance() {
            return SocketClient.this;
        }
    }

    public void sendMessage(SocketResponse data) {
        String room = "nm_chat";
        switch (data.typeData) {
            case TYPE_NOTIFY: {
                room = "nm_notification";       // Client dont use this
                break;
            }
            case TYPE_BOOKING: {
                room = "nm_booking";

                break;
            }
            case TYPE_MESSAGE: {
                room = "nm_chat";
                break;
            }
        }
        try {
            socket.emit(room, new Gson().toJson(data));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        stopLocationUpdate();
        super.onDestroy();
        LogUtils.e("OnDestroy");
        if (socket != null)
            this.socket.disconnect();

        Intent broadcastIntent = new Intent("chayngamT.restart");
        sendBroadcast(broadcastIntent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.e("OnCreate");

        mBinder = new LocalBinder();

        initSocket();
    }

    public void initSocket() {
        try {
            TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }};
            //   SSLContext sc = SSLContext.getInstance("TLS");
            //   sc.init(null, trustManagers, null);
            //   IO.setDefaultSSLContext(sc);
            //   IO.Options os = new IO.Options();
            //  os.secure = true;
            //   os.reconnection = true;
            //  os.reconnectionDelay = 2000;
            //  os.reconnectionAttempts = 5;
            // os.sslContext = sc;
            socket = IO.socket("http://103.237.147.86:3001/");
            socket.connect();
            LogUtils.e("Set socket IO", "Socket IO setting");
        } catch (Exception e) {
            LogUtils.e("Socket Problem", "Try cath", e);
        }

        this.socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                LogUtils.e("Connected");
                final Intent intent = new Intent();
                intent.setAction(SocketConstants.EVENT_CONNECTION);
                intent.putExtra(SocketConstants.KEY_STATUS_CONNECTION, getString(R.string.connected));
                SocketClient.this.sendBroadcast(intent);


                socket.off("nm_send_notification");
                socket.on("nm_send_notification", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        LogUtils.e("nm_send_notification ------> " + args[0].toString());
                        SocketResponse data = new Gson().fromJson(args[0].toString(), SocketResponse.class);
                        Profile profile = new Gson().fromJson(mSetting.getString(AppConstant.KEY_PROFILE), Profile.class);
                        if (profile == null) return;
                        if ( data.getTypeNotifyFromServer() !=2) {
                            notification = CommomUtils.createNotificationWithMsg(getApplicationContext(), "Thông báo", "Bạn có thông báo mới", args[0].toString());
                            showNotificationInStack(0);
                        }
//                        SocketResponse response = new Gson().fromJson(args[0].toString(), SocketResponse.class);
////                        if (response.getU_id().equals(MainApplication.getInstance().getmUser().getU_id())) {
//                        notification = CommomUtils.createNotificationWithMsg(getApplicationContext(), response.getTitle(), response.getContent(), AppConstant.NOTI);
//                        showNotificationInStack();
//                        Intent intent1 = new Intent();
//                        intent1.setAction(SocketConstants.SOCKET_CHAT);
////                            mSetting.put(Constants.FINISH, response.getD_id());
//                        intent1.putExtra(AppConstant.NOTI_, args[0].toString());
//                        SocketClient.this.sendBroadcast(intent1);
//                        }
                    }
                });
                socket.off("nm_send_booking");
                socket.on("nm_send_booking", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        LogUtils.e("nm_send_booking ------> " + args[0].toString());
                        SocketResponse data = new Gson().fromJson(args[0].toString(), SocketResponse.class);
                        if (data.getTypeData() != TYPE_BOOKING) return;
                        Profile profile = new Gson().fromJson(mSetting.getString(AppConstant.KEY_PROFILE), Profile.class);
                        if (profile == null || data.getStateOrder() == STATE_NEWORDER ||
                                !data.getUser().getId().equals(profile.getId())) return;
                        String msg = "";
                        switch (data.getStateOrder()) {
                            case STATE_NEWORDER: {
                                break;
                            }
                            case STATE_RECEIVED: {
                                msg = "Lịch đặt làm Nails của bạn đã được tiếp nhận.";
                                break;
                            }
                            case STATE_INPROGRESS: {
                                msg = "Lịch đặt làm Nails của bạn đang được thực hiện.";
                                break;
                            }
                            case STATE_FINISHED: {
                                msg = "Lịch đặt làm Nails của bạn đã hoàn thành.";
                                break;
                            }
                            case STATE_CANCELED: {
                                msg = "Lịch đặt làm Nails của bạn đã bị hủy.";
                                break;
                            }
                        }
                        notification = CommomUtils.createNotificationWithMsg(getApplicationContext(), "Thông báo", msg, args[0].toString());
                        showNotificationInStack(1);
//                        showNotificationInStack();

                    }
                });
                socket.off("nm_send_chat");
                socket.on("nm_send_chat", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        LogUtils.e("nm_send_chat -------> " + args[0].toString());
                        SocketResponse data = new Gson().fromJson(args[0].toString(), SocketResponse.class);
                        if (data.getTypeData() != TYPE_MESSAGE) return;
                        Profile profile = new Gson().fromJson(mSetting.getString(AppConstant.KEY_PROFILE), Profile.class);
                        if (profile == null) return;
                        if (data.getUser() == null || !data.getUser().getId().equals(profile.getId()))
                            return;
                        notification = CommomUtils.createNotificationWithMsg(getApplicationContext(), "Tin nhắn", "Bạn có một tin nhắn mới.", args[0].toString());
                        showNotificationInStack(2);

                        Intent intent1 = new Intent();
                        intent1.setAction(SocketConstants.SOCKET_CHAT);
                        intent1.putExtra(AppConstant.MSG, args[0].toString());
                        SocketClient.this.sendBroadcast(intent1);
                    //    LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent1);
                        LogUtils.e("nm_send_chat -------> sent to UI" );

//                        LogUtils.e(response.getU_id1());
//                        LogUtils.e(AppConstant.ID_CHAT);
//                        if (response.getU_id1().equals(AppController.getInstance().getmProfileUser().getId())) {
//                            try {
//                                if (!AppConstant.ID_CHAT.equals(response.getU_id())) {
//                                    notification = CommomUtils.createNotificationWithMsg(getApplicationContext(), "Thông báo", "Bạn có tin nhắn mới từ " + response.getNameDriver(), AppConstant.CHAT_INT);
//                                    showNotificationInStack();
//                                }
//                            } catch (Exception e) {
//                                notification = CommomUtils.createNotificationWithMsg(getApplicationContext(), "Thông báo", "Bạn có tin nhắn mới từ " + response.getNameDriver(), AppConstant.CHAT_INT);
//                                showNotificationInStack();
//                            }
//
//
//                        }


                    }
                });

            }
        });

        this.socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
            public void call(Object... args) {
                Log.i("desc", "error desc");
                Intent intent = new Intent();
                intent.setAction(SocketConstants.EVENT_CONNECTION);
                intent.putExtra(SocketConstants.KEY_STATUS_CONNECTION, getString(R.string.disconnect));
                SocketClient.this.sendBroadcast(intent);
            }
        });
        this.socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
            public void call(Object... args) {
                LogUtils.e("Error", args[0].toString());
                Intent intent = new Intent();
                intent.setAction(SocketConstants.EVENT_CONNECTION);
                intent.putExtra(SocketConstants.KEY_STATUS_CONNECTION, getString(R.string.disconnect));
                // SocketClient.this.sendBroadcast(intent);
            }
        });
        this.socket.on(Packet.ERROR, new Emitter.Listener() {
            public void call(Object... args) {
                LogUtils.e("Error", "Event Error");
                LogUtils.e(args[0].toString());
            }
        });
        this.socket.on(Socket.EVENT_RECONNECTING, new Emitter.Listener() {
            public void call(Object... args) {
                Intent intent = new Intent();
                intent.setAction(SocketConstants.EVENT_CONNECTION);
                intent.putExtra(SocketConstants.KEY_STATUS_CONNECTION, SocketClient.this.getString(R.string.reconnect));
                SocketClient.this.sendBroadcast(intent);
                LogUtils.e(" reconecting ");
            }
        });
    }

    public void showNotificationInStack(int id) {
        boolean isOn = AppController.getInstance().getmSetting().getBoolean(AppConstant.NOTI_ON,true);

        if (isOn && notification != null ) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(id, notification);
            notification = null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;

    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //create a intent that you want to start again..
        Intent intent = new Intent(getApplicationContext(), SocketClient.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5000, pendingIntent);
        super.onTaskRemoved(rootIntent);
    }

    private GoogleApiClient _gac;


    private void stopLocationUpdate() {
        if (this._gac != null) {
            this._gac.disconnect();
        }
    }
}

