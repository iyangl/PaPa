package com.dasheng.papa.api;

import com.dasheng.papa.util.Constant;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class ServiceGenerator {


    private static OkHttpClient.Builder builder = new OkHttpClient.Builder();


    private static Retrofit retrofit = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(setLoggingClient())
            .baseUrl(Constant.Api.BASE_API_URL)
            .build();

    private static OkHttpClient setLoggingClient() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        Timber.d("Retrofit ---> %s", message);
                    }
                });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(httpLoggingInterceptor);
        return builder.build();
    }

    public static <T> T createService(Class<T> clazz) {
        return retrofit.create(clazz);
    }
}
