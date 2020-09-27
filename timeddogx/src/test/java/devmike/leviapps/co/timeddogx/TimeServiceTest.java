package devmike.leviapps.co.timeddogx;// Created by Gbenga Oladipupo(Devmike01) on 9/27/20.

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.test.core.app.ApplicationProvider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowService;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.robolectric.Shadows.shadowOf;

import devmike.leviapps.co.timeddogx.services.TimeOutBinderImpl;
import devmike.leviapps.co.timeddogx.services.TimeOutService;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.N)
public class TimeServiceTest {

    private ServiceController<TimeOutService> serviceServiceController;
    private TimeOutService mTimeOutService;
    private ShadowService mShadowService;

    @Before
    public void init(){
        serviceServiceController = Robolectric.buildService(TimeOutService.class);
        mTimeOutService = serviceServiceController.create().get();
        mShadowService = shadowOf(mTimeOutService);
    }

    @Test
    public void shouldReturnProperBinder(){
       final IBinder binder = mTimeOutService.onBind(getIntentService());
       assertNotNull(binder);
       assertTrue(TimeOutBinderImpl.class.isAssignableFrom(binder.getClass()));
    }

    private Intent getIntentService(){
        return new Intent(ApplicationProvider.getApplicationContext(), TimeOutService.class);
    }

    @Test
    public void testTimeout() throws InterruptedException{
        TimeOutBinderImpl binder = (TimeOutBinderImpl)mTimeOutService.onBind(getIntentService());
        assertNotNull(binder);
        assertFalse(binder.isCancelled());
        binder.onStartCounting();
        binder.onTouch();
        Thread.sleep(1000);
        assertTrue(binder.isCancelled());
    }


    @Test
    public void shouldAlwaysReturnTheSameBinder(){
        IBinder firstBinder = mTimeOutService.onBind(getIntentService());
        IBinder secondBinder = mTimeOutService.onBind(getIntentService());

        assertNotNull(firstBinder);
        assertNotNull(secondBinder);

        assertSame(firstBinder, secondBinder);
    }
}
