package be.uclouvain.lsinf1225.v.bartender.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoIngredient;
import be.uclouvain.lsinf1225.v.bartender.model.Ingredient;
import be.uclouvain.lsinf1225.v.bartender.util.CustomActualStock;
import java.util.ArrayList;

/**
 * Created by haroldsomers on 5/05/15.
 */
public class ActualStockActivity extends Activity {
    ListView listView;
    String[] name;
    Double[] sActuel;
    Double[] sSeuil;
    Double[] sMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_stock);
        updateTab();
        CustomActualStock customActualStock = new CustomActualStock(this, name,sActuel,sSeuil,sMax);
        listView = (ListView) findViewById(R.id.actual_listView);
        listView.setAdapter(customActualStock);
    }
    public void updateTab(){
        Ingredient[] ActualTab = DaoIngredient.getStock();
        name= new String[ActualTab.length];
        sActuel= new Double[ActualTab.length];
        sSeuil= new Double[ActualTab.length];
        sMax= new Double[ActualTab.length];
        for (int i = 0; i < ActualTab.length; i++) {
            name[i] = ActualTab[i].getDisplayName();
            sActuel[i] = ActualTab[i].getCurrent();
            sSeuil[i] = ActualTab[i].getCritical();
            sMax[i] = ActualTab[i].getMax();
        }
    }

}

