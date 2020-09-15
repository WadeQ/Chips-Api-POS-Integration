package com.wadektech.chips.ui;

    import android.annotation.SuppressLint;
    import android.app.ProgressDialog;
    import android.content.Intent;
    import android.os.Build;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.Toast;
    import androidx.annotation.RequiresApi;
    import androidx.appcompat.app.AppCompatActivity;
    import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
    import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.wadektech.chips.R;
    import com.wadektech.chips.data.remote.models.PaymentReceiptReq;
    import com.wadektech.chips.data.remote.models.PaymentReceiptRes;
    import com.wadektech.chips.data.remote.models.TokenReqDto;
    import com.wadektech.chips.data.remote.models.TokenResDto;
    import com.wadektech.chips.data.remote.source.PaymentReceiptStatusImpl;
    import com.wadektech.chips.data.remote.source.PaymentRequestServiceImpl;
    import io.reactivex.Observable;
    import io.reactivex.Observer;
    import io.reactivex.android.schedulers.AndroidSchedulers;
    import io.reactivex.disposables.Disposable;
    import io.reactivex.schedulers.Schedulers;
    import timber.log.Timber;


public class MainActivity extends AppCompatActivity {
  Button confirmTransaction, mTransactions, mPayments;
  NiftyDialogBuilder materialDesignAnimatedDialog;
  EditText mAmount;
  String key = " Basic YWE0MjkxZWItMjczOC00ZWQ2LTg3OTItZjc5MTkyMTNiNTExOjM0YzFiYTQ0LWFkNGYtNGNhMy1hMzhiLTRmYTcyNjIyZmFhNA==";

  @RequiresApi(api = Build.VERSION_CODES.O)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    materialDesignAnimatedDialog = NiftyDialogBuilder.getInstance(this);
    confirmTransaction = findViewById(R.id.result_confirm);
    mTransactions = findViewById(R.id.transactions);
    mPayments = findViewById(R.id.payments);

    confirmTransaction.setOnClickListener(view -> {
      mAmount = findViewById(R.id.transAmount);
      String amount = mAmount.getText().toString().trim();
      if (TextUtils.isEmpty(amount)){
        mAmount.setError("Amount cannot be blank!");
      } else {
        downloadResponse(amount);
      }
    });

    mPayments.setOnClickListener(v -> {
      Intent intent = new Intent(MainActivity.this, PaymentsDetailsActivity.class);
      startActivity(intent);
    });

    mTransactions.setOnClickListener(v -> {
      Intent intent = new Intent(MainActivity.this, TransactionsDetailsActivity.class);
      startActivity(intent);
    });

  }

  @RequiresApi(api = Build.VERSION_CODES.O)
  private void downloadResponse(String amount){
    materialDesignAnimatedDialog
        .withTitle("CHOOSE PAYMENT")
        .withMessage("Which payment method do you want to proceed with?")
        .withDialogColor("#0099ff")
        .withIcon(R.drawable.ic_payment)
        .withButton1Text("CHIPS")
        .isCancelableOnTouchOutside(true)
        .withButton2Text("SCOTCH")
        .withDuration(700)
        .withEffect(Effectstype.Fall)
        .setButton2Click(view -> materialDesignAnimatedDialog.dismiss())
        .setButton1Click(view -> {
          fetchToken(amount);
          materialDesignAnimatedDialog.dismiss();
        });
    materialDesignAnimatedDialog.show();
  }

  public void fetchToken(String amt){
    ProgressDialog dialog = new ProgressDialog(MainActivity.this, R.style.DialogStyle);
    dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    dialog.setTitle("Awaiting server response...");
    dialog.setMessage("Please be patient as we process your request");
    dialog.show();
    TokenReqDto req  = new TokenReqDto();
    req.setRequestId("ee94c6cb-f068-4c63-a4fb-2cb2764865d5");
    req.setDueDate("2020-09-12");
    req.setDescription("Once off code");
    req.setExpiryTime("2020-09-30T09:05:41.366Z");
    req.setAmount(Double.parseDouble(amt));
    req.setPayeeRefInfo("d86941ad-b28b-432d-89c2-d189b2a46220");
    req.setPayeeCategory1("string");
    req.setPayeeCategory2("string");
    req.setPayeeCategory3("string");
    req.setSiteName("Test");
    req.setSiteRefInfo("MpRequest");
    req.setRequestTip(false);
    req.setUseOnce(true);
    req.setUseOnce(true);
    req.setNotifyUrl("https://us-central1-chips-d4dae.cloudfunctions.net/updateStatus");
    req.setRequestTokenImage(true);
    req.setTokenImageSize("SMALL");

    String key = " Basic YWE0MjkxZWItMjczOC00ZWQ2LTg3OTItZjc5MTkyMTNiNTExOjM0YzFiYTQ0LWFkNGYtNGNhMy1hMzhiLTRmYTcyNjIyZmFhNA==";

    Observable<TokenResDto> tokenResDtoObservable = PaymentRequestServiceImpl
        .getINSTANCE()
        .getChipService()
        .createPayment(key,req);
    tokenResDtoObservable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io())
        .subscribe(new Observer<TokenResDto>() {
          @Override
          public void onSubscribe(Disposable d) {

          }

          @RequiresApi(api = Build.VERSION_CODES.P)
          @Override
          public void onNext(TokenResDto tokenResDto) {
            dialog.dismiss();
            if (tokenResDto != null){
              Timber.d("Response status code is %s", tokenResDto.getStatus());
              String status = tokenResDto.getStatus();

              DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
              DatabaseReference paymentsReqRef =  rootRef.child("transactions").push();
              paymentsReqRef.setValue(tokenResDto);

              Intent intent = new Intent(getApplicationContext(), TokensDetailsActivity.class);
              intent.putExtra("status",status);
              startActivity(intent);
            }

          }

          @Override
          public void onError(Throwable e) {
            dialog.dismiss();
            Timber.d("Response error status is %s", e.getMessage());
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
    PaymentReceiptReq req = new PaymentReceiptReq();
    req.setAmount(2000);
    req.setBankRefInfo("string");
    req.setGratuityAmount(100);
    req.setPayeeAccountUuid("string");
    req.setPayerRefInfo("string");
    req.setRequestId("string");
    req.setTokenId("string");
    req.setPayeeRefInfo("string");

    Observable<PaymentReceiptRes> merchantPaymentCompletionResObservable = PaymentReceiptStatusImpl
        .getINSTANCE()
        .getPaymentReceipt()
        .notifyPaymentCompletionWithReceipt(key,req);

    merchantPaymentCompletionResObservable.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .unsubscribeOn(Schedulers.io())
        .subscribe(new Observer<PaymentReceiptRes>() {
          @Override
          public void onSubscribe(Disposable d) {
          }

          @SuppressLint("ShowToast")
          @Override
          public void onNext(PaymentReceiptRes completionRes) {
            //TO-DO implementation for successful payment completion receipt
            if (completionRes != null){
              Toast.makeText(MainActivity.this, "Payment completion status: "+completionRes.getStatus(),
                  Toast.LENGTH_LONG);
              Timber.d("Response status code for payment completion status is %s",completionRes.getStatus());
            }

          }

          @Override
          public void onError(Throwable e) {
            Timber.d("Response error status for payment completion is %s", e.getMessage());
          }

          @Override
          public void onComplete() {

          }
        });
  }


  //Construct and send basic auth headers
  // public static String getAuthToken() {
  //    byte[] data;
  //    data = (Constants.UUID + ":" + Constants.API_KEY).getBytes(StandardCharsets.UTF_8);
  //    return " Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
  //}
}
