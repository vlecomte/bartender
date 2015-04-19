package be.uclouvain.lsinf1225.v.bartender;

import be.uclouvain.lsinf1225.v.bartender.dao.DaoProduct;
import be.uclouvain.lsinf1225.v.bartender.model.Product;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
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
        setTitle(R.string.title_activity_new_command);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyApp.setDisplayedProduct(menu[position]);
                Intent intent = new Intent(NewCommandActivity.this, DescriptionActivity.class);
                startActivity(intent);
            }
        });
    }
}
