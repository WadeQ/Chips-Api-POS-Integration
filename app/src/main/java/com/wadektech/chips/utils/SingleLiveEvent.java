package com.wadektech.chips.utils;

import androidx.annotation.MainThread;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicBoolean;
import io.reactivex.annotations.Nullable;
import timber.log.Timber;

public class SingleLiveEvent<T> extends MutableLiveData<T> {
    private final AtomicBoolean mPending=new AtomicBoolean(false);
    @MainThread
    public void observe(LifecycleOwner owner, @NotNull final Observer<? super T> observer){
        if (hasActiveObservers()){
            Timber.d("multiple observers registered but only one will be notified of changes");
        }
        //observe the internal mutablelivedata
        super.observe(owner, t -> {
            if (mPending.compareAndSet(true,false)){
                observer.onChanged(t);
            }
        });
    }
    @MainThread
    public void setValue(@Nullable T t){
        mPending.set(true);
        super.setValue(t);
    }

    @MainThread
    public void call(){
        setValue(null);
    }

    public void postValue(@Nullable T t){
        mPending.set(true);
        super.postValue(t);
    }
}
