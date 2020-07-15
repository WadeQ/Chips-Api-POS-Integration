package com.wadektech.chips.data.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenRequestClient {
    private static final String BASE_URL = "https://tar.tlsag.net/";
    private Retrofit retrofit;
    private static TokenRequestClient INSTANCE;

    private TokenRequestClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
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
