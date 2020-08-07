package com.wadektech.chips.data.local.source;


import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.wadektech.chips.data.local.models.PaymentDetails;
import java.util.List;

@Dao
public interface PaymentDetailsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePaymentDetails(List<PaymentDetails> payments);

    @Query("SELECT * FROM payment_details  ORDER BY tokenId ASC")
    DataSource.Factory<Integer, PaymentDetails> getAllPaymentDetails();

    @Query("SELECT * FROM payment_details WHERE tokenId LIKE :filter ORDER BY id DESC")
    DataSource.Factory<Integer, PaymentDetails> searchPaymentDetailsByTokenId(String filter);
}
