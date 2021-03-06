package be.uclouvain.lsinf1225.v.bartender.gui;

import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.model.Product;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;



public class DescriptionActivity extends Activity {
    private TextView descText;
    private TextView compte;
    private ImageView image;
    private Product conso;
    private BufferedReader reader;
    private String desc = "";
    private Button add;
    private Button remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        descText = (TextView) findViewById(R.id.grdtextdescr);
        image = (ImageView) findViewById(R.id.imgDesc);
        compte = (TextView) findViewById(R.id.compte_desc);
        conso = MyApp.getDisplayedProduct();
        add = (Button) findViewById(R.id.add_pro);
        remove = (Button) findViewById(R.id.rem_pro);
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
            descText.setError(getString(R.string.error_reading_desc_file) + " " + e.getMessage());
            descText.requestFocus();
        } catch (NullPointerException e){
            descText.setError(getString(R.string.error_desc_file_not_found) + " " + e.getMessage());
            descText.requestFocus();
        } catch (Exception e) {
            descText.setError(getString(R.string.error_reading_desc_file_impossible) + " " + e.getMessage());
            descText.requestFocus();
        }

        String indexFile = conso.getDescriptionFilename();
        add.setVisibility(View.INVISIBLE);
        updateCompte();
        updateAdd();
        indexFile = indexFile.substring(0,indexFile.length() -4);
        image.setImageResource(getResources().getIdentifier(indexFile, "drawable", getPackageName()));
        setTitle(conso.getDisplayName() + " - " + conso.getPrice() +"€");
        descText.setText(desc);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp.getCustomer().addToBasket(conso);
                Toast.makeText(DescriptionActivity.this, "+1 '" +conso.getDisplayName()+"'", Toast.LENGTH_LONG).show();
                updateAdd();
                updateCompte();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    MyApp.getCustomer().removeFromBasket(conso);
                    Toast.makeText(DescriptionActivity.this, "-1 '" +conso.getDisplayName()+"'", Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(DescriptionActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } finally {
                    updateAdd();
                    updateCompte();
                }
            }
        });
    }

    protected void updateCompte() {
        compte.setText(getString(R.string.basket_num_short) + " "
                + MyApp.getCustomer().getNumInBasket(conso) + "  ["
                + MyApp.getCustomer().getNumInBasket(conso) * conso.getPrice()+"€]");
    }

    protected void updateAdd() {
        int stock = conso.getNumAvailable();
        if(stock>0) {
            add.setVisibility(View.VISIBLE);
        } else {
            add.setVisibility(View.INVISIBLE);
            Toast.makeText(DescriptionActivity.this, getString(R.string.not_possible_to_order_anymore) + conso.getDisplayName(), Toast.LENGTH_LONG).show(); //
        }
    }
}
