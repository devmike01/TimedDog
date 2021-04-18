package devmike.leviapps.co.timeddogx.interfaces;// Created by Gbenga Oladipupo(Devmike01) on 4/3/21.


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import devmike.leviapps.co.timeddogx.interfaces.OnTimeOutCallback;
import devmike.leviapps.co.timeddogx.services.TimedDog;

public interface TimedDogServiceHelper {

    TimedDog monitor(long timeInMillis, Class<?> activityClass);
    TimedDog monitor(long timeInMillis);
}
