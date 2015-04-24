package be.uclouvain.lsinf1225.v.bartender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import be.uclouvain.lsinf1225.v.bartender.dao.DaoDetail;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoIngredient;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoProduct;


public class MainActivity extends Activity {
    private Button action_new_command;
    private Button action_show_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        action_new_command = (Button) findViewById(R.id.new_command_button);
        action_new_command.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MainActivity.this, NewCommandActivity.class);
                startActivity(intent2);
            }
        });

        action_show_details = (Button) findViewById(R.id.action_show_details);
        action_show_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent show = new Intent(MainActivity.this, ShowDetailsActivity.class);
                startActivity(show);
            }
        });

        updateShowDetails();

        TextView helloText = (TextView) findViewById(R.id.hello_text);
        helloText.setText("Hello " + MyApp.getUser().getUsername() + "!");

        // TODO: Put this in an AsyncTask or something
        DaoProduct.loadMenu();

        // TODO: Test to see if it crashes, to remove
        DaoIngredient.refreshStock();
        DaoDetail.getOpenByTable();
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

    private void updateShowDetails() {
    if(!MyApp.isWaiter()) {
        action_show_details.setVisibility(View.INVISIBLE);
    } else {
        action_show_details.setVisibility(View.VISIBLE);
        action_show_details.requestFocus();
    }
    }
}
