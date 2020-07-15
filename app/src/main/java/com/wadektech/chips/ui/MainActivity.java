package com.wadektech.chips.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;
import com.wadektech.chips.R;
import com.wadektech.chips.data.ChipToken;
import com.wadektech.chips.data.remote.TokenRequestClient;
import org.jetbrains.annotations.NotNull;
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
            @Override
            public void onClick(View view) {
                downloadResponse();
            }
        });

        requestId = findViewById(R.id.et_request_id);
        payeeRefInfo = findViewById(R.id.et_payee_ref);
        description = findViewById(R.id.et_description);

    }

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

    public void sendTokenRequestToServer(){
        //grab token request
        String requestID = requestId.getText().toString().trim();
        String payeeRef = requestId.getText().toString().trim();
        String desc = description.getText().toString().trim();
        //send chips token request to server
        //send token server response to details activity
        ChipToken token = new ChipToken(requestID,1200L,payeeRef,desc,true);
        Call<ChipToken> call = TokenRequestClient
                .getINSTANCE()
                .getRequestToken()
                .createPaymentTokenRequest(token);
        call.enqueue(new Callback<ChipToken>() {
            @Override
            public void onResponse(@NotNull Call<ChipToken> call, Response<ChipToken> response) {
                    String chipToken = String.valueOf(response.code());
                    Timber.d("Response is successful %s", response.body());
                    //Toast.makeText(getApplicationContext(), "response received is "+chipToken, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), TokensDetailsActivity.class);
                    intent.putExtra("token",chipToken);
                    startActivity(intent);
            }
            @Override
            public void onFailure(Call<ChipToken> call, @NotNull Throwable t) {
                //display error type
                Timber.d("Error sending token request %s", t.getMessage());
            }
        });
    }
}