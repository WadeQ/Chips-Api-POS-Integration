package com.wadektech.chips.data.remote.source;

import com.wadektech.chips.data.local.models.PaymentDetails;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * This endpoint is called when an external system needs to retrieve the details of previously submitted payment
 * requests. Various parameters are available to filter the list, and, depending on the criteria, zero or more
 * results will be returned.
 */
public interface PaymentDetailsService {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("payments/requests")
    Observable<List<PaymentDetails>> getPaymentDetailsByCriteria(
            @Header("Authorization") String authKey
    );


    /**
     * @param tokenId
     * @return
     * This endpoint is called when an external system needs to retrieve the details of a previously submitted
     * payment request by providing the tokenId. This endpoint will return, at most, one resulting API structure.
     */
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("payments/requests/{tokenId}")
    Observable<PaymentDetails> getPaymentDetailsByTokenId(
            @Header("Authorization") String authKey,
            @Path("tokenId") String tokenId
    );
}
