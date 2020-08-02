package com.wadektech.chips.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.wadektech.chips.R;
import com.wadektech.chips.databinding.ActivityPaymentsDetailsBinding;

public class PaymentsDetailsActivity extends AppCompatActivity {
    ActivityPaymentsDetailsBinding binding ;
    ChipsViewModel chipsViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_payments_details);

        chipsViewModel = new ViewModelProvider(this).get(ChipsViewModel.class);

        binding.setLifecycleOwner(this);

        binding.setViewModel(chipsViewModel);

        ChipsPaymentAdapter chipsPaymentAdapter = new ChipsPaymentAdapter();

        binding.rvPayments.setAdapter(chipsPaymentAdapter);

    }
}