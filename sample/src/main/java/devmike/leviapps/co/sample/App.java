package devmike.leviapps.co.sample;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import devmike.leviapps.co.timeddogx.utils.TimedDogServiceHelperServiceHelperImpl;
import devmike.leviapps.co.timeddogx.v2.TimedDog;

/**
 * Created by Gbenga Oladipupo on 2020-01-21
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //This should be moved to the base activity
        TimedDog.with(this).run( 1000 * 10, isForeground -> {
            Toast.makeText(this, "Logged out", Toast.LENGTH_LONG).show();
        }, LogoutActivity.class);
    }
}
