package com.wadektech.chips.data;

import android.annotation.SuppressLint;
import android.content.Context;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wadektech.chips.data.local.models.PaymentDetails;
import com.wadektech.chips.data.local.models.Payments;
import com.wadektech.chips.data.remote.models.MerchantPaymentCompletionReq;
import com.wadektech.chips.data.remote.models.MerchantPaymentCompletionRes;
import com.wadektech.chips.data.remote.source.PaymentDetailsServiceImpl;
import com.wadektech.chips.data.remote.source.PaymentReceiptStatusImpl;
import com.wadektech.chips.utils.FirebaseRealtimeDatabaseQueryLiveData;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class RemoteRepository {
    @SuppressLint("StaticFieldLeak")
    public static volatile RemoteRepository rInstance ;
    public static final Object Lock = new Object();
    public Context context;
    String key = " Basic YWE0MjkxZWItMjczOC00ZWQ2LTg3OTItZjc5MTkyMTNiNTExOjM0YzFiYTQ0LWFkNGYtNGNhMy1hMzhiLTRmYTcyNjIyZmFhNA==";

    public synchronized static RemoteRepository getInstance(){
        if (rInstance == null){
            synchronized (Lock){
                if (rInstance == null){
                    rInstance = new RemoteRepository();
                }
            }
        }
        return rInstance ;
    }

    /**
     * This function fetches all payment details from chips server asynchronously then caches it locally
     * to be accessed by user.
     */
    public void fetchPaymentDetails(){
        Observable<Payments> paymentDetailsObservable = PaymentDetailsServiceImpl
                .getINSTANCE()
                .getPaymentRequestDetails()
                .getPaymentDetailsByCriteria(key);
        paymentDetailsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<Payments>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Payments paymentDetailsList) {
                        Timber.d("onNext() payments: %s", paymentDetailsList.getTotalElements());
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference paymentsRef =  rootRef.child("PaymentsDetails");
                        paymentsRef.setValue(paymentDetailsList);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("Response error status for payment details is %s", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * @return LiveData PagedList of payment details from Room database.
     */
    /*
    public LiveData<PagedList<PaymentDetails>> getPaymentDetailsFromLocal() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()
                .setPageSize(30)
                .setPrefetchDistance(5)
                .build());
        return new LivePagedListBuilder<>(
                ChipsRoomDatabase
                        .getInstance(App.getAppContext())
                        .paymentDetailsDao()
                        .getAllPaymentDetails(),
                        pagedListConfig)
                .build();
    }

    /**
     * @return a LiveData PagedList of all transactions cached locally and emits data for display in rv.
     */
    /*
    public LiveData<PagedList<TransactionDetails>> getTransactionDetailsFromLocal() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()
                .setPageSize(30)
                .setPrefetchDistance(5)
                .build());
        return new LivePagedListBuilder<>(
                ChipsRoomDatabase
                       .getInstance(App.getAppContext())
                        .transactionDetailsDao()
                        .getAllTransactionDetails(),
                pagedListConfig)
                .build();
    }

    /**
     * //@param siteRef
     * This method is called when a client needs to retrieve the details of the transaction
     * related to a previously submitted request. The Various parameters are available to filter the list, and,
     * depending on the criteria, zero or more results will be returned.
     */
    /*
    public void searchTransactionDetailsBySiteRefInfo(String siteRef) {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()
                .setPageSize(30)
                .setPrefetchDistance(5)
                .build());
        new LivePagedListBuilder<>(
                ChipsRoomDatabase
                        .getInstance(App.getAppContext())
                        .transactionDetailsDao()
                        .searchTransactionDetailsBySiteRefInfo(siteRef),
                pagedListConfig)
                .build();
    }

  /**
   * This METHOD is called when the SmartPOS device needs to notify the CHIPS® Payment Network platform of a
   * successful card payment. This will enable CHIPS® to allocate the received funds to the involved CHIPS® account.
   */
  public void getPaymentCompletionStatus(){
    MerchantPaymentCompletionReq req = new MerchantPaymentCompletionReq();
    req.setAmount(2000);
    req.setBankRefInfo("string");
    req.setGratuityAmount(100);
    req.setPayeeAccountUuid("string");
    req.setPayerRefInfo("string");
    req.setRequestId("string");
    req.setTokenId("string");
    req.setPayeeRefInfo("string");

    Observable<MerchantPaymentCompletionRes> merchantPaymentCompletionResObservable = PaymentReceiptStatusImpl
        .getINSTANCE()
        .getPaymentReceipt()
        .notifyPaymentCompletionWithReceipt(key,req);

    merchantPaymentCompletionResObservable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io())
        .subscribe(new Observer<MerchantPaymentCompletionRes>() {
          @Override
          public void onSubscribe(Disposable d) {
          }

          @Override
          public void onNext(MerchantPaymentCompletionRes completionRes) {
            //TO-DO implementation for successful payment request
            Timber.d("Response status code for merchant completion status is %s",completionRes.getStatus());
          }

          @Override
          public void onError(Throwable e) {
            Timber.d("Response error status for merchant completion is %s", e.getMessage());
          }

          @Override
          public void onComplete() {

          }
        });
  }

    public FirebaseRealtimeDatabaseQueryLiveData<PaymentDetails> getAllPaymentDetailsFromDB(){
        final DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("PaymentsDetails").child("values");
        return new FirebaseRealtimeDatabaseQueryLiveData<>(PaymentDetails.class, dRef);
    }
/*
    public FirebaseRealtimeDatabaseQueryLiveData<TransactionDetails> getAllTransactionDetailsFromDB(){
        final DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("TransactionsDetails");
        return new FirebaseRealtimeDatabaseQueryLiveData<>(TransactionDetails.class, dRef);
    }
 **/
}
