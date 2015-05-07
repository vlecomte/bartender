package be.uclouvain.lsinf1225.v.bartender.gui;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoIngredient;
import be.uclouvain.lsinf1225.v.bartender.model.Order;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.util.Refreshable;
import be.uclouvain.lsinf1225.v.bartender.util.TableFiller;
import be.uclouvain.lsinf1225.v.bartender.model.Ingredient;
/**
 * Created by haroldsomers on 5/05/15.
 */
public class StockChangeActivity extends Activity {
    Ingredient ingredient;
    TextView name_ingredient;
    TextView stock_actuel;
    TextView stock_critical;
    TextView stock_max;
    EditText quantite1;
    EditText quantite2;
    EditText quantite3;
    Button ajouter;
    Button retirer;
    Button modifier_max;
    Button modifier_critical;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_change);
        ingredient = MyApp.getIngredientToChange();
        name_ingredient = (TextView) findViewById(R.id.txt_change_stock);
        stock_actuel = (TextView) findViewById(R.id.txt_actual_stock);
        stock_critical = (TextView) findViewById(R.id.txt_critical_stock);
        stock_max = (TextView) findViewById(R.id.txt_max_stock);
        quantite1 = (EditText) findViewById(R.id.edit_actual_stock);
        quantite2 = (EditText) findViewById(R.id.edit_critical_stock);
        quantite3 = (EditText) findViewById(R.id.edit_max_stock);
        ajouter = (Button) findViewById(R.id.button1_actual_stock);
        retirer = (Button) findViewById(R.id.button2_actual_stock);
        modifier_critical = (Button) findViewById(R.id.button1_critical_stock);
        modifier_max = (Button) findViewById(R.id.button1_max_stock);


        name_ingredient.setText(ingredient.getDisplayName());
        stock_actuel.setText("Actuel: " + ingredient.getCurrent());
        stock_critical.setText("Seuil: " + ingredient.getCritical());
        stock_max.setText("Max: " + ingredient.getMax());

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = quantite1.getText().toString();
                Double q= ingredient.getCurrent() + (double)Float.valueOf(t);
                if (q>ingredient.getMax()){
                    ingredient.setCurrent(ingredient.getMax());
                    stock_actuel.setText("Actuel: " + ingredient.getCurrent());
                    Toast.makeText(StockChangeActivity.this, "Stock au max", Toast.LENGTH_LONG).show();
                }
                else{
                    ingredient.setCurrent(q);
                    ingredient = MyApp.getIngredientToChange();
                    stock_actuel.setText("Actuel: " + ingredient.getCurrent());
                }

            }
        });

        retirer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String t = quantite1.getText().toString();
                Double q= ingredient.getCurrent() - (double)Float.valueOf(t);
                if (q<0){
                    ingredient.setCurrent(0.0);
                    stock_actuel.setText("Actuel: " + ingredient.getCurrent());
                    Toast.makeText(StockChangeActivity.this, "Stock insuffisant", Toast.LENGTH_LONG).show();
                }
                else{
                    ingredient.setCurrent(q);
                    stock_actuel.setText("Actuel: " + ingredient.getCurrent());
                }
            }
        });

        modifier_critical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = quantite2.getText().toString();
                Double q= (double)Float.valueOf(t);
                ingredient.setCritical(q);
                stock_critical.setText("Seuil: " + ingredient.getCritical());
            }
        });

        modifier_max.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = quantite3.getText().toString();
                Double q= (double)Float.valueOf(t);
                ingredient.setMax(q);
                stock_max.setText("Max: " + ingredient.getMax());
            }
        });

    }
}
