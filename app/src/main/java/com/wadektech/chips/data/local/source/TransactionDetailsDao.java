package com.wadektech.chips.data.local.source;


import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.wadektech.chips.data.local.models.TransactionDetails;

import java.util.List;

@Dao
public interface TransactionDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTransactionDetails(List<TransactionDetails> transactions);

    @Query("SELECT * FROM transaction_details  ORDER BY uuid ASC")
    DataSource.Factory<Integer, TransactionDetails> getAllTransactionDetails();
}
