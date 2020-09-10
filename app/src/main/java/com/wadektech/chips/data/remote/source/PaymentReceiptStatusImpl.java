package com.wadektech.chips.data.remote.source;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wadektech.chips.utils.Constants;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentReceiptStatusImpl {
    private Retrofit retrofit;
    private static PaymentReceiptStatusImpl INSTANCE;

    private PaymentReceiptStatusImpl(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static synchronized PaymentReceiptStatusImpl getINSTANCE(){
        if (INSTANCE == null){
            INSTANCE = new PaymentReceiptStatusImpl();
        }
        return INSTANCE;
    }

    public PaymentReceiptStatus getPaymentReceipt(){
        return retrofit.create(PaymentReceiptStatus.class);
    }
}








