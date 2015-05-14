package be.uclouvain.lsinf1225.v.bartender.dao;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.model.Ingredient;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DaoIngredient {
    private static Ingredient[] sStock;
    private static Map<String, Ingredient> sIngredientByName;

    public static Ingredient getByName(String name) {
        loadStock();
        return sIngredientByName.get(name);
    }

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

            ArrayList<Ingredient> ingredientList = new ArrayList<>();
            sIngredientByName = new HashMap<>();

            while (c.moveToNext()) {
                String name = c.getString(c.getColumnIndex(COL_INGREDIENT_NAME));
                String displayName = c.getString(c.getColumnIndex(COL_INGREDIENT_DISPLAY_NAME));
                double currentStock = c.getDouble(c.getColumnIndex(COL_CURRENT_STOCK));
                String units = c.getString(c.getColumnIndex(COL_UNITS_DISPLAY_NAME));

                Double criticalStock = null;
                if (!c.isNull(c.getColumnIndex(COL_CRITICAL_STOCK))) {
                    criticalStock = c.getDouble(c.getColumnIndex(COL_CRITICAL_STOCK));
                }
                Double maxStock = null;
                if (!c.isNull(c.getColumnIndex(COL_CRITICAL_STOCK))) {
                    maxStock = c.getDouble(c.getColumnIndex(COL_MAX_STOCK));
                }

                Ingredient ingredient = new Ingredient(name, displayName, currentStock,
                        criticalStock, maxStock, units);
                ingredientList.add(ingredient);
                sIngredientByName.put(name, ingredient);
            }
            c.close();

            sStock = ingredientList.toArray(new Ingredient[ingredientList.size()]);
        }
    }

    public static void refreshStock() {
        loadStock();
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_INGREDIENT, new String[]{COL_INGREDIENT_NAME, COL_CURRENT_STOCK},
                null, null,
                null, null, null);

        while (c.moveToNext()) {
            String ingredientName = c.getString(c.getColumnIndex(COL_INGREDIENT_NAME));
            double stock = c.getDouble(c.getColumnIndex(COL_CURRENT_STOCK));
            getByName(ingredientName).refreshCurrent(stock);
        }
        c.close();
    }


    public static ArrayList<Ingredient> getInsufficient() {
        refreshStock();
        ArrayList<Ingredient> insufficient = new ArrayList<>();

        for (Ingredient ingredient : sStock) {
            if (ingredient.getCritical() != null
                    && ingredient.getRemaining() < ingredient.getCritical()) {
                insufficient.add(ingredient);
            }
        }
        return  insufficient;
    }
    public static Ingredient[] getStock() {
        return sStock;
    }

    public static void applyUsages() {
        loadStock();
        SQLiteDatabase db = MyApp.getWritableDb();

        // TODO: Do this in one command maybe?
        for (Ingredient ingredient : sStock) {
            // TODO: Add a tolerance
            if (ingredient.getCurrentUsage() > 0.0) {
                ingredient.applyUsage();

                ContentValues cv = new ContentValues();
                cv.put(COL_CURRENT_STOCK, ingredient.getCurrent());

                db.update(TABLE_INGREDIENT, cv,
                        COL_INGREDIENT_NAME+" = ?", new String[]{ingredient.getName()});
            }
        }
    }


    public static void setCurrent(String name, double stock) {
        ContentValues cv = new ContentValues();
        cv.put(COL_CURRENT_STOCK, stock);

        SQLiteDatabase db = MyApp.getWritableDb();
        db.update(TABLE_INGREDIENT, cv, COL_INGREDIENT_NAME+" = ?", new String[]{name});
    }

    public static void setCritical(String name, Double stock) {
        ContentValues cv = new ContentValues();
        if (stock == null) cv.putNull(COL_CRITICAL_STOCK);
        else cv.put(COL_CRITICAL_STOCK, stock);

        SQLiteDatabase db = MyApp.getWritableDb();
        db.update(TABLE_INGREDIENT, cv, COL_INGREDIENT_NAME+" = ?", new String[]{name});
    }

    public static void setMax(String name, Double stock) {
        ContentValues cv = new ContentValues();
        if (stock == null) cv.putNull(COL_MAX_STOCK);
        else cv.put(COL_MAX_STOCK, stock);

        SQLiteDatabase db = MyApp.getWritableDb();
        db.update(TABLE_INGREDIENT, cv, COL_INGREDIENT_NAME+" = ?", new String[]{name});
    }
}
