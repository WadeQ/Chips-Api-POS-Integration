package com.wadektech.chips.data.local.models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "payment_details")
public class PaymentDetails {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    private long date ;

    public PaymentDetails(int id, long date) {
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
