package com.wadektech.chips.data;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.wadektech.chips.data.local.models.PaymentDetails;
import com.wadektech.chips.data.local.models.TransactionDetails;
import com.wadektech.chips.data.local.source.ChipsRoomDatabase;
import com.wadektech.chips.data.remote.source.TransactionDetailsServiceImpl;
import com.wadektech.chips.data.remote.source.PaymentDetailsServiceImpl;
import com.wadektech.chips.utils.App;
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
                .getPaymentDetailsByCriteria();
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
                        ChipsRoomDatabase
                                .getInstance(App.Companion.getApp())
                                .paymentDetailsDao()
                                .savePaymentDetails(paymentDetailsList);
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
                .getTransactionDetailsAsync();
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
                        ChipsRoomDatabase
                                        .getInstance(App.Companion.getApp())
                                        .transactionDetailsDao()
                                        .saveTransactionDetails(transactionDetails);
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
     * @return LiveData PagedList of payment details from Room database.
     */
    public LiveData<PagedList<PaymentDetails>> getPaymentDetailsFromLocal() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()
                .setPageSize(30)
                .setPrefetchDistance(5)
                .build());
        return new LivePagedListBuilder<>(
                ChipsRoomDatabase
                        .getInstance(App.Companion.getApp())
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
                       .getInstance(App.Companion.getApp())
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
                        .getInstance(App.Companion.getApp())
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
                        .getInstance(App.Companion.getApp())
                        .transactionDetailsDao()
                        .searchTransactionDetailsBySiteRefInfo(siteRef),
                pagedListConfig)
                .build();
    }
}
