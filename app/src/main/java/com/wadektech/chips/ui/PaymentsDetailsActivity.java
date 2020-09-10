package com.wadektech.chips.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.SearchView;

import com.wadektech.chips.R;
import com.wadektech.chips.data.RemoteRepository;
import com.wadektech.chips.data.local.models.PaymentDetails;
import com.wadektech.chips.data.remote.source.PaymentDetailsServiceImpl;
import com.wadektech.chips.databinding.ActivityPaymentsDetailsBinding;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PaymentsDetailsActivity extends AppCompatActivity {
    ActivityPaymentsDetailsBinding binding ;
    ChipsViewModel chipsViewModel;
    String key = " Basic YWE0MjkxZWItMjczOC00ZWQ2LTg3OTItZjc5MTkyMTNiNTExOjM0YzFiYTQ0LWFkNGYtNGNhMy1hMzhiLTRmYTcyNjIyZmFhNA==";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_payments_details);

        chipsViewModel = new ViewModelProvider(this).get(ChipsViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(chipsViewModel);
        ChipsPaymentAdapter chipsPaymentAdapter = new ChipsPaymentAdapter();
        binding.rvPayments.setAdapter(chipsPaymentAdapter);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String tokenId) {
                if (tokenId != null){
                    getPaymentDetailsByTokenIdFromRemote(tokenId);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }
    /**
     * @param tokenId
     * This method is called when an external system needs to retrieve the details of a previously submitted
     * payment request by providing the tokenId. This endpoint will return, at most, one resulting API structure.
     */
    public void getPaymentDetailsByTokenIdFromRemote(String tokenId){
        ProgressDialog dialog = new ProgressDialog(PaymentsDetailsActivity.this, R.style.DialogStyle);
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
}