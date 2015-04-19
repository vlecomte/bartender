package be.uclouvain.lsinf1225.v.bartender.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Arrays;
import java.util.Comparator;
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
                            +", t."+COL_ICON+", d."+COL_TEXT+", d."+COL_IMAGE
                    +" FROM "+TABLE_PRODUCT+" p, "+TABLE_PRODUCT_DISPLAY_NAME+" pdn, "
                            +TABLE_TYPE+" t, "+TABLE_DESCRIPTION+" d"
                    +" WHERE p."+COL_PRODUCT_NAME+" = pdn."+COL_PRODUCT_NAME
                            +" AND pdn."+COL_LANGUAGE+" = ?"
                    +" AND p."+COL_TYPE_NAME+" = t."+COL_TYPE_NAME
                    +" AND p."+COL_DESCRIPTION_NAME+" = d."+COL_DESCRIPTION_NAME
                    +" ORDER BY p."+COL_TYPE_NAME,
                    new String[]{MyApp.getLanguage()});
            c.moveToFirst();

            int numProducts = c.getCount();
            sMenu = new Product[numProducts];
            sProductByName = new HashMap<>();

            for (int i = 0; i < numProducts; i++) {

                String name = c.getString(c.getColumnIndex(COL_PRODUCT_NAME));
                double price = c.getDouble(c.getColumnIndex(COL_PRICE));
                String displayName = c.getString(c.getColumnIndex(COL_PRODUCT_DISPLAY_NAME));
                String typeIconFilename = c.getString(c.getColumnIndex(COL_ICON));
                String descriptionFilename = c.getString(c.getColumnIndex(COL_TEXT));
                String imageFilename = c.getString(c.getColumnIndex(COL_IMAGE));

                Product product = new Product(name, displayName, price, typeIconFilename,
                        descriptionFilename, imageFilename);
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


            // TODO: remove this
            /*for (int i = 0; i < numProducts; i++) {
                Log.v("Listing products", sMenu[i].getName()+sMenu[i].getDescriptionFilename());
            }*/
            Log.v("Number of products", ""+numProducts);
        }
    }

    public static Product[] getMenu() {
        loadMenu();
        return sMenu;
    }

    public void sortByDisplayName(boolean increasing) {
        loadMenu();
        if (increasing) {
            Arrays.sort(sMenu, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    return lhs.getDisplayName().compareTo(rhs.getDisplayName());
                }
            });
        } else {
            Arrays.sort(sMenu, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    return rhs.getDisplayName().compareTo(lhs.getDisplayName());
                }
            });
        }
    }

    public void sortByPrice(boolean increasing) {
        loadMenu();
        if (increasing) {
            Arrays.sort(sMenu, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    return ((Double) lhs.getPrice()).compareTo(rhs.getPrice());
                }
            });
        } else {
            Arrays.sort(sMenu, new Comparator<Product>() {
                @Override
                public int compare(Product lhs, Product rhs) {
                    return ((Double) rhs.getPrice()).compareTo(lhs.getPrice());
                }
            });
        }
    }

    public static Product getByName(String name) {
        loadMenu();
        return sProductByName.get(name);
    }
}
