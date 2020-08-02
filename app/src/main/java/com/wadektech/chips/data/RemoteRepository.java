package com.wadektech.chips.data;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.wadektech.chips.App;
import com.wadektech.chips.data.local.ChipsRoomDatabase;
import com.wadektech.chips.data.local.models.PaymentDetails;
import com.wadektech.chips.data.remote.PaymentDetailsServiceImpl;
import com.wadektech.chips.ui.ChipsAppExecutors;
import com.wadektech.chips.utils.SingleLiveEvent;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RemoteRepository {
    private static final Object LOCK = new Object();
    private static RemoteRepository sInstance;
    private SingleLiveEvent<List<PaymentDetails>> paymentList;

    public synchronized static RemoteRepository getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RemoteRepository ();
            }
        }
        return sInstance;
    }

    public void fetchPaymentDetails(){
        Observable<List<PaymentDetails>> paymentDetailsObservable = PaymentDetailsServiceImpl
                .getINSTANCE()
                .getPaymentRequestDetails()
                .getPaymentDetails();
        paymentDetailsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<PaymentDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PaymentDetails paymentDetails) {
                        ChipsAppExecutors
                                .getInstance()
                                .getDiskIO()
                                .execute(ChipsRoomDatabase
                                        .getInstance(App.getContext().getApplicationContext())
                                        .paymentDetailsDao()
                                        .savePaymentDetails()
                                );
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

    public static LiveData<PagedList<PaymentDetails>> getPaymentDetailsFromRemote() {
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
}
