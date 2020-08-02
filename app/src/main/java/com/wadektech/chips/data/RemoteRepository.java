package com.wadektech.chips.data;

public class RemoteRepository {
    private static final Object LOCK = new Object();
    private static RemoteRepository sInstance;

    public synchronized static RemoteRepository getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new RemoteRepository ();
            }
        }
        return sInstance;
    }
}
