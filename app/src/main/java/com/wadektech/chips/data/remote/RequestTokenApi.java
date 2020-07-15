package com.wadektech.chips.data.remote;

import com.wadektech.chips.data.ChipToken;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestTokenApi {

    @POST("/gate/api/v1.0/masterpass/tokens")
    Call<ChipToken> createPaymentTokenRequest(@Body ChipToken chipToken);
}
