package com.wadektech.chips.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.wadektech.chips.R;
import com.wadektech.chips.data.RemoteRepository;
import com.wadektech.chips.data.local.models.TransactionDetails;
import com.wadektech.chips.data.remote.source.TransactionDetailsServiceImpl;
import com.wadektech.chips.databinding.ActivityTransactionsDetailsBinding;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TransactionsDetailsActivity extends AppCompatActivity {
    ActivityTransactionsDetailsBinding binding;
    ChipsViewModel chipsViewModel;
    String key = " Basic YWE0MjkxZWItMjczOC00ZWQ2LTg3OTItZjc5MTkyMTNiNTExOjM0YzFiYTQ0LWFkNGYtNGNhMy1hMzhiLTRmYTcyNjIyZmFhNA==";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_transactions_details);

        chipsViewModel = new ViewModelProvider(this).get(ChipsViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(chipsViewModel);
        ChipsTransactionsAdapter chipsTransactionsAdapter = new ChipsTransactionsAdapter();
        binding.rvTransactions.setAdapter(chipsTransactionsAdapter);

        binding.transSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null){
                    getTransactionDetailsBySiteRefInfo(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

   public void getTransactionDetailsBySiteRefInfo(String siteRef){
       ProgressDialog dialog = new ProgressDialog(TransactionsDetailsActivity.this, R.style.DialogStyle);
       dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
       dialog.setTitle("Awaiting server response...");
       dialog.setMessage("Please be patient as we process your request");
       dialog.show();
       Observable<TransactionDetails> transactionDetailsObservable = TransactionDetailsServiceImpl
           .getINSTANCE()
           .getTransactionRequestDetails()
           .getTransactionDetailsBySiteRefAsync(key);
       transactionDetailsObservable.subscribeOn(Schedulers.io())
           .observeOn(AndroidSchedulers.mainThread())
           .unsubscribeOn(Schedulers.io())
           .subscribe(new Observer<TransactionDetails>() {
               @Override
               public void onSubscribe(Disposable d) {

               }

               @SuppressLint("SetTextI18n")
               @Override
               public void onNext(TransactionDetails transactionDetails) {
                dialog.dismiss();
                 //display queried transaction result using a bottom sheet dialog
                 if (transactionDetails != null){
                   BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(TransactionsDetailsActivity.this,
                       R.style.BottomSheetDialogTheme);
                   View view = LayoutInflater.from(getApplicationContext())
                       .inflate(R.layout.layout_bottom_sheet_dialog,findViewById(R.id.bottom_sheet_dialog),false);
                   TextView requestId = view.findViewById(R.id.tv_amount_details);
                   requestId.setText("Transaction Request id: "+transactionDetails.getAmount());
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