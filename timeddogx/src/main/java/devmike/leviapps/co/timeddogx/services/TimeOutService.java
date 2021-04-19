package devmike.leviapps.co.timeddogx.services;

// Created by Gbenga Oladipupo(Devmike01) on 9/12/20.


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.MutableLiveData;

import java.util.concurrent.CountDownLatch;

import devmike.leviapps.co.timeddogx.receivers.TimeOutReceiver;
import devmike.leviapps.co.timeddogx.utils.TimedDogPreferencesImpl;

import static devmike.leviapps.co.timeddogx.BuildConfig.IS_TESTING;

public class TimeOutService extends Service{

    private static final String TAG ="TimeOutService";
    public static final String BROADCAST_TIMEOUT ="devmike.leviapps.co.timeddogx.services.BROADCAST_TIMEOUT";
    private static long lastUsed;
    private final CountDownLatch testCountDownLatch = new CountDownLatch(1);
    Messenger mMessenger;
    public static final int MSG_BEGIN_COUNT = 1;
    public static final int MSG_SHUTDOWN_COUNTER = 10;
    public static final int MSG_CURRENT_THREAD = 100;

    /**
     * Handler of incoming messages from clients.
     */
    private static class IncomingHandler extends Handler implements TimeOutBinderImpl{
        private final Context applicationContext;
        private boolean isCancelled;
        final ExecutorServiceContract executorService;
        final TimedDogPreferencesImpl timedDogPreferences;
        boolean isRunning;
        boolean isBackground;
        private MutableLiveData<Boolean> signoutLiveData;

        protected IncomingHandler(Context context,ExecutorServiceContract executorService,
                                  TimedDogPreferencesImpl timedDogPreferences) {
            super(Looper.myLooper());
            this.executorService = executorService;
            this.timedDogPreferences = timedDogPreferences;
            this.signoutLiveData = new MutableLiveData<>();
            applicationContext = context.getApplicationContext();
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_BEGIN_COUNT) {
                BeginCountObject beginCountObject = (BeginCountObject) msg.obj;
                /*Only  start counting when we don't have any pending event. Otherwise stop.
                This is to prevent the counter from restarting when the app resumes from background
                */
                isCancelled = false;
                //Creating another thread for counter
                executorService.onRun(onStartCounting(beginCountObject.getTimeOutInMillis(),
                        beginCountObject.getLogoutActivityClassName()));
            }else if (msg.what == MSG_CURRENT_THREAD){
                this.isBackground = ((boolean)msg.obj);
            }else if(msg.what == MSG_SHUTDOWN_COUNTER){
                isCancelled =true;
                Log.d("TimedDog@!", "Shutting down service...");
            }else {
                super.handleMessage(msg);
            }
        }

        @Override
        public Runnable onStartCounting(long timeoutMillis, String logoutActivityName){
            return () -> {
                long idle;
                onTouch();
                isRunning = true;
                while (!isCancelled){
                    Log.d("TimeOutService", "counting");
                    idle = System.currentTimeMillis() - lastUsed;
                    SystemClock.sleep(1000);

                    //long timeOut = timeoutMillis;
                    if (idle >= timeoutMillis){
                        idle =0;
                        if (isBackground) {
                            timedDogPreferences.setWhatThread(true);
                        }else{
                            notifyOfTimeOut(applicationContext, logoutActivityName);
                        }

                        IncomingHandler.this.isCancelled =true;
                        isRunning = !isCancelled;
                    }

                }
            };


        }

        @Override
        public boolean isCancelled() {
            return false;
        }

    }
    protected static void notifyOfTimeOut(Context context, String logoutActivityName){

        final TimedDogPreferencesImpl timedDogPreferences = new TimedDogPreferencesImpl(context);
        Log.d("notifyOfTimeOut", "notifyOfTimeOut notified "+logoutActivityName);
        Intent broadcastIntent = new Intent(context, TimeOutReceiver.class);
        broadcastIntent.setAction(BROADCAST_TIMEOUT);
        if (logoutActivityName != null) {
            broadcastIntent.putExtra(TimedDog.EXTRA_LOGOUT_ACTIVITY, logoutActivityName);
            context.sendBroadcast(broadcastIntent);
        }else{
            timedDogPreferences.setWhatThread(false);
            Log.d("TimedDog", "App was killed by TimedDog");
            System.exit(419);
        }
    }


    @VisibleForTesting
    private void testMonitor(){
        if (IS_TESTING.get()) {
            try {
                testCountDownLatch.await();
            }catch (InterruptedException i){
                i.printStackTrace();
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        IncomingHandler incomingHandler = new IncomingHandler(this,
                TimedDogExecutorService.getInstance(), new TimedDogPreferencesImpl(getApplicationContext()));
        final String activityName = intent.getStringExtra(TimedDog.EXTRA_LOGOUT_ACTIVITY);
        mMessenger = new Messenger(incomingHandler);
        return mMessenger.getBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "MultiLog unbind!! ");
        return super.onUnbind(intent);
    }

    public static synchronized void onTouch() {
        Log.d(TAG, "MultiLog TOUCHED!! ");
        lastUsed = System.currentTimeMillis();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TimeOutService", "Received start id "  + ": ");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("TimeOutService", "Received start id " + startId + ": " + intent);
        return START_STICKY;
    }

    /**
     * Called by the system to notify a Service that it is no longer used and is being removed.  The
     * service should clean up any resources it holds (threads, registered
     * receivers, etc) at this point.  Upon return, there will be no more calls
     * in to this Service object and it is effectively dead.  Do not call this method directly.
     */
    @Override
    public void onDestroy() {
        //executorService.shutdown();
        Log.i("TimeOutService#", "TimeOutService has been destroyed");
    }

}
