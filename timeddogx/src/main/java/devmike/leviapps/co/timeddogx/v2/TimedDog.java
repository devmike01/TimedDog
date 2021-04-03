package devmike.leviapps.co.timeddogx.v2;

// Created by Gbenga Oladipupo(Devmike01) on 4/3/21.


import android.content.Context;

import androidx.annotation.NonNull;

import devmike.leviapps.co.timeddogx.interfaces.OnTimeOutCallback;
import devmike.leviapps.co.timeddogx.interfaces.TimedDogServiceHelper;
import devmike.leviapps.co.timeddogx.utils.TimedDogPreferencesImpl;
import devmike.leviapps.co.timeddogx.utils.TimedDogServiceHelperServiceHelperImpl;

public class TimedDog{

    Context context;

    static Object timedDog = null;

    TimedDog(Context context){
        this.context = context;
    }

    public static TimedDog with(Context context){
        if (timedDog == null){
            timedDog = new TimedDog(context);
        }
        return ((TimedDog)timedDog);
    }

    public void run(long timeInMillis) {
        TimedDogServiceHelperServiceHelperImpl.getInstance().run(context, timeInMillis);
    }

    public void run( long timeInMillis, OnTimeOutCallback onTimeOutCallback) {
        TimedDogServiceHelperServiceHelperImpl.getInstance().run(context, timeInMillis, onTimeOutCallback);
    }

}
