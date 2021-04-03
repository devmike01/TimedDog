package devmike.leviapps.co.sample;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import devmike.leviapps.co.timeddogx.interfaces.OnTimeOutCallback;
import devmike.leviapps.co.timeddogx.v2.TimedDog;

/**
 * Created by Gbenga Oladipupo on 2020-01-21.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //This should be moved to the base activity
        TimedDog.with(this).run((100*60) * 10, isForeground -> {
            //11: 39
            Log.d("AppApp", "ON BACKGROUND THREAD? "+isForeground);
            Toast.makeText(getApplicationContext(), "YOU HAVE BEEN LOGGED OUT", Toast.LENGTH_LONG).show();
        });
    }
}
