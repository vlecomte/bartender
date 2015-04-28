package be.uclouvain.lsinf1225.v.bartender.gui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoProduct;


public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button displayMenuButton = (Button) findViewById(R.id.button_display_menu);
        displayMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewCommandActivity.class);
                startActivity(intent);
            }
        });

        Button showOrdersButton = (Button) findViewById(R.id.action_show_details);
        showOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ShowDetailsActivity.class);
                startActivity(intent);
            }
        });

        TextView loggedInText = (TextView) findViewById(R.id.text_logged_in);
        loggedInText.setText(getString(R.string.text_logged_in_as) + MyApp.getUser().getUsername());

        initButtonVisibilities(showOrdersButton);

        // TODO: Put this in an AsyncTask or something
        DaoProduct.loadMenu();
    }

    private void initButtonVisibilities(Button showOrdersButton) {
        if (!MyApp.isWaiter()) {
            showOrdersButton.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items.
        switch (item.getItemId()) {

            case R.id.action_logout:
                MyApp.logoutUser();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
