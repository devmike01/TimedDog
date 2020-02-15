package devmike.leviapps.co.sample;

import androidx.appcompat.app.AppCompatActivity;
import devmike.leviapps.co.timeddogx.TimedDogXWorker;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        new TimedDogXWorker.Builder(this)
                .seconds(10)
                .listener(new TimedDogXWorker.OnTimeOutListener() {
                    @Override
                    public void onTimeOut(boolean isForeground) {
                        Log.d("MainActivity", isForeground +"___");
                    }
                }).build();
    }

}
