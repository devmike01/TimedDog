package devmike.leviapps.co.timeddogx.v2;// Created by Gbenga Oladipupo(Devmike01) on 9/13/20.


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import devmike.leviapps.co.timeddogx.services.TimeOutService;
import devmike.leviapps.co.timeddogx.services.TimedDogExecutorService;
import devmike.leviapps.co.timeddogx.utils.TimeDogAppLifecycle;

public class TimedDog implements TimeDogAppLifecycle.OnTimeDogAppLifecycleListener, LifecycleObserver {

    private final Context context;

    public static final String EXTRA_IS_ONBACKGROUND ="devmike.leviapps.co.timeddogx.EXTRA_IS_ONBACKGROUND";

    private static Object timedDog = null;

    private boolean isForeground;

    TimedDog(Context context){
        this.context = context;
        startService();
        ProcessLifecycleOwner.get().getLifecycle().addObserver( this);
    }

    public static void init(Context activity){
        if(timedDog == null){
            timedDog =new TimedDog(activity);
        }
    }

    private void startService(){
        final Intent sIntent = new Intent(context, TimeOutService.class);
        sIntent.putExtra(EXTRA_IS_ONBACKGROUND, true);
        context.startService(sIntent);
        context.bindService(sIntent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        setForeground(true);
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        setForeground(false);
    }

    void setForeground(boolean isForeground){
        this.isForeground = isForeground;
    }

    boolean isForeground(){
        return isForeground;
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimeOutService.TimeOutBinder binder = ((TimeOutService.TimeOutBinder)service);
            TimeOutService timeOutService = binder.getService(TimedDogExecutorService.getInstance(), new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message message) {
                    //if (get)
                    return false;
                }
            });
            timeOutService.onStartCounting();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("onServiceDisconnected", name +" moved to the background");
        }
    };
}
