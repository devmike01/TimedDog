package devmike.leviapps.co.timeddogx.services;// Created by Gbenga Oladipupo(Devmike01) on 9/12/20.


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.VisibleForTesting;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import devmike.leviapps.co.timeddogx.receivers.TimeOutReceiver;
import devmike.leviapps.co.timeddogx.utils.TimeDogAppLifecycle;

import static devmike.leviapps.co.timeddogx.BuildConfig.IS_TESTING;

public class TimeOutService extends Service  implements TimeOutBinderImpl{

    private final IBinder tBinder = new TimeOutBinder();

    private static final String TAG ="TimeOutService";

    private final long TIMEOUT_IN_MINUTES =5;

    private final int WHAT_THREAD =101;

    public static final String ACTION_THREAD ="devmike.leviapps.co.timeddogx.services.THREAD";

    private ExecutorServiceContract executorService;

    private static long lastUsed;
    private boolean isCancelled;
    private HandlerThread handlerThread;
    private final CountDownLatch testCountDownLatch = new CountDownLatch(1);

    private Handler.Callback callback;

    // The binder should call @{onStartCounting()} to start counting to the set duration
    @Override
    public void onStartCounting(){
        //testMonitor();
        executorService.onRun(runnable);
    }

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            long idle;
            onTouch();
            //Start waiting for the service
            //testCountDownLatch.countDown();
            while (!isCancelled){
                Log.d("TimeOutService", "counting");
                idle = System.currentTimeMillis() - lastUsed;
                SystemClock.sleep(1000);
                long timeOut =TIMEOUT_IN_MINUTES * (1000 *60);
                if (idle >= 10000){
                    //Has timed out!
                    System.out.println(timeOut +"<-- Time out!!! -> "+ idle);
                    idle =0;
                    notifyOfTimeOut();

                    TimeOutService.this.isCancelled =true;
                }

            }
        }
    };

    private void notifyOfTimeOut(){
        handlerThread = new HandlerThread("TimeoutHandler");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper(), callback);
        handler.sendEmptyMessage(WHAT_THREAD);
        handlerThread.quit();
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

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return tBinder;
    }

    public class TimeOutBinder extends Binder{


        /**
         * Class for clients to access.  Because we know this service always
         * runs in the same process as its clients, we don't need to deal with
         * IPC.
         */

        public TimeOutService getService( TimedDogExecutorService executorService, Handler.Callback callback){
            TimeOutService.this.executorService = executorService;
            TimeOutService.this.callback = callback;
            return TimeOutService.this;
        }

    }

    public static void onTouch() {
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
        executorService.shutdown();
        Log.i("TimeOutService", "TimeOutService has been destroyed");
    }

}
