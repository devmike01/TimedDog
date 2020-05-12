package devmike.leviapps.co.sample;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.test.espresso.IdlingResource;
import devmike.leviapps.co.timeddogx.TimedDog;
import devmike.leviapps.co.timeddogx.TimedDogXWorker;
import devmike.leviapps.co.timeddogx.activities.TimeoutActivity;
import idlingresources.TimedDogIdlingResources;

import android.app.Instrumentation;
import android.content.pm.InstrumentationInfo;
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

        idlingResources.setIdleState(false);

        Log.d("MainActivity#1", "idlingResources == NULL");

        //This should be moved to the base activity
        TimedDog.with(this.getApplication()).duration(10000)
                .start().getTimedResultLiveData().observe(this, isBackground -> {
                    LogoutActivity.start(this);
        });
        /*new TimedDogXWorker.Builder(this)
                .seconds(10)
                .listener(new TimedDogXWorker.OnTimeOutListener() {
                    @Override
                    public void onTimeOut(boolean isBackground) {
                        //LogoutActivity.start(App.this);
                        Log.d("sdds", "HELLLOOOW");
                        onTimeElapsed();
                    }
                }).build();*/

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
