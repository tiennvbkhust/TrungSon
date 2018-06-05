package com.skynetsoftware.trungson.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {
    @Expose
    @SerializedName("active")
    private int active;
    @Expose
    @SerializedName("user_like")
    private String user_like;
    @Expose
    @SerializedName("date")
    private String date;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("effect")
    private String effect;
    @Expose
    @SerializedName("used")
    private String used;
    @Expose
    @SerializedName("quantity")
    private int quantity;
    @Expose
    @SerializedName("feature")
    private String feature;
    @Expose
    @SerializedName("status")
    private int status;
    @Expose
    @SerializedName("price")
    private double price;
    @Expose
    @SerializedName("img")
    private String img;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("brand")
    private String brand;
    @Expose
    @SerializedName("c_id")
    private String c_id;
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("id")
    private String id;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getUser_like() {
        return user_like;
    }

    public void setUser_like(String user_like) {
        this.user_like = user_like;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
