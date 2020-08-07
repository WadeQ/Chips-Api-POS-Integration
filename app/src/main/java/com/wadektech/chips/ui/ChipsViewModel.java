package com.wadektech.chips.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;
import com.wadektech.chips.data.RemoteRepository;
import com.wadektech.chips.data.local.models.PaymentDetails;
import com.wadektech.chips.data.local.models.TransactionDetails;


public class ChipsViewModel extends ViewModel {
    public RemoteRepository remoteRepository ;
    private final LiveData<PagedList<PaymentDetails>> paymentDetailsList;
    private final LiveData<PagedList<TransactionDetails>> transactionDetailsList;

    public ChipsViewModel() {
        remoteRepository = RemoteRepository.getInstance();
        paymentDetailsList = remoteRepository.getPaymentDetailsFromLocal();
        transactionDetailsList = remoteRepository.getTransactionDetailsFromLocal();
        remoteRepository.fetchTransactionDetailsFromRemote();
        remoteRepository.fetchPaymentDetails();
    }

    public LiveData<PagedList<PaymentDetails>> getPaymentDetails() {
        return paymentDetailsList;
    }

    public LiveData<PagedList<TransactionDetails>> getTransactionDetails() {
        return transactionDetailsList;
    }

    public void getPaymentDetailsByTokenId(String tokenId) {
        remoteRepository.searchPaymentDetailsByTokenIdFromLocal(tokenId);
    }

    public void getTransactionDetailsBySiteRefInfo(String siteRef) {
        remoteRepository.searchTransactionDetailsBySiteRefInfo(siteRef);
    }

}
