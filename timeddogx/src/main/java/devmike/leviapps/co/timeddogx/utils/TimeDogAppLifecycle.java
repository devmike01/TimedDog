package devmike.leviapps.co.timeddogx.utils;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * Created by Gbenga Oladipupo on 2020-02-15.
 */
public class TimeDogAppLifecycle implements LifecycleObserver {

    private static final String TAG ="TimeDogAppLifecycle";

    public interface OnTimeDogAppLifecycleListener{
        void onResume();
        void onStop();
    }

    private OnTimeDogAppLifecycleListener onTimeDogAppLifecycleListener;

    public TimeDogAppLifecycle(OnTimeDogAppLifecycleListener onTimeDogAppLifecycleListener){

        this.onTimeDogAppLifecycleListener = onTimeDogAppLifecycleListener;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void moveToForeground(){
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResumeMode(){
        Log.d(TAG, "Moved to foreground");
        onTimeDogAppLifecycleListener.onResume();
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void moveToBackground(){
        // appLifecycle.setBackground(true);
        Log.d(TAG, "Moved to background");
        onTimeDogAppLifecycleListener.onStop();
    }

}
