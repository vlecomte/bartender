package be.uclouvain.lsinf1225.v.bartender.gui;

import android.app.Activity;
import android.os.Bundle;
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
        ArrayList<Ingredient> insufTab = DaoIngredient.getUnderCritical();
        name= new String[insufTab.size()];
        sActuel= new Double[insufTab.size()];
        sSeuil= new Double[insufTab.size()];
        for (int i = 0; i < insufTab.size(); i++) {
            name[i] = insufTab.get(i).getDisplayName();
            sActuel[i] = insufTab.get(i).getCurrent();
            sSeuil[i] = insufTab.get(i).getCritical();
        }
    }

}
