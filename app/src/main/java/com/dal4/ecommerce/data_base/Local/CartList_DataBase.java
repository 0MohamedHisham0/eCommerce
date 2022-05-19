package com.dal4.ecommerce.data_base.Local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.dal4.ecommerce.data_base.Local.helpers.CartListDao;
import com.dal4.ecommerce.models.Products_Model_Detail;

@Database(entities = {Products_Model_Detail.class}, version = 1)
public abstract class CartList_DataBase extends RoomDatabase {

    public abstract CartListDao studentsDao();

}
