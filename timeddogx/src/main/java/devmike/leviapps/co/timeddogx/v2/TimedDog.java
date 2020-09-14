package devmike.leviapps.co.timeddogx.v2;// Created by Gbenga Oladipupo(Devmike01) on 9/13/20.


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ProcessLifecycleOwner;

import devmike.leviapps.co.timeddogx.services.TimeOutService;
import devmike.leviapps.co.timeddogx.utils.TimeDogAppLifecycle;

public class TimedDog implements TimeDogAppLifecycle.OnTimeDogAppLifecycleListener  {

    private static TimedDog timedDog;

    private FragmentActivity activity;

    private boolean isBound =false;

    private TimeOutService timeOutService;

    TimedDog(FragmentActivity activity){
        this.activity = activity;
        this.timeOutService = new TimeOutService();
        ProcessLifecycleOwner.get().getLifecycle().addObserver( new TimeDogAppLifecycle(this));
    }

    public static void init(FragmentActivity activity){

    }

    @Override
    public void onResume() {
        final Intent sIntent = new Intent(activity, TimeOutService.class);
        activity.bindService(sIntent, serviceConnection, Context.BIND_IMPORTANT);
    }

    @Override
    public void onStop() {

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
