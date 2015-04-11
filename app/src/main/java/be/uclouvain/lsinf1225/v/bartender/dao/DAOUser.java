package be.uclouvain.lsinf1225.v.bartender.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DAOUser {

    public static void create(String username, String password, String email, String rank,
                              String language) {
        SQLiteDatabase db = MyDbHelper.getWritableDb();
        ContentValues cv = new ContentValues(5);
        cv.put("Username", username);
        cv.put("Password", password);
        cv.put("Email", email);
        cv.put("Rank", rank);
        cv.put("Language", language);
        db.insert("User", null, cv);
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

    public static boolean isPasswordCorrect(String username, String password) {
        SQLiteDatabase db = MyDbHelper.getReadableDb();
        Cursor c = db.query("User", new String[]{},
                "Username = ? AND Password = ?", new String[]{username, password},
                null, null, null);
        boolean correct = c.getCount() > 0;
        c.close();
        return correct;
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
}
