package com.wadektech.chips.utils;

import androidx.lifecycle.LiveData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;
import timber.log.Timber;

public class FirebaseQueryLiveData extends LiveData<DataSnapshot> {
  private static final String LOG_TAG = "FirebaseQueryLiveData";

  private final Query query;
  private final MyValueEventListener listener = new MyValueEventListener();

  public FirebaseQueryLiveData(Query query) {
    this.query = query;
  }

  public FirebaseQueryLiveData(DatabaseReference ref) {
    this.query = ref;
  }

  @Override
  protected void onActive() {
    Timber.d("onActive : ValueEventListener has been added");
    query.addValueEventListener(listener);
  }

  @Override
  protected void onInactive() {
    Timber.d("onInactive : ValueEventListener has been removed");
    query.removeEventListener(listener);
  }

  private class MyValueEventListener implements ValueEventListener {
    @Override
    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
      setValue(dataSnapshot);
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
      Timber.d("onCancelled : snapShot not received%s", databaseError.getMessage());
    }
  }
}
