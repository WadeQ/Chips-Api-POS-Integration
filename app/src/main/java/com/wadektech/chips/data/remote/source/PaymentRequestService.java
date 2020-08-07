package com.wadektech.chips.data.remote.source;

import com.wadektech.chips.data.remote.models.TokenReqDto;
import com.wadektech.chips.data.remote.models.TokenResDto;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * This endpoint is called when the SmartPOS device submits a request for the creation of a payment request.
 * Since the created token code is a Masterpass token, it is unique for a predefined period as specified on the request.
 * This endpoint will return, at most, one resulting API structure.
 */
public interface PaymentRequestService {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("payments/requests")
    Observable<TokenResDto> createPayment(
            @Header("Authorization") String authKey,
            @Body TokenReqDto tokenReqDto
    );
}
