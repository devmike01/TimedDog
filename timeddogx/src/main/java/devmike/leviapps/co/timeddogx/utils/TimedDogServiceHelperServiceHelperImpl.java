package devmike.leviapps.co.timeddogx.utils;// Created by Gbenga Oladipupo(Devmike01) on 9/13/20.


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import devmike.leviapps.co.timeddogx.interfaces.OnTimeDogAppLifecycleListener;
import devmike.leviapps.co.timeddogx.interfaces.OnTimeOutCallback;
import devmike.leviapps.co.timeddogx.interfaces.TimedDogPreferences;
import devmike.leviapps.co.timeddogx.services.TimeOutService;
import devmike.leviapps.co.timeddogx.services.TimedDogExecutorService;
import devmike.leviapps.co.timeddogx.interfaces.TimedDogServiceHelper;

public class TimedDogServiceHelperServiceHelperImpl implements OnTimeDogAppLifecycleListener, LifecycleObserver, TimedDogServiceHelper {

    private Context context;

    public static final String EXTRA_IS_ONBACKGROUND ="devmike.leviapps.co.timeddogx.EXTRA_IS_ONBACKGROUND";

    private static Object timedDog = null;

    private OnTimeOutCallback onTimeOutCallback;

    private boolean isForeground;

    TimedDogPreferences timedDogPreferences;

    long timeOutMillis;

    static Object timedDogServiceHelperServiceHelper;


    private TimedDogServiceHelperServiceHelperImpl(Context context, long timeOutMillis, @Nullable OnTimeOutCallback onTimeOutCallback){
        this.context = context;
        this.timeOutMillis = timeOutMillis;
        this.onTimeOutCallback = onTimeOutCallback;
        this.timedDogPreferences = new TimedDogPreferencesImpl(context);
        startService();
        ProcessLifecycleOwner.get().getLifecycle().addObserver( this);
    }


    private TimedDogServiceHelperServiceHelperImpl(){

    }

    public static TimedDogServiceHelperServiceHelperImpl  getInstance(){
        if (timedDogServiceHelperServiceHelper == null){
            timedDogServiceHelperServiceHelper = new TimedDogServiceHelperServiceHelperImpl();
        }
        return ((TimedDogServiceHelperServiceHelperImpl)timedDogServiceHelperServiceHelper);
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
        if(timedDogPreferences.getWhatThread() == TimeOutService.BACKGROUND_THREAD){
            onTimeOutCallback.onTimeOut(false);
        }

        timedDogPreferences.setWhatThread(TimeOutService.FOREGROUND_THREAD);

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

    final boolean isForeground(){
        return isForeground;
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            TimeOutService.TimeOutBinder binder = ((TimeOutService.TimeOutBinder)service);
            TimeOutService timeOutService = binder.getService(TimedDogExecutorService.getInstance(), timeOutMillis,
                    new Handler.Callback() {
                @Override
                public boolean handleMessage(@NonNull Message message) {
                    Log.d("TimedDog", "App was killed by TimedDog = "+onTimeOutCallback);
                    if (null != onTimeOutCallback){
                        if(isForeground()) {
                            onTimeOutCallback.onTimeOut(true);
                        }else{
                            timedDogPreferences.setWhatThread(TimeOutService.BACKGROUND_THREAD);
                        }
                    }else{
                        //Kill the app
                        Log.d("TimedDog", "App was killed by TimedDog");
                        System.exit(0);
                    }
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



    @Override
    public void run(@NonNull Context context, long timeInMillis) {
        init(context, timeInMillis, null);
    }

    @Override
    public void run(@NonNull Context context, long timeInMillis, OnTimeOutCallback onTimeOutCallback) {
        init(context, timeInMillis, onTimeOutCallback);
    }

    void init(@NonNull Context context, long timeInMillis, OnTimeOutCallback onTimeOutCallback){
        new TimedDogServiceHelperServiceHelperImpl(context, timeInMillis, onTimeOutCallback);
    }
}