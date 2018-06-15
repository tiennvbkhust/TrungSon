package com.skynetsoftware.trungson.network.api;


import com.skynetsoftware.trungson.models.Booking;
import com.skynetsoftware.trungson.models.Category;
import com.skynetsoftware.trungson.models.ChatItem;
import com.skynetsoftware.trungson.models.Message;
import com.skynetsoftware.trungson.models.News;
import com.skynetsoftware.trungson.models.Notification;
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

    @GET("notification_detail.php")
    Call<ApiResponse<Notification>> getDetailNotification(@Query("id") String id, @Query("type") int type, @Query("user_id") String shID);

    @FormUrlEncoded
    @POST("register.php")
    Call<ApiResponse<Profile>> signUp(@Field("name") String username, @Field("email") String email, @Field("phone") String phone, @Field("password") String pass);

    @GET("verify_code.php")
    Call<ApiResponse<String>> sendCode(@Query("phone") String phone);

    @GET("get_info.php")
    Call<ApiResponse<Profile>> getProfile(@Query("id") String uid, @Query("type") int type);

    @GET("notification.php")
    Call<ApiResponse<List<Notification>>> getListNotification(@Query("id") String uid, @Query("type") int type);

    @GET("list_agency.php")
    Call<ApiResponse<List<Shop>>> getListShop();

    @FormUrlEncoded
    @POST("rating.php")
    Call<ApiResponse> writeReview(@Field("id") String idShop, @Field("star") double star);

    @FormUrlEncoded
    @POST("booking.php")
    Call<ApiResponse<String>> paidCart(@Field("u_id") String uId, @Field("p_id") String p_id,
                                       @Field("note") String note, @Field("promotion") String promotionCode, @Field("price") double price,
                                       @Field("name") String name, @Field("address") String address, @Field("city") String city, @Field("phone") String phone,
                                       @Field("method_payment") int typePayment);

    @GET("category.php")
    Call<ApiResponse<List<Category>>> getListCategoy();

    @GET("search.php")
    Call<ApiResponse<List<Product>>> getListProductWithFilter(@Query("c_id") String idCategory,
                                                              @Query("price_max") String priceMax,
                                                              @Query("price_min") String priceMin,
                                                              @Query("type_sort") int sort);

    @GET("history.php")
    Call<ApiResponse<List<Booking>>> getHistories(@Query("id") String id);
    @GET("news.php")
    Call<ApiResponse<List<News>>> getNews();

    @GET("list_favourite.php")
    Call<ApiResponse<List<Product>>> getListFavouritesShop(@Query("id") String id);

    @GET("list_product.php")
    Call<ApiResponse<List<Product>>> getListProduct();
    @FormUrlEncoded
    @POST("change_password.php")
    Call<ApiResponse> changePassword(@Field("new_pass") String newpass, @Field("old_pass") String oldpass, @Field("id") String id, @Field("type") int type);

    @GET("history_detail.php")
    Call<ApiResponse<Booking>> getDetailBooking(@Query("id") String id);

    @GET("product_detail.php")
    Call<ApiResponse<Product>> getDetailProduct(@Query("id") String idProduct, @Query("u_id") String idUser);

    @GET("map.php")
    Call<ApiResponse<List<Shop>>> getListShop(@Query("lat") double lat, @Query("lng") double lng);

    @FormUrlEncoded
    @POST("forget_password.php")
    Call<ApiResponse> forgotPss(@Field("email") String email, @Field("type") int type);

    @GET("get_user_chat.php")
    Call<ApiResponse<List<ChatItem>>> getListChatItem(@Query("id") String uid, @Query("type") int type);

    @FormUrlEncoded
    @POST("favourite.php")
    Call<ApiResponse> toggleFavourite(@Field("u_id") String id, @Field("p_id") String idProduct, @Field("type") int like);


    @Multipart
    @POST("update_profile.php")
    Call<ApiResponse> updateInfor(@PartMap() Map<String, okhttp3.RequestBody> partMap);

    @Multipart
    @POST("update_avatar.php")
    Call<ApiResponse<String>> uploadAvatar(@Part MultipartBody.Part image, @PartMap() Map<String, okhttp3.RequestBody> partMap);

    @FormUrlEncoded
    @POST("message.php")
    Call<ApiResponse<Message>> sendMessageTo(@Field("u_id") String idUser, @Field("staff_id") String idShop, @Field("time") String time, @Field("content") String content, @Field("type") int type);


    @GET("get_message.php")
    Call<ApiResponse<List<Message>>> getListMessageBetween(@Query("u_id") String uid, @Query("staff_id") String shID, @Query("type") int type);

}
