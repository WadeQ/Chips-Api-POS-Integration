package com.wadektech.chips.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
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
    Button mDismiss ;
    TextView mStatus, mToken, mPayee, mDescription, mExpiry, mAmount ;
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

        mStatus = findViewById(R.id.tv_status_details) ;
        mAmount = findViewById(R.id.tv_amount_details);
        mToken = findViewById(R.id.tv_token_id_details);
        mPayee = findViewById(R.id.tv_payee_ref_details);
        mDescription = findViewById(R.id.tv_description_details);
        mExpiry = findViewById(R.id.tv_expiry_time_details);

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

                @SuppressLint("SetTextI18n")
                @Override
                public void onNext(PaymentDetails paymentDetailsList) {
                    dialog.dismiss();
                   //display result
                  if (paymentDetailsList != null){
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(PaymentsDetailsActivity.this,
                        R.style.BottomSheetDialogTheme);
                    View bottomSheetView = LayoutInflater.from(getApplicationContext())
                        .inflate(R.layout.layout_bottom_sheet_dialog, findViewById(R.id.bottom_sheet_dialog), false);

                    TextView description = bottomSheetView.findViewById(R.id.tv_description_details);
                    description.setText("Item Description: "+paymentDetailsList.getDescription());
                    TextView expiry = bottomSheetView.findViewById(R.id.tv_expiry_time_details);
                    expiry.setText("Expiration date/time: "+paymentDetailsList.getExpiryTime());
                    TextView siteRef = bottomSheetView.findViewById(R.id.tv_payee_ref_details);
                    siteRef.setText("Site Reference: "+paymentDetailsList.getPayeeSiteRefInfo());
                    TextView status = bottomSheetView.findViewById(R.id.tv_status_details);
                    status.setText("Status: "+paymentDetailsList.getStatus());
                    TextView token = bottomSheetView.findViewById(R.id.tv_token_id_details);
                    token.setText("Token id: "+paymentDetailsList.getTokenId());
                    TextView amount = bottomSheetView.findViewById(R.id.tv_amount_details);
                    amount.setText("Amount paid: "+paymentDetailsList.getAmount());

                    bottomSheetView.findViewById(R.id.btn_dismiss).setOnClickListener(view -> {
                      bottomSheetDialog.dismiss();
                    });

                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();
                  }
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