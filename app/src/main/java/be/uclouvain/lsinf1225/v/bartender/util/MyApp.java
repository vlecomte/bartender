package be.uclouvain.lsinf1225.v.bartender.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.Locale;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoUser;
import be.uclouvain.lsinf1225.v.bartender.model.Admin;
import be.uclouvain.lsinf1225.v.bartender.model.Customer;
import be.uclouvain.lsinf1225.v.bartender.model.Product;
import be.uclouvain.lsinf1225.v.bartender.model.Ingredient;
import be.uclouvain.lsinf1225.v.bartender.model.User;
import be.uclouvain.lsinf1225.v.bartender.model.Waiter;

public class MyApp extends Application {

    private static App sApp;

    public static class App extends Application {

        private static final String DATABASE_NAME = "bartender.sqlite";
        private static final int DATABASE_VERSION = 10;

        private SQLiteAssetHelper mDbHelper;
        private User mCurrentUser;

        /**
         * Product to be displayed by the product details activity.
         */
        private Product mDisplayedProduct;

        private Ingredient mIngredientToChange;

        public App() {
            sApp = this;
        }

        private synchronized SQLiteAssetHelper getDbHelper() {
            if (mDbHelper == null) {
                mDbHelper = new SQLiteAssetHelper(this, DATABASE_NAME, null, DATABASE_VERSION);
                mDbHelper.setForcedUpgrade();
            }
            return mDbHelper;
        }

        private void readUserFromPreferences() {

            SharedPreferences pref = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE);

            if (pref.contains(getString(R.string.saved_username))) {
                String username = pref.getString(getString(R.string.saved_username), null);
                String password = pref.getString(getString(R.string.saved_password), null);
                mCurrentUser = DaoUser.attemptLogin(username, password);
            } else {
                mCurrentUser = null;
            }
        }

        private void writeUserInPreferences() {

            SharedPreferences.Editor prefEditor = getSharedPreferences(
                    getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit();

            if (mCurrentUser == null) {
                prefEditor.remove(getString(R.string.saved_username));
                prefEditor.remove(getString(R.string.saved_password));
            } else {
                prefEditor.putString(getString(R.string.saved_username),
                        mCurrentUser.getUsername());
                prefEditor.putString(getString(R.string.saved_password),
                        mCurrentUser.getPassword());
            }
            prefEditor.apply();
        }
    }

    public static SQLiteDatabase getReadableDb() {
        return sApp.getDbHelper().getReadableDatabase();
    }

    public static SQLiteDatabase getWritableDb() {
        return sApp.getDbHelper().getWritableDatabase();
    }


    public static void readUserFromPreferences() {
        sApp.readUserFromPreferences();
    }

    public static void writeUserInPreferences() {
        sApp.writeUserInPreferences();
    }

    public static User getUser() {
        return sApp.mCurrentUser;
    }

    public static void setUser(User user) {
        sApp.mCurrentUser = user;
        writeUserInPreferences();
    }

    public static boolean isUserLoggedIn() {
        return getUser() != null;
    }

    public static void logoutUser() {
        setUser(null);
    }

    public static Customer getCustomer() {
        return (Customer) getUser();
    }

    public static Waiter getWaiter() {
        return (Waiter) getUser();
    }

    public static boolean isWaiter() {
        return getUser() instanceof Waiter;
    }

    public static boolean isAdmin() {
        return getUser() instanceof Admin;
    }


    public static Product getDisplayedProduct() {
        return sApp.mDisplayedProduct;
    }

    public static void setDisplayedProduct(Product product) {
        sApp.mDisplayedProduct = product;
    }


    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public static Ingredient getIngredientToChange() {return sApp.mIngredientToChange;}

    public static void setIngredientToChange(Ingredient ingredient) {sApp.mIngredientToChange = ingredient;}
}
