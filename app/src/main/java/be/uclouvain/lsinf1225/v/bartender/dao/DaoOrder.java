package be.uclouvain.lsinf1225.v.bartender.dao;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import be.uclouvain.lsinf1225.v.bartender.MyApp;
import be.uclouvain.lsinf1225.v.bartender.model.Customer;
import be.uclouvain.lsinf1225.v.bartender.model.Detail;
import be.uclouvain.lsinf1225.v.bartender.model.Order;

public class DaoOrder {

    private static int getBiggestOrderNum() {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.rawQuery("SELECT MAX("+COL_ORDER_NUM+")"
                +" FROM"+TABLE_ORDER,
                new String[]{});

        c.moveToFirst();
        int biggestOrderNum = c.getInt(c.getColumnIndex(COL_ORDER_NUM));
        c.close();
        return biggestOrderNum;
    }

    public static Order create(Customer customer, int tableNum) {
        int orderNum = getBiggestOrderNum() + 1;
        SQLiteDatabase db = MyApp.getWritableDb();
        ContentValues cv = new ContentValues();
        cv.put(COL_ORDER_NUM, orderNum);
        cv.put(COL_TABLE_NUM, tableNum);
        if (customer != null) {
            cv.put(COL_CUSTOMER_USERNAME, customer.getUsername());
        }
        db.insert(TABLE_ORDER, null, cv);
        return new Order(orderNum, customer, tableNum);
    }

    public static Order createFor(int tableNum) {
        return create(null, tableNum);
    }

    public static Order getOpen(Customer customer) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_ORDER,
                new String[]{COL_ORDER_NUM, COL_TABLE_NUM},
                COL_CUSTOMER_USERNAME+" = ? AND "+COL_DATE_PAID+" = NULL",
                new String[]{customer.getUsername()},
                null, null, null);

        if (c.moveToFirst()) {
            int orderNum = c.getInt(c.getColumnIndex(COL_ORDER_NUM));
            int tableNum = c.getInt(c.getColumnIndex(COL_TABLE_NUM));
            Detail[] details = DaoDetail.getInOrder(orderNum);
            c.close();
            return new Order(orderNum, customer, tableNum, details);
        } else {
            c.close();
            return null;
        }
    }
}
