package be.uclouvain.lsinf1225.v.bartender.dao;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.model.Detail;
import be.uclouvain.lsinf1225.v.bartender.model.Order;

public class DaoOrder {
    private static Map<Integer, Order> sOrderByNum = new HashMap<>();

    private static Order getWithDetails(int orderNum, String customerUsername, int tableNum) {
        Order order = sOrderByNum.get(orderNum);
        if (order != null) return order;

        List<Detail> details = DaoDetail.getFromOrder(orderNum);
        order = new Order(orderNum, customerUsername, tableNum, details);
        sOrderByNum.put(orderNum, order);
        return order;
    }

    private static int getBiggestOrderNum() {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.rawQuery("SELECT MAX("+COL_ORDER_NUM+")"
                +" FROM "+TABLE_ORDER,
                new String[]{});

        c.moveToFirst();
        int biggestOrderNum = c.getInt(c.getColumnIndex("MAX("+COL_ORDER_NUM+")"));
        c.close();
        return biggestOrderNum;
    }

    public static Order create(String customerUsername, int tableNum) {
        int orderNum = getBiggestOrderNum() + 1;

        ContentValues cv = new ContentValues();
        cv.put(COL_ORDER_NUM, orderNum);
        cv.put(COL_TABLE_NUM, tableNum);
        if (customerUsername != null) cv.put(COL_CUSTOMER_USERNAME, customerUsername);

        SQLiteDatabase db = MyApp.getWritableDb();
        db.insert(TABLE_ORDER, null, cv);

        Order order = new Order(orderNum, customerUsername, tableNum);
        sOrderByNum.put(orderNum, order);
        return order;
    }

    public static Order getOpen(String customerUsername) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_ORDER,
                new String[]{COL_ORDER_NUM, COL_TABLE_NUM},
                COL_CUSTOMER_USERNAME+" = ? AND "+COL_DATE_PAID+" IS NULL",
                new String[]{customerUsername},
                null, null, null);

        if (c.moveToFirst()) {
            int orderNum = c.getInt(c.getColumnIndex(COL_ORDER_NUM));
            int tableNum = c.getInt(c.getColumnIndex(COL_TABLE_NUM));
            c.close();
            return getWithDetails(orderNum, customerUsername, tableNum);
        } else {
            c.close();
            return null;
        }
    }

    public static Order getOpenOrCreateFor(int tableNum) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_ORDER,
                new String[]{COL_ORDER_NUM},
                COL_TABLE_NUM+" = ? AND "+COL_DATE_PAID+" IS NULL", new String[]{""+tableNum},
                null, null, null);

        if (c.moveToFirst()) {
            int orderNum = c.getInt(c.getColumnIndex(COL_ORDER_NUM));
            c.close();
            return getWithDetails(orderNum, null, tableNum);
        } else {
            c.close();
            return create(null, tableNum);
        }
    }

    public static List<Order> getAllOpen() {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_ORDER,
                new String[]{COL_ORDER_NUM, COL_CUSTOMER_USERNAME, COL_TABLE_NUM},
                COL_DATE_PAID+" IS NULL", new String[]{},
                null, null, null);

        ArrayList<Order> openOrders = new ArrayList<>();

        while (c.moveToNext()) {
            int orderNum = c.getInt(c.getColumnIndex(COL_ORDER_NUM));
            String customerUsername = null;
            if (!c.isNull(c.getColumnIndex(COL_CUSTOMER_USERNAME))) {
                customerUsername = c.getString(c.getColumnIndex(COL_CUSTOMER_USERNAME));
            }
            int tableNum = c.getInt(c.getColumnIndex(COL_TABLE_NUM));

            openOrders.add(getWithDetails(orderNum, customerUsername, tableNum));
        }
        c.close();

        return openOrders;
    }

    public static void setPaid(int orderNum) {
        ContentValues cv = new ContentValues();
        cv.put(COL_DATE_PAID, System.currentTimeMillis());

        SQLiteDatabase db = MyApp.getWritableDb();
        db.update(TABLE_ORDER, cv, COL_ORDER_NUM+" = ?", new String[]{""+orderNum});
    }

    public static boolean isPaid(int orderNum) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_ORDER,
                new String[]{COL_DATE_PAID},
                COL_ORDER_NUM+" = ?", new String[]{""+orderNum},
                null, null, null);

        c.moveToFirst();
        boolean paid = !c.isNull(c.getColumnIndex(COL_DATE_PAID));
        c.close();

        return paid;
    }
}
