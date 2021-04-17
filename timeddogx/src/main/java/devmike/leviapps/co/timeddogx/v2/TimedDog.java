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
    //Class<FragmentActivity> fragmentActivityClass;

    static Object timedDog = null;

    static  Class<FragmentActivity> fragmentActivityClass;

    private TimedDogServiceHelperServiceHelperImpl timedDogServiceHelperServiceHelper;

    TimedDog(Context context){
        this.context = context;
        timedDogServiceHelperServiceHelper = TimedDogServiceHelperServiceHelperImpl.with(context);
    }

    public static TimedDog with(Context context){

        if (timedDog == null){

            timedDog = new TimedDog(context);
        }
        return ((TimedDog)timedDog);
    }



    public void monitor( long timeInMillis, OnTimeOutCallback onTimeOutCallback, Class<?> activityClass) {

        try{
            fragmentActivityClass = ((Class<FragmentActivity>)activityClass);
        }catch (ClassCastException e){
            e.printStackTrace();
        }
        timedDogServiceHelperServiceHelper.monitor(timeInMillis, onTimeOutCallback, fragmentActivityClass);
    }



}
