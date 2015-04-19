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
            FileReader frea = new FileReader("\\app\\be.uclouvain.lsinf1225.v.bartender\\model\\maes.txt");
            reader = new BufferedReader(frea);
            lu= reader.readLine();
            while(lu!=null) {
            desc = desc + '\n' + lu;
            lu = reader.readLine();
            }
        } catch (IOException e){
            descText.setError("Erreur de lecture du fichier" + e.getMessage());
            descText.requestFocus();
            System.out.println("erreur de lecture"+ e.getMessage());
        } catch (NullPointerException e){
            System.out.println("erreur fichier");
            descText.setError("Fichier introuvabler" + e.getMessage());
            descText.requestFocus();
        }

        String indexFile = conso.getDescriptionFilename();
        indexFile = indexFile.substring(0,indexFile.length() -4);
        image.setImageResource(getResources().getIdentifier(indexFile, "drawable", getPackageName()));
        sstitre.setText(conso.getDisplayName());
        descText.setText(desc);
    }
}
