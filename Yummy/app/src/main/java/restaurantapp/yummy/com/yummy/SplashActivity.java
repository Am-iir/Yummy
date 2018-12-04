package restaurantapp.yummy.com.yummy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import restaurantapp.yummy.com.yummy.helper.GlobalState;
import restaurantapp.yummy.com.yummy.helper.Constants;
import android.content.Intent;
import android.os.Handler;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();

         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                GlobalState state = GlobalState.singleton;

                if (state.getPrefsIsLoggedIn() != null && state.getPrefsIsLoggedIn().equals(Constants.STATE_TRUE)) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

                else{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
            }
        }, 1000);
    }

}
