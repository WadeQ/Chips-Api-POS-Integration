package com.wadektech.chips.data.remote;

import android.os.Build;
import androidx.annotation.RequiresApi;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@RequiresApi(api = Build.VERSION_CODES.O)
public class TokenRequestClient {
    private static final String KEY = "Basic MjIxOGI0YTQtMTkwMi00ZmY4LWI4YjctMDc5YT" +
            "c3M2VkOGU3OmJhOTlkMzhjZGRjYmZmYTFiODliNDE3MWZhOT BhYWZi" ;//TO-DO add key when @thomas provides
    private static final String BASE_URL = "https://tar.tlsag.net/";
    private Retrofit retrofit;
    private static TokenRequestClient INSTANCE;

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request tokenRequest = chain.request();
                Request.Builder builder = tokenRequest.newBuilder()
                        .addHeader("Authorization", KEY);
                Request request = builder.build();
                return chain.proceed(request);
            }).build();

    private TokenRequestClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public static synchronized TokenRequestClient getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new TokenRequestClient();
        }
        return INSTANCE;
    }

    public RequestTokenApi getRequestToken(){
        return retrofit.create(RequestTokenApi.class);
    }
}
