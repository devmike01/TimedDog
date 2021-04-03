package devmike.leviapps.co.timeddogx.utils;// Created by Gbenga Oladipupo(Devmike01) on 4/3/21.


import android.content.Context;
import android.content.SharedPreferences;

import devmike.leviapps.co.timeddogx.interfaces.TimedDogPreferences;

public class TimedDogPreferencesImpl implements TimedDogPreferences {

    SharedPreferences preferences;

    private static final String APP ="devmike.leviapps.co.timeddogx.utils";

    private static final String PREF_TIMED_DOG =APP +".PREF_TIMED_DOG";

    private static final String PREF_WHAT_THREAD = APP+".PREF_TIMED_DOG";

    public TimedDogPreferencesImpl(Context context){
        preferences = context.getSharedPreferences(PREF_TIMED_DOG, Context.MODE_PRIVATE);
    }

    @Override
    public int getWhatThread() {
        return preferences.getInt(PREF_WHAT_THREAD, -1);
    }

    @Override
    public void setWhatThread(int whatThread) {
        backgroundToForeground(whatThread);
    }

    private void backgroundToForeground(int data) {
        preferences.edit().putInt(PREF_WHAT_THREAD, data).apply();
    }

}
