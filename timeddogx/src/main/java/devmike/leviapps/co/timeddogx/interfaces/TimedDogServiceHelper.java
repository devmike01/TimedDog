package devmike.leviapps.co.timeddogx.interfaces;// Created by Gbenga Oladipupo(Devmike01) on 4/3/21.


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import devmike.leviapps.co.timeddogx.interfaces.OnTimeOutCallback;

public interface TimedDogServiceHelper {

    void monitor(long timeInMillis, OnTimeOutCallback onTimeOutCallback, Class<FragmentActivity> activityClass);
}
