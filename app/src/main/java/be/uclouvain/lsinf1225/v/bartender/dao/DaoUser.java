package be.uclouvain.lsinf1225.v.bartender.dao;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;
import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.UserTable.*;

import be.uclouvain.lsinf1225.v.bartender.model.Admin;
import be.uclouvain.lsinf1225.v.bartender.model.Customer;
import be.uclouvain.lsinf1225.v.bartender.model.User;
import be.uclouvain.lsinf1225.v.bartender.model.Waiter;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Data access object for User.
 */
public class DaoUser {

    public static User create(String username, String password, String email, String language) {
        SQLiteDatabase db = MyDbHelper.getWritableDb();
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME, username);
        cv.put(COL_PASSWORD, password);
        cv.put(COL_EMAIL, email);
        cv.put(COL_RANK, RANK_CUSTOMER);
        cv.put(COL_LANGUAGE, language);
        db.insert(TABLE_USER, null, cv);
        return new Customer(username, email, language);
    }

    public static User attemptLogin(String username, String password) {
        SQLiteDatabase db = MyDbHelper.getReadableDb();
        Cursor c = db.query(TABLE_USER, new String[]{COL_EMAIL, COL_LANGUAGE, COL_RANK},
                COL_USERNAME +" = ? AND "+ COL_PASSWORD +" = ?", new String[]{username, password},
                null, null, null);

        if (c.moveToFirst()) {
            String email = c.getString(c.getColumnIndex(COL_EMAIL));
            String language = c.getString(c.getColumnIndex(COL_LANGUAGE));
            String rank = c.getString(c.getColumnIndex(COL_RANK));
            c.close();
            switch (rank)
            {
                case RANK_CUSTOMER:
                    return new Customer(username, email, language);
                case RANK_WAITER:
                    return new Waiter(username, email, language);
                case RANK_ADMIN:
                    return new Admin(username, email, language);
            }
            throw new IllegalArgumentException("Invalid rank.");
        } else {
            c.close();
            return null;
        }
    }

    public static boolean isUsernameTaken(String username) {
        SQLiteDatabase db = MyDbHelper.getReadableDb();
        Cursor c = db.query(TABLE_USER, new String[]{},
                COL_USERNAME + " = ?", new String[]{username},
                null, null, null);
        boolean taken = c.getCount() > 0;
        c.close();
        return taken;
    }

    public static boolean isEmailTaken(String email) {
        SQLiteDatabase db = MyDbHelper.getReadableDb();
        Cursor c = db.query(TABLE_USER, new String[]{},
                COL_EMAIL +"= ?", new String[]{email},
                null, null, null);
        boolean taken = c.getCount() > 0;
        c.close();
        return taken;
    }

    public static void setEmail(String username, String email) {
        SQLiteDatabase db = MyDbHelper.getWritableDb();
        ContentValues cv = new ContentValues();
        cv.put(COL_EMAIL, email);
        db.update(TABLE_USER, cv, COL_USERNAME+" = ?", new String[]{username});
    }

    public static void setLanguage(String username, String language) {
        SQLiteDatabase db = MyDbHelper.getWritableDb();
        ContentValues cv = new ContentValues();
        cv.put(COL_LANGUAGE, language);
        db.update(TABLE_USER, cv, COL_USERNAME+" = ?", new String[]{username});
    }
}
