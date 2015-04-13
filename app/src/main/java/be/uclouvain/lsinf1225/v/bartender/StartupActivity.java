package be.uclouvain.lsinf1225.v.bartender;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StartupActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We choose the activity to start depending on whether a user is logged in.
        Class<? extends Activity> toStart;
        if (MyApp.isUserLoggedIn()) {
            toStart = MainActivity.class;
        } else {
            MyApp.readUserFromPreferences();
            if (MyApp.isUserLoggedIn()) {
                toStart = MainActivity.class;
            } else {
                toStart = LoginActivity.class;
            }
        }
        Intent intent = new Intent(this, toStart);
        startActivity(intent);
    }
}
