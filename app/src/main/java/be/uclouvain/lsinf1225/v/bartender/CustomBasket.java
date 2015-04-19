package be.uclouvain.lsinf1225.v.bartender;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class CustomBasket extends ArrayAdapter<String> {

    private final String[] nom;
    private final Context context;
    private final Double[] prix;

    public CustomBasket(Activity context, String[] nom, Double[] prix){
        super(context, R.layout.list_basket, nom);
        this.nom=nom;
        this.context=context;
        this.prix = prix;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        View layout = RelativeLayout.inflate(context, R.layout.list_basket, null);
        TextView text = (TextView) layout.findViewById(R.id.txt_basket1);
        TextView text2 = (TextView) layout.findViewById(R.id.txt_basket2);
        text.setText(nom[position]);
        text2.setText("==>" + prix[position].toString() + "€");
        return layout;
    }
}
