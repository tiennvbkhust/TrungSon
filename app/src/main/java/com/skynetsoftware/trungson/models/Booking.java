package com.skynetsoftware.trungson.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Booking {

    @Expose
    @SerializedName("active")
    private String active;
    @Expose
    @SerializedName("method_payment")
    private int method_payment;
    @Expose
    @SerializedName("phone")
    private String phone;
    @Expose
    @SerializedName("city")
    private String city;
    @Expose
    @SerializedName("address")
    private String address;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("promotion")
    private String promotion;
    @Expose
    @SerializedName("price")
    private double price;
    @Expose
    @SerializedName("note")
    private String note;
    @Expose
    @SerializedName("quantity")
    private int quantity;
    @Expose
    @SerializedName("time_booking")
    private String time_booking;
    @Expose
    @SerializedName("date_booking")
    private String date_booking;
    @Expose
    @SerializedName("product_info")
    private String product_info;
    @Expose
    @SerializedName("p_id")
    private String p_id;
    @Expose
    @SerializedName("u_id")
    private String u_id;
    @Expose
    @SerializedName("id")
    private String id;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getMethod_payment() {
        return method_payment;
    }

    public void setMethod_payment(int method_payment) {
        this.method_payment = method_payment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTime_booking() {
        return time_booking;
    }

    public void setTime_booking(String time_booking) {
        this.time_booking = time_booking;
    }

    public String getDate_booking() {
        return date_booking;
    }

    public void setDate_booking(String date_booking) {
        this.date_booking = date_booking;
    }

    public String getProduct_info() {
        return product_info;
    }

    public void setProduct_info(String product_info) {
        this.product_info = product_info;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
