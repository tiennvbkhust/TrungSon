package com.skynetsoftware.trungson.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category implements Parcelable {

    boolean isChecked;
    @Expose
    @SerializedName("active")
    private int active;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("parent_id")
    private String parent_id;
    @Expose
    @SerializedName("id")
    private String id;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Category() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeInt(this.active);
        dest.writeString(this.title);
        dest.writeString(this.name);
        dest.writeString(this.parent_id);
        dest.writeString(this.id);
    }

    protected Category(Parcel in) {
        this.isChecked = in.readByte() != 0;
        this.active = in.readInt();
        this.title = in.readString();
        this.name = in.readString();
        this.parent_id = in.readString();
        this.id = in.readString();
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
