package com.wadektech.chips.data.local.source;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.wadektech.chips.data.local.models.PaymentDetails;
import com.wadektech.chips.data.local.models.TransactionDetails;

@Database(entities = {PaymentDetails.class, TransactionDetails.class}, version = 1, exportSchema = false)
public abstract class ChipsRoomDatabase extends RoomDatabase {
    public static volatile ChipsRoomDatabase roomInstance ;
    public static final Object LOCK = new Object() ;

    public synchronized static ChipsRoomDatabase getInstance(Context context){
        if (roomInstance == null){
            synchronized (LOCK){
                if (roomInstance == null){
                    roomInstance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            ChipsRoomDatabase.class,
                            "chips_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return roomInstance ;
    }

    public abstract PaymentDetailsDao paymentDetailsDao();
    public abstract TransactionDetailsDao transactionDetailsDao();

}
