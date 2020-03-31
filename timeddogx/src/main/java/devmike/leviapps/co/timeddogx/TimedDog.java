package devmike.leviapps.co.timeddogx;

import android.app.Application;

import androidx.lifecycle.ProcessLifecycleOwner;
import devmike.leviapps.co.timeddogx.utils.TimeDogAppLifecycle;

/**
 * Created by Gbenga Oladipupo on 2020-01-22.
 */
public class TimedDog {

    private static TimeDogAppLifecycle timeDogAppLifecycle;

    public static void init(Application application){
         if (timeDogAppLifecycle == null){
             timeDogAppLifecycle = new TimeDogAppLifecycle(application);
         }
        ProcessLifecycleOwner.get().getLifecycle().addObserver(timeDogAppLifecycle);
    }
}
