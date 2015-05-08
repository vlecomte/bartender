package be.uclouvain.lsinf1225.v.bartender.dao;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.model.Customer;
import be.uclouvain.lsinf1225.v.bartender.model.Detail;
import be.uclouvain.lsinf1225.v.bartender.model.Product;

public class DaoDetail {
    private static Map<Integer, Detail> sDetailById = new HashMap<>();
    private static Map<Integer, List<Detail>> sOpenByTable;

    private static Detail getOrCreate(int detailId, String productName, Date dateAdded) {
        if (!sDetailById.containsKey(detailId)) {
            sDetailById.put(detailId, new Detail(detailId, DaoProduct.getByName(productName),
                    dateAdded));
        }
        return sDetailById.get(detailId);
    }

    public static void create(int orderNum, Customer.Basket basket) {
        SQLiteDatabase db = MyApp.getWritableDb();

        // TODO: Do it in one request maybe?
        for (Map.Entry<Product, Integer> productGroup : basket.entrySet()) {
            Product product = productGroup.getKey();
            int number = productGroup.getValue();

            for (int i = 0; i < number; i++) {
                ContentValues cv = new ContentValues();
                cv.put(COL_ORDER_NUM, orderNum);
                cv.put(COL_PRODUCT_NAME, product.getName());
                cv.put(COL_DATE_ADDED, System.currentTimeMillis() / 1000);
                db.insert(TABLE_DETAIL, null, cv);
            }
        }
    }

    public static List<Detail> getFromOrder(int orderNum) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_DETAIL,
                new String[]{COL_ID, COL_PRODUCT_NAME, COL_DATE_ADDED},
                COL_ORDER_NUM+" = ?", new String[]{""+orderNum},
                null, null,
                COL_DATE_ADDED+", "+COL_PRODUCT_NAME);

        List<Detail> details = new ArrayList<>();

        while (c.moveToNext()) {
            int detailId = c.getInt(c.getColumnIndex(COL_ID));
            String productName = c.getString(c.getColumnIndex(COL_PRODUCT_NAME));
            Date dateAdded = new Date(c.getLong(c.getColumnIndex(COL_DATE_ADDED)) * 1000);
            details.add(getOrCreate(detailId, productName, dateAdded));
        }
        c.close();

        return details;
    }

    // Supposes getOpenByTable has been called before!
    public static List<Detail> getOpenAtTable(int tableNum) {
        return sOpenByTable.get(tableNum);
    }

    public static Map<Integer, List<Detail>> getOpenByTable() {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.rawQuery("SELECT d."+COL_ID+", d."+COL_PRODUCT_NAME+", d."+COL_DATE_ADDED
                                +", o."+COL_TABLE_NUM
                        +" FROM "+TABLE_DETAIL+" d, "+TABLE_ORDER+" o"
                        +" WHERE o."+COL_ORDER_NUM+" = d."+COL_ORDER_NUM
                                +" AND d."+COL_DATE_DELIVERED+" IS NULL"
                        +" ORDER BY d."+COL_DATE_ADDED+", d."+COL_PRODUCT_NAME,
                new String[]{});

        sOpenByTable = new HashMap<>();

        while (c.moveToNext()) {
            int detailId = c.getInt(c.getColumnIndex(COL_ID));
            String productName = c.getString(c.getColumnIndex(COL_PRODUCT_NAME));
            Date dateAdded = new Date(c.getLong(c.getColumnIndex(COL_DATE_ADDED)) * 1000);
            int tableNum = c.getInt(c.getColumnIndex(COL_TABLE_NUM));

            if (!sOpenByTable.containsKey(tableNum)) {
                sOpenByTable.put(tableNum, new ArrayList<Detail>());
            }
            sOpenByTable.get(tableNum).add(getOrCreate(detailId, productName, dateAdded));
        }
        c.close();

        return sOpenByTable;
    }

    public static void markDelivered(List<Detail> details) {
        StringBuilder whereClause = new StringBuilder();
        String[] detailIds = new String[details.size()];

        for (int i = 0; i < details.size(); i++) {
            if (i != 0) whereClause.append(" OR ");
            whereClause.append(COL_ID+" = ?");
            detailIds[i] = ""+details.get(i).getDetailId();
        }

        ContentValues cv = new ContentValues();
        cv.put(COL_DATE_DELIVERED, System.currentTimeMillis() / 1000);

        SQLiteDatabase db = MyApp.getWritableDb();
        db.update(TABLE_DETAIL, cv, whereClause.toString(), detailIds);
    }
}
