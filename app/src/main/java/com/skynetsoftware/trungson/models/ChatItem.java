package com.skynetsoftware.trungson.models;

import com.google.gson.annotations.SerializedName;

public class ChatItem {
   @SerializedName("user")
    Profile use;
   @SerializedName("shop")
   Profile shop;
   @SerializedName("last_message")
   String lastMessage;
   @SerializedName("time")
   String time;
    @SerializedName("user_read")
    int userRead;
    @SerializedName("shop_read")
    int shopRead;

    public Profile getUse() {
        return use;
    }

    public int getUserRead() {
        return userRead;
    }

    public void setUserRead(int userRead) {
        this.userRead = userRead;
    }

    public int getShopRead() {
        return shopRead;
    }

    public void setShopRead(int shopRead) {
        this.shopRead = shopRead;
    }

    public void setUse(Profile use) {
        this.use = use;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Profile getShop() {
        return shop;
    }

    public void setShop(Profile shop) {
        this.shop = shop;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
