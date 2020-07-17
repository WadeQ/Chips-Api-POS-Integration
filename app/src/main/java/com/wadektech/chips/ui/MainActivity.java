package com.wadektech.chips.ui;

import android.app.Dialog;
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
import com.wadektech.chips.data.ChipToken;
import com.wadektech.chips.data.remote.TokenRequestClient;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        materialDesignAnimatedDialog = NiftyDialogBuilder.getInstance(this);
        confirmTransaction = findViewById(R.id.result_confirm);

        confirmTransaction.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                downloadResponse();
            }
        });

        requestId = findViewById(R.id.et_request_id);
        payeeRefInfo = findViewById(R.id.et_payee_ref);
        description = findViewById(R.id.et_description);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void downloadResponse(){
        materialDesignAnimatedDialog
                .withTitle("CHOOSE PAYMENT")
                .withMessage("Which payment method do you want to proceed with?")
                .withDialogColor("#d35400")
                .withButton1Text("CHIPS")
                .isCancelableOnTouchOutside(true)
                .withButton2Text("SCOTCH")
                .withDuration(700)
                .withEffect(Effectstype.Fall)
                .setButton2Click(view -> materialDesignAnimatedDialog.dismiss())
                .setButton1Click(view -> {
                    sendTokenRequestToServer();
                    materialDesignAnimatedDialog.dismiss();
                });
        materialDesignAnimatedDialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendTokenRequestToServer(){
        ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle("Awaiting server response...");
        dialog.setMessage("Please be patient as we process your request");
        dialog.show();
        //grab token request
        String requestID = "6d861ba6-67f6-450f-a0c8-fa39bb6f5033";
        String payeeRef =  "20191003162400123";
        String desc =  "Basket";
        //send chips token request to server
        //send token server response to details activity
        ChipToken token = new ChipToken(requestID, (long) 199.99,payeeRef,desc,true);
        Call<ChipToken> call = TokenRequestClient
                .getINSTANCE()
                .getRequestToken()
                .createPaymentTokenRequest(getAuthToken(),token);
        call.enqueue(new Callback<ChipToken>() {
            @Override
            public void onResponse(@NotNull Call<ChipToken> call, @NotNull Response<ChipToken> response) {
                dialog.dismiss();
                String chipToken = null;
                chipToken = String.valueOf(response.code());
                Timber.d("Response result %s", response.code());
                Intent intent = new Intent(getApplicationContext(), TokensDetailsActivity.class);
                intent.putExtra("token",chipToken);
                startActivity(intent);
            }
            @Override
            public void onFailure(Call<ChipToken> call, @NotNull Throwable t) {
                dialog.dismiss();
                //display error type
                Timber.d("Error sending token request %s", t.getMessage());
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