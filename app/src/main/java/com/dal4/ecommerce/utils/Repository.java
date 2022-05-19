package com.dal4.ecommerce.utils;

import android.content.Context;

import androidx.room.Room;

import com.dal4.ecommerce.data_base.Local.CartList_DataBase;
import com.dal4.ecommerce.models.Products_Model;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    public static final String CartListDataBaseBuilderName = "CartList";

    public static CartList_DataBase cartList_dataBase;
    public List<Products_Model> List = new ArrayList<>();

    public static CartList_DataBase getCartList_dataBase(Context context) {
        if (cartList_dataBase == null) {
            cartList_dataBase = Room.databaseBuilder(context, CartList_DataBase.class, CartListDataBaseBuilderName).build();
        }
        return cartList_dataBase;
    }

}
