package be.uclouvain.lsinf1225.v.bartender.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoIngredient;
import be.uclouvain.lsinf1225.v.bartender.model.Ingredient;
import be.uclouvain.lsinf1225.v.bartender.util.CustomInsuffStock;
import java.util.ArrayList;

public class InsufStockActivity extends Activity {
    ListView listView;
     String[] name;
    Double[] sActuel;
    Double[] sSeuil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insuf_stock);
        updateTab();
        CustomInsuffStock customInsuffStock = new CustomInsuffStock(this, name,sActuel,sSeuil);
        listView = (ListView) findViewById(R.id.insuff_listView);
        listView.setAdapter(customInsuffStock);
    }

    public void updateTab(){
        ArrayList<Ingredient> insufTab = DaoIngredient.getInsufficient();
        name= new String[insufTab.size()];
        sActuel= new Double[insufTab.size()];
        sSeuil= new Double[insufTab.size()];
        if (insufTab==null) {
            name= new String[2];
            sActuel= new Double[2];
            sSeuil= new Double[2];
            name[0] = "prout";
            sActuel[0] = 0.0;
            sSeuil[0] = 2.0;
            name[1] = "frite";
            sActuel[1] = 0.0;
            sSeuil[1] = 2.0;
        }
        for (int i = 0; i < insufTab.size(); i++) {
            name[i] = insufTab.get(i).getDisplayName();
            sActuel[i] = insufTab.get(i).getCurrent();
            sSeuil[i] = insufTab.get(i).getCritical();
        }
    }

}
