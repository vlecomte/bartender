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
import be.uclouvain.lsinf1225.v.bartender.model.Customer;
import be.uclouvain.lsinf1225.v.bartender.model.Detail;
import be.uclouvain.lsinf1225.v.bartender.model.Product;

public class DaoDetail {
    private static Map<Integer, Detail> sDetailById = new HashMap<>();

    private static Detail getOrCreate(int detailId, String productName) {
        if (!sDetailById.containsKey(detailId)) {
            sDetailById.put(detailId, new Detail(detailId, DaoProduct.getByName(productName)));
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
                cv.put(COL_DATE_ADDED, System.currentTimeMillis());
                db.insert(TABLE_DETAIL, null, cv);
            }
        }
    }

    public static List<Detail> getFromOrder(int orderNum) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_DETAIL,
                new String[]{COL_ID, COL_PRODUCT_NAME},
                COL_ORDER_NUM+" = ?", new String[]{""+orderNum},
                null, null, null);

        List<Detail> details = new ArrayList<>();

        while (c.moveToNext()) {
            int detailId = c.getInt(c.getColumnIndex(COL_ID));
            String productName = c.getString(c.getColumnIndex(COL_PRODUCT_NAME));
            details.add(getOrCreate(detailId, productName));
        }
        c.close();

        return details;
    }

    public static Map<Integer, List<Detail>> getOpenByTable() {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.rawQuery("SELECT d."+COL_ID+", d."+COL_PRODUCT_NAME+", o."+COL_TABLE_NUM
                        +" FROM "+TABLE_DETAIL+" d, "+TABLE_ORDER+" o"
                        +" WHERE d."+COL_DATE_DELIVERED+" IS NULL",
                new String[]{});

        Map<Integer, List<Detail>> openDetailsByTable = new HashMap<>();

        while (c.moveToNext()) {
            int detailId = c.getInt(c.getColumnIndex(COL_ID));
            String productName = c.getString(c.getColumnIndex(COL_PRODUCT_NAME));
            int tableNum = c.getInt(c.getColumnIndex(COL_TABLE_NUM));

            if (!openDetailsByTable.containsKey(tableNum)) {
                openDetailsByTable.put(tableNum, new ArrayList<Detail>());
            }
            openDetailsByTable.get(tableNum).add(getOrCreate(detailId, productName));
        }
        c.close();

        return openDetailsByTable;
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
        cv.put(COL_DATE_DELIVERED, System.currentTimeMillis());

        SQLiteDatabase db = MyApp.getWritableDb();
        db.update(TABLE_DETAIL, cv, whereClause.toString(), detailIds);
    }
}
