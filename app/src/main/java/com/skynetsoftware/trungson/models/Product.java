package com.skynetsoftware.trungson.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product implements Parcelable {
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
    @Expose
    @SerializedName("is_favourite")
    private int is_favourite;
    @Expose
    @SerializedName("numberOfProduct")
    private int numberOfProduct;

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getUser_like() {
        return user_like;
    }

    public int getIs_favourite() {
        return is_favourite;
    }

    public void setIs_favourite(int is_favourite) {
        this.is_favourite = is_favourite;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.active);
        dest.writeString(this.user_like);
        dest.writeString(this.date);
        dest.writeString(this.content);
        dest.writeString(this.effect);
        dest.writeString(this.used);
        dest.writeInt(this.quantity);
        dest.writeString(this.feature);
        dest.writeInt(this.status);
        dest.writeDouble(this.price);
        dest.writeString(this.img);
        dest.writeString(this.name);
        dest.writeString(this.brand);
        dest.writeString(this.c_id);
        dest.writeString(this.code);
        dest.writeString(this.id);
        dest.writeInt(this.is_favourite);
        dest.writeInt(this.numberOfProduct);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.active = in.readInt();
        this.user_like = in.readString();
        this.date = in.readString();
        this.content = in.readString();
        this.effect = in.readString();
        this.used = in.readString();
        this.quantity = in.readInt();
        this.feature = in.readString();
        this.status = in.readInt();
        this.price = in.readDouble();
        this.img = in.readString();
        this.name = in.readString();
        this.brand = in.readString();
        this.c_id = in.readString();
        this.code = in.readString();
        this.id = in.readString();
        this.is_favourite = in.readInt();
        this.numberOfProduct = in.readInt();
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
