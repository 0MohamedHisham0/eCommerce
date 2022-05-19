package com.dal4.ecommerce.data_base.Remote;

import com.dal4.ecommerce.models.Products_Model;
import com.dal4.ecommerce.models.Products_Model_Detail;
import com.dal4.ecommerce.models.RateBodyModel;
import com.dal4.ecommerce.models.RateModel;
import com.dal4.ecommerce.models.UserModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RemoteDB_Dao {
    @POST("api/users")
    Call<UserModel> CreateNewUser(@Body Map<Object, Object> map);

    @POST("api/users/login")
    Call<UserModel> UserLogin(
            @Body Map<Object, Object> map,
            @Query("URL") String URL);

    @GET("api/products")
    Call<Products_Model> GetAllProducts();

    @GET("api/products/{id}")
    Call<Products_Model_Detail> GetProductById(
            @Path("id") String id
    );

    @GET("api/products/top")
    Call<List<Products_Model_Detail>> GetTopProducts();

    @GET("api/products")
    Call<Products_Model> GetProductsModelByPage(
            @Query("pageNumber") String pageNumber);

    @GET("api/products")
    Call<Products_Model> GetProductByName(@Query("keyword") String keyword);

    @POST("api/products/{id}/reviews")
    Call<RateModel> RateProductByID(
            @Header("Authorization") String authHeader,
            @Path("id") String id,
            @Body Map<Object, Object> map,
            @Query("URL") String URL
    );

    @GET("api/users")
    Call<List<UserModel>> GetAllUsers(@Header("Authorization") String authHeader);

    @GET("api/users/profile")
    Call<UserModel> GetUserProfile(@Header("Authorization") String authHeader);
}
