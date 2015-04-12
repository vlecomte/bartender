package be.uclouvain.lsinf1225.v.bartender;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView helloText = (TextView) findViewById(R.id.hello_text);
        helloText.setText("Hello " + MyApp.getCurrentUser().getUsername() + "!");
    }
}
