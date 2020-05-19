package devmike.leviapps.co.timeddogx;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.work.Data;
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

    private static final int WHAT_FOREGROUND =1;

    static final int WHAT_BACKGROUND =-1;

    static final String OUTPUT_FOREGROUND ="devmike.leviapps.co.timeddogx.OUTPUT_FOREGROUND";

    public static final String BACKGROUND_TO_FOREGROUND ="_BACKGROUND_TO_FOREGROUND_STORE";

    private SharedPreferences sharedPreferences;

    public interface OnTimeOutListener{
        void onTimeOut(boolean isBackground);
    }

    public TimedDogXWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        sharedPreferences = context.getSharedPreferences(BACKGROUND_TO_FOREGROUND,
                Context.MODE_PRIVATE);
    }


    @NonNull
    @Override
    public Result doWork() {
        long idle;
        touch();

        Data.Builder dataBuilder = new Data.Builder();

        long TIMEOUT = getInputData().getLong(TimedDog.ARG_TIMEOUT, 0);
        while (!isCancelled) {
            Log.d(TAG, "MultiLog USER OUT IMMEDIATELY IN____ ");
            idle = System.currentTimeMillis() - lastUsed;
            SystemClock.sleep(1000);

            if (idle >= TIMEOUT) {

                if (TimeDogAppLifecycle.getTimeDogAppLifecycleEvents()
                        .isForeground()) {
                    keepInBackground(false);
                    dataBuilder.putBoolean(OUTPUT_FOREGROUND, true);
                    //timeoutLiveData.postValue(WHAT_FOREGROUND);
                }else{
                    keepInBackground(true);
                }


                setCancelled(true);
                idle = 0;

                return Result.success(dataBuilder.build());
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


        public static class Builder{

        private Context context;

        private long timeInMilliseconds;

        private static long milliseconds = 1000;

        private SharedPreferences sharedPreferences;

        @Deprecated
        public Builder(Context context){
            this.context = context;
            sharedPreferences = context.getSharedPreferences(BACKGROUND_TO_FOREGROUND,
                            Context.MODE_PRIVATE);

        }

        public Builder minute(int minute){
            this.timeInMilliseconds += (60 * milliseconds) * minute;
            return this;
        }

        public Builder seconds(long seconds){
            this.timeInMilliseconds += (seconds * milliseconds);
            return this;
        }

        public Builder milliseconds(long milliseconds){
            this.timeInMilliseconds += milliseconds;
            return this;
        }

        public Builder listener(final OnTimeOutListener onTimeOutListener){
            boolean isBackground = sharedPreferences.getBoolean(LOGOUT_CHECK, false);
            if(isBackground){

                onTimeOutListener.onTimeOut(true);
                Log.d(TAG, "Fired from the background!");
            }
            return this;
        }

        public Builder hours(int hours){
            this.timeInMilliseconds += ((60* 60) * milliseconds) * hours;
            return this;
        }

        @Deprecated
        public void build() {
            if (context instanceof FragmentActivity) {
                TimedDog
                        .with(((FragmentActivity)context).getApplication())
                        .duration(timeInMilliseconds)
                        .start();
            }
        }

    }
}