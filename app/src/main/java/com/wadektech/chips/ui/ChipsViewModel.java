package com.wadektech.chips.ui;

import androidx.lifecycle.ViewModel;
import com.wadektech.chips.data.RemoteRepository;
import com.wadektech.chips.data.local.models.PaymentDetails;
import com.wadektech.chips.data.local.models.TransactionDetails;
import com.wadektech.chips.utils.FirebaseRealtimeDatabaseQueryLiveData;


public class ChipsViewModel extends ViewModel {
    public RemoteRepository remoteRepository ;
   // private final LiveData<PagedList<PaymentDetails>> paymentDetailsList;
    public FirebaseRealtimeDatabaseQueryLiveData<PaymentDetails> paymentDetailsList ;
    public FirebaseRealtimeDatabaseQueryLiveData<TransactionDetails> transactionDetailsList ;
    //private final LiveData<PagedList<TransactionDetails>> transactionDetailsList;

    public ChipsViewModel() {
        remoteRepository = RemoteRepository.getInstance();
        paymentDetailsList = remoteRepository.getAllPaymentDetailsFromDB();
        transactionDetailsList = remoteRepository.getAllTransactionDetailsFromDB();
        remoteRepository.fetchTransactionDetailsFromRemote();
        remoteRepository.fetchPaymentDetails();
        remoteRepository.getPaymentCompletionStatus();
    }

    public FirebaseRealtimeDatabaseQueryLiveData<PaymentDetails> getPaymentDetails() {
        return paymentDetailsList;
    }

    public FirebaseRealtimeDatabaseQueryLiveData<TransactionDetails> getTransactionDetails() {
        return transactionDetailsList;
    }

    public void getPaymentDetailsByTokenId(String tokenId) {
        //remoteRepository.searchPaymentDetailsByTokenIdFromLocal(tokenId);
    }

    public void getTransactionDetailsBySiteRefInfo(String siteRef) {
        remoteRepository.searchTransactionDetailsBySiteRefInfo(siteRef);
    }

    public void getPaymentDetailsByTokenIdLiveFromRemote(String token){
        remoteRepository.getPaymentDetailsByTokenIdFromRemote(token);
    }

}









