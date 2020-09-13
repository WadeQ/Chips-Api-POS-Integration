package com.wadektech.chips.data.remote.source;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wadektech.chips.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionDetailsServiceImpl {
    private Retrofit retrofit;
    private static TransactionDetailsServiceImpl INSTANCE;

    private TransactionDetailsServiceImpl(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized TransactionDetailsServiceImpl getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new TransactionDetailsServiceImpl();
        }
        return INSTANCE;
    }

    public TransactionDetailsService getFilteredTransactionRequestDetails(){
        return retrofit.create(TransactionDetailsService.class);
    }
}
