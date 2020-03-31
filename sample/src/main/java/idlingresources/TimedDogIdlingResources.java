package idlingresources;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.test.espresso.IdlingResource;

/**
 * Created by Gbenga Oladipupo on 2020-03-31.
 */
public class TimedDogIdlingResources implements IdlingResource {

    private AtomicBoolean isIdleNow = new AtomicBoolean(true);

    private ResourceCallback resourceCallback;

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    public void setIdleState(boolean isIdleNow){
        this.isIdleNow.set(isIdleNow);
        if(isIdleNow && resourceCallback != null){
            resourceCallback.onTransitionToIdle();
        }
    }
}
