package be.uclouvain.lsinf1225.v.bartender;

import be.uclouvain.lsinf1225.v.bartender.model.Product;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.content.Intent;

import java.util.Map;

public class BasketShowActivity extends Activity {
    private ListView list;
    private TextView total;
    private Button button;
    private double count_total=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_show);
        Map<Product, Integer> basket_elem = MyApp.getCustomer().getBasket();
        String[] tab_elem = new String[basket_elem.size()];
        Double[] tab_prix_elem = new Double[basket_elem.size()];
        int i =0;
        for(Map.Entry<Product, Integer> entry : basket_elem.entrySet()){
            tab_elem[i] = entry.getKey().getDisplayName() + "["+ entry.getKey().getPrice()+"€]" + "  *  " + entry.getValue();
            tab_prix_elem[i] = entry.getValue() * entry.getKey().getPrice();
            count_total = count_total + tab_prix_elem[i];
            i++;
        }
        list = (ListView) findViewById(R.id.listView_basket);
        CustomBasket adapter = new CustomBasket(this, tab_elem, tab_prix_elem);
        list.setAdapter(adapter);
        setTitle(R.string.basket_title);
        total = (TextView) findViewById(R.id.total_basket);
        total.setText("TOTAL: "+count_total+"€");
        button = (Button) findViewById(R.id.confirm_basket_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //CONFIRMATION PANIER
                MyApp.getCustomer().clearBasket();
                Intent intent = new Intent(BasketShowActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(BasketShowActivity.this, R.string.confirmed_basket, Toast.LENGTH_LONG).show();

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_basket_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
