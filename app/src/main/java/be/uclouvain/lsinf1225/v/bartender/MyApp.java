package be.uclouvain.lsinf1225.v.bartender;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.Locale;

import be.uclouvain.lsinf1225.v.bartender.dao.DaoUser;
import be.uclouvain.lsinf1225.v.bartender.model.User;

public class MyApp extends Application {

    private static App sApp;

    public static class App extends Application {

        private static final String DATABASE_NAME = "bartender.sqlite";
        private static final int DATABASE_VERSION = 4;

        private SQLiteAssetHelper mDbHelper;
        private User mCurrentUser;

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

    public static User getCurrentUser() {
        return sApp.mCurrentUser;
    }

    public static void setCurrentUser(User user) {
        sApp.mCurrentUser = user;
        writeUserInPreferences();
    }

    public static boolean isUserLoggedIn() {
        return getCurrentUser() != null;
    }

    public static void logoutUser() {
        setCurrentUser(null);
    }

    public static String getLanguage() {
        return Locale.getDefault().getLanguage();
    }
}
