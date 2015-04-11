package be.uclouvain.lsinf1225.v.bartender.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

/**
 * Creates a database from the pre-existing SQLite asset.
 */
public class MyDbHelper extends SQLiteAssetHelper {

    private static MyDbHelper sInstance;

    private static final String DATABASE_NAME = "bartender.sqlite";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NOT_INITIALIZED = "Database not initialized.";

    public static SQLiteDatabase getReadableDb() {
        if (sInstance == null) {
            throw new IllegalArgumentException(DATABASE_NOT_INITIALIZED);
        }
        return sInstance.getReadableDatabase();
    }

    public static SQLiteDatabase getWritableDb() {
        if (sInstance == null) {
            throw new IllegalArgumentException(DATABASE_NOT_INITIALIZED);
        }
        return sInstance.getWritableDatabase();
    }

    public static synchronized void init(Context context) {
        if (sInstance == null) {
            sInstance = new MyDbHelper(context.getApplicationContext());
        }
    }

    private MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
}
