package com.matthewferry.ideoweather.servicegenerator;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static String apiKey = "c3ae299cd9fa2fa369c0839cc39e7b84";

    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    HttpUrl httpUrl = original.url();

                    HttpUrl newHttpUrl = httpUrl.newBuilder().addQueryParameter("apiKey", apiKey).build();

                    Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            })
            .build();
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}
