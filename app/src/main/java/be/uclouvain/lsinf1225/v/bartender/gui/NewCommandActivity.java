package be.uclouvain.lsinf1225.v.bartender.gui;

import be.uclouvain.lsinf1225.v.bartender.util.CustomList;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoProduct;
import be.uclouvain.lsinf1225.v.bartender.model.Product;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.lang.String;

public class NewCommandActivity extends Activity {
    ListView list;
    Product[] menu;
    String[] fileName;
    String[] productName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_command);
        menu = DaoProduct.getMenu();
        fileName = new String[menu.length];
        productName = new String[menu.length];

        for(int i=0;i<menu.length;i++){
            String nim=menu[i].getTypeIconFilename();
            fileName[i]= nim.substring(0, (nim.length())-4);
            productName[i] = menu[i].getDisplayName();
        }

        Integer[] ids = new Integer[menu.length];
        for(int i =0;i<menu.length;i++) {
            ids[i] = getResources().getIdentifier(fileName[i], "drawable", getPackageName()); //test avec "coca" à la place de filenName[i], ça fonctionne
        }
        CustomList adapter = new CustomList(this,productName,ids);
        list = (ListView) findViewById(R.id.list_consommation);
        list.setAdapter(adapter);
        setTitle(R.string.new_command_activity_title);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyApp.setDisplayedProduct(menu[position]);
                Intent intent = new Intent(NewCommandActivity.this, DescriptionActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar.
        getMenuInflater().inflate(R.menu.menu_new_command, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items.
        switch (item.getItemId()) {
            case R.id.action_show_basket:
                Intent intent = new Intent(this, BasketShowActivity.class);
                startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
