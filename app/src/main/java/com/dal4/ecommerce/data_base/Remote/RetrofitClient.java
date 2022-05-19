package com.dal4.ecommerce.data_base.Remote;

import com.dal4.ecommerce.models.Products_Model;
import com.dal4.ecommerce.models.Products_Model_Detail;
import com.dal4.ecommerce.models.RateBodyModel;
import com.dal4.ecommerce.models.RateModel;
import com.dal4.ecommerce.models.UserModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final static String BASE_URL = "https://saifhanyecommerceapp.herokuapp.com/";
    private final RemoteDB_Dao remoteDB_dao;
    private static RetrofitClient retrofitClient;

    public RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        remoteDB_dao = retrofit.create(RemoteDB_Dao.class);
    }

    public static RetrofitClient getInstance() {
        if (retrofitClient == null)
            retrofitClient = new RetrofitClient();

        return retrofitClient;
    }

    public Call<UserModel> CreateNewUser(Map<Object, Object> map) {

        return remoteDB_dao.CreateNewUser(map);
    }

    public Call<UserModel> UserLogin(Map<Object, Object> map) {

        return remoteDB_dao.UserLogin(map,BASE_URL);
    }

    public Call<Products_Model> GetAll() {
        return remoteDB_dao.GetAllProducts();
    }

    public Call<Products_Model_Detail> GetProductById(String id) {
        return remoteDB_dao.GetProductById(id);
    }

    public Call<List<Products_Model_Detail>> GetTopProducts() {
        return remoteDB_dao.GetTopProducts();
    }

    public Call<Products_Model> GetProductByPage(String pageNumber) {
        return remoteDB_dao.GetProductsModelByPage(pageNumber);
    }

    public Call<Products_Model> GetProductByName(String keyword) {
        return remoteDB_dao.GetProductByName(keyword);
    }

    public Call<RateModel> RateProductById(String TOKEN, Map<Object, Object> map , String ProductID) {
        return remoteDB_dao.RateProductByID("Bearer " + TOKEN, ProductID, map, BASE_URL);
    }
    public Call<UserModel> GetUserProfile(String TOKEN) {
        return remoteDB_dao.GetUserProfile("Bearer " + TOKEN);
    }

}
