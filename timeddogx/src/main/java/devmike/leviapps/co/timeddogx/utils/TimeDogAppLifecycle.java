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

    private static TimeDogAppLifecycleEvents timeDogAppLifecycleEvents;

    public TimeDogAppLifecycle(){
        timeDogAppLifecycleEvents = new TimeDogAppLifecycleEvents();
    }


    public static TimeDogAppLifecycleEvents getTimeDogAppLifecycleEvents() {
        return timeDogAppLifecycleEvents;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void moveToForeground(){
        //appLifecycle.setBackground(false);
        Log.d(TAG, "Moved to foreground");
        timeDogAppLifecycleEvents.setForeground(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void moveToBackground(){
        // appLifecycle.setBackground(true);
        Log.d(TAG, "Moved to background");
        timeDogAppLifecycleEvents.setForeground(false);
    }

}
