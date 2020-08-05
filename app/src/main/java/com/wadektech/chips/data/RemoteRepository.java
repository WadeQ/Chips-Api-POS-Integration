package com.wadektech.chips.data;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import com.wadektech.chips.data.local.models.TransactionDetails;
import com.wadektech.chips.data.remote.source.TransactionDetailsServiceImpl;
import com.wadektech.chips.utils.App;
import com.wadektech.chips.data.local.source.ChipsRoomDatabase;
import com.wadektech.chips.data.local.models.PaymentDetails;
import com.wadektech.chips.data.remote.source.PaymentDetailsServiceImpl;

import java.util.List;
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
    private static final Object LOCK = new Object();
    private static RemoteRepository sInstance;

    public synchronized static RemoteRepository getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RemoteRepository ();
            }
        }
        return sInstance;
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
                        ChipsRoomDatabase
                                        .getInstance(App.getContext().getApplicationContext())
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
                        ChipsRoomDatabase
                                        .getInstance(App.getContext().getApplicationContext())
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
    public static LiveData<PagedList<PaymentDetails>> getPaymentDetailsFromLocal() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()
                .setPageSize(30)
                .setPrefetchDistance(5)
                .build());
        return new LivePagedListBuilder<>(
                ChipsRoomDatabase
                        .getInstance(App.getContext().getApplicationContext())
                        .paymentDetailsDao()
                        .getAllPaymentDetails(),
                        pagedListConfig)
                .build();
    }

    /**
     * @return a LiveData PagedList of all transactions cached locally and emits data for display in rv.
     */
    public static LiveData<PagedList<TransactionDetails>> getTransactionDetailsFromLocal() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()
                .setPageSize(30)
                .setPrefetchDistance(5)
                .build());
        return new LivePagedListBuilder<>(
                ChipsRoomDatabase
                        .getInstance(App.getContext().getApplicationContext())
                        .transactionDetailsDao()
                        .getAllTransactionDetails(),
                pagedListConfig)
                .build();
    }
}
