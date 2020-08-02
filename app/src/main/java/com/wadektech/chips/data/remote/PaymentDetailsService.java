package com.wadektech.chips.data.remote;

import com.wadektech.chips.data.local.models.PaymentDetails;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface PaymentDetailsService {
    @GET("payments/requests")
    Observable<List<PaymentDetails>> getPaymentDetails();
}
