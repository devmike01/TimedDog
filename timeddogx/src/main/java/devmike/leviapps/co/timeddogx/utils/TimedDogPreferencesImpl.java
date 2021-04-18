package devmike.leviapps.co.timeddogx.utils;// Created by Gbenga Oladipupo(Devmike01) on 4/3/21.


import android.content.Context;
import android.content.SharedPreferences;

import devmike.leviapps.co.timeddogx.interfaces.TimedDogPreferences;

public class TimedDogPreferencesImpl implements TimedDogPreferences {

    SharedPreferences preferences;

    private static final String APP ="devmike.leviapps.co.timeddogx.utils";

    private static final String PREF_TIMED_DOG =APP +".PREF_TIMED_DOG";

    private static final String PREF_IS_BACKGROUND = APP+".PREF_IS_BACKGROUND";

    public TimedDogPreferencesImpl(Context context){
        preferences = context.getSharedPreferences(PREF_TIMED_DOG, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isBackground() {
        return  preferences.getBoolean(PREF_IS_BACKGROUND, false);
    }

    @Override
    public void setWhatThread(boolean isBackground) {
        preferences.edit().putBoolean(PREF_IS_BACKGROUND, isBackground).apply();
    }
}
