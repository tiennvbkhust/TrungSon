package com.skynetsoftware.trungson.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProductRequest implements Parcelable {

    @Expose
    @SerializedName("id")
    private String id;
    @Expose
    @SerializedName("numberOfProduct")
    private int numberOfProduct;

    public ProductRequest(String id, int numberOfProduct) {
        this.id = id;
        this.numberOfProduct = numberOfProduct;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumberOfProduct() {
        return numberOfProduct;
    }

    public void setNumberOfProduct(int numberOfProduct) {
        this.numberOfProduct = numberOfProduct;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.numberOfProduct);
    }

    public ProductRequest() {
    }

    protected ProductRequest(Parcel in) {
        this.id = in.readString();
        this.numberOfProduct = in.readInt();
    }

    public static final Creator<ProductRequest> CREATOR = new Creator<ProductRequest>() {
        @Override
        public ProductRequest createFromParcel(Parcel source) {
            return new ProductRequest(source);
        }

        @Override
        public ProductRequest[] newArray(int size) {
            return new ProductRequest[size];
        }
    };
}
