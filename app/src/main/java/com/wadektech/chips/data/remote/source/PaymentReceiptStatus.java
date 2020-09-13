package com.wadektech.chips.data.remote.source;

import com.wadektech.chips.data.remote.models.PaymentReceiptReq;
import com.wadektech.chips.data.remote.models.PaymentReceiptRes;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 *This endpoint is called when the SmartPOS device needs to notify the CHIPS® Payment Network platform of a
 * successful card payment. This will enable CHIPS® to allocate the received funds to the involved CHIPS® account
 */
public interface PaymentReceiptStatus {
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("payments")
    Observable<PaymentReceiptRes> notifyPaymentCompletionWithReceipt(
            @Header("Authorization") String authKey,
            @Body PaymentReceiptReq paymentReceiptReq
    );
}
