package devmike.leviapps.co.sample;

import android.app.Application;
import android.widget.Toast;

import devmike.leviapps.co.timeddogx.receivers.TimeOutReceiver;
import devmike.leviapps.co.timeddogx.services.TimedDog;


/**
 * Created by Gbenga Oladipupo on 2020-01-21
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        TimedDog.with(this).monitor((1000*6), LogoutActivity.class).setTimeOutListener(() -> {
            Toast.makeText(this, "LOGGGOEDU", Toast.LENGTH_LONG).show();
        });
    }
}
