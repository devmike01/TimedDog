package devmike.leviapps.co.timeddogx.services;// Created by Gbenga Oladipupo(Devmike01) on 9/13/20.


import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import devmike.leviapps.co.timeddogx.interfaces.OnTimeDogAppLifecycleListener;
import devmike.leviapps.co.timeddogx.interfaces.TimedDogPreferences;
import devmike.leviapps.co.timeddogx.interfaces.TimedDogServiceHelper;
import devmike.leviapps.co.timeddogx.receivers.TimeOutReceiver;
import devmike.leviapps.co.timeddogx.utils.TimedDogPreferencesImpl;

public class TimedDog implements OnTimeDogAppLifecycleListener, LifecycleObserver, TimedDogServiceHelper {

    private Context context;

    public static final String EXTRA_LOGOUT_ACTIVITY ="devmike.leviapps.co.timeddogx.EXTRA_IS_ONBACKGROUND";


    private Class<FragmentActivity> activityClass;
    TimedDogPreferences timedDogPreferences;

    long timeOutMillis;

    static Object timedDogServiceHelperServiceHelper;
    private boolean isBound;
    Messenger mService = null;



    private void set(long timeOutMillis,
                    Class<FragmentActivity> activityClass){
        this.timeOutMillis = timeOutMillis;
        this.activityClass = activityClass;
        this.timedDogPreferences = new TimedDogPreferencesImpl(context);
        ProcessLifecycleOwner.get().getLifecycle().addObserver( this);
    }


    private TimedDog(Context context){
        this.context = context;
        bindService();
    }



    public static synchronized TimedDog with(Context context){
        if (timedDogServiceHelperServiceHelper == null){
            timedDogServiceHelperServiceHelper = new TimedDog(context);
        }
        return ((TimedDog)timedDogServiceHelperServiceHelper);
    }


    public void signOut(TimeOutReceiver.OnTimeOutReceiverCallback mOnTimeOutReceiverCallback){
        if (isBound){
            if(mOnTimeOutReceiverCallback != null) {
                Log.d("TimedDogServiceHel", "Binding service "+mOnTimeOutReceiverCallback);
                mOnTimeOutReceiverCallback.onTimeOutReceiver();
            }
            Message msg = Message.obtain(null, TimeOutService.MSG_SHUTDOWN_COUNTER);
            try {
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindService(){
        Log.d("TimedDogServiceHel", "Binding service "+isBound);
        Intent sIntent = new Intent(context, TimeOutService.class);
        if (!isBound){
            context.bindService(sIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        }
    }


    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        if (activityClass !=null){
            if( timedDogPreferences.isBackground()){
                TimeOutService.notifyOfTimeOut(context,
                        activityClass.getName());
            }
            beginCount();
            setIsBackgroundThread(false);
        }
    }

    @Override
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        setIsBackgroundThread(true);
    }

    synchronized void setIsBackgroundThread(boolean isBackground){
        if (mService == null)return;
        Message msg = Message.obtain(null, TimeOutService.MSG_CURRENT_THREAD, isBackground);
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    private void beginCount() {
        if (!isBound) return;
        BeginCountObject beginCountObject = new BeginCountObject();
        if(activityClass != null) {
            beginCountObject.setLogoutActivityClassName(activityClass.getName());
        }
        beginCountObject.setTimeOutInMillis(timeOutMillis);
        // Create and send a message to the service, using a supported 'what' value
        Message msg = Message.obtain(null, TimeOutService.MSG_BEGIN_COUNT, beginCountObject);
        try {
            mService.send(msg);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            isBound = true;
            beginCount();
            Log.d("TimedDog", "onServiceConnected by TimedDog = "+name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            mService = null;

            Log.d("onServiceDisconnected", name +" died");
        }

        @Override
        public void onBindingDied(ComponentName name) {

            Log.d("onServiceDisconnected", name +" died~");
        }
    };


    @Override
    public TimedDog monitor(long timeInMillis, Class<?> activityClass) {
        Class<FragmentActivity> mClass = null;
        try{
            mClass = (Class<FragmentActivity>) activityClass;
        }catch (Exception castException){
            castException.printStackTrace();
        }
        set(timeInMillis, mClass);
        return this;
    }

    public void setTimeOutListener(TimeOutReceiver.OnTimeOutReceiverCallback mOnTimeOutReceiverCallback){
        TimeOutReceiver.setOnTimeOutReceiverCallback(mOnTimeOutReceiverCallback);
    }

    @Override
    public TimedDog monitor(long timeInMillis) {
        monitor(timeInMillis, null);
        return this;
    }
}
