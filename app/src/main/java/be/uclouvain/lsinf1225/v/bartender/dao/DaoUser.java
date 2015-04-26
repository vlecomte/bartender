package be.uclouvain.lsinf1225.v.bartender.dao;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

import be.uclouvain.lsinf1225.v.bartender.MyApp;
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

    public static User create(String username, String password, String email) {
        ContentValues cv = new ContentValues();
        cv.put(COL_USERNAME, username);
        cv.put(COL_PASSWORD, password);
        cv.put(COL_EMAIL, email);
        cv.put(COL_RANK, RANK_CUSTOMER);

        SQLiteDatabase db = MyApp.getWritableDb();
        db.insert(TABLE_USER, null, cv);
        return new Customer(username, password, email);
    }

    public static User attemptLogin(String username, String password) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_USER, new String[]{COL_EMAIL, COL_RANK},
                COL_USERNAME +" = ? AND "+ COL_PASSWORD +" = ?", new String[]{username, password},
                null, null, null);

        if (c.moveToFirst()) {
            String email = c.getString(c.getColumnIndex(COL_EMAIL));
            String rank = c.getString(c.getColumnIndex(COL_RANK));
            c.close();
            Customer user;
            switch (rank)
            {
                case RANK_CUSTOMER:
                    user = new Customer(username, password, email);
                    break;
                case RANK_WAITER:
                    user = new Waiter(username, password, email);
                    break;
                case RANK_ADMIN:
                    user = new Admin(username, password, email);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid rank.");
            }
            return user;
        } else {
            c.close();
            return null;
        }
    }

    public static boolean isUsernameTaken(String username) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_USER, new String[]{},
                COL_USERNAME + " = ?", new String[]{username},
                null, null, null);
        boolean taken = c.getCount() > 0;
        c.close();
        return taken;
    }

    public static boolean isEmailTaken(String email) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_USER, new String[]{},
                COL_EMAIL +"= ?", new String[]{email},
                null, null, null);
        boolean taken = c.getCount() > 0;
        c.close();
        return taken;
    }

    public static void setPassword(String username, String newPassword) {
        ContentValues cv = new ContentValues();
        cv.put(COL_PASSWORD, newPassword);

        SQLiteDatabase db = MyApp.getWritableDb();
        db.update(TABLE_USER, cv, COL_USERNAME+" = ?", new String[]{username});
    }

    public static void setEmail(String username, String email) {
        ContentValues cv = new ContentValues();
        cv.put(COL_EMAIL, email);

        SQLiteDatabase db = MyApp.getWritableDb();
        db.update(TABLE_USER, cv, COL_USERNAME+" = ?", new String[]{username});
    }

    public static void setRank(String username, String rank) {
        ContentValues cv = new ContentValues();
        cv.put(COL_RANK, rank);

        SQLiteDatabase db = MyApp.getWritableDb();
        db.update(TABLE_USER, cv, COL_USERNAME+" = ?", new String[]{username});
    }
}
