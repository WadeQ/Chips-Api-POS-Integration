package com.wadektech.chips.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wadektech.chips.R;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import timber.log.Timber;

public class TokensDetailsActivity extends AppCompatActivity {
    ImageView qrCodeImage;
    TextView responseText;
    String encoded_image;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tokens_details);

        qrCodeImage = findViewById(R.id.qr_image);
        responseText = findViewById(R.id.tv_token_response);

        //grab token from intent
        Intent intent = getIntent();
        encoded_image = intent.getStringExtra("encoded_image");
        assert encoded_image!= null;
        Bitmap image = decodeImage(encoded_image);
        qrCodeImage.setImageBitmap(image);
        Toast.makeText(getBaseContext(), "qr code success", Toast.LENGTH_SHORT).show();

//       responseText.setText("Response code is: "+token);
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