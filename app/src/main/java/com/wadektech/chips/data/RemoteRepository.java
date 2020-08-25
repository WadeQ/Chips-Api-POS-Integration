package com.wadektech.chips.data;

import android.app.ProgressDialog;
import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wadektech.chips.R;
import com.wadektech.chips.data.local.models.PaymentDetails;
import com.wadektech.chips.data.local.models.TransactionDetails;
import com.wadektech.chips.data.local.source.ChipsRoomDatabase;
import com.wadektech.chips.data.remote.models.MerchantPaymentCompletionReq;
import com.wadektech.chips.data.remote.models.MerchantPaymentCompletionRes;
import com.wadektech.chips.data.remote.source.MerchantPaymentCompletionServiceImpl;
import com.wadektech.chips.data.remote.source.PaymentDetailsServiceImpl;
import com.wadektech.chips.data.remote.source.TransactionDetailsServiceImpl;
import com.wadektech.chips.utils.App;
import com.wadektech.chips.utils.FirebaseRealtimeDatabaseQueryLiveData;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class RemoteRepository {
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
        Observable<List<PaymentDetails>> paymentDetailsObservable = PaymentDetailsServiceImpl
                .getINSTANCE()
                .getPaymentRequestDetails()
                .getPaymentDetailsByCriteria(key);
        paymentDetailsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<List<PaymentDetails>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PaymentDetails> paymentDetailsList) {
                        Timber.d("onNext() payments: %s", paymentDetailsList.size());
                        //ChipsRoomDatabase
                         //       .getInstance(App.getAppContext())
                          //      .paymentDetailsDao()
                          //      .savePaymentDetails(paymentDetailsList);
                        DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference paymentsRef =  myRootRef.child("PaymentsDetails");
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
     * This function fetches all transactions from chips server asynchronously then caches it locally to be
     * accessed by user, needs to retrieve the details of the transaction related to a previously submitted request.
     */
    public void fetchTransactionDetailsFromRemote(){
        Observable<List<TransactionDetails>> transactionDetailsObservable = TransactionDetailsServiceImpl
                .getINSTANCE()
                .getTransactionRequestDetails()
                .getTransactionDetailsAsync(key);
        transactionDetailsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<List<TransactionDetails>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<TransactionDetails> transactionDetails) {
                        Timber.d("Transactions onNext(): %s", transactionDetails.size());
                        //ChipsRoomDatabase
                         //               .getInstance(App.getAppContext())
                         //               .transactionDetailsDao()
                          //              .saveTransactionDetails(transactionDetails);
                        DatabaseReference myRootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference transactionsRef =  myRootRef.child("TransactionsDetails");
                        transactionsRef.setValue(transactionDetails);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.d("Response error status for transaction details is %s", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * @param tokenId
     * This method is called when an external system needs to retrieve the details of a previously submitted
     * payment request by providing the tokenId. This endpoint will return, at most, one resulting API structure.
     */
    public void getPaymentDetailsByTokenIdFromRemote(String tokenId){
        ProgressDialog dialog = new ProgressDialog(context, R.style.DialogStyle);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Awaiting server response...");
        dialog.setMessage("Please be patient as we process your request");
        dialog.show();
        Observable<PaymentDetails> paymentDetailsObservable = PaymentDetailsServiceImpl
                .getINSTANCE()
                .getPaymentRequestDetails()
                .getPaymentDetailsByTokenId(key,tokenId);
        paymentDetailsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<PaymentDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PaymentDetails paymentDetailsList) {
                        dialog.dismiss();
                        //display result

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

        String key = " Basic YWE0MjkxZWItMjczOC00ZWQ2LTg3OTItZjc5MTkyMTNiNTExOjM0YzFiYTQ0LWFkNGYtNGNhMy1hMzhiLTRmYTcyNjIyZmFhNA==";

        Observable<MerchantPaymentCompletionRes> merchantPaymentCompletionResObservable = MerchantPaymentCompletionServiceImpl
                .getINSTANCE()
                .getMerchantPaymentNotification()
                .notifyPaymentCompletion(key,req);

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

    /**
     * @return LiveData PagedList of payment details from Room database.
     */
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
     * @param tokenId
     * This method is called when an we needs to retrieve the details of a previously submitted payment request
     * by providing the tokenId. This method will return, at most, one resulting API structure.
     */
    public void searchPaymentDetailsByTokenIdFromLocal(String tokenId) {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()
                .setPageSize(30)
                .setPrefetchDistance(5)
                .build());
        new LivePagedListBuilder<>(
                ChipsRoomDatabase
                        .getInstance(App.getAppContext())
                        .paymentDetailsDao()
                        .searchPaymentDetailsByTokenId(tokenId),
                pagedListConfig)
                .build();
    }

    /**
     * @param siteRef
     * This method is called when a client needs to retrieve the details of the transaction
     * related to a previously submitted request. The Various parameters are available to filter the list, and,
     * depending on the criteria, zero or more results will be returned.
     */
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

    public FirebaseRealtimeDatabaseQueryLiveData<PaymentDetails> getAllPaymentDetailsFromDB(){
        final DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("PaymentsDetails");
        return new FirebaseRealtimeDatabaseQueryLiveData<>(PaymentDetails.class, dRef);
    }

    public FirebaseRealtimeDatabaseQueryLiveData<TransactionDetails> getAllTransactionDetailsFromDB(){
        final DatabaseReference dRef = FirebaseDatabase.getInstance().getReference("TransactionsDetails");
        return new FirebaseRealtimeDatabaseQueryLiveData<>(TransactionDetails.class, dRef);
    }
}
