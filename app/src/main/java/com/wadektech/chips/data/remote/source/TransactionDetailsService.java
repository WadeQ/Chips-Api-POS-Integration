package com.wadektech.chips.data.remote.source;

import com.wadektech.chips.data.local.models.TransactionDetails;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * This endpoint is called when a client/external system needs to retrieve the details of the transaction related
 * to a previously submitted request. The Various parameters are available to filter the list, and, depending on
 * the criteria, zero or more results will be returned.
 */
public interface TransactionDetailsService {

    @GET("transactions")
    Observable<List<TransactionDetails>> getTransactionDetailsAsync();
}