package devmike.leviapps.co.timeddogx.interfaces;// Created by Gbenga Oladipupo(Devmike01) on 4/3/21.
import devmike.leviapps.co.timeddogx.services.TimedDog;

public interface TimedDogServiceHelper {
    TimedDog monitor(long timeInMillis, Class<?> activityClass);
    TimedDog monitor(long timeInMillis);
}
