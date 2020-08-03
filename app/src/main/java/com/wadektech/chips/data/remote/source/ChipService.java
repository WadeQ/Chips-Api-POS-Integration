package com.wadektech.chips.data.remote.source;

import com.wadektech.chips.data.remote.models.TokenReqDto;
import com.wadektech.chips.data.remote.models.TokenResDto;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ChipService {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @POST("payments/requests")
    Observable<TokenResDto> createPayment(
            @Header("Authorization") String authKey,
            @Body TokenReqDto tokenReqDto
    );
}
