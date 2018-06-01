package com.skynetsoftware.trungson.network.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by thaopt on 11/24/17.
 */

public class ApiResponse<T> {
    @SerializedName("data")
    T data;
    @SerializedName("errorId")
    int code;
    @SerializedName("message")
    String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
