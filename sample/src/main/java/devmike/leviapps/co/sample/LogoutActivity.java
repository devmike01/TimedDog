package devmike.leviapps.co.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by Gbenga Oladipupo on 2020-04-01.
 */
public class LogoutActivity extends AppCompatActivity {


    public static void start(Context context){
        context.startActivity(new Intent(context, LogoutActivity.class));
    }

    @Override
    public void onCreate(Bundle savedState){
        super.onCreate(savedState);
        LinearLayout linearLayout = new LinearLayout(this);
        TextView logoutTv = new TextView(this);
        logoutTv.setTextSize(20);
        logoutTv.setText(R.string.you_are_logged_out_msg);
        linearLayout.addView(logoutTv);
        linearLayout.setGravity(Gravity.CENTER);
        setContentView(linearLayout);
    }
}
