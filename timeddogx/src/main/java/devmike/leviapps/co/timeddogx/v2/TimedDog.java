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

    private Context activity;

    private boolean isBound =false;

    private TimeOutService timeOutService;

    public static final String EXTRA_IS_ONBACKGROUND ="devmike.leviapps.co.timeddogx.EXTRA_IS_ONBACKGROUND";


    TimedDog(Context activity){
        this.activity = activity;
        this.timeOutService = new TimeOutService();
        ProcessLifecycleOwner.get().getLifecycle().addObserver( new TimeDogAppLifecycle(this));
       // timeOutService.onStartCounting();
    }

    public static void init(Context activity){
        if(timedDog == null){
            timedDog =new TimedDog(activity);
        }
    }

    @Override
    public void onResume() {
        final Intent sIntent = new Intent(activity, TimeOutService.class);
        sIntent.putExtra(EXTRA_IS_ONBACKGROUND, true);
        activity.bindService(sIntent, serviceConnection, Context.BIND_IMPORTANT);
    }

    @Override
    public void onStop() {
        activity.unbindService(serviceConnection);
        isBound = false;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimeOutService.TimeOutBinder binder = ((TimeOutService.TimeOutBinder)service);
            timeOutService = binder.getService();
            isBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound= false;
        }
    };
}
