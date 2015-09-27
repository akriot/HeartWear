package com.clemed.heartwear;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.clemed.heartwear.server.RegistrationIntentService;

public class SplashActivity extends Activity {


    public static final String FB_USERNAME = "fb_username";
    public static final String FB_PASS = "fb_pass";
    public static final String CREDS_SET = "fb_creds_set";

    public static final String EXTRA_UN = "extra_un";
    public static final String EXTRA_P = "extra_p";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        boolean hasLoggedIn = sharedPreferences.getBoolean(CREDS_SET, false);

        if(!hasLoggedIn){
            startActivity(new Intent(this, FitBitLogin.class));
        }else{
            String username = sharedPreferences.getString(FB_USERNAME, "");
            String password = sharedPreferences.getString(FB_PASS, "");

            Intent intent = new Intent(this, NfcActivity.class);
            intent.putExtra(EXTRA_UN, username);
            intent.putExtra(EXTRA_P,password);
            startActivity(intent);
        }

        finish();
    }


}
