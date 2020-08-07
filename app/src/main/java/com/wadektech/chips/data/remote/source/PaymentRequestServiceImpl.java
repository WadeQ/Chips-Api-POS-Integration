package com.wadektech.chips.data.remote.source;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wadektech.chips.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentRequestServiceImpl {
    private Retrofit retrofit;
    private static PaymentRequestServiceImpl INSTANCE;

    private PaymentRequestServiceImpl(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized PaymentRequestServiceImpl getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new PaymentRequestServiceImpl();
        }
        return INSTANCE;
    }

    public PaymentRequestService getChipService(){
        return retrofit.create(PaymentRequestService.class);
    }
}
