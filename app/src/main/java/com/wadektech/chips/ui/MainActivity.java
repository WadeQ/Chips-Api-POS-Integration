package com.wadektech.chips.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.wadektech.chips.R;
import com.wadektech.chips.data.remote.myreq.ChipServiceImpl;
import com.wadektech.chips.data.remote.myreq.TokenReqDto;
import com.wadektech.chips.data.remote.myreq.TokenResDto;
import com.wadektech.chips.utils.Constants;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Awaiting server response...");
        dialog.setMessage("Please be patient as we process your request");
        dialog.show();
        TokenReqDto req  = new TokenReqDto();
        req.setRequestId("string");
        req.setDueDate("2020-07-24");
        req.setDescription("string");
        req.setExpiryTime("2020-07-25T10:12:59.403Z");
        req.setAmount(0);
        req.setPayeeRefInfo("string");
        req.setRequestTokenImage(true);
        req.setTokenImageSize("SMALL");
        String key = " Basic YzU4NTRlYTMtNTUyYi00ZDhkLThmZDAtZjllMzAwZmUyM2UxOjNjNDI1YWQ1LTVmYmItNDJjOC1hZTI2LTRmYWJhZjFmMWY4ZA==";
        Call<TokenResDto> tokenResDtoCall = ChipServiceImpl.getINSTANCE().getChipService().createPayment(key,req);
        tokenResDtoCall.enqueue(new Callback<TokenResDto>() {
            @Override
            public void onResponse(@NotNull Call<TokenResDto> call, @NotNull Response<TokenResDto> response) {
                dialog.dismiss();
                assert response.body() != null;
                TokenResDto data = response.body();
                Timber.d("Response status code is %s", response.code());
                /*
                try {
                    assert response.errorBody() != null;
                    Timber.d("Response error body is %s", response.errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                **/
                String encodedQr = data.getTokenImage();
                int amount = data.getAmount();
                String description = data.getDescription();
                String date = data.getDueDate();
                Intent intent = new Intent(getApplicationContext(), TokensDetailsActivity.class);
                intent.putExtra("encoded_image",encodedQr);
                intent.putExtra("description",description);
                intent.putExtra("date",date);
                intent.putExtra("amount",amount);
                startActivity(intent);
            }

            @Override
            public void onFailure(@NotNull Call<TokenResDto> call, @NotNull Throwable t) {
                dialog.dismiss();
                Timber.i("token err: %s",t.getMessage());
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