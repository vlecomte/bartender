package be.uclouvain.lsinf1225.v.bartender;

import be.uclouvain.lsinf1225.v.bartender.model.Product;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class DescriptionActivity extends Activity {
    private TextView sstitre;
    private TextView descText;
    private ImageView image;
    private Product conso;
    private BufferedReader reader;
    private String desc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        sstitre = (TextView) findViewById(R.id.tV_descr);
        descText = (TextView) findViewById(R.id.grdtextdescr);
        image = (ImageView) findViewById(R.id.imgDesc);
        conso = MyApp.getDisplayedProduct();
        System.out.println(conso.getDescriptionFilename());
        try {
            String lu = "";
            String filename = conso.getDescriptionFilename();
            filename = filename.substring(0,filename.length() -4);
            int id = getResources().getIdentifier(filename,"raw",getPackageName());
            InputStream frea = getResources().openRawResource(id);
            reader = new BufferedReader(new InputStreamReader(frea));
            lu= reader.readLine();
            while(lu!=null) {
            desc = desc + '\n' + lu;
            lu = reader.readLine();
            }
        } catch (IOException e){
            descText.setError("Erreur de lecture du fichier" + e.getMessage());
            descText.requestFocus();
        } catch (NullPointerException e){
            descText.setError("Fichier introuvabler" + e.getMessage());
            descText.requestFocus();
        } catch (Exception e) {
            descText.setError("Erreur: lecture de fichier impossible" + e.getMessage());
            descText.requestFocus();
        }

        String indexFile = conso.getDescriptionFilename();
        indexFile = indexFile.substring(0,indexFile.length() -4);
        image.setImageResource(getResources().getIdentifier(indexFile, "drawable", getPackageName()));
        sstitre.setText("");
        setTitle(conso.getDisplayName() + " - " + conso.getPrice() +"€");
        descText.setText(desc);
    }
}
