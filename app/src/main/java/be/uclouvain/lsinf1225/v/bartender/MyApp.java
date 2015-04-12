package be.uclouvain.lsinf1225.v.bartender;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import be.uclouvain.lsinf1225.v.bartender.model.User;

public class MyApp extends Application {

    private static App sApp;

    public static class App extends Application {

        private static final String DATABASE_NAME = "bartender.sqlite";
        private static final int DATABASE_VERSION = 1;

        private SQLiteAssetHelper mDbHelper;
        private User mCurrentUser;

        public App() {
            sApp = this;
        }

        private SQLiteAssetHelper getDbHelper() {
            if (mDbHelper == null) {
                mDbHelper = new SQLiteAssetHelper(this, DATABASE_NAME, null, DATABASE_VERSION);
            }
            return mDbHelper;
        }
    }

    public static SQLiteDatabase getReadableDb() {
        return sApp.getDbHelper().getReadableDatabase();
    }

    public static SQLiteDatabase getWritableDb() {
        return sApp.getDbHelper().getWritableDatabase();
    }

    public static User getCurrentUser() {
        return sApp.mCurrentUser;
    }

    public static void disconnectUser() {
        sApp.mCurrentUser = null;
    }

    public static void setCurrentUser(User user) {
        sApp.mCurrentUser = user;
    }
}
