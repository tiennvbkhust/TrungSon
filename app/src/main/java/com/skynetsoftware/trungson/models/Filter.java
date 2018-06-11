package com.skynetsoftware.trungson.models;

public class Filter {
    // type sort : 1,Best seller ; 2:maxprice ; 3,minprice
    double minPrice;
    double maxPrice;
    int typeSort;
    String idCategory;

    public Filter() {
        minPrice =0;
        maxPrice = 10000000;
        typeSort = 1;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public int getTypeSort() {
        return typeSort;
    }

    public void setTypeSort(int typeSort) {
        this.typeSort = typeSort;
    }
}
