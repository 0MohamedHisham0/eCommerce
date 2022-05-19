package com.dal4.ecommerce.data_base.Local.DaoFun;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.dal4.ecommerce.models.Products_Model_Detail;
import com.dal4.ecommerce.utils.Constants;
import com.dal4.ecommerce.utils.Repository;

import java.util.List;

public class AddProduct_Cart extends AsyncTask<Products_Model_Detail, Void, Void> {
    @NonNull
    private List<Products_Model_Detail> list_Cart;
    private Context context;

    public AddProduct_Cart(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Products_Model_Detail... products_models) {

        Repository.getCartList_dataBase(context).studentsDao().InsertProductInCartList(products_models[0]);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Constants.saveCartSize(context, Constants.getCartSize(context) + 1);
    }
}

