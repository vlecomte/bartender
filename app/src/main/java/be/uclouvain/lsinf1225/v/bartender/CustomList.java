package be.uclouvain.lsinf1225.v.bartender;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String> {

    private final String[] nom;
    private final Integer[] imageId;
    private final Context context;

    public CustomList(Activity context, String[] nom, Integer[] imageId ){
        super(context, R.layout.list_view_component, nom);
        this.nom=nom;
        this.imageId =imageId;
        this.context=context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent){
        View layout = RelativeLayout.inflate(context, R.layout.list_view_component, null);
        TextView text = (TextView) layout.findViewById(R.id.txt);
        ImageView image = (ImageView) layout.findViewById(R.id.img);
        text.setText(nom[position]);
        image.setImageResource(imageId[position]);
        return layout;
    }
}
