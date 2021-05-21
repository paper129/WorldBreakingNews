package leslie.worldbreakingnews;

/**
 * Created by Leslie on 5/21/2021.
 */

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashScreen_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EasySplashScreen launchScreen = new EasySplashScreen(this)
                .withFullScreen()
                .withTargetActivity(MainActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#F5F5F5"))
                .withLogo(R.drawable.launch_logo);




        View view = launchScreen.create();
        setContentView(view);
    }
}
