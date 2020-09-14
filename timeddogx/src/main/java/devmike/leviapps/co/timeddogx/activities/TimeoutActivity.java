package devmike.leviapps.co.timeddogx.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import devmike.leviapps.co.timeddogx.TimedDog;
import devmike.leviapps.co.timeddogx.TimedDogXWorker;
import devmike.leviapps.co.timeddogx.TimedResult;

/**
 * Created by Gbenga Oladipupo on 2020-01-22.
 */
public abstract class TimeoutActivity extends AppCompatActivity {

    protected abstract void onTimeElapsed();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        TimedDogXWorker.touch();
    }

}
