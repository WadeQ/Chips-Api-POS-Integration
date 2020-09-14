package com.wadektech.chips.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wadektech.chips.data.RemoteRepository;
import com.wadektech.chips.data.local.models.PaymentDetails;
import com.wadektech.chips.data.local.models.TransactionDetails;
import com.wadektech.chips.utils.FirebaseQueryLiveData;
import com.wadektech.chips.utils.FirebaseRealtimeDatabaseQueryLiveData;

public class ChipsViewModel extends ViewModel {
    public RemoteRepository remoteRepository ;
    public FirebaseRealtimeDatabaseQueryLiveData<PaymentDetails> paymentDetailsList ;
    public FirebaseRealtimeDatabaseQueryLiveData<TransactionDetails> transactionDetailsList ;
    private static final DatabaseReference rootRef =
        FirebaseDatabase.getInstance().getReference("transactions").child("payments");
    private final FirebaseQueryLiveData _paymentsListener = new FirebaseQueryLiveData(rootRef);

    public ChipsViewModel() {
        remoteRepository = RemoteRepository.getInstance();
        paymentDetailsList = remoteRepository.getAllPaymentDetailsFromDB();
        remoteRepository.fetchPaymentDetails();
    }

    public FirebaseRealtimeDatabaseQueryLiveData<PaymentDetails> getPaymentDetails() {
        return paymentDetailsList;
    }

    public FirebaseRealtimeDatabaseQueryLiveData<TransactionDetails> getTransactionDetails() {
        return transactionDetailsList;
    }

    public LiveData<DataSnapshot> getPaymentsRequestDataSnapshotLiveData() {
        return _paymentsListener;
    }

}









