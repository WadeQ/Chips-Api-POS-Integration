package com.wadektech.chips.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.wadektech.chips.data.RemoteRepository;
import com.wadektech.chips.data.local.models.PaymentDetails;
import java.util.List;

public class ChipsViewModel extends ViewModel {
    private final LiveData<List<PaymentDetails>> paymentDetailsList;
    public static RemoteRepository remoteRepository;

    public ChipsViewModel() {
        remoteRepository = RemoteRepository.getInstance();
        paymentDetailsList = RemoteRepository.getPaymentDetailsFromRemote();
    }

    public LiveData<List<PaymentDetails>> getPaymentDetails() {
        return paymentDetailsList;
    }
}
