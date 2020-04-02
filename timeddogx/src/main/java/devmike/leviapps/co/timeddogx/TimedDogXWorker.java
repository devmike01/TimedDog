package devmike.leviapps.co.timeddogx;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import devmike.leviapps.co.timeddogx.utils.TimeDogAppLifecycle;

/**
 * Created by Gbenga Oladipupo on 2019-12-27.
 */
public class TimedDogXWorker extends Worker{

    private static final String TAG = "TimeOutDog";
    private boolean isCancelled;

    private static long lastUsed;

    public static final String LOGOUT_CHECK ="LOGOUT_CHECK";

    private static final int FOREGROUND_WHAT =1;

    public static final String BACKGROUND_TO_FOREGROUND ="_BACKGROUND_TO_FOREGROUND_STORE";

    private SharedPreferences sharedPreferences;

    public static long TIMEOUT =1;

    private static Handler handler;


    public interface OnTimeOutListener{
        void onTimeOut(boolean isBackground);
    }

    public TimedDogXWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        sharedPreferences = context.getSharedPreferences(BACKGROUND_TO_FOREGROUND,
                Context.MODE_PRIVATE);
    }


    private static void startOneTime(Context context, Builder builder){
        initHandler(builder);
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(TimedDogXWorker.class).build();
        WorkManager.getInstance(context).enqueue(request);
    }


    private static void initHandler(Builder builder){
        HandlerThread handlerThread = new HandlerThread("TimedDogHandler");
        handlerThread.start();
        handler =new Handler(handlerThread.getLooper(), builder);
    }


    @NonNull
    @Override
    public Result doWork() {
        long idle;
        touch();

        while (!isCancelled) {
            Log.d(TAG, "MultiLog USER OUT IMMEDIATELY IN____ ");
            idle = System.currentTimeMillis() - lastUsed;
            SystemClock.sleep(1000);

            if (idle >= TIMEOUT) {

                if (TimeDogAppLifecycle.getTimeDogAppLifecycleEvents().isForeground()) {
                    keepInBackground(false);
                    handler.sendEmptyMessage(FOREGROUND_WHAT);
                } else {
                    keepInBackground(true);
                }

                setCancelled(true);
                idle = 0;
            }

        }

        return Result.success();
    }


    private void keepInBackground(boolean isInBackground){
        sharedPreferences.edit().putBoolean(LOGOUT_CHECK, isInBackground).apply();
    }

    public static synchronized void touch() {
        Log.d(TAG, "MultiLog TOUCHED!! ");
        lastUsed = System.currentTimeMillis();
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }


    public static class Builder implements Handler.Callback {

        private Context context;

        private long milliseconds;

        private SharedPreferences sharedPreferences;

        private OnTimeOutListener onTimeOutListener;

        public Builder(Context context){
            this.context = context;
            sharedPreferences = context.getSharedPreferences(BACKGROUND_TO_FOREGROUND,
                            Context.MODE_PRIVATE);
        }

        public Builder minute(int minute){
            return seconds(60 *minute);
        }

        public Builder seconds(long seconds){
            return  milliseconds(seconds * 1000);
        }

        public Builder milliseconds(long milliseconds){
            this.milliseconds = milliseconds;
            return this;
        }

        public Builder listener(final OnTimeOutListener onTimeOutListener){
            this.onTimeOutListener = onTimeOutListener;
            boolean isBackground = sharedPreferences.getBoolean(LOGOUT_CHECK, false);
            if(isBackground){
                onTimeOutListener.onTimeOut(true);
                Log.d(TAG, "Fired from the background!");
            }
            return this;
        }

        public Builder hours(int hours){
            return  minute((60 * 60) * hours);
        }

        public void build(){
            TIMEOUT = milliseconds;
            TimedDogXWorker.startOneTime(context, this);
            //getHandler().sendEmptyMessage(BACKGROUND_WHAT);
        }

        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == FOREGROUND_WHAT) {
                onTimeOutListener.onTimeOut(false);
            }
            return false;
        }
    }
}