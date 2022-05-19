package com.dal4.ecommerce.models;

import androidx.annotation.NonNull;
import androidx.annotation.TransitionRes;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.io.Serializable;
import java.util.List;

public class Products_Model implements Serializable {

    @NonNull
    List<Products_Model_Detail> products;
    private int page;
    private int pages;


    public Products_Model(@NonNull List<Products_Model_Detail> products, int page, int pages) {
        this.products = products;
        this.page = page;
        this.pages = pages;
    }

    @NonNull
    public List<Products_Model_Detail> getProducts() {
        return products;
    }

    public void setProducts(@NonNull List<Products_Model_Detail> products) {
        this.products = products;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}
