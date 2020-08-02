package com.wadektech.chips.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.wadektech.chips.R;
import com.wadektech.chips.data.remote.ChipServiceImpl;
import com.wadektech.chips.data.remote.MerchantPaymentApiServiceImpl;
import com.wadektech.chips.data.remote.models.PaymentNotificationReq;
import com.wadektech.chips.data.remote.models.PaymentNotificationRes;
import com.wadektech.chips.data.remote.models.TokenReqDto;
import com.wadektech.chips.data.remote.models.TokenResDto;
import com.wadektech.chips.utils.Constants;
import java.nio.charset.StandardCharsets;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    Button confirmTransaction;
    NiftyDialogBuilder materialDesignAnimatedDialog;
    EditText requestId,payeeRefInfo,description;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        materialDesignAnimatedDialog = NiftyDialogBuilder.getInstance(this);
        confirmTransaction = findViewById(R.id.result_confirm);

        confirmTransaction.setOnClickListener(view -> downloadResponse());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void downloadResponse(){
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
                    fetchToken();
                    materialDesignAnimatedDialog.dismiss();
                });
        materialDesignAnimatedDialog.show();
    }

    public void fetchToken(){
        ProgressDialog dialog = new ProgressDialog(MainActivity.this, R.style.DialogStyle);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Awaiting server response...");
        dialog.setMessage("Please be patient as we process your request");
        dialog.show();
        TokenReqDto req  = new TokenReqDto();
        req.setRequestId("test234");
        req.setDueDate("2020-08-01");
        req.setDescription("string");
        req.setExpiryTime("2020-08-02T09:05:41.366Z");
        req.setAmount(2000);
        req.setPayeeRefInfo("string");
        req.setPayeeCategory1("string");
        req.setPayeeCategory2("string");
        req.setPayeeCategory3("string");
        req.setSiteName("string");
        req.setSiteRefInfo("string");
        req.setRequestTip(false);
        req.setUseOnce(true);
        req.setNotifyUrl("https://us-central1-chips-d4dae.cloudfunctions.net/updateStatus");
        req.setRequestTokenImage(true);
        req.setTokenImageSize("SMALL");
        String key = " Basic YzU4NTRlYTMtNTUyYi00ZDhkLThmZDAtZjllMzAwZmUyM2UxOjNjNDI1YWQ1LTVmYmItNDJjOC1hZTI2LTRmYWJhZjFmMWY4ZA==";

        Observable<TokenResDto> tokenResDtoObservable = ChipServiceImpl
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

                    @Override
                    public void onNext(TokenResDto tokenResDto) {
                    dialog.dismiss();
                        Timber.d("Response status code is %s", tokenResDto.getStatus());
                        String encodedQr = tokenResDto.getTokenImage();
                        int amount = tokenResDto.getAmount();
                        String description = tokenResDto.getStatus();
                        String date = tokenResDto.getDueDate();
                        Intent intent = new Intent(getApplicationContext(), TokensDetailsActivity.class);
                        intent.putExtra("encoded_image",encodedQr);
                        intent.putExtra("description",description);
                        intent.putExtra("date",date);
                        intent.putExtra("amount",amount);
                        startActivity(intent);

                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        Timber.d("Response error status is %s", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        dialog.dismiss();

                    }
                });
    }

    public void getPaymentNotification(){
        ProgressDialog dialog = new ProgressDialog(MainActivity.this, R.style.DialogStyle);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Awaiting server response...");
        dialog.setMessage("Please be patient as we process your payment request");
        dialog.show();
        PaymentNotificationReq paymentNotificationReq = new PaymentNotificationReq();
        String key = " Basic YzU4NTRlYTMtNTUyYi00ZDhkLThmZDAtZjllMzAwZmUyM2UxOjNjNDI1YWQ1LTVmYmItNDJjOC1hZTI2LTRmYWJhZjFmMWY4ZA==";

        Observable<PaymentNotificationRes> paymentNotificationResObservable = MerchantPaymentApiServiceImpl
                .getINSTANCE()
                .getMerchantPaymentNotification()
                .notifyPaymentCompletion(key,paymentNotificationReq);
        paymentNotificationResObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<PaymentNotificationRes>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        dialog.dismiss();
                    }

                    @Override
                    public void onNext(PaymentNotificationRes paymentNotificationRes) {
                        dialog.dismiss();
                        //TO-DO implementation for successful payment request
                    }

                    @Override
                    public void onError(Throwable e) {
                        dialog.dismiss();
                        Timber.d("Response error status for payment is %s", e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //Construct and send basic auth headers
    public static String getAuthToken() {
        byte[] data;
        data = (Constants.UUID + ":" + Constants.API_KEY).getBytes(StandardCharsets.UTF_8);
        return " Basic " + Base64.encodeToString(data, Base64.NO_WRAP);
    }
}