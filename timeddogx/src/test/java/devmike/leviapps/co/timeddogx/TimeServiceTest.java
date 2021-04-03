package devmike.leviapps.co.timeddogx;// Created by Gbenga Oladipupo(Devmike01) on 9/27/20.

import android.content.Intent;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.rule.ServiceTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.robolectric.Shadows.shadowOf;

import devmike.leviapps.co.timeddogx.services.TimeOutBinderImpl;
import devmike.leviapps.co.timeddogx.services.TimeOutService;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.N)
public class TimeServiceTest {

    static {
        BuildConfig.IS_TESTING.set(true);
    }

    @Rule
    public ServiceTestRule serviceTestRule = new ServiceTestRule();


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
    public void testStartedService(){
        Intent serviceIntent = new Intent(ApplicationProvider.getApplicationContext(), TimeOutService.class);
        try {
            serviceTestRule.startService(serviceIntent);
        }catch (TimeoutException te){
            te.printStackTrace();
        }

    }

    private Intent getIntentService(){
        return new Intent(ApplicationProvider.getApplicationContext(), TimeOutService.class);
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
