package com.wadektech.chips.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.wadektech.chips.data.RemoteRepository;
import com.wadektech.chips.data.local.models.PaymentDetails;

public class ChipsViewModel extends ViewModel {
    private final LiveData<PagedList<PaymentDetails>> paymentDetailsList;
    public static RemoteRepository remoteRepository;

    public ChipsViewModel() {
        remoteRepository = RemoteRepository.getInstance();
        paymentDetailsList = RemoteRepository.getPaymentDetailsFromRemote();
    }

    public LiveData<PagedList<PaymentDetails>> getPaymentDetails() {
        return paymentDetailsList;
    }
}
