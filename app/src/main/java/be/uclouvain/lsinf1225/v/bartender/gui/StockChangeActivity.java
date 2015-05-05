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
    EditText quantite;
    Button ajouter;
    Button retirer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_change);
        ingredient = MyApp.getIngredientToChange();
        name_ingredient = (TextView) findViewById(R.id.txt_change_stock);
        stock_actuel = (TextView) findViewById(R.id.txt_actual_stock);
        quantite = (EditText) findViewById(R.id.edit_actual_stock);
        ajouter = (Button) findViewById(R.id.button1_actual_stock);
        retirer = (Button) findViewById(R.id.button2_actual_stock);


        name_ingredient.setText(ingredient.getDisplayName());
        stock_actuel.setText("Actuel: " + ingredient.getCurrent());

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = quantite.getText().toString();
                Double q= ingredient.getCurrent() + (double)Float.valueOf(t);
                if (q>ingredient.getMax()){

                }
                else{
                    DaoIngredient.setCurrent(ingredient.getDisplayName(),q);
                    ingredient = MyApp.getIngredientToChange();
                    stock_actuel.setText("Actuel: " + ingredient.getCurrent());
                }

            }
        });

        retirer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String t = quantite.getText().toString();
                Double q= ingredient.getCurrent() - (double)Float.valueOf(t);
                if (q<0){

                }
                else{
                    DaoIngredient.setCurrent(ingredient.getDisplayName(),q);
                    ingredient = MyApp.getIngredientToChange();
                    stock_actuel.setText("Actuel: " + ingredient.getCurrent());
                }
            }
        });


    }
}
