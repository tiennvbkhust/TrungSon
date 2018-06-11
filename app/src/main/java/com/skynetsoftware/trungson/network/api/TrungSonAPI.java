package com.skynetsoftware.trungson.network.api;


import com.skynetsoftware.trungson.models.Category;
import com.skynetsoftware.trungson.models.Product;
import com.skynetsoftware.trungson.models.Profile;
import com.skynetsoftware.trungson.models.Shop;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by thaopt on 9/6/17.
 */

public interface TrungSonAPI {
    public static String API_ROOT = "http://nguoimoigioi.net/trungsonpharma/api/";

    @GET("login.php")
    Call<ApiResponse<Profile>> login(@Query("username") String username, @Query("password") String password, @Query("type") int type);

    @GET("loginfb.php")
    Call<ApiResponse<Profile>> loginWithFB(@Query("name") String username, @Query("email") String email, @Query("fbid") String fbid, @Query("type") int type);

    @GET("logingg.php")
    Call<ApiResponse<Profile>> loginWithGG(@Query("name") String username, @Query("email") String email, @Query("ggid") String fbid);

    @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponse<Profile>> signUp(@Field("name") String username, @Field("email") String email, @Field("phone") String phone, @Field("password") String pass);

    @GET("verify_code.php")
    Call<ApiResponse<String>> sendCode(@Query("phone") String phone);

    @GET("get_info.php")
    Call<ApiResponse<Profile>> getProfile(@Query("id") String uid, @Query("type") int type);

    @GET("list_agency.php")
    Call<ApiResponse<List<Shop>>> getListShop();

    @GET("category.php")
    Call<ApiResponse<List<Category>>> getListCategoy();
    @GET("search.php")
    Call<ApiResponse<List<Product>>> getListProductWithFilter(@Query("c_id") String idCategory,
                                                              @Query("price_max") String priceMax,
                                                              @Query("price_min") String priceMin,
                                                              @Query("type_sort") int sort);

    @GET("list_product.php")
    Call<ApiResponse<List<Product>>> getListProduct();
    @GET("product_detail.php")
    Call<ApiResponse<Product>> getDetailProduct(@Query("id")String idProduct,@Query("u_id")String idUser);

    @GET("map.php")
    Call<ApiResponse<List<Shop>>> getListShop(@Query("lat") double lat, @Query("lng") double lng);

    @FormUrlEncoded
    @POST("forget_password.php")
    Call<ApiResponse> forgotPss(@Field("email") String email, @Field("type") int type);
    @FormUrlEncoded
    @POST("favourite.php")
    Call<ApiResponse> toggleFavourite(@Field("u_id") String id, @Field("p_id") String idProduct,@Field("type") int like);


}
