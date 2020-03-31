package devmike.leviapps.co.sample;

import android.app.Activity;
import android.content.Intent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.intent.Intents.intended;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Gbenga Oladipupo on 2020-03-31.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TimedDogTest {

    private IdlingResource mIdlingResource;

    private MainActivity mainActivity;

    @Before
    public void setUp(){
        final ActivityScenario<MainActivity> activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
                mainActivity = activity;
                TimedDogTest.this.mIdlingResource = activity.getIdlingResource();

                IdlingRegistry.getInstance().register(mIdlingResource);
            }
        });

    }

    @Test
    public void timeOutInForeground(){
       // intended()
    }

    @After
    public void unregisterIdlingResources(){
        if(mIdlingResource != null){
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
