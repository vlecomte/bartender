package be.uclouvain.lsinf1225.v.bartender.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import be.uclouvain.lsinf1225.v.bartender.R;


public class CustomDetList extends ArrayAdapter<String> {

    private final String[] nom;
    private final Context context;

    public CustomDetList(Activity context, String[] nom){
        super(context, R.layout.detail_list,nom);
        this.nom=nom;
        this.context=context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        View layout = RelativeLayout.inflate(context, R.layout.detail_list, null);
        TextView text = (TextView) layout.findViewById(R.id.det_text);
        text.setText(nom[position]);
        return layout;
    }
}
