package be.uclouvain.lsinf1225.v.bartender.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;

import be.uclouvain.lsinf1225.v.bartender.R;


public class CustomListTable extends ArrayAdapter<String> {

    private final String[] table;
    private final Integer[] nbr;
    private final Context context;

    public CustomListTable(Activity context, String[] table, Integer[] nbr){
        super(context, R.layout.list_basket, table);
        this.table=table;
        this.nbr = nbr;
        this.context=context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        View layout = RelativeLayout.inflate(context, R.layout.list_basket, null);
        TextView text = (TextView) layout.findViewById(R.id.txt_basket1);
        TextView text2 = (TextView) layout.findViewById(R.id.txt_basket2);
        text.setText(table[position]);
        text2.setText(nbr[position] + context.getResources().getString(R.string.unMarketDetails));
        System.out.println(nbr[position]);
        return layout;
    }
}
