package com.wadektech.chips.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.SearchView;

import com.wadektech.chips.R;
import com.wadektech.chips.data.RemoteRepository;
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

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String tokenId) {
                if (tokenId != null){
                    chipsViewModel.getPaymentDetailsByTokenId(tokenId);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        //remoteRepository.fetchPaymentDetails();
    }
}