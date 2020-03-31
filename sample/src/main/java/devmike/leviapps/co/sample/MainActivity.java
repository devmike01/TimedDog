package devmike.leviapps.co.sample;

import androidx.appcompat.app.AppCompatActivity;
import devmike.leviapps.co.timeddogx.TimedDogXWorker;
import devmike.leviapps.co.timeddogx.activities.TimeoutActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

public class MainActivity extends TimeoutActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new TimedDogXWorker.Builder(this)
                .seconds(10)
                .listener(new TimedDogXWorker.OnTimeOutListener() {
                    @Override
                    public void onTimeOut(boolean isBackground) {
                        Log.d("TimeOutDog_", "$_ " + isBackground);
                    }
                }).build();


    }


    @Override
    public void onResume(){
        Log.d("MAINACTIVITY","onResume()");

        super.onResume();
    }

}
