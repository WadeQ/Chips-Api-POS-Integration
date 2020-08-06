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
import java.util.Objects;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/**
 * static @RemoteRepository class that acts as our single source of truth.
 */
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
     * This function fetches all payment details from chips server asynchronously then caches it locally to be accessed by user.
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
     * This function fetches all transactions from chips server asynchronously then caches it locally to be accessed by user.
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
     * @return LiveData PagedList of payment details from local cache which is Room database.
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
}
