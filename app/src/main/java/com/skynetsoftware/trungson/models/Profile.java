package com.skynetsoftware.trungson.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Profile implements Parcelable {

    @Expose
    @SerializedName("img")
    private List<String> img;
    @Expose
    @SerializedName("active")
    private int active;
    @SerializedName("score")
    private int score;
    @Expose
    @SerializedName("status_account")
    private int statusAccount;
    @Expose
    @SerializedName("type_account")
    private int typeAccount;

    @Expose
    @SerializedName("type")
    private int type;
    @Expose
    @SerializedName("message")
    private int hasNewMessage;
    @Expose
    @SerializedName("register_date")
    private String registerDate;
    @Expose
    @SerializedName("password")
    private String password;
    @Expose
    @SerializedName("introduce")
    private String introduce;
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
    @SerializedName("fbid")
    private String fbid;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("phone")
    private String phone;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("img3")
    private String img3;
    @Expose
    @SerializedName("img2")
    private String img2;
    @Expose
    @SerializedName("img1")
    private String img1;
    @Expose
    @SerializedName("avatar")
    private String avatar;
    @Expose
    @SerializedName("id")
    private String id;

    public List<String> getImg() {
        return img;
    }

    public void setImg(List<String> img) {
        this.img = img;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getType() {
        return type;
    }

    public int getHasNewMessage() {
        return hasNewMessage;
    }

    public void setHasNewMessage(int hasNewMessage) {
        this.hasNewMessage = hasNewMessage;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatusAccount() {
        return statusAccount;
    }

    public void setStatusAccount(int statusAccount) {
        this.statusAccount = statusAccount;
    }

    public int getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(int typeAccount) {
        this.typeAccount = typeAccount;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
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

    public String getFbid() {
        return fbid;
    }

    public void setFbid(String fbid) {
        this.fbid = fbid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Profile() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(this.img);
        dest.writeInt(this.active);
        dest.writeInt(this.score);
        dest.writeInt(this.statusAccount);
        dest.writeInt(this.typeAccount);
        dest.writeInt(this.type);
        dest.writeInt(this.hasNewMessage);
        dest.writeString(this.registerDate);
        dest.writeString(this.password);
        dest.writeString(this.introduce);
        dest.writeDouble(this.lng);
        dest.writeDouble(this.lat);
        dest.writeString(this.address);
        dest.writeString(this.fbid);
        dest.writeString(this.email);
        dest.writeString(this.phone);
        dest.writeString(this.name);
        dest.writeString(this.img3);
        dest.writeString(this.img2);
        dest.writeString(this.img1);
        dest.writeString(this.avatar);
        dest.writeString(this.id);
    }

    protected Profile(Parcel in) {
        this.img = in.createStringArrayList();
        this.active = in.readInt();
        this.score = in.readInt();
        this.statusAccount = in.readInt();
        this.typeAccount = in.readInt();
        this.type = in.readInt();
        this.hasNewMessage = in.readInt();
        this.registerDate = in.readString();
        this.password = in.readString();
        this.introduce = in.readString();
        this.lng = in.readDouble();
        this.lat = in.readDouble();
        this.address = in.readString();
        this.fbid = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
        this.name = in.readString();
        this.img3 = in.readString();
        this.img2 = in.readString();
        this.img1 = in.readString();
        this.avatar = in.readString();
        this.id = in.readString();
    }

    public static final Creator<Profile> CREATOR = new Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel source) {
            return new Profile(source);
        }

        @Override
        public Profile[] newArray(int size) {
            return new Profile[size];
        }
    };
}
