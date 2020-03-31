package devmike.leviapps.co.timeddogx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import devmike.leviapps.co.timeddogx.TimedDogXWorker;

/**
 * Created by Gbenga Oladipupo on 2020-02-15.
 */
public class TimeDogAppLifecycle implements LifecycleObserver {

    private static final String TAG ="TimeDogAppLifecycle";

    private Context context;

    private SharedPreferences timedSharedPreference;

    private static TimeDogAppLifecycleEvents timeDogAppLifecycleEvents;

    public TimeDogAppLifecycle(Context context){
        this.context = context;
        timedSharedPreference =
                context.getSharedPreferences(TimedDogXWorker.BACKGROUND_TO_FOREGROUND,
                        Context.MODE_PRIVATE);
        timeDogAppLifecycleEvents = new TimeDogAppLifecycleEvents();
    }


    public static TimeDogAppLifecycleEvents getTimeDogAppLifecycleEvents() {
        return timeDogAppLifecycleEvents;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void moveToForeground(){
        //appLifecycle.setBackground(false);

        timeDogAppLifecycleEvents.setForeground(true);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResumeMode(){
        boolean isLogout = timedSharedPreference.getBoolean(TimedDogXWorker.LOGOUT_CHECK, false);
        if (isLogout){
           // TimedDogXWorker.getHandler().get().sendEmptyMessage(FOREGROUND);
            timedSharedPreference.edit().clear().apply();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void moveToBackground(){
        // appLifecycle.setBackground(true);
        Log.d(TAG, "Moved to background");
        timeDogAppLifecycleEvents.setForeground(false);
    }

}
