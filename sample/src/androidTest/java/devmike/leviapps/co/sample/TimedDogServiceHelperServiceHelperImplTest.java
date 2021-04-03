package devmike.leviapps.co.sample;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Gbenga Oladipupo on 2020-03-31.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TimedDogServiceHelperServiceHelperImplTest {

    private IdlingResource mIdlingResource;

    private static final String TEST_STRING="You're now logged out";

    @Before
    public void setUp(){
        //final Intent intent = new Intent("devmike.leviapps.co.sample.MainActivity");
        final ActivityScenario<MainActivity> activityScenario =
                ActivityScenario.launch(MainActivity.class);

        activityScenario.onActivity(new ActivityScenario.ActivityAction<MainActivity>() {
            @Override
            public void perform(MainActivity activity) {
               TimedDogServiceHelperServiceHelperImplTest.this.mIdlingResource = activity.getIdlingResource();
                IdlingRegistry.getInstance().register(mIdlingResource);

            }
        });

    }


    @Test
    public void timeOutInForeground(){
       onView(withText(TEST_STRING)).check(matches(isDisplayed()));
    }

    @After
    public void unregisterIdlingResources(){
        if(mIdlingResource != null){
            IdlingRegistry.getInstance().unregister(mIdlingResource);
        }
    }
}
