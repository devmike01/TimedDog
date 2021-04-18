package devmike.leviapps.co.timeddogx.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import devmike.leviapps.co.timeddogx.services.TimeOutService;

/**
 * Created by Gbenga Oladipupo on 2020-01-22.
 */
public abstract class TimeoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        TimeOutService.onTouch();
    }

    protected synchronized void touch(){
        TimeOutService.onTouch();
    }

}
