package com.wadektech.chips.data.remote.source;

import com.wadektech.chips.data.remote.models.PaymentRequestByTokenId;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestByTokenService {

    @GET("payments/requests/{tokenId}")
    Observable<PaymentRequestByTokenId> requestPaymentByTokenID(
            @Path("tokenId") String tokenId
    );
}
