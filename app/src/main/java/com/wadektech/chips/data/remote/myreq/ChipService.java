package com.wadektech.chips.data.remote.myreq;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChipService {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("masterpass/tokens")
    Call<TokenResDto> createPayment(
            @Header("Authorization") String authKey,
            @Body TokenReqDto tokenReqDto
    );
}
