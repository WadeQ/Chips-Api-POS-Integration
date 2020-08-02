package com.wadektech.chips.data.local.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "payment_details")
public class PaymentDetails {
    @PrimaryKey(autoGenerate = true)
    private long id ;
    private long date ;
}
