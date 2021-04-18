package devmike.leviapps.co.timeddogx.interfaces;// Created by Gbenga Oladipupo(Devmike01) on 4/18/21.


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import devmike.leviapps.co.timeddogx.services.TimeOutService;
import devmike.leviapps.co.timeddogx.services.TimedDog;
import devmike.leviapps.co.timeddogx.utils.TimedDogPreferencesImpl;

public class TimeOutReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        if (TimeOutService.BROADCAST_TIMEOUT.equals(intent.getAction())){
            TimedDogPreferencesImpl timedDogPreferences = new TimedDogPreferencesImpl(context);
            try {
                Intent activityIntent = new Intent(context,
                        Class.forName(intent.getStringExtra(TimedDog.EXTRA_LOGOUT_ACTIVITY)));
                activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(activityIntent);
                timedDogPreferences.setWhatThread(false);
            } catch (ClassNotFoundException e) {
                Toast.makeText(context, "LOGGGGGED OUT", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}
