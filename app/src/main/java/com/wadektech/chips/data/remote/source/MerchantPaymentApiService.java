package com.wadektech.chips.data.remote.source;

import com.wadektech.chips.data.remote.models.PaymentNotificationReq;
import com.wadektech.chips.data.remote.models.PaymentNotificationRes;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MerchantPaymentApiService {
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("payments")
    Observable<PaymentNotificationRes> notifyPaymentCompletion(
            @Header("Authorization") String authKey,
            @Body PaymentNotificationReq paymentNotificationReq
    );
}
