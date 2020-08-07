package com.wadektech.chips.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.SearchView;

import com.wadektech.chips.R;
import com.wadektech.chips.data.RemoteRepository;
import com.wadektech.chips.databinding.ActivityTransactionsDetailsBinding;

public class TransactionsDetailsActivity extends AppCompatActivity {
    ActivityTransactionsDetailsBinding binding;
    ChipsViewModel chipsViewModel;
    RemoteRepository remoteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_transactions_details);

        chipsViewModel = new ViewModelProvider(this).get(ChipsViewModel.class);
        binding.setLifecycleOwner(this);
        binding.setViewModel(chipsViewModel);
        ChipsTransactionsAdapter chipsTransactionsAdapter = new ChipsTransactionsAdapter();
        binding.rvTransactions.setAdapter(chipsTransactionsAdapter);

        binding.transSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query != null){
                    chipsViewModel.getTransactionDetailsBySiteRefInfo(query);
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

    }
}