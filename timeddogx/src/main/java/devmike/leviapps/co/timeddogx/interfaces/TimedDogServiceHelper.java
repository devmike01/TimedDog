package devmike.leviapps.co.timeddogx.interfaces;// Created by Gbenga Oladipupo(Devmike01) on 4/3/21.


import android.content.Context;

import androidx.annotation.NonNull;

import devmike.leviapps.co.timeddogx.interfaces.OnTimeOutCallback;

public interface TimedDogServiceHelper {

    void run(@NonNull Context context, long timeInMillis);

    void run(@NonNull Context context, long timeInMillis, OnTimeOutCallback onTimeOutCallback);
}
