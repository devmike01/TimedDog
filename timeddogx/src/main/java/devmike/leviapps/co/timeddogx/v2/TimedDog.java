package devmike.leviapps.co.timeddogx.v2;

// Created by Gbenga Oladipupo(Devmike01) on 4/3/21.


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import devmike.leviapps.co.timeddogx.interfaces.OnTimeOutCallback;
import devmike.leviapps.co.timeddogx.interfaces.TimedDogServiceHelper;
import devmike.leviapps.co.timeddogx.utils.TimedDogPreferencesImpl;
import devmike.leviapps.co.timeddogx.utils.TimedDogServiceHelperServiceHelperImpl;

public class TimedDog{

    Context context;
    Class<FragmentActivity> fragmentActivityClass;

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

    public void run( long timeInMillis, OnTimeOutCallback onTimeOutCallback, Class<?> activityClass) {

        try{
            fragmentActivityClass = ((Class<FragmentActivity>)activityClass);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        TimedDogServiceHelperServiceHelperImpl.getInstance().run(context, timeInMillis, onTimeOutCallback, fragmentActivityClass);
    }


    public void run( long timeInMillis, Class<?> loginActivityClass) {

        try{
            fragmentActivityClass = ((Class<FragmentActivity>)loginActivityClass);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        TimedDogServiceHelperServiceHelperImpl.getInstance().run(context, timeInMillis, null, fragmentActivityClass);
    }

}
