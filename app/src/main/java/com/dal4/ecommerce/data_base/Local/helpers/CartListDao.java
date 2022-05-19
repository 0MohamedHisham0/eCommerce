package com.dal4.ecommerce.data_base.Local.helpers;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.dal4.ecommerce.models.Products_Model_Detail;

import java.util.List;

@Dao
public interface CartListDao {
    @Query("SELECT * FROM ProductCartTable ")
    List<Products_Model_Detail> GetCartProduct();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertProductInCartList(Products_Model_Detail products_model_detail);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void InsertAllProducts(List<Products_Model_Detail> list);

    //    @Query("SELECT * FROM ProductCartTable WHERE uid IN (:uid))")
//    List<Products_Model> getAllById(int[] uid);

    @Delete
    void DeleteProductFromCartList(Products_Model_Detail products_model);

    @Update
    void UpdateProductInCartList(Products_Model_Detail products_model);



}
