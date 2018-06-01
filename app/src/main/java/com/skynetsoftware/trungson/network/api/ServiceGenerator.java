package com.skynetsoftware.trungson.network.api;

import com.blankj.utilcode.util.SPUtils;
import com.skynetsoftware.trungson.utils.AppConstant;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PhamThi on 3/8/2017.
 */

public class ServiceGenerator {
    private static OkHttpClient.Builder httpClient;

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(TrungSonAPI.API_ROOT)
            .addConverterFactory(GsonConverterFactory.create());

    // service have not token
    public static <S> S createService(Class<S> serviceClass) {
        httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(3, TimeUnit.MINUTES);
        httpClient.connectTimeout(3, TimeUnit.MINUTES);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceToken( Class<S> serviceClass) {
        httpClient = new OkHttpClient.Builder();
        httpClient.readTimeout(3, TimeUnit.MINUTES);
        httpClient.connectTimeout(3, TimeUnit.MINUTES);
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "" +
                               new SPUtils(AppConstant.KEY_SETTING).getString(AppConstant.KEY_TOKEN,""))
                        .method(original.method(), original.body());
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        OkHttpClient client = httpClient.build();
        Retrofit retrofit = builder.client(client).build();
        return retrofit.create(serviceClass);
    }



}
