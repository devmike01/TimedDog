package devmike.leviapps.co.sample;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.test.espresso.IdlingResource;

import devmike.leviapps.co.timeddogx.activities.TimeoutActivity;
import devmike.leviapps.co.timeddogx.receivers.TimeOutReceiver;
import devmike.leviapps.co.timeddogx.services.TimeOutService;
import idlingresources.TimedDogIdlingResources;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends TimeoutActivity {

    private TimedDogIdlingResources idlingResources = new TimedDogIdlingResources();;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView resultTv = findViewById(R.id.text);
        resultTv.setText(R.string.logged_in_msg);

        //This should be moved to the base activity
    }

    @Override
    public void onResume() {
        super.onResume();
        //This should be moved to the base activity
    }



    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        return idlingResources;
    }


}
