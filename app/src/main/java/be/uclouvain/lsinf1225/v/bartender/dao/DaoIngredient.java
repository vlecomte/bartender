package be.uclouvain.lsinf1225.v.bartender.dao;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

import be.uclouvain.lsinf1225.v.bartender.MyApp;
import be.uclouvain.lsinf1225.v.bartender.model.Ingredient;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

public class DaoIngredient {
    private static Ingredient[] sStock;
    private static Map<String, Ingredient> sIngredientByName;

    public static synchronized void loadStock() {
        if (sStock == null) {

            SQLiteDatabase db = MyApp.getReadableDb();
            Cursor c = db.rawQuery(
                    "SELECT i."+COL_INGREDIENT_NAME +", i."+COL_CURRENT_STOCK
                            +", i."+COL_CRITICAL_STOCK+", i."+COL_MAX_STOCK
                            +", idn."+COL_INGREDIENT_DISPLAY_NAME+", udn."+COL_UNITS_DISPLAY_NAME
                    +" FROM "+TABLE_INGREDIENT+" i, "+TABLE_INGREDIENT_DISPLAY_NAME+" idn, "
                            +TABLE_UNITS_DISPLAY_NAME+" udn"
                    +" WHERE i."+COL_INGREDIENT_NAME+" = idn."+COL_INGREDIENT_NAME
                            +" AND idn."+COL_LANGUAGE+" = ?"
                    +" AND i."+COL_UNITS+" = udn."+COL_UNITS
                            +" AND udn."+COL_LANGUAGE+" = ?",
                    new String[]{MyApp.getLanguage(), MyApp.getLanguage()});
            c.moveToFirst();

            int numIngredients = c.getCount();
            sStock = new Ingredient[numIngredients];
            sIngredientByName = new HashMap<>();
            for (int i = 0; i < numIngredients; i++) {

                String name = c.getString(c.getColumnIndex(COL_INGREDIENT_NAME));
                String displayName = c.getString(c.getColumnIndex(COL_INGREDIENT_DISPLAY_NAME));
                double currentStock = c.getDouble(c.getColumnIndex(COL_CURRENT_STOCK));
                double criticalStock = c.getDouble(c.getColumnIndex(COL_CRITICAL_STOCK));
                double maxStock = c.getDouble(c.getColumnIndex(COL_MAX_STOCK));
                String units = c.getString(c.getColumnIndex(COL_UNITS_DISPLAY_NAME));

                Ingredient ingredient = new Ingredient(name, displayName, currentStock,
                        criticalStock, maxStock, units);
                sStock[i] = ingredient;
                sIngredientByName.put(name, ingredient);
                c.moveToNext();
            }
            c.close();
        }
    }

    public static Ingredient getByName(String name) {
        loadStock();
        return sIngredientByName.get(name);
    }
}
