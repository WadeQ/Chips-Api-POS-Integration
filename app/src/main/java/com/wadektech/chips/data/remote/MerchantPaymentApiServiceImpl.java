package com.wadektech.chips.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wadektech.chips.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MerchantPaymentApiServiceImpl {
    private Retrofit retrofit;
    private static MerchantPaymentApiServiceImpl INSTANCE;

    private MerchantPaymentApiServiceImpl(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized MerchantPaymentApiServiceImpl getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new MerchantPaymentApiServiceImpl();
        }
        return INSTANCE;
    }

    public MerchantPaymentApiService getMerchantPaymentNotification(){
        return retrofit.create(MerchantPaymentApiService.class);
    }
}








