package devmike.leviapps.co.timeddogx;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import devmike.leviapps.co.timeddogx.utils.TimeDogAppLifecycle;

/**
 * Created by Gbenga Oladipupo on 2019-12-27.
 */
public class TimedDogXWorker extends Worker implements Handler.Callback{

    private static final String TAG = "TimeOutDog";
    private boolean isCancelled;

    private static long lastUsed;

    public static final int FOREGROUND = -1;

    public static final int BACKGROUND =0;

    private final Handler handler;

    private static OnTimeOutListener onTimeOutListener;

    public interface OnTimeOutListener{
        void onTimeOut(boolean isForeground);
    }

    public TimedDogXWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        final HandlerThread thread = new HandlerThread("HandlerThread");
        thread.start();
        handler = new Handler(thread.getLooper(), this);
    }

    private static void startOneTime(Context context, OnTimeOutListener listener){
        onTimeOutListener = listener;
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(TimedDogXWorker.class).build();
        WorkManager.getInstance(context).enqueue(request);
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


            long timeOut = Builder.TIMEOUT;


            if (idle >= timeOut) {

                if (TimeDogAppLifecycle.getTimeDogAppLifecycleEvents().isForeground()) {
                    handler.sendEmptyMessage(FOREGROUND);


                    Log.d(TAG, "Time out in foreground!");
                } else {

                    handler.sendEmptyMessage(BACKGROUND);
                    Log.d(TAG, "Time out in background!");
                }

                setCancelled(true);
                idle = 0;
            }

        }

        return Result.success();
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

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        if (onTimeOutListener != null){
            onTimeOutListener.onTimeOut((msg.what <0));
        }
        return false;
    }

    public static class Builder{

        private Context context;

        private long milliseconds;

        public static long TIMEOUT =1;

        private OnTimeOutListener onTimeOutListener;


        public Builder(Context context){
            this.context = context;
        }

        public Builder minute(int minute){
            seconds(60 *minute);
            Log.d("TimeoutBuilder", "MINUTE");
            return this;
        }

        public Builder seconds(long seconds){
            milliseconds(seconds * 1000);
            Log.d("TimeoutBuilder", "SECONDS");
            return this;
        }

        public Builder milliseconds(long milliseconds){
            this.milliseconds = milliseconds;
            Log.d("TimeoutBuilder", "MILLISECONDS");
            return this;
        }

        public Builder listener(OnTimeOutListener onTimeOutListener){
            this.onTimeOutListener = onTimeOutListener;
            return this;
        }


        public Builder hours(int hours){
            minute((60 * 60) * hours);
            Log.d("TimeoutBuilder", "HOURS");
            return this;
        }

        public void build(){
            TIMEOUT = milliseconds;
            TimedDogXWorker.startOneTime(context, onTimeOutListener);
        }

    }
}