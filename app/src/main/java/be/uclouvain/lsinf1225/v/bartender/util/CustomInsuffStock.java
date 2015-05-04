package be.uclouvain.lsinf1225.v.bartender.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import be.uclouvain.lsinf1225.v.bartender.R;


public class CustomInsuffStock extends ArrayAdapter<String> {

    private final String[] nom;
    private final Context context;
    private final Double[] stockActuel;
    private final Double[] stockSeuil;
    public CustomInsuffStock(Activity context, String[] nom, Double[] stockActuel, Double[] stockSeuil){
        super(context, R.layout.list_basket, nom);
        this.nom=nom;
        this.context=context;
        this.stockActuel = stockActuel;
        this.stockSeuil = stockSeuil;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        View layout = RelativeLayout.inflate(context, R.layout.list_basket, null);
        TextView text = (TextView) layout.findViewById(R.id.txt_basket1);
        TextView text2 = (TextView) layout.findViewById(R.id.txt_basket2);
        text.setText(nom[position]);
        text2.setText(view.getResources().getText(R.string.stock_actuel) + " "+stockActuel[position]+"   "+view.getResources().getText(R.string.stock_seuil)+" "+stockSeuil[position] );
        return layout;
    }
}
