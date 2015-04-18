package be.uclouvain.lsinf1225.v.bartender;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


public class NewCommandActivity extends Activity {
    ListView list;
    String[] nom = {
            "Chimay bleue",
            "Maes",
            "Jupiler"};

    Integer[] img = {
            R.drawable.chimaybl,
            R.drawable.maes,
            R.drawable.jupiler};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_command);

        CustomList adapter = new CustomList(this, nom, img);
        list = (ListView) findViewById(R.id.list_consommation);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(NewCommandActivity.this, "Conso sélectionnée " + nom[+position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
