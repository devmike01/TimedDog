package devmike.leviapps.co.timeddogx.services;// Created by Gbenga Oladipupo(Devmike01) on 9/12/20.


import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimeOutService extends Service {

    private final IBinder tBinder = new TimeOutBinder();

    private static final String TAG ="TimeOutService";

    private final long TIMEOUT_IN_MINUTES =5;

    private ExecutorService mExecutorService;


    public synchronized void touch() {
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return tBinder;
    }

    public class TimeOutBinder extends Binder implements TimeOutBinderImpl{


        private long lastUsed;
        private boolean isCancelled;

        /**
         * Class for clients to access.  Because we know this service always
         * runs in the same process as its clients, we don't need to deal with
         * IPC.
         */
        public TimeOutService getService(){
            return TimeOutService.this;
        }

        // The binder should call @{onStartCounting()} to start counting to the set duration
        @Override
        public void onStartCounting() {
            mExecutorService.execute(() -> {
                long idle;
                touch();

                while (!isCancelled){
                    idle = System.currentTimeMillis() - lastUsed;
                    SystemClock.sleep(1000);

                    long timeOut =TIMEOUT_IN_MINUTES * (1000 *60);
                    if (idle >= timeOut){
                        //Has timed out!
                        System.out.println(timeOut +"<-- Time out!!! -> "+ idle);
                        idle =0;
                        this.isCancelled =true;
                    }

                }
            });
        }


        @Override
        public void onTouch() {
            Log.d(TAG, "MultiLog TOUCHED!! ");
            lastUsed = System.currentTimeMillis();
        }

        @Override
        public boolean isCancelled() {
            return isCancelled;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mExecutorService = Executors.newSingleThreadExecutor();
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
        mExecutorService.shutdown();
        Log.i("TimeOutService", "TimeOutService has been destroyed");
    }

}
