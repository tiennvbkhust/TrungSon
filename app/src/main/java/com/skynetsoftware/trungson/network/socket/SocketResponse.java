package com.skynetsoftware.trungson.network.socket;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import com.skynetsoftware.trungson.models.Message;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.models.Shop;

/**
 * Created by DuongKK on 6/20/2017.
 */

public class SocketResponse implements Parcelable {
    @SerializedName("idOrder")
    String idOrder;
    @SerializedName("idUser")
    String idUser;
    @SerializedName("stateOrder")
    int stateOrder;
    @SerializedName("shop")
    Shop shop;
    @SerializedName("user")
    Profile user;
    @SerializedName("typeData")
    int typeData;
    @SerializedName("content")
    String contentMessage;
    @SerializedName("id")
    String idReceiverFromServer;
     @SerializedName("type")
    int typeNotifyFromServer;

    @SerializedName("message")
    Message message;
    public int getTypeData() {
        return typeData;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdReceiverFromServer() {
        return idReceiverFromServer;
    }

    public void setIdReceiverFromServer(String idReceiverFromServer) {
        this.idReceiverFromServer = idReceiverFromServer;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public int getTypeNotifyFromServer() {
        return typeNotifyFromServer;
    }

    public void setTypeNotifyFromServer(int typeNotifyFromServer) {
        this.typeNotifyFromServer = typeNotifyFromServer;
    }

    public void setTypeData(int typeData) {
        this.typeData = typeData;
    }

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public int getStateOrder() {
        return stateOrder;
    }

    public void setStateOrder(int stateOrder) {
        this.stateOrder = stateOrder;
    }


    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public Profile getUser() {
        return user;
    }

    public void setUser(Profile user) {
        this.user = user;
    }

    public SocketResponse() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.idOrder);
        dest.writeString(this.idUser);
        dest.writeInt(this.stateOrder);
        dest.writeParcelable(this.shop, flags);
        dest.writeParcelable(this.user, flags);
        dest.writeInt(this.typeData);
        dest.writeString(this.contentMessage);
        dest.writeString(this.idReceiverFromServer);
        dest.writeInt(this.typeNotifyFromServer);
        dest.writeParcelable(this.message, flags);
    }

    protected SocketResponse(Parcel in) {
        this.idOrder = in.readString();
        this.idUser = in.readString();
        this.stateOrder = in.readInt();
        this.shop = in.readParcelable(Shop.class.getClassLoader());
        this.user = in.readParcelable(Profile.class.getClassLoader());
        this.typeData = in.readInt();
        this.contentMessage = in.readString();
        this.idReceiverFromServer = in.readString();
        this.typeNotifyFromServer = in.readInt();
        this.message = in.readParcelable(Message.class.getClassLoader());
    }

    public static final Creator<SocketResponse> CREATOR = new Creator<SocketResponse>() {
        @Override
        public SocketResponse createFromParcel(Parcel source) {
            return new SocketResponse(source);
        }

        @Override
        public SocketResponse[] newArray(int size) {
            return new SocketResponse[size];
        }
    };
}
