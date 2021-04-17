package devmike.leviapps.co.sample;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.test.espresso.IdlingResource;

import devmike.leviapps.co.timeddogx.activities.TimeoutActivity;
import devmike.leviapps.co.timeddogx.interfaces.OnTimeOutCallback;
import devmike.leviapps.co.timeddogx.services.TimeOutService;
import devmike.leviapps.co.timeddogx.v2.TimedDog;
import idlingresources.TimedDogIdlingResources;

import android.content.Intent;
import android.os.Bundle;
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
        TimedDog.with(this).start(this);
        idlingResources.setIdleState(false);
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


}
