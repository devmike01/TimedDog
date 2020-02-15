package devmike.leviapps.co.timeddogx.utils;

/**
 * Created by Gbenga Oladipupo on 2020-02-15.
 */
public class TimeDogAppLifecycleEvents {

    private boolean isForeground;

    public void setForeground(boolean foreground) {
        isForeground = foreground;
    }

    public boolean isForeground() {
        return isForeground;
    }

}
