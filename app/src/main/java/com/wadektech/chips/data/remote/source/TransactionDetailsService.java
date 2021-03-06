package com.wadektech.chips.data.remote.source;

import com.wadektech.chips.data.local.models.TransactionDetails;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * This endpoint is called when a client/external system needs to retrieve the details of the transaction related
 * to a previously submitted request. The Various parameters are available to filter the list, and, depending on
 * the criteria, zero or more results will be returned.
 */
public interface TransactionDetailsService {

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("transactions")
    Observable<TransactionDetails> getTransactionDetailsBySiteRefAsync(
        @Header("Authorization") String authKey,
        @Query("siteRef") String siteRef
    );
}
