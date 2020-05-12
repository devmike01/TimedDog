package devmike.leviapps.co.timeddogx;


import java.util.PrimitiveIterator;

/**
 * Created by Gbenga Oladipupo on 12/05/2020.
 */
public class TimedResult{

    private boolean isBackground;

    public static TimedResult set(boolean isBackground){
        return new TimedResult(isBackground);
    }

    /**
     * @param isBackground is true when the is in the background
     */
    private TimedResult(boolean isBackground){
        this.isBackground = isBackground;
    }

    public boolean isBackground() {
        return isBackground;
    }

}
