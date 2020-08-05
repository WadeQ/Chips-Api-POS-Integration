package com.wadektech.chips.data.remote.source;

import com.wadektech.chips.data.local.models.PaymentDetails;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PaymentDetailsService {
    @GET("payments/requests/{tokenId}")
    Observable<List<PaymentDetails>> getPaymentDetailsByTokenId(
            @Path("tokenId") String tokenId
    );

    @GET("payments/requests")
    Observable<List<PaymentDetails>> getPaymentDetailsByCriteria();
}
