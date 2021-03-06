package com.wadektech.chips.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wadektech.chips.R;
import com.wadektech.chips.data.remote.models.TokenResDto;
import com.wadektech.chips.utils.SnackBarUtilsKt;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import timber.log.Timber;

public class TokensDetailsActivity extends AppCompatActivity {
    ImageView qrCodeImage;
    TextView responseText;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tokens_details);

        qrCodeImage = findViewById(R.id.qr_image);
        responseText = findViewById(R.id.tv_token_amount);

        //initialize viewmodel
        ChipsViewModel chipsViewModel = ViewModelProviders.of(this).get(ChipsViewModel.class);
        LiveData<DataSnapshot> liveData = chipsViewModel.getPaymentsRequestDataSnapshotLiveData();
        liveData.observe(this, dataSnapshot -> {
           if (dataSnapshot != null){
               // update the UI here with values in the snapshot
               for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                   TokenResDto tokenResDto = snapshot.getValue(TokenResDto.class);
                   assert tokenResDto != null;
                   Double amount = tokenResDto.getAmount();
                   String encoded_image = tokenResDto.getTokenImage();
                   assert encoded_image!= null;
                   Bitmap image = decodeImage(encoded_image);
                   qrCodeImage.setImageBitmap(image);
                   responseText.setText("Amount: "+amount);
                   Timber.d("DataSnapshot: %s", tokenResDto.getAmount());

                   if (tokenResDto.getStatus() != null){
                       SnackBarUtilsKt.snackbar(requireViewById(R.id.token_activity), "Transaction status: "+tokenResDto.getStatus());
                   } else{
                       SnackBarUtilsKt.snackbar(requireViewById(R.id.token_activity), "Please wait for transaction completion status...");
                   }
               }
           }
        });
    }

    private Bitmap decodeImage(String baseString) {
        if (baseString == null) return null;
        try {
            String imageDataBytes = baseString.substring(baseString.indexOf(",") + 1);
            InputStream stream = new ByteArrayInputStream(Base64.decode(imageDataBytes.getBytes(), Base64.DEFAULT));
            return BitmapFactory.decodeStream(stream);
        } catch (Exception e) {
            Timber.e(e);
        }
        return null;
    }

}