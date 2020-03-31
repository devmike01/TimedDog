package devmike.leviapps.co.sample;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.test.espresso.IdlingResource;
import devmike.leviapps.co.timeddogx.TimedDogXWorker;
import devmike.leviapps.co.timeddogx.activities.TimeoutActivity;
import idlingresources.TimedDogIdlingResources;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends TimeoutActivity {

    private TimedDogIdlingResources idlingResources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (idlingResources != null){
            idlingResources.setIdleState(false);
        }
        setContentView(R.layout.activity_main);
        final TextView resultTv = findViewById(R.id.text);
        resultTv.setText(R.string.logged_in_msg);

        new TimedDogXWorker.Builder(this)
                .seconds(10)
                .listener(new TimedDogXWorker.OnTimeOutListener() {
                    @Override
                    public void onTimeOut(boolean isBackground) {
                        LogoutActivity.start(MainActivity.this);
                        if(idlingResources != null) {
                            //MainActivity.this.isBackground = isBackground;
                            idlingResources.setIdleState(true);
                        }
                    }
                }).build();

    }


    @Override
    public void onResume(){
        super.onResume();
    }

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource(){
        if(idlingResources == null){
            idlingResources = new TimedDogIdlingResources();
        }
        return idlingResources;
    }

}
