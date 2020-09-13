package com.wadektech.chips.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.wadektech.chips.R;
import com.wadektech.chips.data.local.models.TransactionDetails;
import com.wadektech.chips.data.remote.source.TransactionDetailsServiceImpl;
import com.wadektech.chips.utils.SnackBarUtilsKt;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;


public class TransactionsDetailsActivity extends AppCompatActivity {
    String key = " Basic YWE0MjkxZWItMjczOC00ZWQ2LTg3OTItZjc5MTkyMTNiNTExOjM0YzFiYTQ0LWFkNGYtNGNhMy1hMzhiLTRmYTcyNjIyZmFhNA==";
    SearchView transSearch ;
    TextView requestId,feeAmount,vatAmount,gratuityAmount,payeeSiteRefInfo,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions_details);

        feeAmount = findViewById(R.id.tv_fee_amt);
        vatAmount = findViewById(R.id.tv_vat_amt);
        amount = findViewById(R.id.tv_amt);
        gratuityAmount = findViewById(R.id.tv_gratuity_amt);
        payeeSiteRefInfo = findViewById(R.id.tv_payee_ref_info);
        requestId = findViewById(R.id.tv_request_id);

        transSearch = findViewById(R.id.trans_search);
        transSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null){
                    getTransactionDetailsBySiteRefInfo(query);
                    transSearch.setQuery("",false);
                    transSearch.clearFocus();
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
   * This function fetches all transactions from chips server asynchronously then caches it locally to be
   * accessed by user, needs to retrieve the details of the transaction related to a previously submitted request.
   * @param siteRef
   */
   public void getTransactionDetailsBySiteRefInfo(String siteRef){
       ProgressDialog dialog = new ProgressDialog(TransactionsDetailsActivity.this, R.style.DialogStyle);
       dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
       dialog.setTitle("Awaiting server response...");
       dialog.setMessage("Please be patient as we process your request");
       dialog.show();
       Observable<TransactionDetails> transactionDetailsObservable = TransactionDetailsServiceImpl
           .getINSTANCE()
           .getFilteredTransactionRequestDetails()
           .getTransactionDetailsBySiteRefAsync(key,siteRef);
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
                   requestId.setText("Transaction Request id: "+transactionDetails.getRequestId());
                   feeAmount.setText("Transaction Fee Amount: "+transactionDetails.getFeeAmount());
                   vatAmount.setText("Transactions VAT Amount: "+transactionDetails.getFeeVatAmount());
                   amount.setText("Transaction Amount: "+transactionDetails.getAmount());
                   gratuityAmount.setText("Transactions Gratuity Amount: "+transactionDetails.getGratuityAmount());
                   payeeSiteRefInfo.setText("Transactions PayeeSiteRefInfo: "+transactionDetails.getPayeeSiteRefInfo());
                 }
               }

               @RequiresApi(api = Build.VERSION_CODES.P)
               @Override
               public void onError(Throwable e) {
                 dialog.dismiss();
                 Timber.d("getTransactionDetailsBySiteRefInfo :error status is %s", e.getMessage());
                 SnackBarUtilsKt.snackbar(requireViewById(R.id.trans_activity),
                     "Error getting queried transaction details, " +
                         "make sure the site reference used is the correct one and you have a stable internet connection...");
               }

               @Override
               public void onComplete() {

               }
           });
   }
}