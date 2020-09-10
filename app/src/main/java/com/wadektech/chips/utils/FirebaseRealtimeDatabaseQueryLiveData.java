package com.wadektech.chips.utils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class FirebaseRealtimeDatabaseQueryLiveData<T> extends LiveData<List<T>> implements ValueEventListener {
    private final Class<T> type ;
    private DatabaseReference databaseReference ;

    public FirebaseRealtimeDatabaseQueryLiveData(Class<T> type, DatabaseReference databaseReference) {
        this.type = type;
        this.databaseReference = databaseReference;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Timber.d("onDataChange : dataSnapshot has been received");
        setValue(dataSnapshotToList(dataSnapshot));
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Timber.d("onCancelled : snapShot not received%s", databaseError.getMessage());
    }

    @Override
    protected void onActive() {
        super.onActive();
        Timber.d("onActive : ValueEventListener has been added");
        databaseReference.addValueEventListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        Timber.d("onActive : ValueEventListener has been removed");
        databaseReference.removeEventListener(this);
    }

    private List<T> dataSnapshotToList(DataSnapshot dataSnapshot) {
        final List<T> list = new ArrayList<>();
        if (!dataSnapshot.exists() || !dataSnapshot.hasChildren()){
            Timber.d("dataSnapshotToList : no dataSnapshot exists");
            return list ;
        } else {
            for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                list.add(snapshot.getValue(type));
            }
        }

        return list ;
    }
}
