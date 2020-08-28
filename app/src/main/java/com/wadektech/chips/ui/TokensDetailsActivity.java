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
    TextView responseText, mSiteRef;
    TextView mDesc;
    TextView mDate;
    String siteRefInfo;
    String encoded_image;
    Double amount;
    String requestId;
    String tokenId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tokens_details);

        qrCodeImage = findViewById(R.id.qr_image);
        responseText = findViewById(R.id.tv_token_amount);
        mDesc = findViewById(R.id.tv_token_desc);
        mDate = findViewById(R.id.tv_token_date);
        mSiteRef = findViewById(R.id.tv_site_ref);

        //grab token from intent
        Intent intent = getIntent();
        encoded_image = intent.getStringExtra("encoded_image");
        amount = intent.getDoubleExtra("amount",0);
        requestId = intent.getStringExtra("requestId");
        tokenId = intent.getStringExtra("tokenId");
        siteRefInfo = intent.getStringExtra("siteRef");
        assert encoded_image!= null;
        Bitmap image = decodeImage(encoded_image);
        qrCodeImage.setImageBitmap(image);
        Toast.makeText(getBaseContext(), "qr code success", Toast.LENGTH_SHORT).show();

       responseText.setText("Amount: "+amount);
       mDate.setText("Token ID: "+tokenId);
       mDesc.setText("Request ID: "+requestId);
       mSiteRef.setText("SiteRefInfo: "+siteRefInfo);

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