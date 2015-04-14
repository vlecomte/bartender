package be.uclouvain.lsinf1225.v.bartender.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

import be.uclouvain.lsinf1225.v.bartender.MyApp;
import be.uclouvain.lsinf1225.v.bartender.model.Ingredient;
import be.uclouvain.lsinf1225.v.bartender.model.Product;

public class DaoProduct {
    private static Product[] sMenu;
    private static Map<String, Product> sProductByName;

    public static synchronized void loadMenu() {

        if (sMenu == null) {
            SQLiteDatabase db = MyApp.getReadableDb();
            Cursor c = db.rawQuery(
                    "SELECT p."+COL_PRODUCT_NAME+", p."+COL_PRICE
                            +", pdn."+COL_PRODUCT_DISPLAY_NAME
                    +" FROM "+TABLE_PRODUCT+" p, "+TABLE_PRODUCT_DISPLAY_NAME+" pdn"
                    +" WHERE p."+COL_PRODUCT_NAME+" = pdn."+COL_PRODUCT_NAME
                    +" AND pdn."+COL_LANGUAGE+" = ?",
                    new String[]{MyApp.getLanguage()});
            c.moveToFirst();

            int numProducts = c.getCount();
            sMenu = new Product[numProducts];
            sProductByName = new HashMap<>();

            for (int i = 0; i < numProducts; i++) {

                String name = c.getString(c.getColumnIndex(COL_PRODUCT_NAME));
                double price = c.getDouble(c.getColumnIndex(COL_PRICE));
                String displayName = c.getString(c.getColumnIndex(COL_PRODUCT_DISPLAY_NAME));

                Product product = new Product(name, displayName, price);
                sMenu[i] = product;
                sProductByName.put(name, product);
                c.moveToNext();
            }
            c.close();

            c = db.query(TABLE_USAGE,
                    new String[]{COL_PRODUCT_NAME, COL_INGREDIENT_NAME, COL_QUANTITY},
                    null, null,
                    null, null, null);
            c.moveToFirst();

            int numUsages = c.getCount();
            for (int i = 0; i < numUsages; i++) {

                String productName = c.getString(c.getColumnIndex(COL_PRODUCT_NAME));
                String ingredientName = c.getString(c.getColumnIndex(COL_INGREDIENT_NAME));
                double quantity = c.getDouble(c.getColumnIndex(COL_QUANTITY));

                Product product = sProductByName.get(productName);
                Ingredient ingredient = DaoIngredient.getByName(ingredientName);
                product.addUsage(ingredient, quantity);

                c.moveToNext();
            }
            c.close();


            for (int i = 0; i < numProducts; i++) {
                Log.v("Listing products", sMenu[i].getName());
            }
        }
    }

    public static Product getByName(String name) {
        loadMenu();
        return sProductByName.get(name);
    }
}
