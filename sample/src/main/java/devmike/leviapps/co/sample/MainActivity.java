package devmike.leviapps.co.sample;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.test.espresso.IdlingResource;

import devmike.leviapps.co.timeddogx.activities.TimeoutActivity;
import devmike.leviapps.co.timeddogx.services.TimeOutService;
import devmike.leviapps.co.timeddogx.v2.TimedDog;
import idlingresources.TimedDogIdlingResources;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends TimeoutActivity {

    private TimedDogIdlingResources idlingResources = new TimedDogIdlingResources();;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView resultTv = findViewById(R.id.text);
        resultTv.setText(R.string.logged_in_msg);

        Intent start = new Intent(this, TimeOutService.class);
        //startService(start);

        idlingResources.setIdleState(false);

        //This should be moved to the base activity
        TimedDog.init(this);

    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        return idlingResources;
    }


    @Override
    protected void onTimeElapsed() {
        LogoutActivity.start(this);
    }


}
