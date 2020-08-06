package com.wadektech.chips.data.remote.source;

import com.wadektech.chips.data.local.models.TransactionDetails;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;


public interface TransactionDetailsService {

    @GET("transactions")
    Observable<List<TransactionDetails>> getTransactionDetailsAsync();
}
