package be.uclouvain.lsinf1225.v.bartender;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import be.uclouvain.lsinf1225.v.bartender.dao.DaoIngredient;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoProduct;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView helloText = (TextView) findViewById(R.id.hello_text);
        helloText.setText("Hello " + MyApp.getCurrentUser().getUsername() + "!");

        // TODO: Put this in an AsyncTask or something
        DaoProduct.loadMenu();

        // TODO: Test to see if it crashes, to remove
        MyApp.getCurrentUser().addToBasket(DaoProduct.getMenu()[0]);
        MyApp.getCurrentUser().getNumInBasket(DaoProduct.getMenu()[0]);
        MyApp.getCurrentUser().removeFromBasket(DaoProduct.getMenu()[0]);
        DaoIngredient.refreshStock();
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
            case R.id.action_new_command:
                Intent intent2 = new Intent(this, NewCommandActivity.class);
                startActivity(intent2);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
