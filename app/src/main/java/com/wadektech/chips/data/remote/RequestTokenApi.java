package com.wadektech.chips.data.remote;

import com.wadektech.chips.data.ChipToken;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RequestTokenApi {

    @Headers("Content-Type:application/json")
    @POST("/gate/api/v1.0/masterpass/tokens")
    Call<ChipToken> createPaymentTokenRequest(
            @Header("Authorization") String authKey,
            @Body ChipToken chipToken
    );
}
