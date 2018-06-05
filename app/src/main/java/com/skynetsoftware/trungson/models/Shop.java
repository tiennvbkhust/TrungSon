package com.skynetsoftware.trungson.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Shop implements Parcelable {

    @Expose
    @SerializedName("active")
    private int active;
    @Expose
    @SerializedName("lng")
    private double lng;
    @Expose
    @SerializedName("lat")
    private double lat;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("phone")
    private String phone;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("code")
    private String code;
    @Expose
    @SerializedName("id")
    private String id;
   @Expose
    @SerializedName("avatar")
    private String avatar;
   @Expose
    @SerializedName("distance")
    private double distance;
   @Expose
    @SerializedName("star")
    private double star;
   @Expose
    @SerializedName("description")
    private String description;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getStar() {
        return star;
    }

    public void setStar(double star) {
        this.star = star;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Shop() {
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Creator<Shop> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.active);
        dest.writeDouble(this.lng);
        dest.writeDouble(this.lat);
        dest.writeString(this.address);
        dest.writeString(this.phone);
        dest.writeString(this.name);
        dest.writeString(this.code);
        dest.writeString(this.id);
        dest.writeString(this.avatar);
        dest.writeDouble(this.distance);
        dest.writeDouble(this.star);
        dest.writeString(this.description);
    }

    protected Shop(Parcel in) {
        this.active = in.readInt();
        this.lng = in.readDouble();
        this.lat = in.readDouble();
        this.address = in.readString();
        this.phone = in.readString();
        this.name = in.readString();
        this.code = in.readString();
        this.id = in.readString();
        this.avatar = in.readString();
        this.distance = in.readDouble();
        this.star = in.readDouble();
        this.description = in.readString();
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel source) {
            return new Shop(source);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };
}
