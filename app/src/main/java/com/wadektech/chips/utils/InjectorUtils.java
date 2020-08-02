package com.wadektech.chips.utils;

import com.wadektech.chips.data.local.models.PaymentDetails;

import java.util.List;

public class InjectorUtils {
    public static SingleLiveEvent<List<PaymentDetails>> provideListSingleLiveEvent() {
        return new SingleLiveEvent<>();
    }
}
