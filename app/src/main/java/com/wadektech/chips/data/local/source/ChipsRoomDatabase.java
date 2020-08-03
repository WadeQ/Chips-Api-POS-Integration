package com.wadektech.chips.data.local.source;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.wadektech.chips.data.local.models.PaymentDetails;

@Database(entities = {PaymentDetails.class}, version = 1, exportSchema = false)
public abstract class ChipsRoomDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "chips";
    private static final Object LOCK = new Object();
    private static volatile ChipsRoomDatabase sInstance;

    public static ChipsRoomDatabase getInstance(Context context) {
        if (sInstance == null) {

            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            ChipsRoomDatabase.class,
                            ChipsRoomDatabase.DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return sInstance;
    }

    public abstract PaymentDetailsDao paymentDetailsDao();
    public abstract TransactionDetailsDao transactionDetailsDao();

}





