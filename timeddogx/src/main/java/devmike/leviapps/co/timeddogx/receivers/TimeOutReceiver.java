package devmike.leviapps.co.timeddogx.receivers;// Created by Gbenga Oladipupo(Devmike01) on 4/2/21.


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import devmike.leviapps.co.timeddogx.services.TimeOutService;

public class TimeOutReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(TimeOutService.ACTION_THREAD.equals(intent.getAction())){

        }
    }
}
