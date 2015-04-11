package be.uclouvain.lsinf1225.v.bartender.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import be.uclouvain.lsinf1225.v.bartender.model.User;

/**
 * Data access object for User.
 */
public class DaoUser {

    public static User create(String username, String password, String email, String rank,
                              String language) {
        SQLiteDatabase db = MyDbHelper.getWritableDb();
        ContentValues cv = new ContentValues();
        cv.put("Username", username);
        cv.put("Password", password);
        cv.put("Email", email);
        cv.put("Rank", rank);
        cv.put("Language", language);
        db.insert("User", null, cv);
        return new User(username, email, language);
    }

    public static boolean exists(String username) {
        SQLiteDatabase db = MyDbHelper.getReadableDb();
        Cursor c = db.query("User", new String[]{},
                "Username = ?", new String[]{username},
                null, null, null);
        boolean exists = c.getCount() > 0;
        c.close();
        return exists;
    }

    public static User attemptLogin(String username, String password) {
        SQLiteDatabase db = MyDbHelper.getReadableDb();
        Cursor c = db.query("User", new String[]{"Email", "Language"},
                "Username = ? AND Password = ?", new String[]{username, password},
                null, null, null);

        if (c.moveToFirst()) {
            String email = c.getString(c.getColumnIndex("Email"));
            String language = c.getString(c.getColumnIndex("Language"));
            c.close();
            return new User(username, email, language);
        } else {
            c.close();
            return null;
        }
    }

    public static boolean isEmailTaken(String email) {
        SQLiteDatabase db = MyDbHelper.getReadableDb();
        Cursor c = db.query("User", new String[]{},
                "Email = ?", new String[]{email},
                null, null, null);
        boolean taken = c.getCount() > 0;
        c.close();
        return taken;
    }

    public static void setEmail(String username, String email) {
        SQLiteDatabase db = MyDbHelper.getWritableDb();
        ContentValues cv = new ContentValues();
        cv.put("Email", email);
        db.update("User", cv, "Username = ?", new String[]{username});
    }

    public static void setLanguage(String username, String language) {
        SQLiteDatabase db = MyDbHelper.getWritableDb();
        ContentValues cv = new ContentValues();
        cv.put("Language", language);
        db.update("User", cv, "Username = ?", new String[]{username});
    }
}
