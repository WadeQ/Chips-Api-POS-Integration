package com.wadektech.chips.utils;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChipsAppExecutors {
    private static final Object LOCK = new Object();
    private static ChipsAppExecutors sInstance;
    private final Executor diskIO;

    public ChipsAppExecutors(Executor diskIO) {
        this.diskIO = diskIO;

    }

    public static ChipsAppExecutors getInstance() {
        if (sInstance == null) {
            synchronized (LOCK) {
                if (sInstance == null) {
                    sInstance = new ChipsAppExecutors (Executors.newSingleThreadExecutor());
                }
            }
        }
        return sInstance;
    }

    public Executor getDiskIO() {
        return diskIO;
    }
}
